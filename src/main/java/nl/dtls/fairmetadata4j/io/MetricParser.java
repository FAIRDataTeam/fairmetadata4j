/**
 * The MIT License
 * Copyright © 2019 DTL
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
package nl.dtls.fairmetadata4j.io;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import nl.dtls.fairmetadata4j.model.Metric;
import nl.dtls.fairmetadata4j.utils.RDFUtils;
import nl.dtls.fairmetadata4j.utils.vocabulary.Sio;
import org.apache.logging.log4j.LogManager;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;

/**
 * Parser for metrics object
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2018-01-16
 * @version 0.1
 */
public class MetricParser {
    
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(MetricParser.class);
    
    public static Metric parse(@Nonnull List<Statement> statements,
            @Nonnull IRI metricURI) {
        Preconditions.checkNotNull(metricURI, "Metric URI must not be null.");
        Preconditions.checkNotNull(statements, "Metric statements must not be null.");
        Preconditions.checkArgument(!statements.isEmpty(), "Mertic statements must not be "
                + "empty.");
        LOGGER.info("Parsing accessRights");
        Metric metric = new Metric();
        for (Statement st : statements) {
            Resource subject = st.getSubject();
            IRI predicate = st.getPredicate();
            Value object = st.getObject();
            // To fix codacy bot issues are combining the if conditions
            if (subject.equals(metricURI) && predicate.equals(Sio.IS_ABOUT)) {
                RDFUtils.checkNotLiteral(object);
                metric.setMetricType((IRI) object);
            }   
            else if (subject.equals(metricURI) && predicate.equals(Sio.REFERS_TO)) {
                RDFUtils.checkNotLiteral(object);
                metric.setValue((IRI) object);
                // We set metric uri only when we have the value for the metric
                metric.setUri(metricURI);
            }
        }
        return metric;
    }
    
}
