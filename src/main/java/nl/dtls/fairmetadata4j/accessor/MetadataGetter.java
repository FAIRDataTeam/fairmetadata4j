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
package nl.dtls.fairmetadata4j.accessor;

import nl.dtls.fairmetadata4j.model.AccessRights;
import nl.dtls.fairmetadata4j.model.Agent;
import nl.dtls.fairmetadata4j.model.Identifier;
import nl.dtls.fairmetadata4j.model.Metric;
import nl.dtls.fairmetadata4j.parser.AccessRightsParser;
import nl.dtls.fairmetadata4j.parser.AgentParser;
import nl.dtls.fairmetadata4j.parser.IdentifierParser;
import nl.dtls.fairmetadata4j.parser.MetricParser;
import nl.dtls.fairmetadata4j.util.ValueFactoryHelper;
import nl.dtls.fairmetadata4j.vocabulary.FDP;
import nl.dtls.fairmetadata4j.vocabulary.R3D;
import nl.dtls.fairmetadata4j.vocabulary.Sio;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.vocabulary.DCAT;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static nl.dtls.fairmetadata4j.util.RDFUtil.*;
import static nl.dtls.fairmetadata4j.util.ValueFactoryHelper.i;
import static nl.dtls.fairmetadata4j.util.ValueFactoryHelper.l;

public class MetadataGetter {

    public static IRI getUri(Model metadata) {
        return i(getSubjectBy(metadata, DCTERMS.TITLE, null));
    }

    public static Identifier getIdentifier(Model metadata) {
        IRI identifierUri = i(getObjectBy(metadata, null, FDP.METADATAIDENTIFIER));
        return identifierUri != null ? IdentifierParser.parse(new ArrayList<>(metadata), identifierUri) : null;
    }

    public static Literal getTitle(Model metadata) {
        return l(getObjectBy(metadata, null, DCTERMS.TITLE));
    }

    public static IRI getParent(Model metadata) {
        return i(getObjectBy(metadata, null, DCTERMS.IS_PART_OF));
    }

    public static IRI getSpecification(Model metadata) {
        return i(getObjectBy(metadata, null, DCTERMS.CONFORMS_TO));
    }

    public static IRI getLanguage(Model metadata) {
        return i(getObjectBy(metadata, null, DCTERMS.LANGUAGE));
    }

    public static IRI getLicence(Model metadata) {
        return i(getObjectBy(metadata, null, DCTERMS.LICENSE));
    }

    public static Agent getPublisher(Model metadata) {
        IRI publisherUri = i(getObjectBy(metadata, null, DCTERMS.PUBLISHER));
        return publisherUri != null ? AgentParser.parse(new ArrayList<>(metadata), publisherUri) : null;
    }

    public static AccessRights getAccessRights(Model metadata) {
        IRI arUri = i(getObjectBy(metadata, null, DCTERMS.ACCESS_RIGHTS));
        return arUri != null ? AccessRightsParser.parse(new ArrayList<>(metadata), arUri) : null;
    }

    public static LocalDateTime getIssued(Model metadata) {
        String result = getStringObjectBy(metadata, null, FDP.METADATAISSUED);
        return result != null ? LocalDateTime.parse(result) : null;
    }

    public static LocalDateTime getModified(Model metadata) {
        String result = getStringObjectBy(metadata, null, FDP.METADATAMODIFIED);
        return result != null ? LocalDateTime.parse(result) : null;
    }

    // ------------------------------------------------------------------------------------------------------------
    //  Custom
    // ------------------------------------------------------------------------------------------------------------
    public static IRI getRepositoryIdentifier(Model metadata) {
        return i(getObjectBy(metadata, null, R3D.REPOSITORYIDENTIFIER));
    }

    public static List<IRI> getCatalogs(Model metadata) {
        return getObjectsBy(metadata, null, R3D.DATACATALOG)
                .stream()
                .map(ValueFactoryHelper::i)
                .collect(Collectors.toList());
    }

    public static List<IRI> getDatasets(Model metadata) {
        return getObjectsBy(metadata, null, DCAT.HAS_DATASET)
                .stream()
                .map(ValueFactoryHelper::i)
                .collect(Collectors.toList());
    }

    public static List<IRI> getDistributions(Model metadata) {
        return getObjectsBy(metadata, null, DCAT.HAS_DISTRIBUTION)
                .stream()
                .map(ValueFactoryHelper::i)
                .collect(Collectors.toList());
    }

    public static LocalDateTime getDataRecordModified(Model metadata) {
        String result = getStringObjectBy(metadata, null, DCTERMS.MODIFIED);
        return result != null ? LocalDateTime.parse(result) : null;
    }

    public static List<IRI> getThemeTaxonomies(Model metadata) {
        return getObjectsBy(metadata, null, DCAT.THEME_TAXONOMY)
                .stream()
                .map(ValueFactoryHelper::i)
                .collect(Collectors.toList());
    }

    public static List<Metric> getMetrics(Model metadata) {
        return getObjectsBy(metadata, null, Sio.REFERS_TO)
                .stream()
                .map(m -> MetricParser.parse(new ArrayList<>(metadata), i(m)))
                .collect(Collectors.toList());
    }

}
