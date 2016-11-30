/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.io;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata.model.Identifier;
import org.apache.logging.log4j.LogManager;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-11-30
 * @version 0.1
 */
public class IdentifierParser {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(IdentifierParser.class);

    public static Identifier parse(@Nonnull List<Statement> statements,
            @Nonnull URI identifierURI) {
        Preconditions.checkNotNull(identifierURI,
                "Identifier URI must not be null.");
        Preconditions.checkNotNull(statements,
                "Identifier statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(),
                "Identifier statements must not be empty.");
        LOGGER.info("Parsing identifier");
        

        Identifier id = new Identifier();
        id.setUri(identifierURI);
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            URI predicate = st.getPredicate();
            Value object = st.getObject();

            if (subject.equals(identifierURI)) {
                if (predicate.equals(RDF.TYPE)) {
                    id.setType((URI) object);
                } else if (predicate.equals(DCTERMS.IDENTIFIER)) {
                    id.setIdentifier(new LiteralImpl(object.stringValue(),
                            XMLSchema.STRING));
                }
            }
        }
        return id;
    }
}
