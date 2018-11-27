/*-------------------------------------------------------------------------------------------------
| *** UNCLASSIFIED ***
|--------------------------------------------------------------------------------------------------
| U.S. Army Research, Development, and Engineering Command
| Aviation and Missile Research, Development, and Engineering Center
| Software Engineering Directorate, Redstone Arsenal, AL
|--------------------------------------------------------------------------------------------------
| Export-Control Act Warning: Warning - This Document contains technical data whose export is
| restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751, et seq) or the Export
| Administration Act of 1979, as amended, Title 50, U.S.C., App. 2401 et seq. Violations of these
| export laws are subject to severe criminal penalties. Disseminate in accordance with provisions
| of DoD Directive 5230.25.
|--------------------------------------------------------------------------------------------------
| Distribution Statement C:  Distribution authorized to U.S. Government Agencies and their
| contractors, Export Controlled, Critical Technology, 13 Feb 2017. Other request for this document
| shall be referred to U.S. Army Aviation and Missile Research Development and Engineering Center
| Software Engineering Directorate, Attn: RDMR-BAW, Redstone Arsenal, AL 35898.
--------------------------------------------------------------------------------------------------*/

package mil.don.common.messages.tcut21;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataReport" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SystemStatus" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}XYZPos" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="sw_version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="system_state" use="required" type="{}ew_mode" />
 *                           &lt;attribute name="overall_status" use="required" type="{}bit_result_status_e" />
 *                           &lt;attribute name="gps_is_locked" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="EWTrack" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}XYZPos" minOccurs="0"/>
 *                             &lt;element ref="{}PosError" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="track_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="signal_type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="update_time" use="required" type="{}utc_time" />
 *                           &lt;attribute name="affiliation" type="{}affiliation" />
 *                           &lt;attribute name="affiliation_conf" type="{}confidence" />
 *                           &lt;attribute name="approaching" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="azimuth" type="{}azimuth_angle" />
 *                           &lt;attribute name="azimuth_err" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                           &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                           &lt;attribute name="elevation" type="{}elevation_angle" />
 *                           &lt;attribute name="elevation_err" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                           &lt;attribute name="end_track" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="frequency" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                           &lt;attribute name="group_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="mac_address" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="noise_strength" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                           &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="quality" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="signal_conf" type="{}confidence" />
 *                           &lt;attribute name="signal_strength" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="time" use="required" type="{}utc_time" />
 *                 &lt;attribute name="time_is_valid" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="msg_count" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                 &lt;attribute name="receipt_required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DataReceipt" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="destination_system" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="msg_count" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="EWRequest" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ActivateCounterMeasure" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="track_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="ecm_type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="HaltCounterMeasure" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="track_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *                           &lt;attribute name="ecm_type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="request_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *                 &lt;attribute name="destination_system" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="EWResponse" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="destination_system" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="request_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="revision" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="source_system" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dataReport",
    "dataReceipt",
    "ewRequest",
    "ewResponse"
})
@XmlRootElement(name = "EWMessage")
public class EWMessage {

    @XmlElement(name = "DataReport")
    protected List<DataReport> dataReport;
    @XmlElement(name = "DataReceipt")
    protected List<DataReceipt> dataReceipt;
    @XmlElement(name = "EWRequest")
    protected List<EWRequest> ewRequest;
    @XmlElement(name = "EWResponse")
    protected List<EWResponse> ewResponse;
    @XmlAttribute(name = "revision", required = true)
    protected String revision;
    @XmlAttribute(name = "source_system", required = true)
    protected String sourceSystem;

    /**
     * Gets the value of the dataReport property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataReport property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataReport().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataReport }
     *
     *
     */
    public List<DataReport> getDataReport() {
        if (dataReport == null) {
            dataReport = new ArrayList<DataReport>();
        }
        return this.dataReport;
    }

