//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.05 at 11:33:26 AM KST 
//

package org.oliot.epcis.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * 
 * Object Event captures information about an event pertaining to one or more
 * objects identified by EPCs.
 * 
 * 
 * <p>
 * Java class for ObjectEventType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectEventType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:epcglobal:epcis:xsd:2}EPCISEventType">
 *       &lt;sequence>
 *         &lt;element name="epcList" type="{urn:epcglobal:epcis:xsd:2}EPCListType" minOccurs="0"/>
 *         &lt;element name="action" type="{urn:epcglobal:epcis:xsd:2}ActionType"/>
 *         &lt;element name="bizStep" type="{urn:epcglobal:epcis:xsd:2}BusinessStepIDType" minOccurs="0"/>
 *         &lt;element name="disposition" type="{urn:epcglobal:epcis:xsd:2}DispositionIDType" minOccurs="0"/>
 *         &lt;element name="readPoint" type="{urn:epcglobal:epcis:xsd:2}ReadPointType" minOccurs="0"/>
 *         &lt;element name="bizLocation" type="{urn:epcglobal:epcis:xsd:2}BusinessLocationType" minOccurs="0"/>
 *         &lt;element name="bizTransactionList" type="{urn:epcglobal:epcis:xsd:2}BusinessTransactionListType" minOccurs="0"/>
 *         &lt;element name="quantityList" type="{urn:epcglobal:epcis:xsd:2}QuantityListType" minOccurs="0"/>
 *         &lt;element name="sourceList" type="{urn:epcglobal:epcis:xsd:2}SourceListType" minOccurs="0"/>
 *         &lt;element name="destinationList" type="{urn:epcglobal:epcis:xsd:2}DestinationListType" minOccurs="0"/>
 *         &lt;element name="sensorElementList" type="{urn:epcglobal:epcis:xsd:2}SensorElementListType" minOccurs="0"/>
 *         &lt;element name="persistentDisposition" type="{urn:epcglobal:epcis:xsd:2}PersistentDispositionType" minOccurs="0"/>
 *         &lt;element name="ilmd" type="{urn:epcglobal:epcis:xsd:2}ILMDType" minOccurs="0"/>
 *         &lt;element name="extension" type="{urn:epcglobal:epcis:xsd:2}ObjectEventExtensionType" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectEventType", propOrder = { "epcList", "action", "bizStep", "disposition", "readPoint",
		"bizLocation", "bizTransactionList", "quantityList", "sourceList", "destinationList", "sensorElementList",
		"persistentDisposition", "ilmd", "extension", "any" })
@XmlRootElement
public class ObjectEventType extends EPCISEventType {

	protected EPCListType epcList;
	@XmlElement(required = true)
	@XmlSchemaType(name = "string")
	protected ActionType action;
	@XmlSchemaType(name = "anyURI")
	protected String bizStep;
	@XmlSchemaType(name = "anyURI")
	protected String disposition;
	protected ReadPointType readPoint;
	protected BusinessLocationType bizLocation;
	protected BusinessTransactionListType bizTransactionList;
	protected QuantityListType quantityList;
	protected SourceListType sourceList;
	protected DestinationListType destinationList;
	protected SensorElementListType sensorElementList;
	protected PersistentDispositionType persistentDisposition;
	protected ILMDType ilmd;
	protected ObjectEventExtensionType extension;
	@XmlAnyElement(lax = true)
	protected List<Object> any;

	public void setAny(List<Object> any) {
		this.any = any;
	}

	/**
	 * Gets the value of the epcList property.
	 * 
	 * @return possible object is {@link EPCListType }
	 * 
	 */
	public EPCListType getEpcList() {
		return epcList;
	}

	/**
	 * Sets the value of the epcList property.
	 * 
	 * @param value allowed object is {@link EPCListType }
	 * 
	 */
	public void setEpcList(EPCListType value) {
		this.epcList = value;
	}

	/**
	 * Gets the value of the action property.
	 * 
	 * @return possible object is {@link ActionType }
	 * 
	 */
	public ActionType getAction() {
		return action;
	}

	/**
	 * Sets the value of the action property.
	 * 
	 * @param value allowed object is {@link ActionType }
	 * 
	 */
	public void setAction(ActionType value) {
		this.action = value;
	}

	/**
	 * Gets the value of the bizStep property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBizStep() {
		return bizStep;
	}

	/**
	 * Sets the value of the bizStep property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setBizStep(String value) {
		this.bizStep = value;
	}

	/**
	 * Gets the value of the disposition property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDisposition() {
		return disposition;
	}

	/**
	 * Sets the value of the disposition property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDisposition(String value) {
		this.disposition = value;
	}

	/**
	 * Gets the value of the readPoint property.
	 * 
	 * @return possible object is {@link ReadPointType }
	 * 
	 */
	public ReadPointType getReadPoint() {
		return readPoint;
	}

