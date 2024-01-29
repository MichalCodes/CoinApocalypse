module lab01 {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens lab to javafx.fxml;
    exports lab;
    exports lab.Game;
    opens lab.Game to javafx.fxml;
    exports lab.interfaces;
    opens lab.interfaces to javafx.fxml;
    exports lab.Screens;
    opens lab.Screens to javafx.fxml;
}