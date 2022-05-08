package kafkavisualizer;

import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
        var gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            var o = gson.fromJson(json, Object.class);
            prettyJSON = gson.toJson(o);
        } catch (Exception ex) {
            // do nothing
        }
        return prettyJSON;
    }
}
