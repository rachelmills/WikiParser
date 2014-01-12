package wikiparser;

import java.io.BufferedReader;
import java.io.File;
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
        BufferedReader br;
        MyHandler handler = new MyHandler();

       // File folder = new File("/Volumes/Untitled/wikiprep/testFolder/");
        File folderRemote = new File("home/wikiprep/wikiprep/work/gumFiles");
      //  File folderRemote = new File("/home/wikiprep/wikiprep/work/gumFiles");
        File[] listOfFiles = folderRemote.listFiles();
        

        for (File file : listOfFiles) {
            String fileName = file.getName();
      //  String fileName = "/Volumes/Untitled/wikiprep/testFolder/enwiki-20131202-pages-articles.gum.xml.0000.gz";
            FileInputStream stream = new FileInputStream(folderRemote+"/"+fileName);
            GZIPInputStream gzipstream = new GZIPInputStream(stream);
            br = new BufferedReader(new InputStreamReader(gzipstream));
            saxParser.parse(new InputSource(br), handler);
        }
    }
}
