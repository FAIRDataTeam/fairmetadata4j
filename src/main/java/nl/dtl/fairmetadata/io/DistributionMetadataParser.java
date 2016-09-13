/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.datatype.DatatypeConfigurationException;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.DCAT;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Literal;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

/**
 * Parser for distribution metadata
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-07
 * @version 0.1
 */
public class DistributionMetadataParser extends MetadataParser
        <DistributionMetadata> {
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DatasetMetadataParser.class);
    
    @Override
    protected DistributionMetadata createMetadata() {
        return new DistributionMetadata();
    }
    
    /**
     * Parse RDF statements to distribution metadata object
     * @param statements        List of RDF statement list
     * @param distributionURI   Distribution URI
     * @return                  DistributionMetadata object 
     */
    @Override
    public DistributionMetadata parse(@Nonnull List<Statement> statements, 
            @Nonnull URI distributionURI)  {
        Preconditions.checkNotNull(distributionURI, 
                "Distribution URI must not be null.");
        Preconditions.checkNotNull(statements, "Distribution statements must not be null.");
        LOGGER.info("Parsing distribution metadata");
        DistributionMetadata metadata = super.parse(statements, 
                distributionURI);
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            if (subject.equals(distributionURI)) {
                if (predicate.equals(DCAT.ACCESS_URL)) {
                    URI accessURL = (URI) st.getObject();
                    metadata.setAccessURL(accessURL);
                } else if (predicate.equals(DCAT.DOWNLOAD_URL)) {
                    URI downloadURL = (URI) st.getObject();
                    metadata.setDownloadURL(downloadURL);
                } else if (predicate.equals(DCAT.FORMAT)) {
                     Literal format = new LiteralImpl(st.getObject().
                            stringValue(), XMLSchema.STRING);                    
                     metadata.setFormat(format);
                } else if (predicate.equals(DCAT.BYTE_SIZE)) {
                     Literal byteSize = new LiteralImpl(st.getObject().
                            stringValue(), XMLSchema.STRING);                    
                     metadata.setByteSize(byteSize);
                } else if (predicate.equals(DCAT.MEDIA_TYPE)) {
                     Literal mediaType = new LiteralImpl(st.getObject().
                            stringValue(), XMLSchema.STRING);                    
                     metadata.setMediaType(mediaType);
                }
            }
        }
        return metadata;
    }
    
    /**
     * Parse RDF string to distribution metadata object
     * 
     * @param distributionMetadata  Distribution metadata as a RDF string
     * @param distributionID        Distribution ID
     * @param distributionURI       Distribution URI
     * @param datasetURI            Dataset URI
     * @param format                RDF string's RDF format
     * @return                      DistributionMetadata object
     * @throws  DatatypeConfigurationException 
     * @throws nl.dtl.fairmetadata.io.MetadataParserException 
     */
    public DistributionMetadata parse (@Nonnull String distributionMetadata, 
            @Nonnull String distributionID, @Nonnull URI distributionURI, 
            URI datasetURI, @Nonnull RDFFormat format) 
            throws DatatypeConfigurationException, MetadataParserException {
        Preconditions.checkNotNull(distributionMetadata, 
                "Distribution metadata string must not be null."); 
        Preconditions.checkNotNull(distributionID, 
                "Distribution ID must not be null.");
        Preconditions.checkNotNull(distributionURI, 
                "Distribution URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        
        Preconditions.checkArgument(!distributionMetadata.isEmpty(), "The distribution metadata content can't be EMPTY");
        Preconditions.checkArgument(!distributionID.isEmpty(), "The distribution id content can't be EMPTY");        
        try {
            Model modelDistribution = Rio.parse(new StringReader(distributionMetadata), distributionURI.stringValue(), 
                    format);
            Iterator<Statement> it = modelDistribution.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            
            DistributionMetadata metadata = this.parse(statements, distributionURI);
            metadata.setIdentifier(new LiteralImpl(distributionID, 
                    XMLSchema.STRING));
            metadata.setParentURI(datasetURI);
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading distribution metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing distribution metadata content. "
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        }        
    }
    
}
