/* Copyright (C) 2016 Advanced Digital Science Centre

        * This file is part of Soft-Grid.
        * For more information visit https://www.illinois.adsc.com.sg/cybersage/
        *
        * Soft-Grid is free software: you can redistribute it and/or modify
        * it under the terms of the GNU General Public License as published by
        * the Free Software Foundation, either version 3 of the License, or
        * (at your option) any later version.
        *
        * Soft-Grid is distributed in the hope that it will be useful,
        * but WITHOUT ANY WARRANTY; without even the implied warranty of
        * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        * GNU General Public License for more details.
        *
        * You should have received a copy of the GNU General Public License
        * along with Soft-Grid.  If not, see <http://www.gnu.org/licenses/>.

        * @author Prageeth Mahendra Gunathilaka
*/
package it.illinois.adsc.ema.control.ied;

import it.illinois.adsc.ema.control.LogEventListener;
import it.illinois.adsc.ema.control.ied.pw.PWModelDetails;
import it.illinois.adsc.ema.pw.ied.IedControlerFactory;
import it.illinois.adsc.ema.softgrid.common.ConfigUtil;
import it.illinois.adsc.ema.softgrid.common.IEDLogFormatter;
import it.illinois.adsc.ema.softgrid.common.ied.IedControlAPI;
import it.illinois.adsc.ema.softgrid.common.ied.data.ParameterGenerator;
import org.openmuc.openiec61850.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * @author Prageeth Mahendra
 *         This is the IED Server. this server supports only one socket address
 */
public class SmartPowerIEDServer implements ServerEventListener {
    private static Logger logger = null;
    private static LogEventListener logEventListener = null;
    private ServerSap serverSap = null;
    private String ipAddress;
    private static int PW_INTERROGATION_INTERVAL = 100;
    private volatile static int IED_COUNT = 0;
    private IEDType type;
    private int id;
    private boolean serverStoped = false;
    private static IedControlAPI controlAPI = null;
    //  public static boolean MANUAL_EXPERIMENT_MODE = false;
    private boolean serverStarted = false;


    public SmartPowerIEDServer() {
        super();
        synchronized (this) {
            if (logger == null) {
                initLogger();
            }
        }
    }

    @Override
    public void serverStoppedListening(ServerSap serverSap) {
        logger.severe("The SAP stopped listening");
    }

    @Override
    public List<ServiceError> write(List<BasicDataAttribute> bdas) {
        if (ConfigUtil.MANUAL_EXPERIMENT_MODE) {
            for (BasicDataAttribute bda : bdas) {
                logger.info("got a write request: " + bda);
            }
        }
        return null;
    }

