/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 *
 * @author rajaram
 */
//@Ignore
public class CatalogMetadataParserTest {

    /**
     * Test of parse method, of class CatalogMetadataParser.
     * @throws java.lang.Exception
     */
    private final CatalogMetadataParser parser = new CatalogMetadataParser();
    @Test
    public void testParseFile() throws Exception {
        System.out.println("parse valid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        CatalogMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.CATALOG_METADATA_FILE), 
                ExampleFilesUtils.CATALOG_ID, cURI, fURI, 
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }
    
}
