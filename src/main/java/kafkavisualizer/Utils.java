package kafkavisualizer;

import com.google.gson.GsonBuilder;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Objects;

public class Utils {

    public static void showError(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarning(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean showConfirmation(Component component, String message) {
        var confirmation = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(component), message, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return confirmation == JOptionPane.YES_OPTION;
    }

    public static Font getMonoFont() {
        Font font;
        try (var is = Utils.class.getClassLoader().getResourceAsStream("fonts/FiraCode-Regular.ttf")) {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is));
            font = font.deriveFont(Font.PLAIN, 12);
        } catch (IOException | FontFormatException e) {
            font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        }
        return font;
    }

    public static String beautifyJSON(String json) {
        var prettyJSON = json;
        var gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
        try {
            var o = gson.fromJson(json, Object.class);
            prettyJSON = gson.toJson(o);
        } catch (Exception ex) {
            // do nothing
        }
        return prettyJSON;
    }

    public static String beautifyXML(String xml) {
        var prettyXML = xml;
        try {
            var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            var transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            var transformer = transformerFactory.newTransformer();
            var hasDeclaration = xml.trim().toLowerCase(Locale.ROOT).startsWith("<?xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, hasDeclaration ? "no" : "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            var writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            prettyXML = writer.toString();
        } catch (Exception ex) {
            // do nothing
        }
        return prettyXML;
    }
}
