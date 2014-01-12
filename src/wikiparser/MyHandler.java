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
    
    private StringBuilder builder;

    private FileOutputStream fos;
    private FileOutputStream fos2;
    static private Writer out;
    static private Writer out2;

    private String temp;

    private boolean isId = false;
    private boolean isTitle = false;
    private boolean isCategories = false;
    private boolean isText = false;

    public MyHandler() {
        try {
            fos = new FileOutputStream("ID_Title_Categories.txt");
            fos2 = new FileOutputStream("ID_Text_v2.txt");
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
                            writeTextData(attributes.getValue(i));
                            isId = false;
                        }
                    }
                }
                break;
            case "title":
                isTitle = true;
                writeGraphData("#");
                break;
            case "categories":
                isCategories = true;
                break;
            case "text":
                writeTextData(",");
                builder = new StringBuilder();

                isText = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       if (qName.equals("text")) {
        if (builder != null) {
            writeTextData(builder.toString().trim().concat("\n"));
        }
        isText = false;
        }
    }

    @Override
    public void characters(char[] buffer, int start, int length) throws SAXException {
        temp = new String(buffer, start, length);
        if (isId) {
            writeGraphData(temp);
            isId = false;
        } else if (isTitle) {
            writeGraphData(temp);
            isTitle = false;
        } else if (isCategories) {            
            if (!(temp.length() == 1 && temp.equals("\n"))) {
                writeGraphData("#");
                writeGraphData(temp);
            } 
            writeGraphData("\n");
            isCategories = false;
            
        } else if (isText) {
            if (builder != null) {
                for (int i = start; i < start+length; i++) {
                        builder.append(buffer[i]);
                }
            }
        } 
    }
}
