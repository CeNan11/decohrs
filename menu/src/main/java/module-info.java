module deco {
    requires javafx.controls;
    requires javafx.fxml;

    opens deco to javafx.fxml;
    exports deco;
}
