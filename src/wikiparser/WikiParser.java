/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
//import org.apache.logging.log4j.Logger;
import java.util.logging.*;

/**
 *
 * @author rachelmills
 */
public class WikiParser {

    private final static Logger log = Logger.getLogger(WikiParser.class.getName());
    protected static final String XML_FILE_NAME = "Archive.zip";

    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {

        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            // parse the input
            SAXParser saxParser = factory.newSAXParser();

            MyHandler handler = new MyHandler();

            ZipFile zip = new ZipFile(XML_FILE_NAME);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                System.out.println("\nOpening zip entry: " + entry.getName());
                if (!entry.getName().contains("__MACOSX/")) {
                    try (InputStream xmlStream = zip.getInputStream(entry)) {
                        saxParser.parse(xmlStream, handler);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.log(Level.INFO, "Exception is {0}", e);
        }
        System.exit(0);
    }
}
