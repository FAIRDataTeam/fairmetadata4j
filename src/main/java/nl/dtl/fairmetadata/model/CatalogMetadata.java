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
public final class CatalogMetadata extends Metadata {    
    
    private IRI homepage;
    private List<IRI> datasets = new ArrayList();
    private List<IRI> themeTaxonomy = new ArrayList();
    private Literal catalogIssued;
    private Literal catalogModified;    
    /**
     * @param homepage the homepage to set
     */
    public void setHomepage(IRI homepage) {
        this.homepage = homepage;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<IRI> datasets) {
        this.datasets = datasets;
    }

    /**
     * @param themeTaxonomy the themeTaxonomy to set
     */
    public void setThemeTaxonomy(List<IRI> themeTaxonomy) {
        this.themeTaxonomy = themeTaxonomy;
    }    

    /**
     * @return the homepage
     */
    public IRI getHomepage() {
        return homepage;
    }

    /**
     * @return the datasets
     */
    public List<IRI> getDatasets() {
        return datasets;
    }

    /**
     * @return the themeTaxonomy
     */
    public List<IRI> getThemeTaxonomy() {
        return themeTaxonomy;
    }

    /**
     * @return the catalogIssued
     */
    public Literal getCatalogIssued() {
        return catalogIssued;
    }

    /**
     * @param catalogIssued the catalogIssued to set
     */
    public void setCatalogIssued(Literal catalogIssued) {
        this.catalogIssued = catalogIssued;
    }

    /**
     * @return the catalogModified
     */
    public Literal getCatalogModified() {
        return catalogModified;
    }

    /**
     * @param catalogModified the catalogModified to set
     */
    public void setCatalogModified(Literal catalogModified) {
        this.catalogModified = catalogModified;
    }

}
