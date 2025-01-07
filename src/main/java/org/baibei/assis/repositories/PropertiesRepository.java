package org.baibei.assis.repositories;

import org.baibei.tools.FileTools;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.baibei.tools.FileTools.createFile;

@Repository
public class PropertiesRepository {

    private List<String> properties;
    private String repoPath;

    public PropertiesRepository() {
        this.properties = new ArrayList<String>();
        this.repoPath = "properties.txt";
        createFile(repoPath);
        read();

        if (properties.isEmpty()) {
            fillStdProperties();
            save();
        }
    }

    public PropertiesRepository(String repoPath) {
        this.properties = new ArrayList<>();
        this.repoPath = repoPath;
        createFile(repoPath);
        read();

        if (properties.isEmpty()) {
            fillStdProperties();
        }
    }

    public void read() {
        properties = FileTools.readFile(repoPath);
    }

    public void save(String property) {
        changeProperty(property);
        save();
    }

    public void save() {
        FileTools.writeFile(repoPath, properties.toArray(new String[0]), false);
    }

    private void changeProperty(String property) {
        String[] split = property.split("=");
        changeProperty(split[0], split[1]);
    }

    private void changeProperty(String name, String value) {
        for (int i = 0; i < properties.size(); i++) {
            String[] split = properties.get(i).split("=");
            if (split[0].equals(name)) {
                split[1] = value;
                properties.set(i, buildProperty(name, value));
                return;
            }
        }
    }

    private String buildProperty(String name, String value) {
        return name + "=" + value;
    }

    private String buildInfo() {
        StringBuilder sb = new StringBuilder();
        for (String prop : properties) {
            sb.append(prop).append("\n");
        }
        return sb.toString();
    }

    public List<String> getProperties() {
        return properties;
    }

    public String getProperty(String propertyName) {
        for (String property : properties) {
            String[] split = property.split("=");
            if (split[0].equals(propertyName)) {
                return split[1];
            }
        }
        return null;
    }

    public int getFindDepth() {
        return Integer.parseInt(getProperty("find_depth"));
    }

    public int getTypeSpeed() {
        int speed = Integer.parseInt(getProperty("type_speed"));
        if (speed < 0) {
            speed = 0;
        }
        return speed;
    }

    public void setTypeSpeed(int speed) {
        changeProperty("type_speed", String.valueOf(speed));
    }

    public String getModel() {
        String model = getProperty("model");
        return switch (model) {
            case "en" -> "model-en";
            case "ru" -> "model-ru";
            default -> "model-en";
        };
    }

    public void setModel(String model) {
        changeProperty("model", model);
    }

    private void fillStdProperties() {
        properties.add("find_depth=5");
        properties.add("model=en");
        properties.add("name=hi");
        properties.add("type_speed=50");
        save();
    }
}
