
package it.illinois.adsc.ema.control.conf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for dataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="pwname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sclName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataType", propOrder = {
    "value"
})
public class DataType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "pwname")
    protected String pwname;
    @XmlAttribute(name = "sclName")
    protected String sclName;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the pwname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPwname() {
        return pwname;
    }

    /**
     * Sets the value of the pwname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPwname(String value) {
        this.pwname = value;
    }

    /**
     * Gets the value of the sclName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSclName() {
        return sclName;
    }

    /**
     * Sets the value of the sclName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSclName(String value) {
        this.sclName = value;
    }

}
