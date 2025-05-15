module deco {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens deco to javafx.fxml;
    opens entity to javafx.fxml;
    opens tables to javafx.fxml;
    exports deco;
    exports tables;
    exports entity;
}
