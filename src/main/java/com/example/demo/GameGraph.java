package com.example.demo;

import java.util.*;
import java.util.Map;

import static java.util.function.Predicate.not;


public class GameGraph {
    Map<GameGraph.Square,Integer> visited;
    int squareCount=0;
    Square theSquare;
    Square fartest;
    private LinkedList<Square> route;

    public Square getFartest() {
        return fartest;
    }

    public Square getTheSquare() {
        return theSquare;
    }

    public void setTheSquare(Square theSquare) {
        this.theSquare = theSquare;
    }
    public void setTheSquare(int x,int y) {
        if(grid[x][y]==null)throw new NullPointerException();
        this.theSquare = grid[x][y];
    }

    Square[][] grid;
    int grid_x;
    int grid_y;

    public GameGraph(int grid_x,int grid_y) {
        this.grid_x=grid_x;
        this.grid_y=grid_y;
        this.grid = new Square[grid_x][grid_y];
    }

    public class Square{
        public Square(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public class Target{
            Square square;
            int distance;

            public Target(Square target, int distance) {
                this.square = target;
                this.distance = distance;
            }
        }
        int x;
        int y;
        int shortest = Integer.MAX_VALUE;
        ArrayList<Target> adjacents=new ArrayList<>();
        void shortestSeen(int begin){
            int dist = begin;



            do {
                for (int i = dist; i > 0; i--) {
                    int lx = x + i;
                    int ly = y + (dist - i);
                    if (lx < grid_x && ly < grid_y && inRange(lx,ly) && grid[lx][ly]!=null) {
                        connect( dist, lx, ly);
                    }
                }
                for (int i = dist; i > 0; i--) {
                    int lx = x - (dist - i);
                    int ly = y + i;
                    if (lx > 0 && ly < grid_y && inRange(lx,ly) &&  grid[lx][ly]!=null) {
                        connect( dist, lx, ly);
                    }
                }
                for (int i = dist; i > 0; i--) {
                    int lx = x - i;
                    int ly = y - (dist - i);
                    if (lx > 0 && ly > 0 && inRange(lx,ly) && grid[lx][ly]!=null) {
                        connect( dist, lx, ly);
                    }
                }
                for (int i = dist; i > 0; i--) {
                    int lx = x + (dist - i);
                    int ly = y - i;
                    if (lx < grid_x && ly > 0 && inRange(lx,ly) && grid[lx][ly]!=null) {
                        connect( dist, lx, ly);
                    }
                }
                if (!(inRange(x+dist,y) || inRange(x-dist,y) || inRange(x,y+dist) || inRange(x,y-dist))) {

                    break;
                }
                dist++;
            } while (x+y+dist < grid_x+grid_y || x+y-dist >=0 || x+dist-y < grid_x || y+dist-x < grid_y);

        }


        void shortestSeenFirst(){
            this.adjacents=new ArrayList<>();
            shortestSeen(1);

        }
        void shortestSeenFirst(Target t){
            this.adjacents=new ArrayList<>();
            this.adjacents.add(t);
            shortestSeen(1);

        }//maybe try in wash but not much hope
        private void wash(){
            int dist = 1;



            do {
                for (int i = dist; i > 0; i--) {
                    int lx = x + i;
                    int ly = y + (dist - i);
                    if (lx < grid_x && ly < grid_y && inRange(lx,ly) && grid[lx][ly]!=null) {
                        grid[lx][ly].shortestSeenFirst();
                    }
                }
                for (int i = dist; i > 0; i--) {
                    int lx = x - (dist - i);
                    int ly = y + i;
                    if (lx > 0 && ly < grid_y && inRange(lx,ly) &&  grid[lx][ly]!=null) {
                        grid[lx][ly].shortestSeenFirst();
                    }
                }
                for (int i = dist; i > 0; i--) {
                    int lx = x - i;
                    int ly = y - (dist - i);
                    if (lx > 0 && ly > 0 && inRange(lx,ly) && grid[lx][ly]!=null) {
                        grid[lx][ly].shortestSeenFirst();
                    }
                }
                for (int i = dist; i > 0; i--) {
                    int lx = x + (dist - i);
                    int ly = y - i;
                    if (lx < grid_x && ly > 0 && inRange(lx,ly) && grid[lx][ly]!=null) {
                        grid[lx][ly].shortestSeenFirst();
                    }
                }
                if (!(inRange(x+dist,y) || inRange(x-dist,y) || inRange(x,y+dist) || inRange(x,y-dist))) {

                    break;
                }
                dist++;
            } while (x+y+dist < grid_x+grid_y || x+y-dist >=0 || x+dist-y < grid_x || y+dist-x < grid_y);
        }


        private void connect(int dist, int lx, int ly) {
            if(dist < shortest)shortest=dist;
            if(dist < grid[lx][ly].shortest)grid[lx][ly].shortest=dist;
            this.adjacents.add(new Target(grid[lx][ly],dist));
            grid[lx][ly].adjacents.add(new Target(this,dist));

        }
        private void connect(Square s,int dist) {
            if(dist < shortest)shortest=dist;
            if(dist < s.shortest)s.shortest=dist;
            this.adjacents.add(new Target(s,dist));
            s.adjacents.add(new Target(this,dist));
        }
        private void removeTarget(Square s){
            for (Target t:
                    this.adjacents) {
                if(t.square==s) {
                    this.adjacents.remove(t);
                    s.removeT(this);
                    int temp = shortest;
                    if(t.distance==shortest)
                        shortest=Integer.MAX_VALUE;
                    shortestSeen(temp);
                }
            }
        }
        private void removeT(Square s){
            for (Target t:
                    this.adjacents) {
                if(t.square==s) {
                    this.adjacents.remove(t);
                    int temp = shortest;
                    if(t.distance==shortest)
                        shortest=Integer.MAX_VALUE;
                    shortestSeen(temp);

                }
            }
        }
        boolean inRange(int x,int y){
            boolean result = true;
            for (Target target:
            this.adjacents) {
                Square s = target.square;
                if(this.x > s.x){
                    if(this.y>s.y){
                        result&=(x > s.x || y > s.y);
                    }else if(this.y < s.y){
                        result&=(x > s.x || y < s.y);
                    }else {
                        result&=(x > s.x);
                    }
                } else if (this.x < s.x) {
                    if(this.y>s.y){
                        result&=(x < s.x || y > s.y);
                    }else if(this.y < s.y){
                        result&=(x < s.x || y < s.y);
                    }else {
                        result&=(x < s.x);
                    }
                }else if(this.y > s.y){
                    result&=(y > s.y);
                }else if(this.y < s.y){
                    result&=(y < s.y);
                }

            }
            return result;
        }

    }
    public int flatAddFirst(ArrayList<Square> squares){
        for (Square s:
             squares) {
            if(grid[s.x][s.y]!=null)return -1;

            grid[s.x][s.y]=s;
        }
        for (Square s:
             squares) {
            s.shortestSeenFirst();
        }
        squareCount+=squares.size();
        return 0;
    }
    public int flatAdd(ArrayList<Square> squares){
        for (Square s:
                squares) {
            if(grid[s.x][s.y]!=null)return -1;

            grid[s.x][s.y]=s;
        }
        for (Square s:
                squares) {
            s.wash();
        }
        squareCount+=squares.size();
        return 0;
    }
    public int add(Square s){
        if(grid[s.x][s.y]!=null)return -1;

        grid[s.x][s.y]=s;
        s.wash();
        squareCount++;

        return 0;
    }
    public void flatRemove(ArrayList<Square> squares){
        for (Square s:
             squares) {
            grid[s.x][s.y]=null;
        }
        for (Square s :
                squares) {
            for (Square.Target t :
                    s.adjacents) {
                t.square.removeT(s);



            }
        }
        squareCount-=squares.size();
    }
    public void remove(Square s){
        grid[s.x][s.y]=null;
        for (Square.Target t :
                s.adjacents) {
            t.square.removeT(s);



        }
        squareCount--;

    }
    public void fastestRoute(int x,int y){
        ArrayList<Integer> x_values=new ArrayList<>();
        ArrayList<Integer> y_values=new ArrayList<>();



        for (int i = 0; i < grid_x; i++) {
            if( (i <= x && i%7==3) || (grid_x-i >= x && (grid_x-i)%7==3) ||  i==x){
                for (int j = 0; j < grid_y; j++) {
                    if((j <= y && j%7 == 3) || (grid_y-j >= y && (grid_y-j)%7==3) || (j==y)){
                        x_values.add(i);
                        y_values.add(j);

                    }
                }
            }

        }

        int num_of_squares = x_values.size();
        ArrayList<GameGraph.Square> squares=new ArrayList<>();
        visited=new HashMap<>(num_of_squares);
        for (int i = 0; i < num_of_squares; i++) {
            GameGraph.Square temp = this.new Square(x_values.removeFirst(),y_values.removeFirst());
            squares.add(temp);
            visited.put(temp,0);
        }
        this.flatAddFirst(squares);
        this.setTheSquare(x,y);

        int maxDist = 0;
        Square farOne=theSquare;
        BinaryMinHeap<Square> minHeap = new BinaryMinHeap<>();


        Map<Square,Integer> distance = new HashMap<>();

        Map<Square,Square> parent = new HashMap<>();

        for (Square[] dizi:
                grid) {
            for (Square temp:
                    dizi) {
                if(temp!=null)minHeap.add(Integer.MAX_VALUE,temp);

            }
        }
        minHeap.decrease(theSquare,0);

        distance.put(theSquare, 0);

        parent.put(theSquare, null);

        while(!minHeap.empty()){
            //get the min value from heap node which has vertex and distance of that vertex from source vertex.
            BinaryMinHeap<Square>.Node heapNode = minHeap.extractMinNode();
            Square current = heapNode.key;

            //update shortest distance of current vertex from source vertex
            distance.put(current, heapNode.weight);

            //iterate through all edges of current vertex
            for(Square.Target edge : current.adjacents){

                //get the adjacent vertex
                Square adjacent = edge.square;

                //if heap does not contain adjacent vertex means adjacent vertex already has shortest distance from source vertex
                if(!minHeap.containsData(adjacent)){
                    continue;
                }

                //add distance of current vertex to edge weight to get distance of adjacent vertex from source vertex
                //when it goes through current vertex
                int newDistance = distance.get(current) + edge.distance;

                //see if this above calculated distance is less than current distance stored for adjacent vertex from source vertex
                if(minHeap.getWeight(adjacent) > newDistance) {
                    if(adjacent==farOne)maxDist=newDistance;
                    else if (newDistance > maxDist) {
                        maxDist = newDistance;
                        farOne=adjacent;
                    }
                    minHeap.decrease(adjacent, newDistance);
                    parent.put(adjacent, current);
                }
            }
        }





        this.fartest=farOne;
        GameGraph.Square temp=this.fartest;

        do {

            visited.replace(temp,null);
            route.addFirst(temp);
            temp=parent.get(temp);
        }while (temp!=null);
    }







}
