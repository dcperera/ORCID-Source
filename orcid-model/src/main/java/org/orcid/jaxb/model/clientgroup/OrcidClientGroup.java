/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2013 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.18 at 12:45:00 PM BST 
//

package org.orcid.jaxb.model.clientgroup;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}group-orcid" minOccurs="0"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}group-name"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}email"/>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}orcid-client" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "groupOrcid", "groupName", "email", "orcidClient" })
@XmlRootElement(name = "orcid-client-group")
public class OrcidClientGroup implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "group-orcid")
    protected String groupOrcid;
    @XmlElement(name = "group-name", required = true)
    protected String groupName;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(name = "orcid-client", required = true)
    protected List<OrcidClient> orcidClient;

    /**
     * Gets the value of the groupOrcid property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGroupOrcid() {
        return groupOrcid;
    }

    /**
     * Sets the value of the groupOrcid property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setGroupOrcid(String value) {
        this.groupOrcid = value;
    }

    /**
     * Gets the value of the groupName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the orcidClient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the orcidClient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getOrcidClient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrcidClient }
     * 
     * 
     */
    public List<OrcidClient> getOrcidClient() {
        if (orcidClient == null) {
            orcidClient = new ArrayList<OrcidClient>();
        }
        return this.orcidClient;
    }

    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(getClass());
            StringWriter writer = new StringWriter();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, writer);
            return writer.toString();
        } catch (JAXBException e) {
            return ("Unable to unmarshal because: " + e);
        }
    }

    public static OrcidClientGroup unmarshall(InputStream input) {
        try {
            JAXBContext context = JAXBContext.newInstance(OrcidClientGroup.class.getPackage().getName());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (OrcidClientGroup) unmarshaller.unmarshal(input);
        } catch (JAXBException e) {
            // XXX Should be more specific exception
            throw new RuntimeException("Unable to unmarshall client group" + e);
        }
    }

}
