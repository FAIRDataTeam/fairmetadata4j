package nl.dtl.fairmetadata.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.Literal;
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
    private List<URI> catalogs = new ArrayList();
    private Identifier repostoryIdentifier;
    private URI institutionCountry;  
    private Literal lastUpdate;
    private Literal startDate;
    
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

    /**
     * @return the catalogs
     */
    public List<URI> getCatalogs() {
        return catalogs;
    }

    /**
     * @param catalogs the catalogs to set
     */
    public void setCatalogs(List<URI> catalogs) {
        this.catalogs = catalogs;
    }

    /**
     * @return the repostoryIdentifier
     */
    public Identifier getRepostoryIdentifier() {
        return repostoryIdentifier;
    }

    /**
     * @param repostoryIdentifier the repostoryIdentifier to set
     */
    public void setRepostoryIdentifier(Identifier repostoryIdentifier) {
        this.repostoryIdentifier = repostoryIdentifier;
    }

    /**
     * @return the institutionCountry
     */
    public URI getInstitutionCountry() {
        return institutionCountry;
    }

    /**
     * @param institutionCountry the institutionCountry to set
     */
    public void setInstitutionCountry(URI institutionCountry) {
        this.institutionCountry = institutionCountry;
    }

    /**
     * @return the lastUpdate
     */
    public Literal getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Literal lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the startDate
     */
    public Literal getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Literal startDate) {
        this.startDate = startDate;
    }
    
}
