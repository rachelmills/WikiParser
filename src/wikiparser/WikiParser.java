/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

/**
 *
 * @author rachelmills
 */
public class WikiParser {

    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {

        SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser saxParser = factory.newSAXParser();
            
            String fileName = "enwiki-20131202-pages-articles.gum.xml.0000.gz";
            BufferedReader br;
            
            FileInputStream stream = new FileInputStream(fileName);
            GZIPInputStream gzipstream = new GZIPInputStream(stream);
            br = new BufferedReader(new InputStreamReader(gzipstream));
        
            MyHandler handler = new MyHandler();
         //   TestHandler handler = new TestHandler();
                        
            saxParser.parse(new InputSource(br), handler);
    }
}

