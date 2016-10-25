/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.model;

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
    
}