	/**
	 * Sets the value of the readPoint property.
	 * 
	 * @param value allowed object is {@link ReadPointType }
	 * 
	 */
	public void setReadPoint(ReadPointType value) {
		this.readPoint = value;
	}

	/**
	 * Gets the value of the bizLocation property.
	 * 
	 * @return possible object is {@link BusinessLocationType }
	 * 
	 */
	public BusinessLocationType getBizLocation() {
		return bizLocation;
	}

	/**
	 * Sets the value of the bizLocation property.
	 * 
	 * @param value allowed object is {@link BusinessLocationType }
	 * 
	 */
	public void setBizLocation(BusinessLocationType value) {
		this.bizLocation = value;
	}

	/**
	 * Gets the value of the bizTransactionList property.
	 * 
	 * @return possible object is {@link BusinessTransactionListType }
	 * 
	 */
	public BusinessTransactionListType getBizTransactionList() {
		return bizTransactionList;
	}

	/**
	 * Sets the value of the bizTransactionList property.
	 * 
	 * @param value allowed object is {@link BusinessTransactionListType }
	 * 
	 */
	public void setBizTransactionList(BusinessTransactionListType value) {
		this.bizTransactionList = value;
	}

	/**
	 * Gets the value of the quantityList property.
	 * 
	 * @return possible object is {@link QuantityListType }
	 * 
	 */
	public QuantityListType getQuantityList() {
		return quantityList;
	}

	/**
	 * Sets the value of the quantityList property.
	 * 
	 * @param value allowed object is {@link QuantityListType }
	 * 
	 */
	public void setQuantityList(QuantityListType value) {
		this.quantityList = value;
	}

	/**
	 * Gets the value of the sourceList property.
	 * 
	 * @return possible object is {@link SourceListType }
	 * 
	 */
	public SourceListType getSourceList() {
		return sourceList;
	}

	/**
	 * Sets the value of the sourceList property.
	 * 
	 * @param value allowed object is {@link SourceListType }
	 * 
	 */
	public void setSourceList(SourceListType value) {
		this.sourceList = value;
	}

	/**
	 * Gets the value of the destinationList property.
	 * 
	 * @return possible object is {@link DestinationListType }
	 * 
	 */
	public DestinationListType getDestinationList() {
		return destinationList;
	}

	/**
	 * Sets the value of the destinationList property.
	 * 
	 * @param value allowed object is {@link DestinationListType }
	 * 
	 */
	public void setDestinationList(DestinationListType value) {
		this.destinationList = value;
	}

	/**
	 * Gets the value of the sensorElementList property.
	 * 
	 * @return possible object is {@link SensorElementListType }
	 * 
	 */
	public SensorElementListType getSensorElementList() {
		return sensorElementList;
	}

	/**
	 * Sets the value of the sensorElementList property.
	 * 
	 * @param value allowed object is {@link SensorElementListType }
	 * 
	 */
	public void setSensorElementList(SensorElementListType value) {
		this.sensorElementList = value;
	}

	/**
	 * Gets the value of the persistentDisposition property.
	 * 
	 * @return possible object is {@link PersistentDispositionType }
	 * 
	 */
	public PersistentDispositionType getPersistentDisposition() {
		return persistentDisposition;
	}

	/**
	 * Sets the value of the persistentDisposition property.
	 * 
	 * @param value allowed object is {@link PersistentDispositionType }
	 * 
	 */
	public void setPersistentDisposition(PersistentDispositionType value) {
		this.persistentDisposition = value;
	}

	/**
	 * Gets the value of the ilmd property.
	 * 
	 * @return possible object is {@link ILMDType }
	 * 
	 */
	public ILMDType getIlmd() {
		return ilmd;
	}

	/**
	 * Sets the value of the ilmd property.
	 * 
	 * @param value allowed object is {@link ILMDType }
	 * 
	 */
	public void setIlmd(ILMDType value) {
		this.ilmd = value;
	}

	/**
	 * Gets the value of the extension property.
	 * 
	 * @return possible object is {@link ObjectEventExtensionType }
	 * 
	 */
	public ObjectEventExtensionType getExtension() {
		return extension;
	}

	/**
	 * Sets the value of the extension property.
	 * 
	 * @param value allowed object is {@link ObjectEventExtensionType }
	 * 
	 */
	public void setExtension(ObjectEventExtensionType value) {
		this.extension = value;
	}

	/**
	 * Gets the value of the any property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the any property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAny().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Element }
	 * {@link Object }
	 * 
	 * 
	 */
	public List<Object> getAny() {
		if (any == null) {
			any = new ArrayList<Object>();
		}
		return this.any;
	}

}
