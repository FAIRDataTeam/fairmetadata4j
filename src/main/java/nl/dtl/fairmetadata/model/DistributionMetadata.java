package nl.dtl.fairmetadata.model;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class DistributionMetadata extends Metadata {
    
    private IRI accessURL;
    private IRI downloadURL;
    private Literal mediaType;
    private Literal format;
    private Literal byteSize;
    private Literal distributionIssued;
    private Literal distributionModified;
    
    /**
     * @param accessURL the accessURL to set
     */
    public void setAccessURL(IRI accessURL) {
        this.accessURL = accessURL;
    }

    /**
     * @param downloadURL the downloadURL to set
     */
    public void setDownloadURL(IRI downloadURL) {
        this.downloadURL = downloadURL;
    }

    /**
     * @param mediaType the mediaType to set
     */
    public void setMediaType(Literal mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(Literal format) {
        this.format = format;
    }

    /**
     * @param byteSize the byteSize to set
     */
    public void setByteSize(Literal byteSize) {
        this.byteSize = byteSize;
    }

    /**
     * @return the accessURL
     */
    public IRI getAccessURL() {
        return accessURL;
    }

    /**
     * @return the downloadURL
     */
    public IRI getDownloadURL() {
        return downloadURL;
    }

    /**
     * @return the mediaType
     */
    public Literal getMediaType() {
        return mediaType;
    }

    /**
     * @return the format
     */
    public Literal getFormat() {
        return format;
    }

    /**
     * @return the byteSize
     */
    public Literal getByteSize() {
        return byteSize;
    }

    /**
     * @return the distributionIssued
     */
    public Literal getDistributionIssued() {
        return distributionIssued;
    }

    /**
     * @param distributionIssued the distributionIssued to set
     */
    public void setDistributionIssued(Literal distributionIssued) {
        this.distributionIssued = distributionIssued;
    }

    /**
     * @return the distributionModified
     */
    public Literal getDistributionModified() {
        return distributionModified;
    }

    /**
     * @param distributionModified the distributionModified to set
     */
    public void setDistributionModified(Literal distributionModified) {
        this.distributionModified = distributionModified;
    }
    
}
