/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author rachelmills
 */
public class QuickHandler extends DefaultHandler {

    private FileOutputStream fosDisambiguationIds;
    static private Writer outDisambiguationIds;

    private String temp;
    private String id = "";
    String textDataToWrite = "";

    private boolean isTitle = false;

    private final static String OUTPUT_FILE_PATH = "/Volumes/Untitled/wikiprep/WikiOutput/";

    public QuickHandler() {

        try {
            fosDisambiguationIds = new FileOutputStream(OUTPUT_FILE_PATH + "Disambiguation_IDs.txt");
        } catch (FileNotFoundException e) {
            Logger.getLogger(QuickHandler.class.getName()).log(Level.INFO, "Exception is {0}", e);
        }
        try {
            outDisambiguationIds = new OutputStreamWriter(fosDisambiguationIds, "UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QuickHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeDisambiguationData(String s) throws SAXException {
        try {
            outDisambiguationIds.write(s);
            outDisambiguationIds.flush();
        } catch (IOException e) {
            throw new SAXException("I/O Error", e);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "page":
                if (attributes != null) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if ("id".equals(attributes.getLocalName(i))) {
                            id = attributes.getValue(i);
                        }
                    }
                }
                break;
            case "title":
                isTitle = true;
                break;
        }
    }

    @Override
    public void characters(char[] buffer, int start, int length) throws SAXException {

        if (isTitle) {
            temp = new String(buffer, start, length);
            if (temp.contains("Category:Disambiguation") || temp.contains("disambiguation pages")) {
                writeDisambiguationData(id + "\n");
            }
            isTitle = false;
        }
    }
}
