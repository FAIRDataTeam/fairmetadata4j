package nl.dtl.fairmetadata.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.openrdf.model.URI;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public final class FDPMetadata extends Metadata {
    
    private URI homepage;
    private URI swaggerDoc;  
    
    /**
     * @param homepage the homepage to set
     */
    public void setHomepage(URI homepage) {
        this.homepage = homepage;
    }

    /**
     * @param swaggerDoc the swaggerDoc to set
     */
    public void setSwaggerDoc(URI swaggerDoc) {
        this.swaggerDoc = swaggerDoc;
    }

    /**
     * @return the homepage
     */
    public URI getHomepage() {
        return homepage;
    }

    /**
     * @return the swaggerDoc
     */
    public URI getSwaggerDoc() {
        return swaggerDoc;
    }
    
}
