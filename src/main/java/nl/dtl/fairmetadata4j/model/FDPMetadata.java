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
 * Repository metadata object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-06
 * @version 0.1
 */
public final class FDPMetadata extends Metadata {

    private IRI swaggerDoc;
    private List<IRI> catalogs = new ArrayList();
    private Identifier repostoryIdentifier;
    private IRI institutionCountry;
    private Literal lastUpdate;
    private Literal startDate;
    private Agent institution;

    /**
     * @param swaggerDoc the swaggerDoc to set
     */
    public void setSwaggerDoc(IRI swaggerDoc) {
        this.swaggerDoc = swaggerDoc;
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
