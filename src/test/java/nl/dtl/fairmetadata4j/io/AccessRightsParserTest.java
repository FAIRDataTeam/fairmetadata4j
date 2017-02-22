/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata4j.io;

import java.util.ArrayList;
import java.util.List;
import nl.dtl.fairmetadata4j.model.AccessRights;
import nl.dtl.fairmetadata4j.utils.ExampleFilesUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
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
    public void testValidMetadataID() {
        System.out.println("Parse dataset accessRights");
        List<Statement> statements = ExampleFilesUtils.
                getFileContentAsStatements(ExampleFilesUtils.
                        DATASET_METADATA_FILE, 
                        ExampleFilesUtils.DATASET_URI.toString());
        IRI uri = ExampleFilesUtils.BIOSEM_ACCESS_RIGHTS_URI;
        AccessRights result = AccessRightsParser.parse(statements, uri);
        assertNotNull(result);
    }
    
}
