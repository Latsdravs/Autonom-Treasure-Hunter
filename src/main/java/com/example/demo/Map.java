package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    Scene Map;
    Scene miniMap;

    public Map(int grid_x,int grid_y,int size,int width,int offset,int miniSize) {
        int[][] mapValue = new int[grid_x][grid_y];






        Group mini = new Group();
        WritableImage colorMapped = new WritableImage(offset*2+1+grid_x*(miniSize+1),offset*2+1+grid_y*(miniSize+1));

        PixelWriter writer = colorMapped.getPixelWriter();
        for (int i = 0; i < 50; i++) {
            BPSRoom(grid_x,grid_y,5,5,mapValue,542);
        }
        for (int i = 0; i < 4; i++) {
            BPSRoom(grid_x,grid_y,5,15,mapValue,4);
        }
        NoiseFill(grid_x,grid_y,65,mapValue);
        paintCanvas(mapValue,writer,grid_x,grid_y,miniSize,offset);


        ImageView currentMap = new ImageView(colorMapped);
        miniMap = addScene(mini);
        mini.getChildren().add(currentMap);












        Group root = new Group();

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
        mapScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
    void paint(int i,int j,int size,Color color,PixelWriter writer){
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                writer.setColor(i+k,j+l,color);
            }
        }
    }
    void paint(int i,int j,int size,int ID,PixelWriter writer){
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                writer.setColor(i+k,j+l,getColor(ID));
            }
        }
    }

    void paintMini(int i,int j,int miniSize,int ID,PixelWriter writer,int offset){
        paint(i*(miniSize+1)+1+offset,j*(miniSize+1)+1+offset,miniSize,ID,writer);
    }
    boolean isHere(int[] array,int value){
        boolean empty = false;
        for (int check:
                array) {

            if(value == check)
            {
                empty = true;
                break;
            }


        }
        return empty;

    }
    void paintCanvas(int[][] map,PixelWriter writer,int grid_x,int grid_y,int miniSize,int sizedOffset){
        int screen_x = grid_x*(miniSize+1)+1+sizedOffset*2;
        int screen_y = grid_y*(miniSize+1)+1+sizedOffset*2;
        for (int i = 0; i < screen_x; i++) {
            for (int j = 0; j <screen_y ; j++) {

                if(i>=sizedOffset && i<screen_x-sizedOffset && j>=sizedOffset && j<screen_y-sizedOffset){
                    int k = i-sizedOffset;
                    int l = j-sizedOffset;

                    if(k%(miniSize+1)==0 || l%(miniSize+1)==0){
                        writer.setColor(i,j,Color.DARKGRAY);
                    } else if (k%(miniSize+1)==1 && l%(miniSize+1)==1) {
                        int tempID=map[k/(miniSize+1)][l/(miniSize+1)];
                        paint(i,j,miniSize ,tempID,writer);
                    }


                }
                else
                    writer.setColor(i,j,Color.SADDLEBROWN);
            }
        }
    }

    /*
    * Color.WHITE kış zemin 0 (>>)0
    * Color.YELLOW yaz zemin 1 (>>)0
    *
    *
    *
    *
    *
    * */
    Color getColor(int ID){
        switch (ID){
            case 0:
                return Color.WHITE;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.FIREBRICK;
            case 4:
                return Color.BROWN;
            case 542:
                return Color.GOLD;
            case 70153:
                return Color.DARKBLUE;

        }
        return Color.DEEPPINK;
    }

    void NoiseFill(int grid_x,int grid_y,int darkPercent,int[][] map){
        int[] empties = new int[]{0};
        Random random = new Random();
        for (int i = 0;i < grid_x;i++) {
            for (int j = 0;j < grid_y;j++) {

                if(isHere(empties,map[i][j])){
                    if(random.nextInt(100)<darkPercent)
                        map[i][j]=70153;
                }

            }
        }

    }
    void cellularAutomata( int grid_x,int grid_y,int darkID){

    }
    void BPSRoom(int grid_x,int grid_y,int expSpace,int roomSize,int[][] map,int ID) {
        BPSRoom(grid_x,grid_y,expSpace,roomSize,roomSize,map,ID);
    }

    void BPSRoom(int grid_x,int grid_y,int expSpace,int roomSize_x,int roomSize_y,int[][] map,int ID){
        Random random = new Random();
        int steps_x = Integer.highestOneBit(grid_x)>>expSpace;
        int steps_y = Integer.highestOneBit(grid_y)>>expSpace;
        int theStep_x = random.nextInt(steps_x);
        int theStep_y = random.nextInt(steps_y);
        int start_x = 0;
        int end_x = grid_x;

        int start_y = 0;
        int end_y = grid_y;
        for (int i = 1; i < steps_x; i*=2) {

            if(theStep_x%2==1)
                start_x=(end_x+start_x)/2;
            else
                end_x = (end_x+start_x)/2;
            theStep_x/=2;
        }
        for (int i = 1; i < steps_y; i*=2) {

            if(theStep_y%2==1)
                start_y=(end_y+start_y)/2;
            else
                end_y = (end_y+start_y)/2;
            theStep_y/=2;
        }
        //random sayı 11111 iken dene
        int[] empties = new int[]{0};

        if(checkEdge(map,start_x,end_x,start_y,end_y,empties)){
            int temp_x = random.nextInt(start_x,end_x-roomSize_x+1);
            int temp_y = random.nextInt(start_y,end_y-roomSize_y+1);
            fillMatrix(map,temp_x,temp_x+roomSize_x,temp_y,temp_y+roomSize_y,ID);
        }else BPSRoom(grid_x,grid_y,expSpace,roomSize_x,roomSize_y,map,ID);





    }
    void fillMatrix(int[][] matrix,int start_x,int end_x,int start_y,int end_y,int filler){
        for (int i = start_x; i < end_x; i++) {
            for (int j = start_y; j < end_y; j++) {

                matrix[i][j]=filler;

            }
        }
    }
    boolean checkSpace(int[][] matrix,int start_x,int end_x,int start_y,int end_y,int[] empties){
        for (int i = start_x; i < end_x; i++) {
            for (int j = start_y; j < end_y; j++) {


                boolean empty = false;
                for (int check:
                     empties) {

                    if(matrix[i][j] == check)
                    {
                        empty = true;
                        break;
                    }


                }
                if(!empty)return false;

            }
        }
        return true;
    }

    boolean checkEdge(int[][] matrix,int start_x,int end_x,int start_y,int end_y,int[] empties){
        int i = start_x;
        int j = start_y;
        int im=1;
        int jm=0;
        do {

            boolean empty = false;
            for (int check:
                    empties) {

                if(matrix[i][j] == check)
                {
                    empty = true;
                    break;
                }


            }
            if(!empty)return false;

            i+=im;
            j+=jm;

            if(i==end_x){

                i--;
                j++;
                im=0;
                jm=1;
            } else if (j==end_y) {
                j--;
                i--;
                im=-1;
                jm=0;
            } else if (i == start_x-1) {
                i++;
                j--;
                im=0;
                jm=-1;
            }


        }while (i!=start_x || j!=start_y);

        return true;
    }

}
