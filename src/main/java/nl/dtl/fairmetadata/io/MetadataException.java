package nl.dtl.fairmetadata.io;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class MetadataException extends Exception {

    /**
     * Creates a new instance of <code>CatalogMetadataExeception</code> without
     * detail message.
     */
    public MetadataException() {
    }

    /**
     * Constructs an instance of <code>CatalogMetadataExeception</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MetadataException(String msg) {
        super(msg);
    }
}
