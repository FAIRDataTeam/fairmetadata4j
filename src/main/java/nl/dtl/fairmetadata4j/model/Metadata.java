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

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * Metadata object
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-06
 * @version 0.1
 */
public class Metadata {

    private Literal title;
    private Identifier identifier;
    private Literal issued;
    private Literal modified;
    private Literal version;
    private Literal description;
    private IRI license;
    private IRI rights;
    private IRI accessRights;
    private IRI uri;
    private IRI parentURI;
    private IRI language;
    private Agent publisher;

    /**
     * @param title the title to set
     */
    public void setTitle(Literal title) {
        this.title = title;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * @param issued the issued to set
     */
    public void setIssued(Literal issued) {
        this.issued = issued;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Literal modified) {
        this.modified = modified;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Literal version) {
        this.version = version;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(Literal description) {
        this.description = description;
    }

    /**
     * @param license the license to set
     */
    public void setLicense(IRI license) {
        this.license = license;
    }

    /**
     * @param rights the rights to set
     */
    public void setRights(IRI rights) {
        this.rights = rights;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(IRI uri) {
        this.uri = uri;
    }
    /**
     * @return the title
     */
    public Literal getTitle() {
        return title;
    }

    /**
     * @return the identifier
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * @return the issued
     */
    public Literal getIssued() {
        return issued;
    }

    /**
     * @return the modified
     */
    public Literal getModified() {
        return modified;
    }

    /**
     * @return the version
     */
    public Literal getVersion() {
        return version;
    }

    /**
     * @return the description
     */
    public Literal getDescription() {
        return description;
    }

    /**
     * @return the license
     */
    public IRI getLicense() {
        return license;
    }

    /**
     * @return the rights
     */
    public IRI getRights() {
        return rights;
    }

    /**
     * @return the uri
     */
    public IRI getUri() {
        return uri;
    }    

    /**
     * @return the publisher
     */
    public Agent getPublisher() {
        return publisher;
    }

    /**
     * @return the language
     */
    public IRI getLanguage() {
        return language;
    }   

    /**
     * @param language the language to set
     */
    public void setLanguage(IRI language) {
        this.language = language;
    }

    /**
     * @return the parentURI
     */
    public IRI getParentURI() {
        return parentURI;
    }

    /**
     * @param parentURI the parentURI to set
     */
    public void setParentURI(IRI parentURI) {
        this.parentURI = parentURI;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(Agent publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the accessRights
     */
    public IRI getAccessRights() {
        return accessRights;
    }

    /**
     * @param accessRights the accessRights to set
     */
    public void setAccessRights(IRI accessRights) {
        this.accessRights = accessRights;
    }
}
