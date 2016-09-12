/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-12
 * @version 0.1
 */
public class MetadataParserException extends MetadataException {

    /**
     * Creates a new instance of <code>MetadataParserException</code> without
     * detail message.
     */
    public MetadataParserException() {
    }

    /**
     * Constructs an instance of <code>MetadataParserException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MetadataParserException(String msg) {
        super(msg);
    }
}
