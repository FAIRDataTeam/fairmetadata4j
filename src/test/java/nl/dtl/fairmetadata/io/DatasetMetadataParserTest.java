/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDFS;

/**
 * Unit tests for DatasetMetadataParser.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-09
 * @version 0.1
 */
public class DatasetMetadataParserTest {
    
    private final DatasetMetadataParser parser = new DatasetMetadataParser();
    
    /**
     * Test null RDF string, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFString() throws Exception {
        System.out.println("parse invalid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        parser.parse(null, ExampleFilesUtils.DATASET_ID, dURI, cURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test empty RDF string, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyRDFString() throws Exception {
        System.out.println("parse invalid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        parser.parse("", ExampleFilesUtils.DATASET_ID, dURI, cURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null dataset ID, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullDatasetID() throws Exception {
        System.out.println("parse invalid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DATASET_METADATA_FILE), null, dURI, cURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test empty dataset ID, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyDatasetID() throws Exception {
        System.out.println("parse invalid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DATASET_METADATA_FILE), "", dURI, cURI, 
                ExampleFilesUtils.FILE_FORMAT);
        fail("This test is execpeted to throw an error");
    }    
    /**
     * Test null RDFFormat, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFFormat() throws Exception {
        System.out.println("parse invalid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        parser.parse(ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DATASET_METADATA_FILE), 
                ExampleFilesUtils.DATASET_ID, dURI, cURI, null);
        fail("This test is execpeted to throw an error");
    }
    /**
     * Test valid dataset metadata rdf file
     * @throws Exception 
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("parse valid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        DatasetMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsString(
                ExampleFilesUtils.DATASET_METADATA_FILE), 
                ExampleFilesUtils.DATASET_ID, dURI, cURI, 
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }   
    /**
     * Test missing dataset rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataExeception.class)
    public void testParseNoThemeStatement() throws Exception {
        System.out.println("Missing theme statement");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI);
        Iterator<Statement> it = stmts.iterator();
        List<Statement> in = new ArrayList();
        while(it.hasNext()) {
            Statement st = it.next();
            if(!st.getPredicate().equals(DCAT.THEME)) {
                in.add(st);
            }
        }
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse(in , dURI);
       fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null dataset URI, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testNullDatasetURI() throws Exception {
        System.out.println("Missing dataset URL");
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI);
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
        System.out.println("parse valid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        parser.parse(null, dURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test valid dataset rdf statements
     * @throws Exception 
     */
    @Test
    public void testParseStatements() throws Exception {
        System.out.println("parse valid dataset content");
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        DatasetMetadata metadata = parser.parse(
                ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI), dURI);
        assertNotNull(metadata);
    }
    
}