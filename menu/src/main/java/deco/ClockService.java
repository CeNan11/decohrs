package deco;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

public class ClockService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    private final SimpleStringProperty time = new SimpleStringProperty();
    
    public ClockService() {
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            time.set(LocalTime.now().format(formatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    @SuppressWarnings("exports")
    public SimpleStringProperty timeProperty() {
        return time;
    }

    public String getTime() {
        return time.get();
    }
}