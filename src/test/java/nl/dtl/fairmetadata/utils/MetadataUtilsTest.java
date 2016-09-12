/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils;

import java.util.List;
import nl.dtl.fairmetadata.io.CatalogMetadataParser;
import nl.dtl.fairmetadata.io.DatasetMetadataParser;
import nl.dtl.fairmetadata.io.DistributionMetadataParser;
import nl.dtl.fairmetadata.io.FDPMetadataParser;
import nl.dtl.fairmetadata.io.MetadataExeception;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.model.FDPMetadata;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

/**
 * Unit tests for MetadataUtils.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-12
 * @version 0.1
 */
public class MetadataUtilsTest {    
        
    
     /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataExeception.class)
    public void testParseNoVersionStatement() throws Exception {
        System.out.println("Missing version statement");
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
    @Test(expected = MetadataExeception.class)
    public void testParseNoTitleStatement() throws Exception {
        System.out.println("Missing title statement");
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
     * Test missing catalog rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataExeception.class)
    public void testParseNoThemeTaxonomyStatement() throws Exception {
        System.out.println("Missing theme taxonomy statement");
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
    @Test(expected = MetadataExeception.class)
    public void testParseNoThemeStatement() throws Exception {
        System.out.println("Missing theme statement");
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
    @Test(expected = MetadataExeception.class)
    public void testParseNoDowloadOrAccessStatement() throws Exception {        
        System.out.println("Missing download or access statement");
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
    
}
