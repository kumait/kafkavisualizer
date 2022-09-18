package kafkavisualizer.navigator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kafkavisualizer.models.Cluster;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class NavigatorModel {
    private static final Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".kafkavisualizer", "clusters.json");
    private final Gson gson;
    private final List<Cluster> clusters;

    public NavigatorModel() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        clusters = new ArrayList<>();
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void addCluster(Cluster cluster) {
        clusters.add(cluster);
    }

    public void load() throws IOException {
        if (!Files.exists(FILE_PATH)) {
            return;
        }

        var json = Files.readString(FILE_PATH);
        List<Cluster> loadedClusters = gson.fromJson(json, new TypeToken<List<Cluster>>() {
        }.getType());
        clusters.clear();
        clusters.addAll(loadedClusters);
    }

    public void save() throws IOException {
        Files.createDirectories(FILE_PATH.getParent());
        var json = gson.toJson(clusters);
        Files.writeString(FILE_PATH, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
