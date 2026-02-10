package eus.ehu.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginController {

    private static final String DARK_CSS = "https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css";

    private final File configFile = new File("config.properties");
    private final Properties props = new Properties();

    @FXML
    private ComboBox<String> roleCombo;
    @FXML
    private Label roleLabel;
    @FXML
    private RadioButton darkTheme;
    @FXML
    private RadioButton lightTheme;

    private ObservableList<String> roles;

    @FXML
    void initialize() {
        // Combobox setup
        roles = FXCollections.observableArrayList("Admin", "User", "Guest");
        roleCombo.setItems(roles);
        roleLabel.textProperty().bind(roleCombo.valueProperty());

        // Load saved theme
        String savedTheme = loadTheme();

        darkTheme.setSelected("dark".equals(savedTheme));
        lightTheme.setSelected(!"dark".equals(savedTheme));

        // Apply theme when Scene exists
        roleCombo.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                applyThemeToScene(newScene, savedTheme);
            }
        });
    }

    @FXML
    void comboHandler(ActionEvent event) {
        roleLabel.setText(roleCombo.getValue());
    }

    @FXML
    void switchTheme() {
        Scene scene = roleCombo.getScene();
        if (scene == null)
            return;

        String theme = darkTheme.isSelected() ? "dark" : "light";
        applyThemeToScene(scene, theme);
        saveTheme(theme);
    }

    private void applyThemeToScene(Scene scene, String theme) {
        if ("dark".equals(theme)) {
            if (!scene.getStylesheets().contains(DARK_CSS)) {
                scene.getStylesheets().add(DARK_CSS);
            }
            // make RadioButton labels white in dark mode.
            darkTheme.setStyle("-fx-text-fill: white;");
            lightTheme.setStyle("-fx-text-fill: white;");
        } else {
            scene.getStylesheets().remove(DARK_CSS);
            // restore default color
            darkTheme.setStyle(null);
            lightTheme.setStyle(null);
        }
        printTheme(theme);
    }

    private String loadTheme() {
        if (!configFile.exists())
            return "light";

        try (FileInputStream in = new FileInputStream(configFile)) {
            props.load(in);
            return props.getProperty("theme", "light");
        } catch (IOException e) {
            e.printStackTrace();
            return "light";
        }
    }

    private void saveTheme(String theme) {
        props.setProperty("theme", theme);

        try (FileOutputStream out = new FileOutputStream(configFile)) {
            props.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTheme(String theme) {
        System.out.println(("dark".equals(theme) ? "Dark" : "Light") + " " + "Theme");
    }
}
