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
package nl.dtl.fairmetadata4j.utils;

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata4j.io.CatalogMetadataParser;
import nl.dtl.fairmetadata4j.io.DataRecordMetadataParser;
import nl.dtl.fairmetadata4j.io.DatasetMetadataParser;
import nl.dtl.fairmetadata4j.io.DistributionMetadataParser;
import nl.dtl.fairmetadata4j.io.FDPMetadataParser;
import nl.dtl.fairmetadata4j.io.MetadataException;
import nl.dtl.fairmetadata4j.model.CatalogMetadata;
import nl.dtl.fairmetadata4j.model.DataRecordMetadata;
import nl.dtl.fairmetadata4j.model.DatasetMetadata;
import nl.dtl.fairmetadata4j.model.DistributionMetadata;
import nl.dtl.fairmetadata4j.model.FDPMetadata;
import nl.dtl.fairmetadata4j.model.Identifier;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for MetadataUtils.
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
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
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
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
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
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
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
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
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        metadata.setIdentifier(null);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoRepostoryIDStatement() throws Exception {
        System.out.println("Test : Missing repostiory ID statement");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        metadata.setRepostoryIdentifier(null);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNoPublisherStatement() throws Exception {
        System.out.println("Test : Missing publisher statement");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        metadata.setPublisher(null);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing id statement with missing triple, this test is excepted to 
     * throw an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseIDStatementWithMissingLiteral() throws Exception {
        System.out.println("Test : Missing ID literal");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        Identifier id = metadata.getIdentifier();
        id.setIdentifier(null);
        metadata.setIdentifier(id);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing id statement with missing triple, this test is excepted to 
     * throw an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseIDStatementWithMissingURI() throws Exception {
        System.out.println("Test : Missing ID uri");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        Identifier id = metadata.getIdentifier();
        id.setUri(null);
        metadata.setIdentifier(id);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing id statement with missing triple, this test is excepted to 
     * throw an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseIDStatementWithMissingType() throws Exception {
        System.out.println("Test : Missing ID type");
        FDPMetadataParser fdpParser = new FDPMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
        FDPMetadata metadata = fdpParser.parse(stmts , fURI);
        Identifier id = metadata.getIdentifier();
        id.setType(null);
        metadata.setIdentifier(id);    
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing catalog rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNullThemeTaxonomyStatement() throws Exception {
        System.out.println("Test : Null theme taxonomy statement");
        CatalogMetadataParser parser = new CatalogMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, 
                        ExampleFilesUtils.CATALOG_URI.toString());
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        CatalogMetadata metadata = parser.parse(stmts , cURI);
        metadata.setThemeTaxonomys(null);
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    
    /**
     * Test missing catalog rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseEmptyThemeTaxonomyStatement() throws Exception {
        System.out.println("Test : Empty theme taxonomy statement");
        CatalogMetadataParser parser = new CatalogMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, 
                        ExampleFilesUtils.CATALOG_URI.toString());
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        CatalogMetadata metadata = parser.parse(stmts , cURI);
        metadata.setThemeTaxonomys(new ArrayList());
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
       
    /**
     * Test missing dataset rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseNullThemeStatement() throws Exception {
        System.out.println("Test : Missing theme statement");
        DatasetMetadataParser parser = new DatasetMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI.toString());        
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        DatasetMetadata metadata = parser.parse(stmts , dURI);
        metadata.setThemes(null);
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test missing dataset rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testParseEmptyThemeStatement() throws Exception {
        System.out.println("Test : Missing theme statement");
        DatasetMetadataParser parser = new DatasetMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI.toString());        
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        DatasetMetadata metadata = parser.parse(stmts , dURI);
        metadata.setThemes(new ArrayList());
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
                        ExampleFilesUtils.DISTRIBUTION_URI.toString());
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        DistributionMetadata metadata = parser.parse(stmts , disURI);
        metadata.setAccessURL(null);
        metadata.setDownloadURL(null);     
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }  
    
    /**
     * Test missing rdf statement, this test is excepted to throw 
     * an exception
     * @throws Exception 
     */
    @Test(expected = MetadataException.class)
    public void testGetDataRecordNoRMLURI() throws Exception {        
        System.out.println("Test : Missing rml uri for  datarecord metadata");
        DataRecordMetadataParser parser = new DataRecordMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATARECORD_METADATA_FILE, 
                        ExampleFilesUtils.DATARECORD_URI.toString());
        IRI drURI = ExampleFilesUtils.DATARECORD_URI;
        DataRecordMetadata metadata = parser.parse(stmts , drURI);  
        metadata.setRmlURI(null);
        MetadataUtils.getStatements(metadata);
        fail("This test is execpeted to throw an error");
    }
    
    
    
    /**
     * Test missing distribution rdf statement, this test is excepted to pass
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testParseNoDowload() throws Exception {        
        System.out.println("Test : Missing download url");
        DistributionMetadataParser parser = new DistributionMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI.toString());
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        DistributionMetadata metadata = parser.parse(stmts , disURI);
        ValueFactory f = SimpleValueFactory.getInstance();
        metadata.setAccessURL(f.createIRI("http://example.com/accessuri"));
        metadata.setDownloadURL(null);     
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }
    
    /**
     * Test missing distribution rdf statement, this test is excepted to pass
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testParseNoAccess() throws Exception {        
        System.out.println("Test : Missing download url");
        DistributionMetadataParser parser = new DistributionMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI.toString());
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        DistributionMetadata metadata = parser.parse(stmts , disURI);
        metadata.setAccessURL(null);
        ValueFactory f = SimpleValueFactory.getInstance();
        metadata.setDownloadURL(f.createIRI("http://example.com/downloaduri"));     
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
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
                        ExampleFilesUtils.FDP_URI.toString());
        IRI fURI = ExampleFilesUtils.FDP_URI;
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
                        ExampleFilesUtils.CATALOG_URI.toString());
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
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
                        ExampleFilesUtils.DATASET_URI.toString());        
        IRI dURI = ExampleFilesUtils.DATASET_URI;
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
                        ExampleFilesUtils.DISTRIBUTION_URI.toString());
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
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
                        ExampleFilesUtils.DATARECORD_URI.toString());
        IRI drURI = ExampleFilesUtils.DATARECORD_URI;
        DataRecordMetadata metadata = parser.parse(stmts , drURI);             
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }    
    
    /**
     * Test missing distribution rdf statement, this test is excepted to pass
     * 
     * @throws Exception 
     */
    @Test
    public void testGetRDFString() throws Exception {        
        System.out.println("Test : Valid distribution metadata");
        DistributionMetadataParser parser = new DistributionMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE, 
                        ExampleFilesUtils.DISTRIBUTION_URI.toString());
        IRI disURI = ExampleFilesUtils.DISTRIBUTION_URI;
        DistributionMetadata metadata = parser.parse(stmts , disURI);             
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE);
        assertFalse(out.isEmpty());
    }    
}
