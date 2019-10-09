/**
 * The MIT License
 * Copyright © 2019 DTL
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
package nl.dtl.fairmetadata4j.model;

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
 * Dataset metadata object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-06
 * @version 0.1
 */
public final class DatasetMetadata extends Metadata {

    private List<IRI> distributions = new ArrayList();
    private List<IRI> themes = new ArrayList();
    private IRI contactPoint;
    private List<Literal> keywords = new ArrayList();
    private IRI landingPage;
    private Literal datasetIssued;
    private Literal datasetModified;

    /**
     * @param distribution the distributions to set
     */
    public void setDistributions(List<IRI> distribution) {
        this.distributions = distribution;
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
     * @return the distributions
     */
    public List<IRI> getDistributions() {
        return distributions;
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

    public void accept(MetadataVisitor visitor) {
        visitor.visit(this);
    }
}
