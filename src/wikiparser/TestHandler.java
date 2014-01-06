/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wikiparser;

import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author rachelmills
 */
public class TestHandler extends DefaultHandler {
    
    private DefaultHandler handler; 
 
    /**
     * Wrap (or 'decorate') a handler object. Every call to the Handler will
     * be printed to the System.out stream.
     */
    public TestHandler() { 
        this.handler=new DefaultHandler(); 
    }

    
 
    /**
     * Print all attributes associated with this node.
     * @param attributes the Attributes to be printed.
     */
    private void print(Attributes attributes) {
 
        for (int i= 0, n= attributes.getLength(); i < n; i++) {
            System.out.println("attribute#"+i+":");
 
            System.out.println("\tlocalName= '"+attributes.getLocalName(i)+"'");
            System.out.println("\tqName= '"+attributes.getQName(i)+"'");
            System.out.println("\ttype= '"+attributes.getType(i)+"'");
            System.out.println("\turi= '"+attributes.getURI(i)+"'");
            System.out.println("\tvalue= '"+attributes.getValue(i)+"'");
 
            System.out.println();
        }
    }
 
    /**
     * @return 
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity (String publicId, String systemId) throws IOException, SAXException {
 
        System.out.println("resolveEntity: publicId= '"+publicId+"' systemId= '"+systemId+"'");
        return handler.resolveEntity(publicId, systemId);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void notationDecl (String name, String publicId, String systemId) throws SAXException {
 
        System.out.println("notationDecl: name= '"+name+"' publicId= '"+publicId+"' systemId= '"+systemId+"'");
        handler.notationDecl(name, publicId, systemId);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void unparsedEntityDecl (String name, String publicId,
                    String systemId, String notationName) throws SAXException {
 
        System.out.println("unparsedEntityDecl: name= '"+name+"' publicId= '"+publicId+"' systemId= '"+systemId+"' notationName= '"+notationName+"'");
        handler.unparsedEntityDecl(name, publicId, systemId, notationName);
    }
 
    /**
     * @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
    public void setDocumentLocator (Locator locator) {
 
        System.out.println("setDocumentLocator: locator= "+locator);
        handler.setDocumentLocator(locator);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    @Override
    public void startDocument () throws SAXException {
 
        System.out.println("startDocument");
        handler.startDocument();
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    @Override
    public void endDocument () throws SAXException {
 
        System.out.println("endDocument");
        handler.endDocument();
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
    public void startPrefixMapping (String prefix, String uri) throws SAXException {
 
        System.out.println("startPrefixMapping: prefix= '"+prefix+"' uri= '"+uri+"'");
        handler.startPrefixMapping(prefix, uri);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#endPrefixMapping(java.lang.String)
     */
    @Override
    public void endPrefixMapping (String prefix) throws SAXException {
 
        System.out.println("endPrefixMapping: prefix= '"+prefix+"'");
        handler.endPrefixMapping(prefix);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement (String uri, String localName,
                  String qName, Attributes attributes) throws SAXException {
 
        System.out.println("startElement: uri= '"+uri+"' localName= '"+localName+"' qName= '"+qName+"'");
        print(attributes);
        handler.startElement(uri, localName, qName, attributes);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
 
        System.out.println("endElement: uri= '"+uri+"' localName= '"+localName+"' qName= '"+qName+"'");
        handler.endElement(uri, localName, qName);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters (char ch[], int start, int length) throws SAXException {
 
        String s= new String(ch, start, length);
 
        if (s.trim().length() > 0)
            System.out.println("characters: ch= '"+s+"'");
 
        handler.characters(ch, start, length);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace (char ch[], int start, int length) throws SAXException {
 
        System.out.println("ignorableWhitespace: ch= '"+new String(ch, start, length)+"'");
        handler.ignorableWhitespace(ch, start, length);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
    public void processingInstruction (String target, String data) throws SAXException {
 
        System.out.println("processingInstruction: target= '"+target+"' data= '"+data+"'");
        handler.processingInstruction(target, data);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity (String name) throws SAXException {
 
        System.out.println("skippedEntity: name= '"+name+"'");
        handler.skippedEntity(name);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#warning(org.xml.sax.SAXParseException)
     */
    @Override
    public void warning (SAXParseException e) throws SAXException {
 
        System.out.println("warning: e= "+e);
        e.printStackTrace(System.out);
        handler.warning(e);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
     */
    @Override
    public void error (SAXParseException e) throws SAXException {
 
        System.out.println("error: e= "+e);
        e.printStackTrace(System.out);
        handler.error(e);
    }
 
    /**
     * @throws org.xml.sax.SAXException
     * @see org.xml.sax.helpers.DefaultHandler#fatalError(org.xml.sax.SAXParseException)
     */
    @Override
    public void fatalError (SAXParseException e) throws SAXException {
 
        System.out.println("fatalError: e= "+e);
        e.printStackTrace(System.out);
        handler.fatalError(e);
    }    

    private TestHandler(DefaultHandler handler) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
