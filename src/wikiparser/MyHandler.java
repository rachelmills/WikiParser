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
public class MyHandler extends DefaultHandler {

    FileOutputStream fos;
    FileOutputStream fos2;
    static private Writer out;
    static private Writer out2;

    public String temp;

    boolean isId = false;
    boolean isTitle = false;
    boolean isCategories = false;
    boolean isText = false;

    public MyHandler() {
        try {
            fos = new FileOutputStream("test5.txt");
            fos2 = new FileOutputStream("test6.txt");
        } catch (FileNotFoundException e) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.INFO, "Exception is {0}", e);
        }
        try {
            out = new OutputStreamWriter(fos, "UTF8");
            out2 = new OutputStreamWriter(fos2, "UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeGraphData(String s) throws SAXException {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O Error", e);
        }
    }

    private void writeTextData(String s) throws SAXException {
        try {
            out2.write(s);
            out2.flush();
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
                            isId = true;
                            writeGraphData(attributes.getValue(i));
                            writeGraphData(",");
                            writeTextData(attributes.getValue(i));
                            writeTextData(",");
                            isId = false;
                        }
                    }
                }
                break;
            case "title":
                isTitle = true;
                break;
            case "categories":
                isCategories = true;
                break;
            case "text":
                isText = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // not required 
    }

    @Override
    public void characters(char[] buffer, int start, int length) throws SAXException {
        temp = new String(buffer, start, length);
        if (isId) {
            writeGraphData(temp);
            isId = false;
        } else if (isTitle) {
            writeGraphData(temp);
            writeGraphData(",");
            isTitle = false;
        } else if (isCategories) {
            writeGraphData(temp);
            if (!temp.isEmpty()) {
                writeGraphData("\n");
            } else {
                System.out.println("debugging");
            }
            isCategories = false;
            
        } else if (isText) {
            if (temp.startsWith("\n")) {
                temp = temp.substring(1);
            }
            temp = temp + "\n";
            writeTextData(temp);
            isText = false;
        }
    }
}
