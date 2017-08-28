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
package nl.dtl.fairmetadata4j.io;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtl.fairmetadata4j.model.AccessRights;
import nl.dtl.fairmetadata4j.utils.RDFUtils;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

/**
 * Parser for accessRights object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2017-02-22
 * @version 0.1
 */
public class AccessRightsParser {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(AccessRightsParser.class);

    public static AccessRights parse(@Nonnull List<Statement> statements,
            @Nonnull IRI accessRightsURI) {
        Preconditions.checkNotNull(accessRightsURI, "AccessRights URI must not be null.");
        Preconditions.checkNotNull(statements, "AccessRights statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(), "AccessRights statements must not be "
                + "empty.");
        LOGGER.info("Parsing accessRights");
        AccessRights accessRights = new AccessRights();
        accessRights.setUri(accessRightsURI);
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            // To fix codacy bot issues are combining the if conditions
            if (subject.equals(accessRightsURI) && predicate.equals(DCTERMS.IS_PART_OF)) {
                RDFUtils.checkNotLiteral(object);
                accessRights.setAuthorization(AuthorizationParser.parse(statements,
                        (IRI) object));
            }            
        }
        return accessRights;
    }
}
