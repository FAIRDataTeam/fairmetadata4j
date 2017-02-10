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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata4j.model;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;



/**
 * Data record metadata object 
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-10-24
 * @version 0.1
 */
public class DataRecordMetadata extends Metadata {
    
    private IRI rmlURI;
    private IRI distributionURI;
    private Literal dataRecordIssued;
    private Literal dataRecordModified;

    /**
     * @return the rmlURI
     */
    public IRI getRmlURI() {
        return rmlURI;
    }

    /**
     * @param rmlURI the rmlURI to set
     */
    public void setRmlURI(IRI rmlURI) {
        this.rmlURI = rmlURI;
    }

    /**
     * @return the distributionURI
     */
    public IRI getDistributionURI() {
        return distributionURI;
    }

    /**
     * @param distributionURI the distributionURI to set
     */
    public void setDistributionURI(IRI distributionURI) {
        this.distributionURI = distributionURI;
    }

    /**
     * @return the dataRecordIssued
     */
    public Literal getDataRecordIssued() {
        return dataRecordIssued;
    }

    /**
     * @param dataRecordIssued the dataRecordIssued to set
     */
    public void setDataRecordIssued(Literal dataRecordIssued) {
        this.dataRecordIssued = dataRecordIssued;
    }

    /**
     * @return the dataRecordModified
     */
    public Literal getDataRecordModified() {
        return dataRecordModified;
    }

    /**
     * @param dataRecordModified the dataRecordModified to set
     */
    public void setDataRecordModified(Literal dataRecordModified) {
        this.dataRecordModified = dataRecordModified;
    }
    
}