    /**
     * Gets the value of the dataReceipt property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataReceipt property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataReceipt().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataReceipt }
     *
     *
     */
    public List<DataReceipt> getDataReceipt() {
        if (dataReceipt == null) {
            dataReceipt = new ArrayList<DataReceipt>();
        }
        return this.dataReceipt;
    }

    /**
     * Gets the value of the ewRequest property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ewRequest property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEWRequest().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EWRequest }
     *
     *
     */
    public List<EWRequest> getEWRequest() {
        if (ewRequest == null) {
            ewRequest = new ArrayList<EWRequest>();
        }
        return this.ewRequest;
    }

    /**
     * Gets the value of the ewResponse property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ewResponse property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEWResponse().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EWResponse }
     *
     *
     */
    public List<EWResponse> getEWResponse() {
        if (ewResponse == null) {
            ewResponse = new ArrayList<EWResponse>();
        }
        return this.ewResponse;
    }

    /**
     * Gets the value of the revision property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRevision(String value) {
        this.revision = value;
    }

    /**
     * Gets the value of the sourceSystem property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="destination_system" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="msg_count" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DataReceipt {

        @XmlAttribute(name = "destination_system", required = true)
        protected String destinationSystem;
        @XmlAttribute(name = "msg_count", required = true)
        @XmlSchemaType(name = "unsignedShort")
        protected int msgCount;

        /**
         * Gets the value of the destinationSystem property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDestinationSystem() {
            return destinationSystem;
        }

        /**
         * Sets the value of the destinationSystem property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDestinationSystem(String value) {
            this.destinationSystem = value;
        }

        /**
         * Gets the value of the msgCount property.
         *
         */
        public int getMsgCount() {
            return msgCount;
        }

