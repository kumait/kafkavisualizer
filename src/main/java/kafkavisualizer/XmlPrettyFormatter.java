package kafkavisualizer;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlPrettyFormatter {

    private final DocumentBuilder documentBuilder;
    private final Transformer transformer;

    public XmlPrettyFormatter() {
        documentBuilder = createDocumentBuilder();
        transformer = createTransformer();
    }

    private DocumentBuilder createDocumentBuilder() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // completely disable external entities declarations:
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            return factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("Error creating document builder: " + e.getMessage());
            return null;
        }
    }

    private Transformer createTransformer() {
        try {
            var factory = TransformerFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

            var xmlTransformer = factory.newTransformer();
            // prohibit the use of all protocols by external entities:
            xmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xmlTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            return xmlTransformer;
        } catch (TransformerConfigurationException e) {
            System.err.println("Error creating transformer: " + e.getMessage());
            return null;
        }
    }

    public String format(String xml) {
        if (documentBuilder == null || transformer == null) {
            return xml;
        }

        try {
            Document doc = documentBuilder.parse(new InputSource(new StringReader(xml)));

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (SAXException | IOException | TransformerException e) {
            System.err.println("Error prettifying xml: " + e.getMessage());
            return xml;
        }
    }
}
