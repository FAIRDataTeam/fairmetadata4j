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
package nl.dtl.fairmetadata4j.io;

import java.util.List;
import nl.dtl.fairmetadata4j.model.CatalogMetadata;
import nl.dtl.fairmetadata4j.utils.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for CatalogMetadataParser.
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-08
 * @version 0.1
 */
public class CatalogMetadataParserTest {

    private final CatalogMetadataParser PARSER = new CatalogMetadataParser();
    private final String MDATA_STR = ExampleFilesUtils.getFileContentAsString(
            ExampleFilesUtils.CATALOG_METADATA_FILE);
    private final List<Statement> STMTS = ExampleFilesUtils.getFileContentAsStatements(
            ExampleFilesUtils.CATALOG_METADATA_FILE, ExampleFilesUtils.CATALOG_URI.toString());

    /**
     * Test null RDF string, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFString() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        IRI fURI = ExampleFilesUtils.FDP_URI;
        PARSER.parse(null, cURI, fURI, ExampleFilesUtils.FILE_FORMAT);
    }

    /**
     * Test empty RDF string, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyRDFString() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        IRI fURI = ExampleFilesUtils.FDP_URI;
        PARSER.parse("", cURI, fURI, ExampleFilesUtils.FILE_FORMAT);
    }

    /**
     * Test null RDFFormat, this test is expected to throw exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullRDFFormat() throws Exception {
        System.out.println("Test : Parse invalid catalog content");
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        IRI fURI = ExampleFilesUtils.FDP_URI;
        PARSER.parse(MDATA_STR, cURI, fURI, null);
    }

    /**
     * Test valid catalog MDATA_STR rdf file
     *
     * @throws Exception
     */
    @Test
    public void testParseFile() throws Exception {
        System.out.println("Test : Parse valid catalog content");
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        IRI fURI = ExampleFilesUtils.FDP_URI;
        CatalogMetadata mdata = PARSER.parse(MDATA_STR, cURI, fURI,
                ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(mdata);
    }

    /**
     * Test null catalog URI, this test is excepted to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullCatalogURI() throws Exception {
        System.out.println("Test : Missing catalog URL");
        PARSER.parse(STMTS, null);
    }

    /**
     * Test null statements, this test is excepted to throw an exception
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void testNullStatements() throws Exception {
        System.out.println("Test : Parse valid catalog content");
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        PARSER.parse(null, cURI);
    }

    /**
     * Test valid catalog rdf statements
     *
     * @throws Exception
     */
    @Test
    public void testParseStatements() throws Exception {
        System.out.println("Test : Parse valid catalog content");
        IRI cURI = ExampleFilesUtils.CATALOG_URI;
        CatalogMetadata metadata = PARSER.parse(STMTS, cURI);
        assertNotNull(metadata);
    }

    /**
     * Test valid catalog MDATA_STR rdf file, with no base
     *
     * @throws Exception
     */
    @Test
    public void testParseFileWithNoBase() throws Exception {
        System.out.println("Test : Parse valid catalog content with no base uri");
        CatalogMetadata mdata = PARSER.parse(MDATA_STR, null, ExampleFilesUtils.FILE_FORMAT);
        assertNotNull(mdata);
    }
}
