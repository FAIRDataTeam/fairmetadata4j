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
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata.model.FDPMetadata;
import nl.dtl.fairmetadata.utils.vocabulary.R3D;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-07
 * @version 0.1
 */
public class FDPMetadataParser extends MetadataParser<FDPMetadata> {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DatasetMetadataParser.class);
    
    @Override
    protected FDPMetadata createMetadata() {
        return new FDPMetadata();
    }
    
    @Override
    public FDPMetadata parse(List<Statement> statements, IRI fdpURI) {
        FDPMetadata metadata = super.parse(statements, fdpURI);
        List<IRI> catalogs = new ArrayList();
        ValueFactory f = SimpleValueFactory.getInstance();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            
            if (subject.equals(fdpURI)) {
                if (predicate.equals(FOAF.HOMEPAGE)) {
                    metadata.setHomepage((IRI) object);
                } else if (predicate.equals(RDFS.SEEALSO)) {
                    metadata.setSwaggerDoc((IRI) object);
                } else if (predicate.equals(R3D.DATA_CATALOG)) {
                    catalogs.add((IRI) object);
                } else if (predicate.equals(R3D.REPO_IDENTIFIER)) {
                    metadata.setRepostoryIdentifier(IdentifierParser.parse(
                            statements, (IRI)object));
                } else if (predicate.equals(R3D.INSTITUTION_COUNTRY)) {
                    metadata.setInstitutionCountry((IRI) object);
                } else if (predicate.equals(R3D.REPO_START_DATE)) {
                    metadata.setStartDate((f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME)));
                } else if (predicate.equals(R3D.REPO_LAST_UPDATE)) {
                    metadata.setLastUpdate((f.createLiteral(object.
                            stringValue(), XMLSchema.DATETIME)));
                } else if (predicate.equals(R3D.INSTITUTION)) {
                    metadata.setInstitution(AgentParser.parse(
                            statements, (IRI)object));
                }
            }
        }
        if(!catalogs.isEmpty()) {
            metadata.setCatalogs(catalogs);
        }
        return metadata;
    }
    
    /**
     * Parse RDF string to dataset fdpMetadata object
     *
     * @param fdpMetadata FDP fdpMetadata as a RDF string
     * @param baseURI
     * @param format RDF string's RDF format
     * @return FDPMetadata object
     * @throws MetadataParserException
     */
    public FDPMetadata parse(@Nonnull String fdpMetadata,
            IRI baseURI, @Nonnull RDFFormat format)
            throws MetadataParserException {
        Preconditions.checkNotNull(fdpMetadata,
                "FDP metadata string must not be null.");
        Preconditions.checkNotNull(format, "RDF format must not be null.");

        Preconditions.checkArgument(!fdpMetadata.isEmpty(),
                "The fdp metadata content can't be EMPTY");
        try {
            Model modelFDP;
            if (baseURI != null) {
                modelFDP = Rio.parse(new StringReader(fdpMetadata),
                        baseURI.stringValue(), format);
            } else {
                String dummyURI = "http://example.com/dummyResource";
                modelFDP = Rio.parse(new StringReader(
                        fdpMetadata), dummyURI, format);
            }
            Iterator<Statement> it = modelFDP.iterator();
            List<Statement> statements = ImmutableList.copyOf(it);
            IRI fdpURI = (IRI) statements.get(0).getSubject();
            FDPMetadata metadata = this.parse(statements, fdpURI);
            metadata.setUri(null);
            return metadata;
        } catch (IOException ex) {
            String errMsg = "Error reading fdp metadata content"
                    + ex.getMessage();
            LOGGER.error(errMsg);
            throw (new MetadataParserException(errMsg));
        } catch (RDFParseException ex) {
            String errMsg = "Error parsing fdp metadata content. "
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