        /**
         * Sets the value of the msgCount property.
         *
         */
        public void setMsgCount(int value) {
            this.msgCount = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SystemStatus" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}XYZPos" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="sw_version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="system_state" use="required" type="{}ew_mode" />
     *                 &lt;attribute name="overall_status" use="required" type="{}bit_result_status_e" />
     *                 &lt;attribute name="gps_is_locked" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="EWTrack" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}XYZPos" minOccurs="0"/>
     *                   &lt;element ref="{}PosError" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="track_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="signal_type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="update_time" use="required" type="{}utc_time" />
     *                 &lt;attribute name="affiliation" type="{}affiliation" />
     *                 &lt;attribute name="affiliation_conf" type="{}confidence" />
     *                 &lt;attribute name="approaching" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="azimuth" type="{}azimuth_angle" />
     *                 &lt;attribute name="azimuth_err" type="{http://www.w3.org/2001/XMLSchema}float" />
     *                 &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}double" />
     *                 &lt;attribute name="elevation" type="{}elevation_angle" />
     *                 &lt;attribute name="elevation_err" type="{http://www.w3.org/2001/XMLSchema}float" />
     *                 &lt;attribute name="end_track" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="frequency" type="{http://www.w3.org/2001/XMLSchema}double" />
     *                 &lt;attribute name="group_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="mac_address" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="noise_strength" type="{http://www.w3.org/2001/XMLSchema}float" />
     *                 &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="quality" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="signal_conf" type="{}confidence" />
     *                 &lt;attribute name="signal_strength" type="{http://www.w3.org/2001/XMLSchema}float" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="time" use="required" type="{}utc_time" />
     *       &lt;attribute name="time_is_valid" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="msg_count" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *       &lt;attribute name="receipt_required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "systemStatus",
        "ewTrack"
    })
    public static class DataReport {

        @XmlElement(name = "SystemStatus")
        protected List<SystemStatus> systemStatus;
        @XmlElement(name = "EWTrack")
        protected List<EWTrack> ewTrack;
        @XmlAttribute(name = "time", required = true)
        protected BigInteger time;
        @XmlAttribute(name = "time_is_valid", required = true)
        protected boolean timeIsValid;
        @XmlAttribute(name = "msg_count", required = true)
        @XmlSchemaType(name = "unsignedShort")
        protected int msgCount;
        @XmlAttribute(name = "receipt_required")
        protected Boolean receiptRequired;

        /**
         * Gets the value of the systemStatus property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the systemStatus property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSystemStatus().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SystemStatus }
         *
         *
         */
        public List<SystemStatus> getSystemStatus() {
            if (systemStatus == null) {
                systemStatus = new ArrayList<SystemStatus>();
            }
            return this.systemStatus;
        }

        /**
         * Gets the value of the ewTrack property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ewTrack property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEWTrack().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EWTrack }
         *
         *
         */
        public List<EWTrack> getEWTrack() {
            if (ewTrack == null) {
                ewTrack = new ArrayList<EWTrack>();
            }
            return this.ewTrack;
        }

        /**
         * Gets the value of the time property.
         *
         * @return
         *     possible object is
         *     {@link BigInteger }
         *
         */
        public BigInteger getTime() {
            return time;
        }

        /**
         * Sets the value of the time property.
         *
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *
         */
        public void setTime(BigInteger value) {
            this.time = value;
        }

        /**
         * Gets the value of the timeIsValid property.
         *
         */
        public boolean isTimeIsValid() {
            return timeIsValid;
        }

        /**
         * Sets the value of the timeIsValid property.
         *
         */
        public void setTimeIsValid(boolean value) {
            this.timeIsValid = value;
        }

        /**
         * Gets the value of the msgCount property.
         *
         */
        public int getMsgCount() {
            return msgCount;
        }

        /**
         * Sets the value of the msgCount property.
         *
         */
        public void setMsgCount(int value) {
            this.msgCount = value;
        }

        /**
         * Gets the value of the receiptRequired property.
         *
         * @return
         *     possible object is
         *     {@link Boolean }
         *
         */
        public Boolean isReceiptRequired() {
            return receiptRequired;
        }

        /**
         * Sets the value of the receiptRequired property.
         *
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *
         */
        public void setReceiptRequired(Boolean value) {
            this.receiptRequired = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{}XYZPos" minOccurs="0"/>
         *         &lt;element ref="{}PosError" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="track_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="signal_type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="update_time" use="required" type="{}utc_time" />
         *       &lt;attribute name="affiliation" type="{}affiliation" />
         *       &lt;attribute name="affiliation_conf" type="{}confidence" />
         *       &lt;attribute name="approaching" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="azimuth" type="{}azimuth_angle" />
         *       &lt;attribute name="azimuth_err" type="{http://www.w3.org/2001/XMLSchema}float" />
         *       &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}double" />
         *       &lt;attribute name="elevation" type="{}elevation_angle" />
         *       &lt;attribute name="elevation_err" type="{http://www.w3.org/2001/XMLSchema}float" />
         *       &lt;attribute name="end_track" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="frequency" type="{http://www.w3.org/2001/XMLSchema}double" />
         *       &lt;attribute name="group_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="mac_address" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="noise_strength" type="{http://www.w3.org/2001/XMLSchema}float" />
         *       &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="quality" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="signal_conf" type="{}confidence" />
         *       &lt;attribute name="signal_strength" type="{http://www.w3.org/2001/XMLSchema}float" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "xyzPos",
            "posError"
        })
        public static class EWTrack {

            @XmlElement(name = "XYZPos")
            protected XYZPos xyzPos;
            @XmlElement(name = "PosError")
            protected PosError posError;
            @XmlAttribute(name = "track_id", required = true)
            @XmlSchemaType(name = "unsignedShort")
            protected int trackId;
            @XmlAttribute(name = "signal_type", required = true)
            protected String signalType;
            @XmlAttribute(name = "update_time", required = true)
            protected BigInteger updateTime;
            @XmlAttribute(name = "affiliation")
            protected Affiliation affiliation;
            @XmlAttribute(name = "affiliation_conf")
            protected Float affiliationConf;
            @XmlAttribute(name = "approaching")
            protected Boolean approaching;
            @XmlAttribute(name = "azimuth")
            protected Float azimuth;
            @XmlAttribute(name = "azimuth_err")
            protected Float azimuthErr;
            @XmlAttribute(name = "bandwidth")
            protected Double bandwidth;
            @XmlAttribute(name = "elevation")
            protected Float elevation;
            @XmlAttribute(name = "elevation_err")
            protected Float elevationErr;
            @XmlAttribute(name = "end_track")
            protected Boolean endTrack;
            @XmlAttribute(name = "frequency")
            protected Double frequency;
            @XmlAttribute(name = "group_id")
            @XmlSchemaType(name = "unsignedShort")
            protected Integer groupId;
            @XmlAttribute(name = "mac_address")
            protected String macAddress;
            @XmlAttribute(name = "noise_strength")
            protected Float noiseStrength;
            @XmlAttribute(name = "priority")
            @XmlSchemaType(name = "unsignedShort")
            protected Integer priority;
            @XmlAttribute(name = "quality")
            @XmlSchemaType(name = "unsignedShort")
            protected Integer quality;
            @XmlAttribute(name = "signal_conf")
            protected Float signalConf;
            @XmlAttribute(name = "signal_strength")
            protected Float signalStrength;

            /**
             * Estimated position of EW Track if available
             *
             * @return
             *     possible object is
             *     {@link XYZPos }
             *
             */
            public XYZPos getXYZPos() {
                return xyzPos;
            }

            /**
             * Sets the value of the xyzPos property.
             *
             * @param value
             *     allowed object is
             *     {@link XYZPos }
             *
             */
            public void setXYZPos(XYZPos value) {
                this.xyzPos = value;
            }

            /**
             * Estimated error of the position of EW Track
             *
             * @return
             *     possible object is
             *     {@link PosError }
             *
             */
            public PosError getPosError() {
                return posError;
            }

            /**
             * Sets the value of the posError property.
             *
             * @param value
             *     allowed object is
             *     {@link PosError }
             *
             */
            public void setPosError(PosError value) {
                this.posError = value;
            }

            /**
             * Gets the value of the trackId property.
             *
             */
            public int getTrackId() {
                return trackId;
            }

            /**
             * Sets the value of the trackId property.
             *
             */
            public void setTrackId(int value) {
                this.trackId = value;
            }

            /**
             * Gets the value of the signalType property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSignalType() {
                return signalType;
            }

            /**
             * Sets the value of the signalType property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSignalType(String value) {
                this.signalType = value;
            }

            /**
             * Gets the value of the updateTime property.
             *
             * @return
             *     possible object is
             *     {@link BigInteger }
             *
             */
            public BigInteger getUpdateTime() {
                return updateTime;
            }

            /**
             * Sets the value of the updateTime property.
             *
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *
             */
            public void setUpdateTime(BigInteger value) {
                this.updateTime = value;
            }

            /**
             * Gets the value of the affiliation property.
             *
             * @return
             *     possible object is
             *     {@link Affiliation }
             *
             */
            public Affiliation getAffiliation() {
                return affiliation;
            }

            /**
             * Sets the value of the affiliation property.
             *
             * @param value
             *     allowed object is
             *     {@link Affiliation }
             *
             */
            public void setAffiliation(Affiliation value) {
                this.affiliation = value;
            }

            /**
             * Gets the value of the affiliationConf property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getAffiliationConf() {
                return affiliationConf;
            }

            /**
             * Sets the value of the affiliationConf property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setAffiliationConf(Float value) {
                this.affiliationConf = value;
            }

            /**
             * Gets the value of the approaching property.
             *
             * @return
             *     possible object is
             *     {@link Boolean }
             *
             */
            public Boolean isApproaching() {
                return approaching;
            }

            /**
             * Sets the value of the approaching property.
             *
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *
             */
            public void setApproaching(Boolean value) {
                this.approaching = value;
            }

            /**
             * Gets the value of the azimuth property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getAzimuth() {
                return azimuth;
            }

            /**
             * Sets the value of the azimuth property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setAzimuth(Float value) {
                this.azimuth = value;
            }

            /**
             * Gets the value of the azimuthErr property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getAzimuthErr() {
                return azimuthErr;
            }

            /**
             * Sets the value of the azimuthErr property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setAzimuthErr(Float value) {
                this.azimuthErr = value;
            }

            /**
             * Gets the value of the bandwidth property.
             *
             * @return
             *     possible object is
             *     {@link Double }
             *
             */
            public Double getBandwidth() {
                return bandwidth;
            }

            /**
             * Sets the value of the bandwidth property.
             *
             * @param value
             *     allowed object is
             *     {@link Double }
             *
             */
            public void setBandwidth(Double value) {
                this.bandwidth = value;
            }

            /**
             * Gets the value of the elevation property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getElevation() {
                return elevation;
            }

            /**
             * Sets the value of the elevation property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setElevation(Float value) {
                this.elevation = value;
            }

            /**
             * Gets the value of the elevationErr property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getElevationErr() {
                return elevationErr;
            }

            /**
             * Sets the value of the elevationErr property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setElevationErr(Float value) {
                this.elevationErr = value;
            }

            /**
             * Gets the value of the endTrack property.
             *
             * @return
             *     possible object is
             *     {@link Boolean }
             *
             */
            public Boolean isEndTrack() {
                return endTrack;
            }

            /**
             * Sets the value of the endTrack property.
             *
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *
             */
            public void setEndTrack(Boolean value) {
                this.endTrack = value;
            }

            /**
             * Gets the value of the frequency property.
             *
             * @return
             *     possible object is
             *     {@link Double }
             *
             */
            public Double getFrequency() {
                return frequency;
            }

            /**
             * Sets the value of the frequency property.
             *
             * @param value
             *     allowed object is
             *     {@link Double }
             *
             */
            public void setFrequency(Double value) {
                this.frequency = value;
            }

            /**
             * Gets the value of the groupId property.
             *
             * @return
             *     possible object is
             *     {@link Integer }
             *
             */
            public Integer getGroupId() {
                return groupId;
            }

            /**
             * Sets the value of the groupId property.
             *
             * @param value
             *     allowed object is
             *     {@link Integer }
             *
             */
            public void setGroupId(Integer value) {
                this.groupId = value;
            }

            /**
             * Gets the value of the macAddress property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getMacAddress() {
                return macAddress;
            }

            /**
             * Sets the value of the macAddress property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setMacAddress(String value) {
                this.macAddress = value;
            }

            /**
             * Gets the value of the noiseStrength property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getNoiseStrength() {
                return noiseStrength;
            }

            /**
             * Sets the value of the noiseStrength property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setNoiseStrength(Float value) {
                this.noiseStrength = value;
            }

            /**
             * Gets the value of the priority property.
             *
             * @return
             *     possible object is
             *     {@link Integer }
             *
             */
            public Integer getPriority() {
                return priority;
            }

            /**
             * Sets the value of the priority property.
             *
             * @param value
             *     allowed object is
             *     {@link Integer }
             *
             */
            public void setPriority(Integer value) {
                this.priority = value;
            }

            /**
             * Gets the value of the quality property.
             *
             * @return
             *     possible object is
             *     {@link Integer }
             *
             */
            public Integer getQuality() {
                return quality;
            }

            /**
             * Sets the value of the quality property.
             *
             * @param value
             *     allowed object is
             *     {@link Integer }
             *
             */
            public void setQuality(Integer value) {
                this.quality = value;
            }

            /**
             * Gets the value of the signalConf property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getSignalConf() {
                return signalConf;
            }

            /**
             * Sets the value of the signalConf property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setSignalConf(Float value) {
                this.signalConf = value;
            }

            /**
             * Gets the value of the signalStrength property.
             *
             * @return
             *     possible object is
             *     {@link Float }
             *
             */
            public Float getSignalStrength() {
                return signalStrength;
            }

            /**
             * Sets the value of the signalStrength property.
             *
             * @param value
             *     allowed object is
             *     {@link Float }
             *
             */
            public void setSignalStrength(Float value) {
                this.signalStrength = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{}XYZPos" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="sw_version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="system_state" use="required" type="{}ew_mode" />
         *       &lt;attribute name="overall_status" use="required" type="{}bit_result_status_e" />
         *       &lt;attribute name="gps_is_locked" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "xyzPos"
        })
        public static class SystemStatus {

            @XmlElement(name = "XYZPos")
            protected XYZPos xyzPos;
            @XmlAttribute(name = "sw_version", required = true)
            protected String swVersion;
            @XmlAttribute(name = "system_state", required = true)
            protected EwMode systemState;
            @XmlAttribute(name = "overall_status", required = true)
            protected BitResultStatusE overallStatus;
            @XmlAttribute(name = "gps_is_locked", required = true)
            protected boolean gpsIsLocked;

            /**
             * The position of the EW sensor
             *
             * @return
             *     possible object is
             *     {@link XYZPos }
             *
             */
            public XYZPos getXYZPos() {
                return xyzPos;
            }

            /**
             * Sets the value of the xyzPos property.
             *
             * @param value
             *     allowed object is
             *     {@link XYZPos }
             *
             */
            public void setXYZPos(XYZPos value) {
                this.xyzPos = value;
            }

            /**
             * Gets the value of the swVersion property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSwVersion() {
                return swVersion;
            }

            /**
             * Sets the value of the swVersion property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSwVersion(String value) {
                this.swVersion = value;
            }

            /**
             * Gets the value of the systemState property.
             *
             * @return
             *     possible object is
             *     {@link EwMode }
             *
             */
            public EwMode getSystemState() {
                return systemState;
            }

            /**
             * Sets the value of the systemState property.
             *
             * @param value
             *     allowed object is
             *     {@link EwMode }
             *
             */
            public void setSystemState(EwMode value) {
                this.systemState = value;
            }

            /**
             * Gets the value of the overallStatus property.
             *
             * @return
             *     possible object is
             *     {@link BitResultStatusE }
             *
             */
            public BitResultStatusE getOverallStatus() {
                return overallStatus;
            }

            /**
             * Sets the value of the overallStatus property.
             *
             * @param value
             *     allowed object is
             *     {@link BitResultStatusE }
             *
             */
            public void setOverallStatus(BitResultStatusE value) {
                this.overallStatus = value;
            }

            /**
             * Gets the value of the gpsIsLocked property.
             *
             */
            public boolean isGpsIsLocked() {
                return gpsIsLocked;
            }

            /**
             * Sets the value of the gpsIsLocked property.
             *
             */
            public void setGpsIsLocked(boolean value) {
                this.gpsIsLocked = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ActivateCounterMeasure" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="track_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="ecm_type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="HaltCounterMeasure" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="track_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
     *                 &lt;attribute name="ecm_type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="request_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *       &lt;attribute name="destination_system" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "activateCounterMeasure",
        "haltCounterMeasure"
    })
    public static class EWRequest {

        @XmlElement(name = "ActivateCounterMeasure")
        protected List<ActivateCounterMeasure> activateCounterMeasure;
        @XmlElement(name = "HaltCounterMeasure")
        protected List<HaltCounterMeasure> haltCounterMeasure;
        @XmlAttribute(name = "request_id", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long requestId;
        @XmlAttribute(name = "destination_system")
        protected String destinationSystem;

        /**
         * Gets the value of the activateCounterMeasure property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the activateCounterMeasure property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getActivateCounterMeasure().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ActivateCounterMeasure }
         *
         *
         */
        public List<ActivateCounterMeasure> getActivateCounterMeasure() {
            if (activateCounterMeasure == null) {
                activateCounterMeasure = new ArrayList<ActivateCounterMeasure>();
            }
            return this.activateCounterMeasure;
        }

        /**
         * Gets the value of the haltCounterMeasure property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the haltCounterMeasure property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHaltCounterMeasure().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HaltCounterMeasure }
         *
         *
         */
        public List<HaltCounterMeasure> getHaltCounterMeasure() {
            if (haltCounterMeasure == null) {
                haltCounterMeasure = new ArrayList<HaltCounterMeasure>();
            }
            return this.haltCounterMeasure;
        }

        /**
         * Gets the value of the requestId property.
         *
         */
        public long getRequestId() {
            return requestId;
        }

        /**
         * Sets the value of the requestId property.
         *
         */
        public void setRequestId(long value) {
            this.requestId = value;
        }

        /**
         * Gets the value of the destinationSystem property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDestinationSystem() {
            return destinationSystem;
        }

        /**
         * Sets the value of the destinationSystem property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDestinationSystem(String value) {
            this.destinationSystem = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="track_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="ecm_type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class ActivateCounterMeasure {

            @XmlAttribute(name = "track_id")
            @XmlSchemaType(name = "unsignedShort")
            protected Integer trackId;
            @XmlAttribute(name = "ecm_type")
            protected String ecmType;

            /**
             * Gets the value of the trackId property.
             *
             * @return
             *     possible object is
             *     {@link Integer }
             *
             */
            public Integer getTrackId() {
                return trackId;
            }

            /**
             * Sets the value of the trackId property.
             *
             * @param value
             *     allowed object is
             *     {@link Integer }
             *
             */
            public void setTrackId(Integer value) {
                this.trackId = value;
            }

            /**
             * Gets the value of the ecmType property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getEcmType() {
                return ecmType;
            }

            /**
             * Sets the value of the ecmType property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setEcmType(String value) {
                this.ecmType = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="track_id" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
         *       &lt;attribute name="ecm_type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class HaltCounterMeasure {

            @XmlAttribute(name = "track_id")
            @XmlSchemaType(name = "unsignedShort")
            protected Integer trackId;
            @XmlAttribute(name = "ecm_type")
            protected String ecmType;

            /**
             * Gets the value of the trackId property.
             *
             * @return
             *     possible object is
             *     {@link Integer }
             *
             */
            public Integer getTrackId() {
                return trackId;
            }

            /**
             * Sets the value of the trackId property.
             *
             * @param value
             *     allowed object is
             *     {@link Integer }
             *
             */
            public void setTrackId(Integer value) {
                this.trackId = value;
            }

            /**
             * Gets the value of the ecmType property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getEcmType() {
                return ecmType;
            }

            /**
             * Sets the value of the ecmType property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setEcmType(String value) {
                this.ecmType = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="destination_system" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="request_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class EWResponse {

        @XmlAttribute(name = "destination_system", required = true)
        protected String destinationSystem;
        @XmlAttribute(name = "success", required = true)
        protected boolean success;
        @XmlAttribute(name = "message")
        protected String message;
        @XmlAttribute(name = "request_id", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long requestId;

        /**
         * Gets the value of the destinationSystem property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDestinationSystem() {
            return destinationSystem;
        }

        /**
         * Sets the value of the destinationSystem property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDestinationSystem(String value) {
            this.destinationSystem = value;
        }

        /**
         * Gets the value of the success property.
         *
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * Sets the value of the success property.
         *
         */
        public void setSuccess(boolean value) {
            this.success = value;
        }

        /**
         * Gets the value of the message property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the value of the message property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setMessage(String value) {
            this.message = value;
        }

        /**
         * Gets the value of the requestId property.
         *
         */
        public long getRequestId() {
            return requestId;
        }

        /**
         * Sets the value of the requestId property.
         *
         */
        public void setRequestId(long value) {
            this.requestId = value;
        }

    }

}