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
package nl.dtls.fairmetadata4j.io;

import java.util.List;
import nl.dtls.fairmetadata4j.model.DatasetMetadata;
import nl.dtls.fairmetadata4j.utils.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for DatasetMetadataParser.
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-09
 * @version 0.1
 */
public class DatasetMetadataParserTest {

    private final DatasetMetadataParser PARSER = new DatasetMetadataParser();
    private final String MDATA_STR = ExampleFilesUtils.getFileContentAsString(
            ExampleFilesUtils.DATASET_METADATA_FILE);
    private final List<Statement> STMTS = ExampleFilesUtils.getFileContentAsStatements(
            ExampleFilesUtils.DATASET_METADATA_FILE, ExampleFilesUtils.DATASET_URI.toString());

    /**
     * Test null RDF string, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFString() throws Exception {
        System.out.println("Test : Parse invalid dataset content");
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        PARSER.parse(null, dURI, cURI, ExampleFilesUtils.FILE_FORMAT);
    }

    /**
     * Test empty RDF string, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyRDFString() throws Exception {
        System.out.println("Test : Parse invalid dataset content");
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        PARSER.parse("", dURI, cURI, ExampleFilesUtils.FILE_FORMAT);
    }

    /**
     * Test null RDFFormat, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFFormat() throws Exception {
        System.out.println("Test : Parse invalid dataset content");
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        PARSER.parse(MDATA_STR, dURI, cURI, null);
    }

    /**
     * Test valid dataset metadata rdf file
     *
     * @throws Exception
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("Test : Parse valid dataset content");
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        DatasetMetadata metadata = PARSER.parse(MDATA_STR, dURI, cURI,
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }

    /**
     * Test null dataset URI, this test is excepted to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullDatasetURI() throws Exception {
        System.out.println("Test : Missing dataset URL");
        PARSER.parse(STMTS, null);
    }

    /**
     * Test null statements, this test is excepted to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullStatements() throws Exception {
        System.out.println("Test : Parse valid dataset content");
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        PARSER.parse(null, dURI);
    }

    /**
     * Test valid dataset rdf statements
     *
     * @throws Exception
     */
    @Test
    public void testParseStatements() throws Exception {
        System.out.println("Test : Parse valid dataset content");
        IRI dURI = ExampleFilesUtils.DATASET_URI;
        DatasetMetadata metadata = PARSER.parse(STMTS, dURI);
        assertNotNull(metadata);
    }

    /**
     * Test valid dataset metadata rdf file, with no base
     *
     * @throws Exception
     */
    @Test
    public void testParseFileWithNoBase() throws Exception {
        System.out.println("Test : Parse valid dataset content with no base uri");
        DatasetMetadata metadata = PARSER.parse(MDATA_STR, null, ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(metadata);
    }
}
