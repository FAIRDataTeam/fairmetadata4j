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

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata4j.model.AccessRights;
import nl.dtl.fairmetadata4j.model.DatasetMetadata;
import nl.dtl.fairmetadata4j.model.DistributionMetadata;
import nl.dtl.fairmetadata4j.utils.ExampleFilesUtils;
import nl.dtl.fairmetadata4j.utils.MetadataUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for IdentifierParser.
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-02-22
 * @version 0.1
 */
public class AccessRightsParserTest {
    
    /**
     * Test null accessRights uri, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullAccessRightsUri() throws Exception {
        System.out.println("Test : Parse null id uri");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.
                        DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI.toString());
        AccessRightsParser.parse(statements, null);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null rdf statements, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullStatements() throws Exception {
        System.out.println("Test : Parse null statements");
        IRI uri = ExampleFilesUtils.BIOSEM_ACCESS_RIGHTS_URI;
        AccessRightsParser.parse(null, uri);
        fail("This test is execpeted to throw an error");
    }
    
     /**
     * Test empty rdf statements, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyStatements() throws Exception {
        System.out.println("Test : Parse empty statements");
        List<Statement> statements = new ArrayList();
        IRI uri = ExampleFilesUtils.BIOSEM_ACCESS_RIGHTS_URI;
        AccessRightsParser.parse(statements, uri);
        fail("This test is execpeted to throw an error");
    }
    
    
    /**
     * Test valid accessRights.
     */
    @Test
    public void testValidMetadataAccessRights() {
        System.out.println("Parse dataset accessRights");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.
                        DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI.toString());
        IRI uri = ExampleFilesUtils.BIOSEM_ACCESS_RIGHTS_URI;
        AccessRights result = AccessRightsParser.parse(statements, uri);
        assertNotNull(result);
    }
    
    /**
     * Test existence of rights rdf statement, this test is excepted to pass
     * 
     * @throws Exception 
     */
    @Test
    public void testGetRDFString() throws Exception {        
        System.out.println("Test : Valid dataset metadata with accessRights");
        DatasetMetadataParser parser = new DatasetMetadataParser();
        List<Statement> stmts = ExampleFilesUtils.getFileContentAsStatements(
                ExampleFilesUtils.DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI.toString());
        IRI disURI = ExampleFilesUtils.DATASET_URI;
        DatasetMetadata metadata = parser.parse(stmts , disURI);             
        String out = MetadataUtils.getString(metadata, RDFFormat.TURTLE);
        assertTrue(out.contains("accessRights"));
    }
    
}
