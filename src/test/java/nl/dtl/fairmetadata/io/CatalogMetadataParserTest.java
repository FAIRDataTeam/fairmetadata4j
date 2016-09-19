/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.List;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * Unit tests for CatalogMetadataParser.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-08
 * @version 0.1
 */
public class CatalogMetadataParserTest {
    
    private final CatalogMetadataParser parser = new CatalogMetadataParser();
    
    /**
     * Test null RDF string, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFString() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse(null, ExampleFilesUtils.CATALOG_ID, cURI, fURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test empty RDF string, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyRDFString() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse("", ExampleFilesUtils.CATALOG_ID, cURI, fURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null catalog ID, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullCatalogID() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.CATALOG_METADATA_FILE), null, cURI, fURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test empty catalog ID, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyCatalogID() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.CATALOG_METADATA_FILE), "", cURI, fURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }    
    /**
     * Test null RDFFormat, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFFormat() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.CATALOG_METADATA_FILE), 
                ExampleFilesUtils.CATALOG_ID, cURI, fURI, null);
        fail("This test is execpeted to throw an error");
    }
    /**
     * Test valid catalog metadata rdf file
     * @throws Exception 
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("Test : Parse valid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        CatalogMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.CATALOG_METADATA_FILE), 
                ExampleFilesUtils.CATALOG_ID, cURI, fURI, 
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }
    
    /**
     * Test null catalog URI, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testNullCatalogURI() throws Exception {
        System.out.println("Test : Missing catalog URL");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, 
                        ExampleFilesUtils.CATALOG_URI);
        parser.parse(stmts , null);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null statements, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testNullStatements() throws Exception {
        System.out.println("Test : Parse valid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        parser.parse(null, cURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test valid catalog rdf statements
     * @throws Exception 
     */
    @Test
    public void testParseStatements() throws Exception {
        System.out.println("Test : Parse valid catalog content");
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        CatalogMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, 
                        ExampleFilesUtils.CATALOG_URI), cURI);
        assertNotNull(metadata);
    }
    
    /**
     * Test valid catalog metadata rdf file, with no base
     * @throws Exception 
     */
    @Test
    public void testParseFileWithNoBase() throws Exception {
        System.out.println("Test : Parse valid catalog content with no base uri");
        CatalogMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.CATALOG_METADATA_FILE), null,
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }
    
}