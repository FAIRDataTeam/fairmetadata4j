package nl.dtl.fairmetadata.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public final class FDPMetadata extends Metadata {
    
    private IRI homepage;
    private IRI swaggerDoc;  
    private List<IRI> catalogs = new ArrayList();
    private Identifier repostoryIdentifier;
    private IRI institutionCountry;  
    private Literal lastUpdate;
    private Literal startDate;
    private Agent institution;
    
    /**
     * @param homepage the homepage to set
     */
    public void setHomepage(IRI homepage) {
        this.homepage = homepage;
    }

    /**
     * @param swaggerDoc the swaggerDoc to set
     */
    public void setSwaggerDoc(IRI swaggerDoc) {
        this.swaggerDoc = swaggerDoc;
    }

    /**
     * @return the homepage
     */
    public IRI getHomepage() {
        return homepage;
    }

    /**
     * @return the swaggerDoc
     */
    public IRI getSwaggerDoc() {
        return swaggerDoc;
    }

    /**
     * @return the catalogs
     */
    public List<IRI> getCatalogs() {
        return catalogs;
    }

    /**
     * @param catalogs the catalogs to set
     */
    public void setCatalogs(List<IRI> catalogs) {
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
    public IRI getInstitutionCountry() {
        return institutionCountry;
    }

    /**
     * @param institutionCountry the institutionCountry to set
     */
    public void setInstitutionCountry(IRI institutionCountry) {
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

    /**
     * @return the institution
     */
    public Agent getInstitution() {
        return institution;
    }

    /**
     * @param institution the institution to set
     */
    public void setInstitution(Agent institution) {
        this.institution = institution;
    }
    
}
