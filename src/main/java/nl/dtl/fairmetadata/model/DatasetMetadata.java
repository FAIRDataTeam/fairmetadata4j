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
public final class DatasetMetadata extends Metadata {
    
    private List<IRI> distribution = new ArrayList();
    private List<IRI> themes = new ArrayList();
    private IRI contactPoint;
    private List<Literal> keywords = new ArrayList();
    private IRI landingPage;   
    private Literal datasetIssued;
    private Literal datasetModified;

    /**
     * @param distribution the distribution to set
     */
    public void setDistribution(List<IRI> distribution) {
        this.distribution = distribution;
    }

    /**
     * @param themes the themes to set
     */
    public void setThemes(List<IRI> themes) {
        this.themes = themes;
    }

    /**
     * @param contactPoint the contactPoint to set
     */
    public void setContactPoint(IRI contactPoint) {
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
    public void setLandingPage(IRI landingPage) {
        this.landingPage = landingPage;
    }
    
    /**
     * @return the distribution
     */
    public List<IRI> getDistribution() {
        return distribution;
    }

    /**
     * @return the themes
     */
    public List<IRI> getThemes() {
        return themes;
    }

    /**
     * @return the contactPoint
     */
    public IRI getContactPoint() {
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
    public IRI getLandingPage() {
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
