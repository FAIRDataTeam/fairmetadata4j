/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;



/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-10-24
 * @version 0.1
 */
public class DataRecordMetadata extends Metadata {
    
    private IRI rmlURI;
    private IRI distributionURI;
    private Literal dataRecordIssued;
    private Literal dataRecordModified;

    /**
     * @return the rmlURI
     */
    public IRI getRmlURI() {
        return rmlURI;
    }

    /**
     * @param rmlURI the rmlURI to set
     */
    public void setRmlURI(IRI rmlURI) {
        this.rmlURI = rmlURI;
    }

    /**
     * @return the distributionURI
     */
    public IRI getDistributionURI() {
        return distributionURI;
    }

    /**
     * @param distributionURI the distributionURI to set
     */
    public void setDistributionURI(IRI distributionURI) {
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
