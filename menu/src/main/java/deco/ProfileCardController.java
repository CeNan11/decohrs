package deco;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class ProfileCardController {
    @FXML private Label status, name, position;
    @FXML private ImageView profilePhoto;

    public void initData(String status, String name, String position) {
        this.status.setText(status);
        this.name.setText(name);
        this.position.setText(position);
    }

    public void setStatus(String status) {this.status.setText(status);}
    public void setName(String name) {this.name.setText(name);}
    public void setPosition(String position) {this.position.setText(position);}
}