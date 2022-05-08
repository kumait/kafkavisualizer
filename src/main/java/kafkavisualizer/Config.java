package kafkavisualizer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class Config {
    public static final String KEY_THEME = "theme";

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), ".kafkavisualizer", "config.properties");
    private static final Properties properties = new Properties();

    public static Properties load() throws IOException {
        if (Files.exists(CONFIG_PATH)) {
            properties.load(Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8));
        }

        return properties;
    }

    public static void save() throws IOException {
        Files.createDirectories(CONFIG_PATH.getParent());
        var writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        properties.store(writer, null);
    }

    public static Properties props() {
        return properties;
    }

    public static void main(String[] args) throws IOException {
        var props = Config.load();
        Config.load();
        props.put("theme", "light");
        Config.save();
    }
}
