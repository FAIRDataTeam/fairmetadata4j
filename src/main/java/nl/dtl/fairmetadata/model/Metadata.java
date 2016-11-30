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
public class Metadata {

    private Literal title;
    private Identifier identifier;
    private Literal issued;
    private Literal modified;
    private Literal version;
    private Literal description;
    private URI license;
    private URI rights;
    private URI uri;
    private URI parentURI;
    private List<URI> publisher = new ArrayList<>();
    private URI language;

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
    public void setLicense(URI license) {
        this.license = license;
    }

    /**
     * @param rights the rights to set
     */
    public void setRights(URI rights) {
        this.rights = rights;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(URI uri) {
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
    public URI getLicense() {
        return license;
    }

    /**
     * @return the rights
     */
    public URI getRights() {
        return rights;
    }

    /**
     * @return the uri
     */
    public URI getUri() {
        return uri;
    }    

    /**
     * @return the publisher
     */
    public List<URI> getPublisher() {
        return publisher;
    }

    /**
     * @return the language
     */
    public URI getLanguage() {
        return language;
    }   

    /**
     * @param language the language to set
     */
    public void setLanguage(URI language) {
        this.language = language;
    }

    /**
     * @return the parentURI
     */
    public URI getParentURI() {
        return parentURI;
    }

    /**
     * @param parentURI the parentURI to set
     */
    public void setParentURI(URI parentURI) {
        this.parentURI = parentURI;
    }
}
