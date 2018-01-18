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

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

/**
 * Contains references to the example metadata rdf files which are used in the Junit tests.
 *
 * @author Rajaram Kaliyaperumal <rr.kaliyaperumal@gmail.com>
 * @author Kees Burger <kees.burger@dtls.nl>
 * @since 2016-09-08
 * @version 0.1
 */
public class ExampleFilesUtils {

    private final static Logger LOGGER
            = LogManager.getLogger(ExampleFilesUtils.class.getName());
    private static final ValueFactory f = SimpleValueFactory.getInstance();
    public static final String FDP_METADATA_FILE = "dtl-fdp.ttl";
    public static final String CATALOG_METADATA_FILE = "textmining-catalog.ttl";
    public static final String CATALOG_ID = "textmining";
    public static final String DATASET_METADATA_FILE = "gda-lumc.ttl";
    public static final String DATASET_ID = "gene-disease-association_lumc";
    public static final String DISTRIBUTION_METADATA_FILE = "gda-lumc-sparql.ttl";
    public static final String DATARECORD_METADATA_FILE = "example-datarecord.ttl";
    public static final String DISTRIBUTION_ID = "sparql";
    public static final String DATARECORD_ID = "dataRecord";
    public final static IRI FDP_URI = f.createIRI("http://localhost/fdp");
    public final static IRI FDP_METADATA_ID_URI = f.createIRI(
            "http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/fdp-metadataID");
    public final static IRI FDP_METADATA_PUBLISHER_URI = f.createIRI("http://dtls.nl");
    public final static IRI FDP_REPO_ID_URI = f.createIRI(
            "http://dev-vm.fair-dtls.surf-hosted.nl:8082/fdp/fdp-repositoryID");
    public final static IRI BIOSEM_ACCESS_RIGHTS_URI
            = f.createIRI("http://biosemantics.humgen.nl/groupAccess");
    public final static IRI METRIC1_URI = f.createIRI("http://dummy.com/metric1");
    public final static IRI CATALOG_URI = f.createIRI(FDP_URI.toString() + "/" + CATALOG_ID);
    public final static IRI DATASET_URI = f.createIRI(FDP_URI.toString() + "/" + CATALOG_ID + "/"
            + DATASET_ID);
    public final static IRI DISTRIBUTION_URI = f.createIRI(FDP_URI.toString() + "/" + CATALOG_ID
            + "/" + DATASET_ID + "/" + DISTRIBUTION_ID);
    public final static IRI DATARECORD_URI = f.createIRI(FDP_URI.toString() + "/" + CATALOG_ID
            + "/" + DATASET_ID + "/" + DATARECORD_ID);
    public final static String TEST_SUB_URI = "http://www.dtls.nl/test";
    public static final String VALID_TEST_FILE = "valid-test-file.ttl";
    public static final RDFFormat FILE_FORMAT = RDFFormat.TURTLE;

    /**
     * Method to read the content of a turtle file
     *
     * @param fileName Turtle file name
     * @return File content as a string
     */
    public static String getFileContentAsString(String fileName) {
        String content = "";
        try {
            URL fileURL = ExampleFilesUtils.class.getResource(fileName);
            content = Resources.toString(fileURL, Charsets.UTF_8);
        } catch (IOException ex) {
            LOGGER.error("Error getting turle file", ex);
        }
        return content;
    }

    /**
     * Method to read the content of a turtle file
     *
     * @param fileName Turtle file name
     * @param baseURI RDF content's base uri
     * @return File content as a string
     */
    public static List<Statement> getFileContentAsStatements(String fileName, String baseURI) {
        List<Statement> statements = null;
        try {
            String content = getFileContentAsString(fileName);
            StringReader reader = new StringReader(content);
            Model model;
            model = Rio.parse(reader, baseURI, FILE_FORMAT);
            Iterator<Statement> it = model.iterator();
            statements = Lists.newArrayList(it);
        } catch (IOException | RDFParseException | UnsupportedRDFormatException ex) {
            LOGGER.error("Error getting turle file", ex);
        }
        return statements;
    }
}
