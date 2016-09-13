/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils;

import nl.dtl.fairmetadata.io.CatalogMetadataParser;
import nl.dtl.fairmetadata.io.DatasetMetadataParser;
import nl.dtl.fairmetadata.io.DistributionMetadataParser;
import nl.dtl.fairmetadata.io.FDPMetadataParser;

/**
 * Provides metadata parser instances 
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-13
 * @version 0.1
 */
public class MetadataParserUtils {
    
    private static final FDPMetadataParser fdpParser = new FDPMetadataParser();
    private static final CatalogMetadataParser catalogParser = 
            new CatalogMetadataParser();
    private static final DatasetMetadataParser datasetParser = 
            new DatasetMetadataParser();
    private static final DistributionMetadataParser distributionParser = 
            new DistributionMetadataParser();

    /**
     * @return the fdpParser
     */
    public static FDPMetadataParser getFdpParser() {
        return fdpParser;
    }

    /**
     * @return the catalogParser
     */
    public static CatalogMetadataParser getCatalogParser() {
        return catalogParser;
    }

    /**
     * @return the datasetParser
     */
    public static DatasetMetadataParser getDatasetParser() {
        return datasetParser;
    }

    /**
     * @return the distributionParser
     */
    public static DistributionMetadataParser getDistributionParser() {
        return distributionParser;
    }
    
}
