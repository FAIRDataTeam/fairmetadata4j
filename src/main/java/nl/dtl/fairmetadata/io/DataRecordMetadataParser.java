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
import nl.dtl.fairmetadata.model.DataRecordMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.FDP;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

/**
 * Parser for datarecord metadata
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-10-25
 * @version 0.1
 */
public class DataRecordMetadataParser extends 
        MetadataParser< DataRecordMetadata> {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DataRecordMetadataParser.class);

    @Override
    protected DataRecordMetadata createMetadata() {
        return new DataRecordMetadata();
    }
    
    /**
     * Parse RDF statements to datarecord metadata object
     * @param statements        List of RDF statement list
     * @param dataRecordURI     Datarecord URI
     * @return                  DataRecordMetadata object 
     */
    @Override
    public DataRecordMetadata parse(@Nonnull List<Statement> statements, 
            @Nonnull URI dataRecordURI)  {
        Preconditions.checkNotNull(dataRecordURI, 
                "Datarecord URI must not be null.");
        Preconditions.checkNotNull(statements, 
                "Datarecord statements must not be null.");
        LOGGER.info("Parsing distribution metadata");
        DataRecordMetadata metadata = super.parse(statements, 
                dataRecordURI);
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            Value object = st.getObject();
            
            if (subject.equals(dataRecordURI)) {
                if (predicate.equals(FDP.RML_MAPPING)) {
                    metadata.setRmlURI((URI) object);
                } else if (predicate.equals(FDP.REFERS_TO)) {
                    metadata.setDistributionURI((URI) object);
                } else if (predicate.equals(DCTERMS.ISSUED)) {
                    metadata.setDataRecordIssued(new LiteralImpl(object.
                            stringValue(), XMLSchema.DATETIME));
                } else if (predicate.equals(DCTERMS.MODIFIED)) {
                    metadata.setDataRecordModified(new LiteralImpl(object.
                            stringValue(), XMLSchema.DATETIME));
                }
            }
        }
        return metadata;
    }
    
    /**
     * Parse RDF string to datarecord metadata object
     * 
     * @param dataRecordMetadata  Datarecord metadata as a RDF string
     * @param dataRecordID        Datarecord ID
     * @param dataRecordURI       Datarecord URI
     * @param datasetURI            Dataset URI
     * @param format                RDF string's RDF format
     * @return                      DataRecordMetadata object 
     * @throws nl.dtl.fairmetadata.io.MetadataParserException 
     */
    public DataRecordMetadata parse (@Nonnull String dataRecordMetadata, 
            @Nonnull String dataRecordID, @Nonnull URI dataRecordURI, 
            URI datasetURI, @Nonnull RDFFormat format) 
            throws MetadataParserException {
        Preconditions.checkNotNull(dataRecordMetadata, 
                "Datarecord metadata string must not be null."); 
        Preconditions.checkNotNull(dataRecordID, 
                "Datarecord ID must not be null.");
        Preconditions.checkNotNull(dataRecordURI, 
                "Datarecord URI must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");
        
        Preconditions.checkArgument(!dataRecordMetadata.isEmpty(), 
                "The datarecord metadata content can't be EMPTY");
        Preconditions.checkArgument(!dataRecordID.isEmpty(), 
                "The datarecord id content can't be EMPTY");        
        try {
            Model modelDistribution = Rio.parse(new StringReader(dataRecordMetadata), 
                    dataRecordURI.stringValue(), 
                    format);
            Iterator<Statement> it = modelDistribution.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            
            DataRecordMetadata metadata = this.parse(statements, 
                    dataRecordURI);
//            metadata.setIdentifier(new LiteralImpl(dataRecordID, 
//                    XMLSchema.STRING));
            metadata.setParentURI(datasetURI);
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading datarecord metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing datarecord metadata content. "
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (UnsupportedRDFormatException ex) {
            String errMsg = "Unsuppoerted RDF format. " + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        }        
    }
    
    /**
     * Parse RDF string to dataset dataRecordMetadata object
     *
     * @param dataRecordMetadata Datarecord metadata as a RDF string
     * @param baseURI
     * @param format RDF string's RDF format
     * @return DataRecordMetadata object
     * @throws MetadataParserException
     */
    public DataRecordMetadata parse(@Nonnull String dataRecordMetadata,
            URI baseURI, @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(dataRecordMetadata,
                "Datarecord metadata string must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!dataRecordMetadata.isEmpty(),
                "The datarecord metadata content can't be EMPTY");
        try {
            Model modelCatalog;
            if (baseURI != null) {
                modelCatalog = Rio.parse(new StringReader(dataRecordMetadata),
                        baseURI.stringValue(), format);
            } else {
                modelCatalog = Rio.parse(new StringReader(
                        dataRecordMetadata), "", format);
            }
            Iterator<Statement> it = modelCatalog.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            URI catalogURI = (URI) statements.get(0).getSubject();
            DataRecordMetadata metadata = this.parse(statements, catalogURI);
            metadata.setUri(null);
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading datarecord metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            if (ex.getMessage().contains("Not a valid (absolute) URI")) {
                String dummyURI = "http://example.com/dummyResource";
                return parse(dataRecordMetadata, new URIImpl(dummyURI), 
                        format);
            }
            String errMsg = "Error parsing datarecord metadata content. "
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
