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
public final class DatasetMetadata extends Metadata {
    
    private List<URI> distribution = new ArrayList();
    private List<URI> themes = new ArrayList();
    private URI contactPoint;
    private List<Literal> keywords = new ArrayList();
    private URI landingPage;   
    private Literal datasetIssued;
    private Literal datasetModified;

    /**
     * @param distribution the distribution to set
     */
    public void setDistribution(List<URI> distribution) {
        this.distribution = distribution;
    }

    /**
     * @param themes the themes to set
     */
    public void setThemes(List<URI> themes) {
        this.themes = themes;
    }

    /**
     * @param contactPoint the contactPoint to set
     */
    public void setContactPoint(URI contactPoint) {
        this.contactPoint = contactPoint;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(List<Literal> keywords) {
        this.keywords = keywords;
    }

    /**
     * @param landingPage the landingPage to set
     */
    public void setLandingPage(URI landingPage) {
        this.landingPage = landingPage;
    }
    
    /**
     * @return the distribution
     */
    public List<URI> getDistribution() {
        return distribution;
    }

    /**
     * @return the themes
     */
    public List<URI> getThemes() {
        return themes;
    }

    /**
     * @return the contactPoint
     */
    public URI getContactPoint() {
        return contactPoint;
    }

    /**
     * @return the keywords
     */
    public List<Literal> getKeywords() {
        return keywords;
    }

    /**
     * @return the landingPage
     */
    public URI getLandingPage() {
        return landingPage;
    }

    /**
     * @return the datasetIssued
     */
    public Literal getDatasetIssued() {
        return datasetIssued;
    }

    /**
     * @param datasetIssued the datasetIssued to set
     */
    public void setDatasetIssued(Literal datasetIssued) {
        this.datasetIssued = datasetIssued;
    }

    /**
     * @return the datasetModified
     */
    public Literal getDatasetModified() {
        return datasetModified;
    }

    /**
     * @param datasetModified the datasetModified to set
     */
    public void setDatasetModified(Literal datasetModified) {
        this.datasetModified = datasetModified;
    }
}
