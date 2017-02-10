/**
 * The MIT License
 * Copyright © 2017 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
 * Catalog metadata object
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-06
 * @version 0.1
 */
public final class CatalogMetadata extends Metadata {    
    
    private IRI homepage;
    private List<IRI> datasets = new ArrayList();
    private List<IRI> themeTaxonomys = new ArrayList();
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
     * @param themeTaxonomys the themeTaxonomys to set
     */
    public void setThemeTaxonomys(List<IRI> themeTaxonomys) {
        this.themeTaxonomys = themeTaxonomys;
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
     * @return the themeTaxonomys
     */
    public List<IRI> getThemeTaxonomys() {
        return themeTaxonomys;
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
