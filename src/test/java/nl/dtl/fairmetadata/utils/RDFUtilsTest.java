/**
 * The MIT License
 * Copyright © 2016 DTL
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
package nl.dtl.fairmetadata.utils;

import nl.dtl.fairmetadata.io.MetadataParserException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rajaram
 */
public class RDFUtilsTest {
    
    /**
     * Test of getCurrentTime method, of class RDFUtils.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetCurrentTime() throws Exception {
        System.out.println("getCurrentTime");
        assertNotNull(RDFUtils.getCurrentTime());
    }
    
    /**
     * Invalid rdf string 
     * @throws java.lang.Exception
     */
    @Test(expected = MetadataParserException.class)
    public void testInvalidRdfStr() throws Exception {
        String input = "<sub> pred> <obj> .";
        System.out.println("Invalid rdf string");
        RDFUtils.getStatements(input, null, RDFFormat.TURTLE);
    }
    
    /**
     * Valid rdf string 
     * @throws java.lang.Exception
     */
    @Test
    public void testValidRdfStr() throws Exception {
        String input = "<sub> <pred> <obj> .";
        System.out.println("Valid rdf string");
        assertNotNull(RDFUtils.getStatements(input, null, RDFFormat.TURTLE));
    }
}
