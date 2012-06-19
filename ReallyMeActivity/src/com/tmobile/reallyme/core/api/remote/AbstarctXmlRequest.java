package com.tmobile.reallyme.core.api.remote;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * User: Kolesnik Aleksey
 * Date: 24.07.2009
 * Time: 17:13:14
 */
public abstract class AbstarctXmlRequest extends AbstractRequest {
    protected static final String XML_START = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";

    protected abstract void processStart(String namespaceURI, String localName,
                        String qName, Attributes atts);
    protected abstract void processEnd(String namespaceURI, String localName,
            String qName);

    /*
    * Parsin xml
    */
    protected void parsinResult(InputStream instream) {
        /* Get a SAXParser from the SAXPArserFactory. */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
             /* Get the XMLReader of the SAXParser we created. */
            XMLReader xr = sp.getXMLReader();
             /* Create a new ContentHandler and apply it to the XML-Reader*/
            ResponseParser responseParser = new ResponseParser();
            xr.setContentHandler(responseParser);
            /* Parse the xml-data */
            xr.parse(new InputSource(instream));
            /* Parsing has finished. */
        }  catch (Exception e) {
           log.error(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString());
        }
    }

    class ResponseParser extends DefaultHandler {

        @Override
        public void endDocument() throws SAXException {
            p_parse.end();
            onEndDocument();
        }

        /** Gets be called on opening tags like:
        * <tag>
        * Can provide attribute(s), when xml was like:
        * <tag attribute="attributeValue">*/
        @Override
        public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
            processStart(namespaceURI, localName, qName, atts);
            if (localName.equals("response")) {
                Boolean ok = Boolean.valueOf(atts.getValue("ok"));
                if (ok == null || !ok) {
                    logError.info(atts.getValue("errorMessage"));
                }
            }
        }

        /** Gets be called on opening tags like:
        * <tag>
        * Can provide attribute(s), when xml was like:
        * <tag attribute="attributeValue">*/
        @Override
        public void endElement(String namespaceURI, String localName,
            String qName) throws org.xml.sax.SAXException {
            processEnd(namespaceURI, localName, qName);
        }
    }
}
