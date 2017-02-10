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

import nl.dtl.fairmetadata4j.utils.MetadataParserUtils;
import nl.dtl.fairmetadata4j.io.CatalogMetadataParser;
import nl.dtl.fairmetadata4j.io.DataRecordMetadataParser;
import nl.dtl.fairmetadata4j.io.DatasetMetadataParser;
import nl.dtl.fairmetadata4j.io.DistributionMetadataParser;
import nl.dtl.fairmetadata4j.io.FDPMetadataParser;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for MetadataParserUtils class
 * 
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 */
public class MetadataParserUtilsTest {

    /**
     * Test of getFdpParser method, of class MetadataParserUtils.
     */
    @Test
    public void testGetFdpParser() {
        System.out.println("getFdpParser");
        assertThat(MetadataParserUtils.getFdpParser(), 
                instanceOf(FDPMetadataParser.class));
    }

    /**
     * Test of getCatalogParser method, of class MetadataParserUtils.
     */
    @Test
    public void testGetCatalogParser() {
        System.out.println("getCatalogParser");
        assertThat(MetadataParserUtils.getCatalogParser(), 
                instanceOf(CatalogMetadataParser.class));
    }

    /**
     * Test of getDatasetParser method, of class MetadataParserUtils.
     */
    @Test
    public void testGetDatasetParser() {
        System.out.println("getDatasetParser");
        assertThat(MetadataParserUtils.getDatasetParser(), 
                instanceOf(DatasetMetadataParser.class));
    }

    /**
     * Test of getDistributionParser method, of class MetadataParserUtils.
     */
    @Test
    public void testGetDistributionParser() {
        System.out.println("getDistributionParser");
        assertThat(MetadataParserUtils.getDistributionParser(), 
                instanceOf(DistributionMetadataParser.class));
    }

    /**
     * Test of getDataRecordParser method, of class MetadataParserUtils.
     */
    @Test
    public void testGetDataRecordParser() {
        System.out.println("getDataRecordParser");
        assertThat(MetadataParserUtils.getDataRecordParser(), 
                instanceOf(DataRecordMetadataParser.class));
    }
    
}
