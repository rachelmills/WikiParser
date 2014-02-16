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
//        SAXParserFactory factoryQuick = SAXParserFactory.newInstance();

        SAXParser saxParser = factory.newSAXParser();
//        SAXParser saxParserQuick = factoryQuick.newSAXParser();
        
        BufferedReader br;
//        BufferedReader brQuick;
        MyHandler handler = new MyHandler();
//        QuickHandler quickHandler = new QuickHandler();

        File folder = new File("/Volumes/Untitled/wikiprep/gumFiles/");
       // File folderRemote = new File("home/wikiprep/wikiprep/work/gumFiles");
      //  File folderRemote = new File("/home/wikiprep/wikiprep/work/gumFiles/");
        File[] listOfFiles = folder.listFiles();
        

        for (File file : listOfFiles) {
            if (!file.isHidden()) {
                String fileName = file.getName();
            
            FileInputStream stream = new FileInputStream(folder+"/"+fileName);
//            FileInputStream streamQuick = new FileInputStream(folder+ "/"+fileName);
//            GZIPInputStream gzipstreamQuick = new GZIPInputStream(streamQuick);
            GZIPInputStream gzipstream = new GZIPInputStream(stream);
            br = new BufferedReader(new InputStreamReader(gzipstream));
//            brQuick = new BufferedReader(new InputStreamReader(gzipstreamQuick));
//            saxParserQuick.parse(new InputSource(brQuick), quickHandler);
            saxParser.parse(new InputSource(br), handler);
            }
        }
    }
}
