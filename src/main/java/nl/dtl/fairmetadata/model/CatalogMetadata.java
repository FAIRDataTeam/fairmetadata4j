package nl.dtl.fairmetadata.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.URI;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public final class CatalogMetadata extends Metadata {    
    
    private URI homepage;
    private List<URI> datasets = new ArrayList();
    private List<URI> themeTaxonomy = new ArrayList();
    /**
     * @param homepage the homepage to set
     */
    public void setHomepage(URI homepage) {
        this.homepage = homepage;
    }

    /**
     * @param datasets the datasets to set
     */
    public void setDatasets(List<URI> datasets) {
        this.datasets = datasets;
    }

    /**
     * @param themeTaxonomy the themeTaxonomy to set
     */
    public void setThemeTaxonomy(List<URI> themeTaxonomy) {
        this.themeTaxonomy = themeTaxonomy;
    }    

    /**
     * @return the homepage
     */
    public URI getHomepage() {
        return homepage;
    }

    /**
     * @return the datasets
     */
    public List<URI> getDatasets() {
        return datasets;
    }

    /**
     * @return the themeTaxonomy
     */
    public List<URI> getThemeTaxonomy() {
        return themeTaxonomy;
    }

}
