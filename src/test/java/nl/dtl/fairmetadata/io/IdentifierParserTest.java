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
package nl.dtl.fairmetadata.io;

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata.model.Identifier;
import nl.dtl.fairmetadata.utils.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for IdentifierParser.
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-11-30
 * @version 0.1
 */
public class IdentifierParserTest {
    
    /**
     * Test null id uri, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullIDUri() throws Exception {
        System.out.println("Test : Parse null id uri");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI identifierURI = null;
        IdentifierParser.parse(statements, identifierURI);
        fail("This test is execpeted to throw an error");
    }
    
    /**
     * Test null rdf statements, this test is expected to throw exception
     * @throws Exception 
     */
    @Test(expected = NullPointerException.class)
    public void testParseNullStatements() throws Exception {
        System.out.println("Test : Parse null statements");
        List<Statement> statements = null;
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_ID_URI;
        IdentifierParser.parse(statements, identifierURI);
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
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_ID_URI;
        IdentifierParser.parse(statements, identifierURI);
        fail("This test is execpeted to throw an error");
    }
    
    
    /**
     * Test valid metadata id.
     */
    @Test
    public void testValidMetadataID() {
        System.out.println("Parse fdp metadata ID");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI identifierURI = ExampleFilesUtils.FDP_METADATA_ID_URI;
        Identifier result = IdentifierParser.parse(statements, identifierURI);
        assertNotNull(result);
    }
    
    /**
     * Test valid repo id.
     */
    @Test
    public void testValidRepoID() {
        System.out.println("Parse fdp repo ID");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.FDP_METADATA_FILE, 
                        ExampleFilesUtils.FDP_URI.toString());
        IRI identifierURI = ExampleFilesUtils.FDP_REPO_ID_URI;
        Identifier result = IdentifierParser.parse(statements, identifierURI);
        assertNotNull(result);
    }
    
}
