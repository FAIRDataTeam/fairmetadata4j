/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils;

import java.util.List;
import nl.dtl.fairmetadata.io.CatalogMetadataParser;
import nl.dtl.fairmetadata.io.DataRecordMetadataParser;
import nl.dtl.fairmetadata.io.DatasetMetadataParser;
import nl.dtl.fairmetadata.io.DistributionMetadataParser;
import nl.dtl.fairmetadata.io.FDPMetadataParser;
import nl.dtl.fairmetadata.io.MetadataException;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.model.DataRecordMetadata;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.model.FDPMetadata;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFFormat;

/**
 * Unit tests for MetadataUtils.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-12
 * @version 0.1
 */
public class MetadataUtilsTest {  
    
    /**
     * Test null metadata, this test is excepted to throw 
     * an exception
     * 
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class) 
    public void testNullMetadataObject() throws Exception{ 
        System.out.println("Test : Null metadata");
        MetadataUtils.getStatements(null);
        fail("This test is execpeted to throw an error");        
    }  
    
    /**
     * Test null metadata, this test is excepted to throw 
     * an exception
     * 
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class) 
    public void testNullMetadataObjectForStringConvertion() throws Exception{ 
        System.out.println("Test : Null metadata");
        MetadataUtils.getString(null, RDFFormat.TRIG);
        fail("This test is execpeted to throw an error");        
    }
    
    /**
     * Test null rdf format, this test is excepted to throw 
     * an exception
     * 
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class) 
    public void testNullRDFFormatForStringConvertion() throws Exception{ 
        System.out.println("Test : Null RDFFormat");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        MetadataUtils.getString(metadata, null);
        fail("This test is execpeted to throw an error");        
    }
    
     /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoVersionStatement() throws Exception {
        System.out.println("Test : Missing version statement");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        metadata.setVersion(null);
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoTitleStatement() throws Exception {
        System.out.println("Test : Missing title statement");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        metadata.setTitle(null);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoIDStatement() throws Exception {
        System.out.println("Test : Missing ID statement");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        metadata.setIdentifier(null);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing catalog rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoThemeTaxonomyStatement() throws Exception {
        System.out.println("Test : Missing theme taxonomy statement");
        CatalogMetadataParser parser = new CatalogMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, 
                        ExampleFilesUtils.CATALOG_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        CatalogMetadata metadata = parser.parse(stmts , cURI);
        metadata.setThemeTaxonomy(null);
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
       
    /**
     * Test missing dataset rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoThemeStatement() throws Exception {
        System.out.println("Test : Missing theme statement");
        DatasetMetadataParser parser = new DatasetMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI);        
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        DatasetMetadata metadata = parser.parse(stmts , dURI);
        metadata.setThemes(null);
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing distribution rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoDowloadOrAccessStatement() throws Exception {        
        System.out.println("Test : Missing download or access statement");
        DistributionMetadataParser parser = new DistributionMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI);
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        DistributionMetadata metadata = parser.parse(stmts , disURI);
        metadata.setAccessURL(null);
        metadata.setDownloadURL(null);     
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }  
    
    /**
     * This test is excepted to pass 
     * an exception
     * @throws Exception 
     */
    @Test
    public void testGetFDPStatements() throws Exception {
        System.out.println("Test : Valid FDP metadata");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI);
        URI fURI = new URIImpl(ExampleFilesUtils.FDP_URI);
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);   
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }
    
    /**
     * This test is excepted to pass 
     *
     * @throws Exception 
     */
    @Test
    public void testGetCatalogStatements() throws Exception {
        System.out.println("Test : Valid catalog metadata");
        CatalogMetadataParser parser = new CatalogMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, 
                        ExampleFilesUtils.CATALOG_URI);
        URI cURI = new URIImpl(ExampleFilesUtils.CATALOG_URI);
        CatalogMetadata metadata = parser.parse(stmts , cURI);
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }
    
       
    /**
     * This test is excepted to pass 
     *
     * @throws Exception 
     */
    @Test
    public void testGetDatasetStatements() throws Exception {
        System.out.println("Test : Valid dataset metadata");
        DatasetMetadataParser parser = new DatasetMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI);        
        URI dURI = new URIImpl(ExampleFilesUtils.DATASET_URI);
        DatasetMetadata metadata = parser.parse(stmts , dURI);
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }
    
    /**
     * This test is excepted to pass 
     *
     * @throws Exception 
     */
    @Test
    public void testGetDistributionStatements() throws Exception {        
        System.out.println("Test : Valid distribution metadata");
        DistributionMetadataParser parser = new DistributionMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI);
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        DistributionMetadata metadata = parser.parse(stmts , disURI);             
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }
    
    /**
     * This test is excepted to pass 
     *
     * @throws Exception 
     */
    @Test
    public void testGetDataRecordStatements() throws Exception {        
        System.out.println("Test : Valid datarecord metadata");
        DataRecordMetadataParser parser = new DataRecordMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATARECORD_METADATA_FILE, 
                        ExampleFilesUtils.DATARECORD_URI);
        URI drURI = new URIImpl(ExampleFilesUtils.DATARECORD_URI);
        DataRecordMetadata metadata = parser.parse(stmts , drURI);             
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }
    
    
    
    
    /**
     * Test missing distribution rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test
    public void testGetRDFString() throws Exception {        
        System.out.println("Test : Valid distribution metadata");
        DistributionMetadataParser parser = new DistributionMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI);
        URI disURI = new URIImpl(ExampleFilesUtils.DISTRIBUTION_URI);
        DistributionMetadata metadata = parser.parse(stmts , disURI);             
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE);
        assertFalse(out.isEmpty());
    }
    
}
