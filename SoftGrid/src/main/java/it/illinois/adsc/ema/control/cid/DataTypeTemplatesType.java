//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.24 at 04:18:37 PM SGT 
//


package it.illinois.adsc.ema.control.cid;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataTypeTemplatesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataTypeTemplatesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LNodeType" type="{http://www.iec.ch/61850/2003/SCL}LNodeTypeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DOType" type="{http://www.iec.ch/61850/2003/SCL}DOTypeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DAType" type="{http://www.iec.ch/61850/2003/SCL}DATypeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EnumType" type="{http://www.iec.ch/61850/2003/SCL}EnumTypeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataTypeTemplatesType", namespace = "http://www.iec.ch/61850/2003/SCL", propOrder = {
    "lNodeType",
    "doType",
    "daType",
    "enumType"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class DataTypeTemplatesType {

    @XmlElement(name = "LNodeType", namespace = "http://www.iec.ch/61850/2003/SCL")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<LNodeTypeType> lNodeType;
    @XmlElement(name = "DOType", namespace = "http://www.iec.ch/61850/2003/SCL")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<DOTypeType> doType;
    @XmlElement(name = "DAType", namespace = "http://www.iec.ch/61850/2003/SCL")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<DATypeType> daType;
    @XmlElement(name = "EnumType", namespace = "http://www.iec.ch/61850/2003/SCL")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<EnumTypeType> enumType;

    /**
     * Gets the value of the lNodeType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lNodeType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLNodeType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LNodeTypeType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<LNodeTypeType> getLNodeType() {
        if (lNodeType == null) {
            lNodeType = new ArrayList<LNodeTypeType>();
        }
        return this.lNodeType;
    }

    /**
     * Gets the value of the doType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the doType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDOType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DOTypeType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<DOTypeType> getDOType() {
        if (doType == null) {
            doType = new ArrayList<DOTypeType>();
        }
        return this.doType;
    }

    /**
     * Gets the value of the daType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the daType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDAType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DATypeType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<DATypeType> getDAType() {
        if (daType == null) {
            daType = new ArrayList<DATypeType>();
        }
        return this.daType;
    }

    /**
     * Gets the value of the enumType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the enumType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnumType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnumTypeType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<EnumTypeType> getEnumType() {
        if (enumType == null) {
            enumType = new ArrayList<EnumTypeType>();
        }
        return this.enumType;
    }

}