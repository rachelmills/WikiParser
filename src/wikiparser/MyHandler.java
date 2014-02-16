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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private FileOutputStream fosCategories;
    private FileOutputStream fosDescription;
    private FileOutputStream fosIDTitle;
    static private Writer outCategories;
    static private Writer outDescription;
    static private Writer outIDTitle;

    private String temp;
    private String id;
    String textDataToWrite = "";

    List<String> mainTopics = new ArrayList<>();
    List<String> disambiguationCats = new ArrayList<>();

    private boolean isId = false;
    private boolean isTitle = false;
    private boolean isCategories = false;
    private boolean isText = false;
    private boolean keep = false;
    private boolean isDisambiguation = false;

    final static Charset ENCODING = StandardCharsets.UTF_8;

    private final static String OUTPUT_FILE_PATH = "/Volumes/Untitled/wikiprep/WikiOutput/";
    //private final static String OUTPUT_FILE_PATH = "/home/wikiprep/wikiprep/work/";

    public MyHandler() {
        mainTopics.add("Category:Agriculture");
        mainTopics.add("Category:Arts");
        mainTopics.add("Category:Belief");
        mainTopics.add("Category:Business");
        mainTopics.add("Category:Chronology");
        mainTopics.add("Category:Concepts");
        mainTopics.add("Category:Culture");
        mainTopics.add("Category:Education");
        mainTopics.add("Category:Environment");
        mainTopics.add("Category:Geography");
        mainTopics.add("Category:Health");
        mainTopics.add("Category:History");
        mainTopics.add("Category:Humanities");
        mainTopics.add("Category:Humans");
        mainTopics.add("Category:Language");
        mainTopics.add("Category:Law");
        mainTopics.add("Category:Life");
        mainTopics.add("Category:Mathematics");
        mainTopics.add("Category:Medicine");
        mainTopics.add("Category:Nature");
        mainTopics.add("Category:People");
        mainTopics.add("Category:Politics");
        mainTopics.add("Category:Science");
        mainTopics.add("Category:Society");
        mainTopics.add("Category:Sports");
        mainTopics.add("Category:Technology");
        try {
            readDisambiguationIds("/Volumes/Untitled/wikiprep/WikiOutput/Disambiguation_IDs.txt");
        } catch (IOException ex) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fosCategories = new FileOutputStream(OUTPUT_FILE_PATH + "ID_Title_Categories.txt");
            fosDescription = new FileOutputStream(OUTPUT_FILE_PATH + "ID_Text.txt");
            fosIDTitle = new FileOutputStream(OUTPUT_FILE_PATH + "ID_Title.txt");
        } catch (FileNotFoundException e) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.INFO, "Exception is {0}", e);
        }
        try {
            outCategories = new OutputStreamWriter(fosCategories, "UTF8");
            outDescription = new OutputStreamWriter(fosDescription, "UTF8");
            outIDTitle = new OutputStreamWriter(fosIDTitle, "UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readDisambiguationIds(String filename) throws IOException {
        Path path = Paths.get(filename);
        for (String s : Files.readAllLines(path, ENCODING)) {
            disambiguationCats.add(s);
        }
    }

    private void writeGraphData(String s) throws SAXException {
        try {
            outCategories.write(s);
            outCategories.flush();
        } catch (IOException e) {
            throw new SAXException("I/O Error", e);
        }
    }

    private void writeTextData(String s) throws SAXException {
        try {
            outDescription.write(s);
            outDescription.flush();
        } catch (IOException e) {
            throw new SAXException("I/O Error", e);
        }
    }

    private void writeTitleData(String s) throws SAXException {
        try {
            outIDTitle.write(s);
            outIDTitle.flush();
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
            case "categories":
                isCategories = true;
                break;
            case "text":
                builder = new StringBuilder();
                isText = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("text") && keep == true && !isDisambiguation) {
            if (builder != null) {
                writeTextData(builder.toString().trim().replace("\n", " ").replace("\u2028", " ").replace("\u0085", " ").replace("\u2029", " ").concat("~~}~~").concat("\n"));
            }
            isText = false;
            keep = false;
        } else if (qName.equals("text")) {
            isDisambiguation = false;
        }
        
    }

    @Override
    public void characters(char[] buffer, int start, int length) throws SAXException {

        temp = new String(buffer, start, length);
//        if (temp.contains("loss of coordination and problems of vision")) {
//            for (char c : temp.toCharArray()) {
//                System.outCategories.println(c + ":  " + String.format("\\u%04x", (int)c));
//                System.outCategories.println(Character.getType(c));
//            }
//          
//            System.outCategories.println("Debug here");
//        }
        if (isTitle) {
            if (temp.contains("Category:Disambiguation") || temp.contains("disambiguation pages")) {
                System.out.println("disam1 = " + id + ":  " + temp);
            }
            if (!temp.contains("Category:") && !temp.contains("(disambiguation)")) {
                keep = true;
                textDataToWrite = id + "~~}~~" + temp + "~~}~~";
            } else {
                builder = null;
            }
            writeGraphData(id + "#" + temp);
            writeTitleData(id + "," + temp + "\n");

            isTitle = false;
        } else if (isCategories) {
            boolean writeData = true;
            if (keep) {
                for (String s : disambiguationCats) {
                    if (temp.contains(s)) {
                        writeData = false;
                        isDisambiguation = true;
                        break;
                    }
                }
                if (writeData) {
                    writeTextData(textDataToWrite);
                }
            }
            if (!(temp.length() == 1 && temp.equals("\n"))) {
                writeGraphData("#");
                writeGraphData(temp);
            }
            writeGraphData("\n");
            isCategories = false;

        } else if (isText && keep == true && !isDisambiguation) {
            if (builder != null) {
                for (int i = start; i < start + length; i++) {
                    builder.append(buffer[i]);
                }
            }
        }
    }
}
