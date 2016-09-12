/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils;

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
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

/** 
 * Contains references to the example metadata rdf files which are used in the 
 * Junit tests.
 * 
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-08
 * @version 0.1
 */
public class ExampleFilesUtils {
    private final static Logger LOGGER = 
            LogManager.getLogger(ExampleFilesUtils.class.getName());
    public static final String FDP_METADATA_FILE = "dtl-fdp.ttl";
    public static final String CATALOG_METADATA_FILE = "textmining-catalog.ttl";
    public static final String CATALOG_ID = "textmining";
    public static final String DATASET_METADATA_FILE = "gda-lumc.ttl";
    public static final String DATASET_ID = "gene-disease-association_lumc";
    public static final String DISTRIBUTION_METADATA_FILE = 
            "gda-lumc-sparql.ttl";
    public static final String DISTRIBUTION_ID = "sparql";
    public final static String FDP_URI = "http://localhost/fdp";
    public final static String CATALOG_URI = "http://localhost/fdp/textmining";
    public final static String DATASET_URI = 
            "http://localhost/fdp/textmining/gene-disease-association_lumc";
    public final static String DISTRIBUTION_URI = 
            "http://localhost/fdp/textmining/gene-disease-association_lumc/sparql";   
    public final static String TEST_SUB_URI = "http://www.dtls.nl/test";  
    public static final String VALID_TEST_FILE = "valid-test-file.ttl";
    public static final RDFFormat FILE_FORMAT = RDFFormat.TURTLE;
    
    /**
     * Method to read the content of a turtle file
     * 
     * @param fileName Turtle file name
     * @return File content as a string
     */
    public static String getFileContentAsString(String fileName)  {        
        String content = "";  
        try {
            URL fileURL = ExampleFilesUtils.class.getResource(fileName);
            content = Resources.toString(fileURL, Charsets.UTF_8);
        } catch (IOException ex) {
            LOGGER.error("Error getting turle file",ex);          
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
    public static List<Statement> getFileContentAsStatements(String fileName, 
            String baseURI)  {        
        List<Statement> statements = null;  
        try {
            String content = getFileContentAsString(fileName);
            StringReader reader = new StringReader(content);
            org.openrdf.model.Model model;
            model = Rio.parse(reader, baseURI, FILE_FORMAT);
            Iterator<Statement> it = model.iterator();
            statements =  Lists.newArrayList(it);
        } catch (IOException | RDFParseException | 
                UnsupportedRDFormatException ex) {
            LOGGER.error("Error getting turle file",ex);          
        }         
        return statements;
    }
    
}
