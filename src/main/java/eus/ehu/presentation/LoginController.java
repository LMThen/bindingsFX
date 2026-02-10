package eus.ehu.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.Scene;

public class LoginController {

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
    void comboHandler(ActionEvent event) {
        String role = roleCombo.getValue();
        roleLabel.setText(role);
    }

    @FXML
    void initialize() {
        // use observable list to populate the combo box
        roles = FXCollections.observableArrayList("Admin", "User", "Guest");
        roleCombo.setItems(roles);
        
        // bind the roleLabel text property to the selected role value property
        roleLabel.textProperty().bind(roleCombo.valueProperty());
    }

    @FXML
    void switchTheme(){
        Scene scene = roleCombo.getScene();
        scene.getStylesheets().clear();

        if (darkTheme.isSelected()) {
            scene.getStylesheets().add("https://raw.githubusercontent.com/antoniopelusi/JavaFX-Dark-Theme/main/style.css");
        } else if (lightTheme.isSelected()) {
            scene.getStylesheets().clear();
        }
    }


}
