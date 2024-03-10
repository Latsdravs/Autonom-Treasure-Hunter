package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Map {
    Scene Map;

    public Map() {
        Group root = new Group();

        int grid_x = 32;
        int grid_y = 32;
        int width = 2;
        int size=64;

        Circle ball = new Circle();
        ball.setCenterX(32+width);
        ball.setCenterY(32+width);
        ball.setRadius(32);
        ball.setStroke(Color.FIREBRICK);
        ball.setFill(Color.FIREBRICK);
        root.getChildren().add(ball);

        for (int i = 0; i <= grid_x; i++) {
            Line temp = new Line();
            temp.setStartX(i*(size+width*2));
            temp.setEndX(i*(size+width*2));

            temp.setStartY(0);
            temp.setEndY((size+width*2)*grid_y);
            temp.setStroke(Color.GAINSBORO);
            temp.setStrokeWidth(width*2);
            root.getChildren().add(temp);

        }
        for (int j = 0; j <= grid_y; j++) {
            Line temp = new Line();
            temp.setStartX(0);
            temp.setEndX((size+width*2)*grid_x);

            temp.setStartY(j*(size+width*2));
            temp.setEndY(j*(size+width*2));

            temp.setStroke(Color.GAINSBORO);
            temp.setStrokeWidth(width*2);
            root.getChildren().add(temp);

        }


        this.Map = addScene(root);
    }

    private  ScrollPane addMapScroll(Node node)
    {
        ScrollPane mapScroll = new ScrollPane();

        mapScroll.setContent(node);
        mapScroll.setPannable(true);
        return mapScroll;
    }
    private AnchorPane addAnchorPane(Node root)
    {
        ScrollPane mapScroll = addMapScroll(root);
        AnchorPane mapAnchor = new AnchorPane();
        mapAnchor.getChildren().add(mapScroll);
        AnchorPane.setLeftAnchor(mapScroll, 0.0);
        AnchorPane.setTopAnchor(mapScroll, 0.0);
        AnchorPane.setRightAnchor(mapScroll, 0.0);
        AnchorPane.setBottomAnchor(mapScroll, 0.0);
        return mapAnchor;
    }
    public Scene addScene(Node root)
    {
        return new Scene(addAnchorPane(root),720,720);
    }
}
