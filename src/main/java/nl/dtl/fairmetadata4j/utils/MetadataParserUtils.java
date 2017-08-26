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

import nl.dtl.fairmetadata4j.io.CatalogMetadataParser;
import nl.dtl.fairmetadata4j.io.DataRecordMetadataParser;
import nl.dtl.fairmetadata4j.io.DatasetMetadataParser;
import nl.dtl.fairmetadata4j.io.DistributionMetadataParser;
import nl.dtl.fairmetadata4j.io.FDPMetadataParser;

/**
 * Provides metadata parser instances
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-13
 * @version 0.1
 */
public class MetadataParserUtils {

    private static final FDPMetadataParser FDP_PARSER = new FDPMetadataParser();
    private static final CatalogMetadataParser CATALOG_PARSER = new CatalogMetadataParser();
    private static final DatasetMetadataParser DATASET_PARSER = new DatasetMetadataParser();
    private static final DistributionMetadataParser DISTRIBUTION_PARSER
            = new DistributionMetadataParser();
    private static final DataRecordMetadataParser DATA_RECORD_PARSER
            = new DataRecordMetadataParser();

    private MetadataParserUtils() {
    }

    /**
     * @return the FDP_PARSER
     */
    public static FDPMetadataParser getFdpParser() {
        return FDP_PARSER;
    }

    /**
     * @return the CATALOG_PARSER
     */
    public static CatalogMetadataParser getCatalogParser() {
        return CATALOG_PARSER;
    }

    /**
     * @return the DATASET_PARSER
     */
    public static DatasetMetadataParser getDatasetParser() {
        return DATASET_PARSER;
    }

    /**
     * @return the DISTRIBUTION_PARSER
     */
    public static DistributionMetadataParser getDistributionParser() {
        return DISTRIBUTION_PARSER;
    }

    /**
     * @return the DATA_RECORD_PARSER
     */
    public static DataRecordMetadataParser getDataRecordParser() {
        return DATA_RECORD_PARSER;
    }

}
