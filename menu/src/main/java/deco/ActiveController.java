package deco;

import java.io.IOException;

import entity.User;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
    
    public class ActiveController implements FilterableController{

    @FXML private Label time;
    @FXML private StackPane stackPane;
    @FXML private FlowPane flowPane;

    @FXML private Button next;
    @FXML private Button prev;
    @FXML private Label pageLabel;
    @FXML private Label totalLabel;
    
    private User user;
    private static final int ITEMS_PER_PAGE = 19;
    private int currentPage = 0;
    private static final int TOTAL_ITEMS = 120; // Total number of items in the list

    @FXML
    private void initialize() {
        updatePage(currentPage);
    }
    
    public void updatePage(int page) {
        this.pageLabel.setText(String.valueOf("PAGE: " + (currentPage+1)));
        totalLabel.setText(String.valueOf("TOTAL: " + (TOTAL_ITEMS)));
        
        flowPane.getChildren().clear();
        
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, TOTAL_ITEMS);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile_add.fxml"));
            Node profileCardNode = loader.load();
    
            flowPane.getChildren().add(profileCardNode);
        } catch (IOException error) {
            error.printStackTrace();
        }
        
        for (int i = start + 1; i <= end; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile_Card.fxml"));
                Node card = loader.load();
                ProfileCardController controller = loader.getController();

                controller.initData("Active", "Name", "Position");

                flowPane.getChildren().add(card);

            } catch (IOException error) {
                error.printStackTrace();
            }
        }
        
        prev.setDisable(page == 0);
        next.setDisable((page + 1) * ITEMS_PER_PAGE >= TOTAL_ITEMS);
    }

    @FXML
    private void prevPage() {
        if (currentPage > 0) {
            currentPage--;
            updatePage(currentPage);
        }
    }
    
    @FXML
    private void nextPage() {
        if ((currentPage + 1) * ITEMS_PER_PAGE < TOTAL_ITEMS) {
            currentPage++;
            updatePage(currentPage);
        }
    }

    public void setClockService(ClockService clockService) {
        time.textProperty().bind(clockService.timeProperty());
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void navigateToHome() throws IOException {
        Object controller = App.setRoot("Home");
        ((HomeController) controller).setUser(user);
    }

    @FXML
    private void navigateToInactive() throws IOException {
        Object controller = App.setRoot("Inactive");
        ((InactiveController) controller).setUser(user);
    }

    @FXML private void navigateToAuditLogs() throws IOException {
        Object controller = App.setRoot("AuditLogs");
        ((AuditLogsController) controller).setUser(user);
    }

    @FXML void navigateToLogin() throws IOException {
        App.setRoot("Login");
    }    

    @FXML private void logout() throws IOException {
        navigateToLogin();
    }

    @FXML
    private void showFilter() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Filter.fxml"));
            Parent overlay = loader.load();
            
            // Get the FilterController and pass this controller to it
            FilterController filterController = loader.getController();
            filterController.setParentController(this);
            
            // Background overlay
            StackPane backgroundOverlay = new StackPane();
            backgroundOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
            backgroundOverlay.setPrefSize(stackPane.getWidth(), stackPane.getHeight());
            backgroundOverlay.prefWidthProperty().bind(stackPane.widthProperty());
            backgroundOverlay.prefHeightProperty().bind(stackPane.heightProperty());
            
            // Set overlay properties in FilterController
            filterController.setOverlay(overlay, stackPane, backgroundOverlay);
            
            backgroundOverlay.setOnMouseClicked(event -> filterController.handleClose());
            overlay.setOnMouseClicked(event -> event.consume());
            
            StackPane.setAlignment(overlay, Pos.TOP_RIGHT);
            StackPane.setMargin(overlay, new Insets(0, 100, 0, 0));
            overlay.setTranslateY(-400); // Start position for animation
            
            stackPane.getChildren().addAll(backgroundOverlay, overlay);
            
            // Slide-in animation
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), overlay);
            slideIn.setFromY(-400);
            slideIn.setToY(0);
            slideIn.play();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void applyFilterData(FilterData data) {
    System.out.println(data);
    }

        @FXML private void checkAsGuest() {
        if (user.getRole() == User.roles.GUEST) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Denied");
            alert.setHeaderText(null);
            alert.setContentText("You do not have permission to access this page.");
            alert.showAndWait();
        }
    }

}