    /**
     * To start the IED server
     *
     * @param pwModelDetails
     * @throws IOException
     */
    public void startServer(PWModelDetails pwModelDetails) {
        try {
            List<ServerSap> serverSaps = null;
            try {
                serverSaps = ServerSap.getSapsFromSclFile(pwModelDetails.getSclFileName());
            } catch (SclParseException e) {
                logger.severe("Error parsing SCL/ICD file: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            serverSap = serverSaps.get(0);
            serverSap.setPort(pwModelDetails.getPortNumber());

            InetAddress address = null;
            try {
                ipAddress = pwModelDetails.getIpAddress();
                address = InetAddress.getByName(ipAddress);
            } catch (UnknownHostException e) {
                logger.severe("Unknown host: " + ipAddress);
                logger.info("Proxy will run with the defualt IP as define in the SCL file.");
                logger.info("Unknown host " + ipAddress);
                return;
            }
            if (address != null) {
                serverSap.setBindAddress(address);
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (serverSap != null) {
                        serverSap.stop();
                    }
                    logger.info("Server was stopped");
                }
            });
            ServerModel serverModel = serverSap.getModelCopy();
//          create a SampleServer instance that can be passed as a callback object to startListening() and
//          SmartPowerIEDServer sampleServer = new SmartPowerIEDServer();
            SmartPowerIEDServer sampleServer = this;
//          Open MUC initialization
            List<BasicDataAttribute> branchCircuitBreakerVals = new ArrayList<BasicDataAttribute>(3);
            for (String sclFieldName : pwModelDetails.getSclToPWMapping().keySet()) {
                String reference = pwModelDetails.getModelNodeReference() + "." + sclFieldName;
                BdaVisibleString field = (BdaVisibleString) serverModel.findModelNode(reference, Fc.CF);
                if (field == null) {
                    logger.info(">>>>>> Error in obtaining SCL reference object = " + reference);
                }
                branchCircuitBreakerVals.add(field);
            }
//          Power World Device Initialization
            ParameterGenerator parameterGenerator = new ParameterGenerator();
            parameterGenerator.setSclKeyToPWKeyMap(pwModelDetails.getSclToPWMapping());
            parameterGenerator.setKeyParameters(pwModelDetails.getKeyArray());
            parameterGenerator.setValueParameters(pwModelDetails.getDataFieldArray());
            parameterGenerator.setPersistedValues(pwModelDetails.getValueArray());
            parameterGenerator.setDeviceObjectName(pwModelDetails.getDeviceName());
            if (controlAPI == null) {
                controlAPI = IedControlerFactory.getPWComBridgeIterface();
                synchronized (controlAPI) {
                    if (!controlAPI.isCaseOpened()) {
                        controlAPI.openCase();
                    }
                }
            }
            parameterGenerator.setControlAPI(controlAPI);
//          load power world data
            serverSap.startListening(sampleServer, parameterGenerator);
            try {
                serverSap.setValues(branchCircuitBreakerVals);
            } catch (Exception e) {
                logger.info("pwModelDetails.getModelNodeReference() = " + pwModelDetails.getModelNodeReference());
                e.printStackTrace();
            }

            String[][] paramPack = parameterGenerator.getParamPack();
            type = getIEDType(parameterGenerator.getDeviceObjectName());
            id = ++IED_COUNT;
            StringBuffer sb = new StringBuffer();
            String logDataSeperator = ":";
            boolean loged = false;
            logEvent("IED : " + type.name() + " : " + id + " is Started...!");

            String[] elements = null;
            while (true) {
                synchronized (this) {
                    try {
                        Thread.sleep(PW_INTERROGATION_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                elements = parameterGenerator.loadDataValues(paramPack);
                if (elements == null) {
                    continue;
                }
                sb.append("Type:").append(type.name());
                for (int i = 0; i < branchCircuitBreakerVals.size(); i++) {
                    BdaVisibleString modelNodes = (BdaVisibleString) branchCircuitBreakerVals.get(i);
                    if (modelNodes != null && elements.length > i) {
                        String pwKeyName = parameterGenerator.getSclKeyToPWKeyMap().get(modelNodes.getName());
                        for (int j = 0; j < paramPack[0].length; j++) {
                            if (!loged) {
                                sb.append(logDataSeperator).append(paramPack[0][j]).append(logDataSeperator).append(elements[j]);
                            }
                            if (pwKeyName.equals(paramPack[0][j])) {
                                modelNodes.setValue(elements[j]);
                            }
                        }
                        loged = true;
                    }
                }
                serverStarted = true;
                synchronized (logger) {
                    if (ConfigUtil.MANUAL_EXPERIMENT_MODE) {
                        // if this string is not printed in the log file,
                        logger.info(sb.toString());
                    }
                }
                sb = new StringBuffer("");
                loged = false;
                serverSap.setValues(branchCircuitBreakerVals);
                if (serverStoped) {
                    serverStarted = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void logEvent(String event) {
        if (logEventListener != null) {
            logEventListener.logEvent(event);
        }
    }

    public static void setCommonIEDEventListener(LogEventListener iedListener) {
        logEventListener = iedListener;
    }

    private IEDType getIEDType(String deviceObjectName) {
        switch (deviceObjectName.toUpperCase()) {
            case "TRANSFORMER":
                return IEDType.TRANSFRMER;
            case "GEN":
                return IEDType.GENERATOR;
            case "SHUNT":
                return IEDType.SHUNT;
            case "BRANCH":
                return IEDType.BRANCH;
            case "BUS":
                return IEDType.BUS;
            case "LOAD":
                return IEDType.BUS;
            default:
                return IEDType.VIRTUAL;
        }
    }

    private static synchronized void initLogger() {
        FileHandler fileTxt = null;
        Formatter formatterTxt;
        if (logger != null) {
            return;
        }
        // suppress the logging output to the console
        logger = Logger.getLogger("IED");
        logger.setLevel(Level.INFO);
        try {
            fileTxt = new FileHandler(ConfigUtil.LOG_FILE, 500000, 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileTxt != null) {
            logger.addHandler(fileTxt);// create a TXT formatter
        }
        formatterTxt = new IEDLogFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(new ConsoleHandler());
        // create an HTML formatter
    }

    public void stop() {
        if (controlAPI != null) {
            controlAPI.stop();
        }
        controlAPI = null;
        if (serverSap != null) {
            try {
                serverSap.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        serverStoped = true;
        System.out.println("Server Stoped...!");
    }

    public boolean isServerStarted() {
        return serverStarted;
    }
}

