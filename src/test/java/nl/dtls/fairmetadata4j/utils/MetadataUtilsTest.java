/**
 * The MIT License
 * Copyright © 2019 DTL
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
package nl.dtls.fairmetadata4j.utils;

import java.util.ArrayList;
import java.util.List;
import nl.dtls.fairmetadata4j.io.CatalogMetadataParser;
import nl.dtls.fairmetadata4j.io.DataRecordMetadataParser;
import nl.dtls.fairmetadata4j.io.DatasetMetadataParser;
import nl.dtls.fairmetadata4j.io.DistributionMetadataParser;
import nl.dtls.fairmetadata4j.io.FDPMetadataParser;
import nl.dtls.fairmetadata4j.io.MetadataException;
import nl.dtls.fairmetadata4j.model.CatalogMetadata;
import nl.dtls.fairmetadata4j.model.DataRecordMetadata;
import nl.dtls.fairmetadata4j.model.DatasetMetadata;
import nl.dtls.fairmetadata4j.model.DistributionMetadata;
import nl.dtls.fairmetadata4j.model.FDPMetadata;
import nl.dtls.fairmetadata4j.model.Identifier;
import org.apache.logging.log4j.LogManager;
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

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetadataUtilsTest.class);
    /**
     * FDP metadata parser
     */
    private final FDPMetadataParser FDP_PARSER = new FDPMetadataParser();
    /**
     * Catalog metadata parser
     */
    private final CatalogMetadataParser CAT_PARSER = new CatalogMetadataParser();
    /**
     * Dataset metadata parser
     */
    private final DatasetMetadataParser DAT_PARSER = new DatasetMetadataParser();
    /**
     * Distribution metadata parser
     */
    private final DistributionMetadataParser DIS_PARSER = new DistributionMetadataParser();
    /**
     * Datarecord metadata parser
     */
    private final DataRecordMetadataParser DAT_REC_PARSER = new DataRecordMetadataParser();
    private final ValueFactory f = SimpleValueFactory.getInstance();

    /**
     * Test null metadata, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullMetadataObject() throws Exception {
        LOGGER.info("Test : Null metadata");
        MetadataUtils.getStatements(null);
    }

    /**
     * Test null metadata, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullMetadataObjectForStringConvertion() throws Exception {
        LOGGER.info("Test : Null metadata");
        MetadataUtils.getString(null, RDFFormat.TRIG);
    }

    /**
     * Test null rdf format, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullRDFFormatForStringConvertion() throws Exception {
        LOGGER.info("Test : Null RDFFormat");
        MetadataUtils.getString(getFdpMetadata(), null);
    }

    /**
     * Test missing rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNoVersionStatement() throws Exception {
        LOGGER.info("Test : Missing version statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setVersion(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNoTitleStatement() throws Exception {
        LOGGER.info("Test : Missing title statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setTitle(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseNoTitleStatementWithFlag() throws Exception {
        LOGGER.info("Test : Missing title statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setTitle(null);
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNoIDStatement() throws Exception {
        LOGGER.info("Test : Missing ID statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setIdentifier(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseNoIDStatementWithFlag() throws Exception {
        LOGGER.info("Test : Missing ID statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setIdentifier(null);
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNoRepostoryIDStatement() throws Exception {
        LOGGER.info("Test : Missing repostiory ID statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setRepostoryIdentifier(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseNoRepostoryIDStatementWithFlag() throws Exception {
        LOGGER.info("Test : Missing repostiory ID statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setRepostoryIdentifier(null);
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNoPublisherStatement() throws Exception {
        LOGGER.info("Test : Missing publisher statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setPublisher(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseNoPublisherStatementWithFlag() throws Exception {
        LOGGER.info("Test : Missing publisher statement");
        FDPMetadata metadata = getFdpMetadata();
        metadata.setPublisher(null);
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing id statement with missing triple, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseIDStatementWithMissingLiteral() throws Exception {
        LOGGER.info("Test : Missing ID literal");
        FDPMetadata metadata = getFdpMetadata();
        Identifier id = metadata.getIdentifier();
        id.setIdentifier(null);
        metadata.setIdentifier(id);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing id statement with missing triple, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseIDStatementWithMissingURI() throws Exception {
        LOGGER.info("Test : Missing ID uri");
        FDPMetadata metadata = getFdpMetadata();
        Identifier id = metadata.getIdentifier();
        id.setUri(null);
        metadata.setIdentifier(id);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing id statement with missing triple, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseIDStatementWithMissingType() throws Exception {
        LOGGER.info("Test : Missing ID type");
        FDPMetadata metadata = getFdpMetadata();
        Identifier id = metadata.getIdentifier();
        id.setType(null);
        metadata.setIdentifier(id);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing catalog rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNullThemeTaxonomyStatement() throws Exception {
        LOGGER.info("Test : Null theme taxonomy statement");
        CatalogMetadata metadata = getCatMetadata();
        metadata.setThemeTaxonomys(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing catalog rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseEmptyThemeTaxonomyStatement() throws Exception {
        LOGGER.info("Test : Empty theme taxonomy statement");
        CatalogMetadata metadata = getCatMetadata();
        metadata.setThemeTaxonomys(new ArrayList());
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseNoThemeTaxonomyStatementWithFlag() throws Exception {
        LOGGER.info("Test : Empty theme taxonomy statement");
        CatalogMetadata metadata = getCatMetadata();
        metadata.setThemeTaxonomys(new ArrayList());
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
        metadata.setThemeTaxonomys(null);
        expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing dataset rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNullThemeStatement() throws Exception {
        LOGGER.info("Test : Missing theme statement");
        DatasetMetadata metadata = getDatMetadata();
        metadata.setThemes(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing dataset rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseEmptyThemeStatement() throws Exception {
        LOGGER.info("Test : Missing theme statement");
        DatasetMetadata metadata = getDatMetadata();
        metadata.setThemes(new ArrayList());
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseEmptyThemeStatementWithFlag() throws Exception {
        LOGGER.info("Test : Missing theme statement");
        DatasetMetadata metadata = getDatMetadata();
        metadata.setThemes(new ArrayList());
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
        metadata.setThemes(null);
        expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing distribution rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testParseNoDowloadOrAccessStatement() throws Exception {
        LOGGER.info("Test : Missing download or access statement");
        DistributionMetadata metadata = getDisMetadata();
        metadata.setAccessURL(null);
        metadata.setDownloadURL(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testParseNoDowloadOrAccessStatementWithFlag() throws Exception {
        LOGGER.info("Test : Missing download or access statement");
        DistributionMetadata metadata = getDisMetadata();
        metadata.setAccessURL(null);
        metadata.setDownloadURL(null);
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing rdf statement, this test is expected to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = MetadataException.class)
    public void testGetDataRecordNoRMLURI() throws Exception {
        LOGGER.info("Test : Missing rml uri for  datarecord metadata");
        DataRecordMetadata metadata = getDatRecMetadata();
        metadata.setRmlURI(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDataRecordNoRMLURIWithFlag() throws Exception {
        LOGGER.info("Test : Missing rml uri for  datarecord metadata");
        DataRecordMetadata metadata = getDatRecMetadata();
        metadata.setRmlURI(null);
        List<Statement> expected = MetadataUtils.getStatements(metadata, false);
        assertNotNull(expected);
    }

    /**
     * Test missing rdf statement, this test is expected to pass
     *
     */
    public void testGetDataRecordNoRMLSource() throws Exception {
        LOGGER.info("Test : Missing rml source for  datarecord metadata");
        DataRecordMetadata metadata = getDatRecMetadata();
        metadata.setRmlInputSourceURI(null);
        MetadataUtils.getStatements(metadata);
    }

    /**
     * Test missing distribution rdf statement, this test is expected to pass
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testParseNoDowload() throws Exception {
        LOGGER.info("Test : Missing download url");
        DistributionMetadata metadata = getDisMetadata();
        metadata.setAccessURL(f.createIRI("http://example.com/accessuri"));
        metadata.setDownloadURL(null);
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * Test missing distribution rdf statement, this test is expected to pass
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testParseNoAccess() throws Exception {
        LOGGER.info("Test : Missing download url");
        DistributionMetadata metadata = getDisMetadata();
        metadata.setAccessURL(null);
        metadata.setDownloadURL(f.createIRI("http://example.com/downloaduri"));
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * This test is expected to pass an exception
     *
     * @throws Exception
     */
    @Test
    public void testGetFDPStatements() throws Exception {
        LOGGER.info("Test : Valid FDP metadata");
        FDPMetadata metadata = getFdpMetadata();
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetCatalogStatements() throws Exception {
        LOGGER.info("Test : Valid catalog metadata");
        CatalogMetadata metadata = getCatMetadata();
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDatasetStatements() throws Exception {
        LOGGER.info("Test : Valid dataset metadata");
        DatasetMetadata metadata = getDatMetadata();
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDistributionStatements() throws Exception {
        LOGGER.info("Test : Valid distribution metadata");
        DistributionMetadata metadata = getDisMetadata();
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDataRecordStatements() throws Exception {
        LOGGER.info("Test : Valid datarecord metadata");
        DataRecordMetadata metadata = getDatRecMetadata();
        List<Statement> out = MetadataUtils.getStatements(metadata);
        assertTrue(out.size() > 1);
    }

    /**
     * Test missing distribution rdf statement, this test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetRDFString() throws Exception {
        LOGGER.info("Test : Valid distribution metadata");
        DistributionMetadata metadata = getDisMetadata();
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE);
        assertFalse(out.isEmpty());
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDatasetSchemaDotOrgString() throws Exception {
        LOGGER.info("Test : Valid dataset metadata tranformation");
        DatasetMetadata metadata = getDatMetadata();
        String out = MetadataUtils.getString(metadata, RDFFormat.JSONLD,
                MetadataUtils.SCHEMA_DOT_ORG_MODEL);
        assertFalse(out.isEmpty());
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDIstributionSchemaDotOrgString() throws Exception {
        LOGGER.info("Test : Valid distribution metadata");
        DistributionMetadata metadata = getDisMetadata();
        String out = MetadataUtils.getString(metadata, RDFFormat.JSONLD,
                MetadataUtils.SCHEMA_DOT_ORG_MODEL);
        assertFalse(out.isEmpty());
    }

    /**
     * This test is expected to pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDisWithDownloadUrlSchemaDotOrg() throws Exception {
        LOGGER.info("Test : Valid distribution metadata");
        DistributionMetadata metadata = getDisMetadata();
        metadata.setAccessURL(null);
        metadata.setDownloadURL(f.createIRI("http://example.com/downloaduri"));
        metadata.setFormat(f.createLiteral("text/html"));
        String out = MetadataUtils.getString(metadata, RDFFormat.JSONLD,
                MetadataUtils.SCHEMA_DOT_ORG_MODEL);
        assertFalse(out.isEmpty());
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testGetCatalogStringNoMandatoryFileds() throws Exception {
        LOGGER.info("Test : Null or empty mandatory fields");
        CatalogMetadata metadata = getCatMetadata();
        metadata.setThemeTaxonomys(new ArrayList());
        metadata.setIdentifier(null);
        metadata.setLanguage(null);
        metadata.setLicense(null);
        metadata.setPublisher(null);
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE, false);
        assertFalse(out.isEmpty());
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDatasetStringNoMandatoryFileds() throws Exception {
        LOGGER.info("Test : Null or empty mandatory fields");
        DatasetMetadata metadata = getDatMetadata();
        metadata.setThemes(new ArrayList());
        metadata.setIdentifier(null);
        metadata.setLanguage(null);
        metadata.setLicense(null);
        metadata.setPublisher(null);
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE, false);
        assertFalse(out.isEmpty());
    }

    /**
     * Test missing rdf statement but the mandatory check is disabled, so this test is expected to
     * pass
     *
     * @throws Exception
     */
    @Test
    public void testGetDistributionStringNoMandatoryFileds() throws Exception {
        LOGGER.info("Test : Null or empty mandatory fields");
        DistributionMetadata metadata = getDisMetadata();
        metadata.setAccessURL(null);
        metadata.setDownloadURL(null);
        metadata.setIdentifier(null);
        metadata.setLanguage(null);
        metadata.setLicense(null);
        metadata.setPublisher(null);
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE, false);
        assertFalse(out.isEmpty());
    }
    
    /**
     * This test is expected to pass. Check metric uri in the rdf string
     *
     * @throws Exception
     */
    @Test
    public void testGetMetricString() throws Exception {
        FDPMetadata metadata = getFdpMetadata();
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE);
        assertTrue(out.contains(ExampleFilesUtils.METRIC1_URI.toString()));
    }

    /**
     * Example fdp metadata object
     */
    private FDPMetadata getFdpMetadata() {
        IRI fURI = ExampleFilesUtils.FDP_URI;
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.FDP_METADATA_FILE, ExampleFilesUtils.FDP_URI.toString());
        return FDP_PARSER.parse(stmts, fURI);
    }

    /**
     * Example catalog metadata object
     */
    private CatalogMetadata getCatMetadata() {
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.CATALOG_METADATA_FILE, ExampleFilesUtils.CATALOG_URI.toString());
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        return CAT_PARSER.parse(stmts, cURI);
    }

    /**
     * Example dataset metadata object
     */
    private DatasetMetadata getDatMetadata() {
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, ExampleFilesUtils.DATASET_URI.toString());
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        return DAT_PARSER.parse(stmts, dURI);
    }

    /**
     * Example distribution metadata object
     */
    private DistributionMetadata getDisMetadata() {
        List<Statement> stms = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DISTRIBUTION_METADATA_FILE,
                ExampleFilesUtils.DISTRIBUTION_URI.toString());
        IRI dURI = ExampleFilesUtils.DISTRIBUTION_URI;
        return DIS_PARSER.parse(stms, dURI);
    }

    /**
     * Example datarecord metadata object
     */
    private DataRecordMetadata getDatRecMetadata() {
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATARECORD_METADATA_FILE,
                ExampleFilesUtils.DATARECORD_URI.toString());
        IRI drURI = ExampleFilesUtils.DATARECORD_URI;
        return DAT_REC_PARSER.parse(stmts, drURI);
    }
    
    
}
