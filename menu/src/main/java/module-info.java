module deco {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens deco to javafx.fxml;
    exports deco;
}
