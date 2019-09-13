package com.thecodinginterface.sorting;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private ComboBox<String> sortMethodComboBox;

    @FXML
    private ToggleGroup sortDirectionToggleGrp;

    @FXML
    private RadioButton ascendingRadioBtn;
    
    @FXML
    private RadioButton descendingRadioBtn;

    @FXML
    private StackPane stackPane;
    
    @FXML
    private Group group;
    
    private static final String COMPARABLE = "Natural Ordering, X then Y Coords (Comparable)";
    private static final String COMPARATOR_DIST = "Euclidean Distance from Origin (Comparator)";
    private static final String COMPARATOR_DIST_EVEN_FIRST = "Even First then Distance (Comparator)";
    private static final double DIMENSION = 880;
    private static final int MIN = (int) (-1 *  DIMENSION / 2);
    private static final int MAX = (int) (DIMENSION / 2);
    private static final int MAX_PTS = 360;

    @FXML
    void handleLoadPoints(ActionEvent event) {
        group.getChildren().removeIf(node -> node instanceof Circle);
        List<MyPoint> points = new ArrayList<>(200);
        for (int i = 0; i < MAX_PTS; i++) {
            var x = ThreadLocalRandom.current().nextInt(MIN, MAX);
            var y = ThreadLocalRandom.current().nextInt(MIN, MAX);
            points.add(new MyPoint(x, y));
        }
        var sortMethod = sortMethodComboBox.getValue();
        if (sortMethod.equals(COMPARABLE)) {
            Collections.sort(points);
            if (descendingRadioBtn.isSelected()) {
                Collections.reverse(points);
            }
        } else {
            Comparator<MyPoint> comparator = null;
            
            if (sortMethod.equals(COMPARATOR_DIST)) {
                comparator = (a, b) -> Double.compare(a.getDistance(), b.getDistance());
            } else {
                comparator = (a, b) -> {
                    var aEven = a.isEvenDistance() ? 0 : 1;
                    var bEven = b.isEvenDistance() ? 0 : 1;
                    var c = Integer.compare(aEven, bEven);

                    return c == 0 ? Double.compare(a.getDistance(), b.getDistance()) : c;
                };
            }
            
            if (ascendingRadioBtn.isSelected()) {
                Collections.sort(points, comparator);
            } else {
                Collections.sort(points, comparator.reversed());
            }
        }
        
        for (int i = 0; i < MAX_PTS; i++) {
            var pt = points.get(i);
            group.getChildren().add(makeCircle(pt, i * 10));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        sortMethodComboBox.getItems().addAll(
             COMPARABLE,
             COMPARATOR_DIST,
             COMPARATOR_DIST_EVEN_FIRST
        );
        
        sortMethodComboBox.getSelectionModel().selectFirst();
        
        group.getChildren().addAll(
            makeLine(0.0, DIMENSION/2.0,  DIMENSION, DIMENSION/2),
            makeLine(DIMENSION/2, 0.0, DIMENSION/2, DIMENSION)
        );
    }
    
    Line makeLine(double x0, double y0, double x1, double y1) {
        var line = new Line(x0, y0, x1, y1);
        line.setFill(Color.BLACK);
        line.setStrokeWidth(2);
        return line;
    }
    
    Circle makeCircle(MyPoint pt, double delay) {
        var color = pt.isEvenDistance() ? Color.BLUE : Color.RED;
        var circle = new Circle(pt.getX() + MAX, DIMENSION - (pt.getY() + MAX), 8, color);
        var fadeTransition = new FadeTransition(Duration.millis(400), circle);
        circle.setOpacity(0);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setDelay(Duration.millis(delay));
        fadeTransition.playFromStart();
        return circle;
    }
}
