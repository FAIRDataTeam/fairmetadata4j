/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-10-24
 * @version 0.1
 */
public class DataRecordMetadata extends Metadata {
    
    private URI rmlURI;
    private URI distributionURI;
    private Literal dataRecordIssued;
    private Literal dataRecordModified;

    /**
     * @return the rmlURI
     */
    public URI getRmlURI() {
        return rmlURI;
    }

    /**
     * @param rmlURI the rmlURI to set
     */
    public void setRmlURI(URI rmlURI) {
        this.rmlURI = rmlURI;
    }

    /**
     * @return the distributionURI
     */
    public URI getDistributionURI() {
        return distributionURI;
    }

    /**
     * @param distributionURI the distributionURI to set
     */
    public void setDistributionURI(URI distributionURI) {
        this.distributionURI = distributionURI;
    }

    /**
     * @return the dataRecordIssued
     */
    public Literal getDataRecordIssued() {
        return dataRecordIssued;
    }

    /**
     * @param dataRecordIssued the dataRecordIssued to set
     */
    public void setDataRecordIssued(Literal dataRecordIssued) {
        this.dataRecordIssued = dataRecordIssued;
    }

    /**
     * @return the dataRecordModified
     */
    public Literal getDataRecordModified() {
        return dataRecordModified;
    }

    /**
     * @param dataRecordModified the dataRecordModified to set
     */
    public void setDataRecordModified(Literal dataRecordModified) {
        this.dataRecordModified = dataRecordModified;
    }
    
}
