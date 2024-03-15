package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.nio.Buffer;
import java.util.*;

public class Map {
    Scene Map;
    Scene miniMap;

    public Scene getMap() {
        return Map;
    }

    public Scene getMiniMap() {
        return miniMap;
    }


    class Point{

        int x;
        int y;
        public Point(int x,int y){
            this.x=x;
            this.y=y;


        }
    }

    public Map(int grid_x, int grid_y, int size, int width, int offset, int miniSize) {
        int[][] mapValue = new int[grid_x][grid_y];






        Group mini = new Group();



        BPSRoom(grid_x,grid_y,4,5,mapValue,542,40,true);


        BPSRoom(grid_x,grid_y,5,15,mapValue,4,60,false);





        NoiseFill(grid_x,grid_y,62,mapValue);
        mapValue=cellularAutomata(grid_x,grid_y,mapValue,12);

        BPSRoom(grid_x,grid_y,4,2,7,mapValue,10,50,false);
        BPSRoom(grid_x,grid_y,4,5,2,mapValue,9,50,false);


        System.out.println("1");
        ArrayList<Point>[] P_D=forestation(grid_x,grid_y,mapValue);
        System.out.println("2");

        BPSRoomOffsetted(grid_x,grid_y,4,1,10,mapValue,7,10,false,8,1);
        BPSRoomOffsetted(grid_x,grid_y,4,10,1,mapValue,7,10,false,8,1);
        System.out.println("3");



        int[] IDs = new int[]{4};
        int[] empties = new int[]{0,70153,8};
        executeStoneAge(grid_x,grid_y,mapValue,empties,P_D,800,800);
        System.out.println("4");
        //paintSides(IDs,mapValue,empties,542);

        WritableImage colorMapped = new WritableImage(offset*2+1+grid_x*(miniSize+1),offset*2+1+grid_y*(miniSize+1));

        PixelWriter miniWriter = colorMapped.getPixelWriter();
        paintCanvas(mapValue,miniWriter,grid_x,grid_y,miniSize,offset);



        System.out.println("Done deal");
        ImageView currentMap = new ImageView(colorMapped);






        miniMap = addScene(mini);
        mini.getChildren().add(currentMap);












        Group root = new Group();
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

        ImageView[][] grid=new ImageView[grid_x][grid_y];
        for (int i = 0; i < grid_x; i++) {
            for (int j = 0; j < grid_y; j++) {
                ImageView temp=new ImageView();
                temp.setFitHeight(size);
                temp.setPreserveRatio(true);
                temp.setX(width+i*(size+width*2));
                temp.setY(width+j*(size+width*2));
                root.getChildren().add(temp);
                grid[i][j] = temp;
            }
        }
        WritableImage denem = new WritableImage(64,64);
        PixelWriter denemWriter = denem.getPixelWriter();
        paint(0,0,64,Color.PALEGREEN,denemWriter);
        grid[0][0].setImage(denem);



        Circle ball = new Circle();
        ball.setCenterX(32+width);
        ball.setCenterY(32+width);
        ball.setRadius(32);
        ball.setStroke(Color.FIREBRICK);
        ball.setFill(Color.FIREBRICK);
        root.getChildren().add(ball);







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
    void paintCanvas(int[][] map,PixelWriter writer,int grid_x,int grid_y,int miniSize,int sizedOffset,int width){
        int screen_x = grid_x*(miniSize+width)+width+sizedOffset*2;
        int screen_y = grid_y*(miniSize+width)+width+sizedOffset*2;
        for (int i = 0; i < screen_x; i++) {
            for (int j = 0; j <screen_y ; j++) {

                if(i>=sizedOffset && i<screen_x-sizedOffset && j>=sizedOffset && j<screen_y-sizedOffset){
                    int k = i-sizedOffset;
                    int l = j-sizedOffset;

                    if(k%(miniSize+width)<width || l%(miniSize+width)<width){
                        writer.setColor(i,j,Color.DARKGRAY);
                    } else if (k%(miniSize+width)==width && l%(miniSize+width)==width) {
                        int tempID=map[k/(miniSize+width)][l/(miniSize+width)];
                        paint(i,j,miniSize ,tempID,writer);
                    }


                }
                else {
                    System.out.println(j);
                    writer.setColor(i, j, Color.SADDLEBROWN);
                }
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
        return switch (ID) {
            case 0 -> Color.WHITE;
            case 1 -> Color.YELLOW;
            case 2 -> Color.FIREBRICK;
            case 4 -> Color.BROWN;
            case 542 -> Color.GOLD;
            case 70153 -> Color.DARKBLUE;
            case 5 -> Color.DARKGREEN;
            case 6 -> Color.DARKSLATEGRAY;
            case 7 -> Color.DARKVIOLET;
            case 8 -> Color.VIOLET;
            case 9 -> Color.YELLOWGREEN;
            case 10 -> Color.LIGHTSKYBLUE;
            case 40 -> Color.rgb(100,100,100);
            case 41 -> Color.rgb(110,110,110);
            case 42 -> Color.rgb(120,120,120);
            default -> Color.DEEPPINK;
        };
    }
    void executeStoneAge(int grid_x,int grid_y,int[][] map,int[] empties ,ArrayList<Point>[] P_D,int stoneCount,int treeCount){
        Random random = new Random();
        for (int i = 0; i < treeCount; i++) {
            boolean again;
            do {
                again=false;
                int temp = random.nextInt(P_D[0].size());
                int state = random.nextInt(4);
                int size = random.nextInt(1,3);
                int sx;
                int ex;
                int sy;
                int ey;
                Point p = P_D[0].get(temp);
                switch (state){
                    case 0:
                        sx=p.x;
                        ex=p.x+size*2;
                        sy=p.y;
                        ey=p.y+size*2;
                        if(ex < grid_x && ey < grid_y && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,5);
                            break;
                        }

                    case 1:
                        sx=p.x+1-size*2;
                        ex=p.x+1;
                        sy=p.y;
                        ey=p.y+size*2;
                        if(sx > 0 && ey < grid_y && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,5);
                            break;
                        }
                    case 2:
                        sx=p.x+1-size*2;
                        ex=p.x+1;
                        sy=p.y+1-size*2;
                        ey=p.y+1;
                        if(sx > 0 && sy > 0 && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,5);
                            break;
                        }
                    case 3:
                        sx=p.x;
                        ex=p.x+size*2;
                        sy=p.y+1-size*2;
                        ey=p.y+1;
                        if(ex < grid_x && sy > 0 && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,5);
                            break;
                        }
                    default:
                        again=true;

                }
            }while (again);
        }
        for (int i = 0; i < stoneCount; i++) {
            boolean again;
            do {
                again=false;
                int temp = random.nextInt(P_D[1].size());
                int state = random.nextInt(4);
                int size = random.nextInt(1,3);
                int sx;
                int ex;
                int sy;
                int ey;
                Point p = P_D[1].get(temp);
                switch (state){
                    case 0:
                        sx=p.x;
                        ex=p.x+size*2;
                        sy=p.y;
                        ey=p.y+size*2;
                        if(ex < grid_x && ey < grid_y && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,6);
                            break;
                        }

                    case 1:
                        sx=p.x+1-size*2;
                        ex=p.x+1;
                        sy=p.y;
                        ey=p.y+size*2;
                        if(sx > 0 && ey < grid_y && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,6);
                            break;
                        }
                    case 2:
                        sx=p.x+1-size*2;
                        ex=p.x+1;
                        sy=p.y+1-size*2;
                        ey=p.y+1;
                        if(sx > 0 && sy > 0 && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,6);
                            break;
                        }
                    case 3:
                        sx=p.x;
                        ex=p.x+size*2;
                        sy=p.y+1-size*2;
                        ey=p.y+1;
                        if(ex < grid_x && sy > 0 && checkSpace(map,sx,ex,sy,ey,empties)  ){
                            fillMatrix(map,sx,ex,sy,ey,6);
                            break;
                        }
                    default:
                        again=true;

                }
            }while (again);
        }
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
    int[][] cellularAutomata( int grid_x,int grid_y,int[][] map,int iteration){
        int [][] phase1 = new int[map.length][];
        int [][] phase2 = new int[map.length][];
        for(int i = 0; i < map.length; i++) {
            phase1[i] = map[i].clone();
            phase2[i] = map[i].clone();
        }
        for (int r = 0; r < iteration; r++) {
            int[][] temp = phase1;
            phase1 = phase2;
            phase2 = temp;
            for (int i = 0; i < grid_x; i++) {
                for (int j = 0; j < grid_y; j++) {
                    if(map[i][j]!=0 && map[i][j]!=70153)continue;
                    int darkCount=0;
                    if(i > 0){
                        if(phase1[i-1][j]!=0 )darkCount++;

                        if(j > 0 && phase1[i-1][j-1]!=0)darkCount++;


                    }
                    if(j > 0){
                        if(phase1[i][j-1]!=0)darkCount++;

                        if(i < grid_x-1 && phase1[i+1][j-1]!=0)darkCount++;


                    }
                    if(i < grid_x-1){
                        if(phase1[i+1][j]!=0)darkCount++;

                        if(j < grid_y-1 && phase1[i+1][j+1]!=0)darkCount++;


                    }
                    if(j < grid_y-1){
                        if(phase1[i][j+1]!=0)darkCount++;

                        if(i > 0 && phase1[i-1][j+1]!=0)darkCount++;


                    }
                    if(darkCount > 4)phase2[i][j]=70153;
                    else phase2[i][j]=0;

                }
            }
        }
        return phase2;


    }
    ArrayList[] forestation(int grid_x,int grid_y,int[][] map){
        int heightMap[][] = new int[grid_x][grid_y];
        for (int i = 0; i < grid_x; i++) {
            for (int j = 0; j < grid_y; j++) {
                if(map[i][j]==70153)
                    heightMap[i][j]=shortest(grid_x,grid_y,map,i,j,-1);
                else if (map[i][j]==0) {
                    heightMap[i][j]=shortest(grid_x,grid_y,map,i,j,1);
                }else heightMap[i][j]=0;
            }
        }

        ArrayList<Point>[] peaks_dips = peaks_dips(grid_x,grid_y,heightMap);
        Random random =new Random();
        System.out.println(peaks_dips[0].size());
        System.out.println(peaks_dips[1].size());


        return peaks_dips;

    }
    ArrayList<Point>[] peaks_dips(int grid_x,int grid_y,int[][] map){
        ArrayList<Point>[] P_and_D = new ArrayList[2];
        P_and_D[0]=new ArrayList<>();
        P_and_D[1]=new ArrayList<>();
        for (int i = 0; i < grid_x; i++) {
            for (int j = 0; j < grid_y; j++) {
                if(        (i == 0 || map[i-1][j] < map[i][j])
                        && (j == 0 || map[i][j-1] < map[i][j])
                        && (i == grid_x-1 || map[i+1][j] < map[i][j])
                        && (j == grid_y-1 || map[i][j+1] < map[i][j])
                        && map[i][j] > 2)P_and_D[0].add(new Point(i,j));
                else if(   (i == 0 || map[i-1][j] > map[i][j])
                        && (j == 0 || map[i][j-1] > map[i][j])
                        && (i == grid_x-1 || map[i+1][j] > map[i][j])
                        && (j == grid_y-1 || map[i][j+1] > map[i][j])
                        && map[i][j] < -2)P_and_D[1].add(new Point(i,j));

            }
        }
        return P_and_D;
    }

    int shortest(int grid_x,int grid_y,int[][] map,int x, int y,int side){
        int dist = 1;

        do {
            for (int i = dist; i > 0; i--) {
                int lx = x + i;
                int ly = y + (dist - i);
                if (lx < grid_x && ly < grid_y && map[lx][ly] != map[x][y]) return dist * side;
            }
            for (int i = dist; i > 0; i--) {
                int lx = x - (dist - i);
                int ly = y + i;
                if (lx > 0 && ly < grid_y && map[lx][ly] != map[x][y]) return dist * side;
            }
            for (int i = dist; i > 0; i--) {
                int lx = x - i;
                int ly = y - (dist - i);
                if (lx > 0 && ly > 0 && map[lx][ly] != map[x][y]) return dist * side;
            }
            for (int i = dist; i > 0; i--) {
                int lx = x + (dist - i);
                int ly = y - i;
                if (lx < grid_x && ly > 0 && map[lx][ly] != map[x][y]) return dist * side;
            }
            dist++;
        } while (dist <= grid_x || dist <= grid_y);
        return -1;
    }

    void BPSRoom(int grid_x,int grid_y,int expSpace,int roomSize,int[][] map,int ID,int iteration,boolean isRoaded) {
        BPSRoom(grid_x,grid_y,expSpace,roomSize,roomSize,map,ID,iteration,isRoaded);
    }
    //duvarlara renkli offsett ekle

    void BPSRoom(int grid_x,int grid_y,int expSpace,int roomSize_x,int roomSize_y,int[][] map,int ID,int iteration,boolean isRoaded){


        int[] empties = new int[]{0,8};

        int steps_x = Integer.highestOneBit(grid_x)>>expSpace;
        int steps_y = Integer.highestOneBit(grid_y)>>expSpace;
        ArrayList<roomKey> rooms = new ArrayList<>();
        for (int r = 0; r < iteration; r++) {
            Random random = new Random();
            int theStep_x = random.nextInt(steps_x);//steps_x-1 e eşitken dene
            int theStep_y = random.nextInt(steps_y);
            int start_x = 0;
            int end_x = grid_x;

            int start_y = 0;
            int end_y = grid_y;

            for (int i = 1; i < steps_x; i*=2) {


                if(theStep_x%2==1) {
                    start_x = (end_x + start_x) / 2;

                }
                else
                    end_x = (end_x+start_x)/2;



                theStep_x/=2;
            }

            for (int i = 1; i < steps_y; i*=2) {


                if(theStep_y%2==1) {
                    start_y = (end_y + start_y) / 2;

                }
                else
                    end_y = (end_y+start_y)/2;



                theStep_y/=2;
            }
            //random sayı 11111 iken dene
            int temp_x = random.nextInt(start_x,end_x-roomSize_x+1);
            int temp_y = random.nextInt(start_y,end_y-roomSize_y+1);


            if(checkSpace(map,temp_x,temp_x+roomSize_x,temp_y,temp_y+roomSize_y,empties)){
                fillMatrix(map,temp_x,temp_x+roomSize_x,temp_y,temp_y+roomSize_y,ID);

                if(isRoaded)
                    rooms.add(new roomKey(temp_x+roomSize_x/2,temp_y+roomSize_y/2));

            }else r--;
        }

        if(isRoaded){

            ArrayList<distanceKey> roads = new ArrayList<>();
            roomKey head = rooms.getFirst();
            for (int i = 0; i < iteration-1; i++) {
                distanceKey temp = null;
                roomKey next = null;
                int tempDist=Integer.MAX_VALUE;
                for (roomKey room:
                     rooms) {
                    if(room==head)continue;
                    distanceKey temp2 = new distanceKey(head,room);
                    if(temp2.distance<=tempDist){
                        temp=temp2;
                        tempDist=temp2.distance;
                        next = room;
                    }

                }
                if (temp != null) {
                    roads.add(temp);
                    rooms.remove(head);
                    head=next;
                }else System.out.println("ohh noo");


            }


            for (distanceKey road:
                    roads) {
                fillRoad(3,road.A.x,road.B.x,road.A.y,road.B.y,ID,map,empties);
            }
        }









    }
    void BPSRoomOffsetted(int grid_x,int grid_y,int expSpace,int roomSize_x,int roomSize_y,int[][] map,int ID,int iteration,boolean isRoaded,int offsetId,int offsetSize){


        int[] empties = new int[]{0};

        int steps_x = Integer.highestOneBit(grid_x)>>expSpace;
        int steps_y = Integer.highestOneBit(grid_y)>>expSpace;
        ArrayList<roomKey> rooms = new ArrayList<>();
        for (int r = 0; r < iteration; r++) {
            Random random = new Random();
            int theStep_x = random.nextInt(steps_x);//steps_x-1 e eşitken dene
            int theStep_y = random.nextInt(steps_y);
            int start_x = 0;
            int end_x = grid_x;

            int start_y = 0;
            int end_y = grid_y;

            for (int i = 1; i < steps_x; i*=2) {


                if(theStep_x%2==1) {
                    start_x = (end_x + start_x) / 2;

                }
                else
                    end_x = (end_x+start_x)/2;



                theStep_x/=2;
            }

            for (int i = 1; i < steps_y; i*=2) {


                if(theStep_y%2==1) {
                    start_y = (end_y + start_y) / 2;

                }
                else
                    end_y = (end_y+start_y)/2;



                theStep_y/=2;
            }
            //random sayı 11111 iken dene

            int temp_x = random.nextInt(start_x,end_x-roomSize_x-2*offsetSize+1);
            int temp_y = random.nextInt(start_y,end_y-roomSize_y-2*offsetSize+1);

            if(checkSpace(map
                    ,temp_x
                    ,temp_x+roomSize_x+offsetSize*2
                    ,temp_y
                    ,temp_y+roomSize_y+offsetSize*2
                    ,empties)){
                fillMatrix(map
                        ,temp_x
                        ,temp_x+roomSize_x+offsetSize*2
                        ,temp_y
                        ,temp_y+roomSize_y+offsetSize*2
                        ,offsetId);
                fillMatrix(map
                        ,temp_x+offsetSize
                        ,temp_x+roomSize_x+offsetSize
                        ,temp_y+offsetSize
                        ,temp_y+roomSize_y+offsetSize
                        ,ID);

                if(isRoaded)
                    rooms.add(new roomKey(temp_x+roomSize_x/2,temp_y+roomSize_y/2));

            }else r--;
        }

        if(isRoaded){

            ArrayList<distanceKey> roads = new ArrayList<>();
            roomKey head = rooms.getFirst();
            for (int i = 0; i < iteration-1; i++) {
                distanceKey temp = null;
                roomKey next = null;
                int tempDist=Integer.MAX_VALUE;
                for (roomKey room:
                        rooms) {
                    if(room==head)continue;
                    distanceKey temp2 = new distanceKey(head,room);
                    if(temp2.distance<=tempDist){
                        temp=temp2;
                        tempDist=temp2.distance;
                        next = room;
                    }

                }
                if (temp != null) {
                    roads.add(temp);
                    rooms.remove(head);
                    head=next;
                }else System.out.println("ohh noo");


            }


            for (distanceKey road:
                    roads) {
                fillRoad(3,road.A.x,road.B.x,road.A.y,road.B.y,ID,map,empties);
            }
        }









    }
    void paintRoadV(int width,int x,int start_y,int end_y,int ID,int[][] map,int[] empty){
        if(start_y>end_y){
            int temp=start_y;
            start_y=end_y;
            end_y=temp;
        }

        for (int i = start_y; i <= end_y; i++) {
            for (int j = -width/2; j < width/2+1; j++) {

                if(isHere(empty,map[x+j][i]))map[x+j][i]=ID;
            }
        }
    }
    void paintRoadH(int width,int y,int start_x,int end_x,int ID,int[][] map,int[] empty){
        if(start_x>end_x){
            int temp=start_x;
            start_x=end_x;
            end_x=temp;
        }

        for (int i = start_x; i <= end_x; i++) {
            for (int j = -width/2; j < width/2+1; j++) {

                if(isHere(empty,map[i][y+j]))map[i][y+j]=ID;
            }
        }
    }
    void paintSides(int ID,int[][] map,int[] empty,int sideID){
        for (int i = 0;i < map.length;i++){
            for (int j = 0; j < map[i].length;j++){
                if(map[i][j]==ID){
                    if(i!=0               && isHere(empty,map[i-1][j]))map[i-1][j]=sideID;
                    if(i!=map.length-1    && isHere(empty,map[i+1][j]))map[i+1][j]=sideID;
                    if(j!=0               && isHere(empty,map[i][j-1]))map[i][j-1]=sideID;
                    if(i!=map[i].length-1 && isHere(empty,map[i][j+1]))map[i][j+1]=sideID;
                }
            }
        }
    }
    void paintSides(int[] IDs,int[][] map,int[] empty,int sideID){
        for (int i = 0;i < map.length;i++){
            for (int j = 0; j < map[i].length;j++){
                if(isHere(IDs,map[i][j])){
                    if(i!=0               && isHere(empty,map[i-1][j]))map[i-1][j]=sideID;
                    if(i!=map.length-1    && isHere(empty,map[i+1][j]))map[i+1][j]=sideID;
                    if(j!=0               && isHere(empty,map[i][j-1]))map[i][j-1]=sideID;
                    if(j!=map[i].length-1 && isHere(empty,map[i][j+1]))map[i][j+1]=sideID;
                }
            }
        }
    }

    void fillRoad(int width,int start_x,int end_x,int start_y,int end_y,int ID,int[][] map,int[] empty){//choose width odd


        Random random = new Random();
        if(random.nextInt(2)==0){
            paintRoadV(width,start_x,start_y,end_y,ID,map,empty);
            paintRoadH(width,end_y,start_x,end_x,ID,map,empty);
        }else {
            paintRoadH(width,start_y,start_x,end_x,ID,map,empty);
            paintRoadV(width,end_x,start_y,end_y,ID,map,empty);
        }



    }
    class roomKey{
        public roomKey(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int roaded = -1;

        final int x;
        final int y;
    }
    class distanceKey{
        final int distance;
        final roomKey A;
        final roomKey B;
        public boolean sameWay(){
            if(A.roaded < B.roaded)B.roaded = A.roaded;
            else if(B.roaded < A.roaded)A.roaded = B.roaded;
            else return true;
            return false;
        }

        public distanceKey( roomKey a, roomKey b) {

            A = a;
            B = b;
            this.distance = Math.abs((A.x- B.x))+Math.abs(A.y- B.y);
        }
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
    }//Hatalı

}
