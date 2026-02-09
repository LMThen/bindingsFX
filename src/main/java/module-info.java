module eus.ehu.lab1 {
  requires transitive javafx.controls;
  requires javafx.fxml;

  opens eus.ehu.presentation to javafx.fxml;
  exports eus.ehu.presentation;
}
