/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtl.fairmetadata.utils;

import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2016-09-06
 * @version 0.1
 */
public class RDFUtils {
    
    public static Literal getCurrentTime() throws 
            DatatypeConfigurationException {
        Date date = new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().
                newXMLGregorianCalendar(c);
        Literal currentTime = new LiteralImpl(xmlDate.toXMLFormat(),
                    XMLSchema.DATETIME);
        return currentTime;
    }
    
}
