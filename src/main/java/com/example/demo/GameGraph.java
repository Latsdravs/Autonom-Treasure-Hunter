package com.example.demo;

import java.util.*;
import java.util.Map;

import static java.util.function.Predicate.not;


public class GameGraph {


    Map<GameGraph.Square,Integer> visited;
    int visitedCount=0;
    int squareCount=0;
    Square theSquare;
    Square fartest;
    class Step{
        int size;
        Square adding;
        Step step1;
        Step step2;
        int addingPrize;

        public Step(int size) {
            this.size = size;
            this.addingPrize=Integer.MAX_VALUE;
        }
    }
    private LinkedList<Square> route=new LinkedList<>();
    private LinkedList<Step> steps=new LinkedList<>();

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
            final Square square;
            final int distance;

            public Target(Square target, int distance) {
                this.square = target;
                this.distance = distance;
            }
        }
        boolean obstacle = false;
        final int x;
        final int y;
        int shortest = Integer.MAX_VALUE;
        ArrayList<Target> adjacents=new ArrayList<>();
        void shortestSeen(int begin){
            if(this.obstacle)return;
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
            if(!grid[lx][ly].obstacle)
                grid[lx][ly].adjacents.add(new Target(this,dist));

        }
        private void connect(Square s,int dist) {
            if(dist < shortest)shortest=dist;
            if(dist < s.shortest)s.shortest=dist;
            this.adjacents.add(new Target(s,dist));
            if(!s.obstacle)
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
            if(this.obstacle)return;
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
    public void emptySpace(int start_x,int start_y,int end_x,int end_y){

    }
    public void obstacleWall(int start_x,int start_y,int end_x,int end_y){}
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
        System.out.println("after first flat:"+squareCount);
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
    public void fastestRouteFirst(int x,int y){
        ArrayList<Integer> x_values=new ArrayList<>();
        ArrayList<Integer> y_values=new ArrayList<>();



        for (int i = 0; i < grid_x; i++) {
            if( (i <= x && i%7==3) || (grid_x-1-i >= x && (grid_x-1-i)%7==3) ||  i==x){
                for (int j = 0; j < grid_y; j++) {
                    if((j <= y && j%7 == 3) || (grid_y-1-j >= y && (grid_y-1-j)%7==3) || (j==y)){
                        x_values.add(i);
                        y_values.add(j);
                        if(x==i && y==j) System.out.println(i+" , "+j);

                    }
                }
            }

        }

        int num_of_squares = x_values.size();
        System.out.println("check points:"+num_of_squares);
        ArrayList<GameGraph.Square> squares=new ArrayList<>();
        visited=new HashMap<>();
        for (int i = 0; i < num_of_squares; i++) {
            GameGraph.Square temp = this.new Square(x_values.removeFirst(),y_values.removeFirst());
            squares.add(temp);
            visited.put(temp,0);

            if(temp.x==x && temp.y==y) System.out.println("in grid:"+temp.x+" , "+temp.y);
        }
        this.flatAddFirst(squares);
        System.out.println(x+" , "+y);
        System.out.println("ekledim:"+squares.size());
        this.setTheSquare(x,y);

        int maxDist = Integer.MIN_VALUE;
        Square farOne=theSquare;
        BinaryMinHeap<Square> minHeap = new BinaryMinHeap<>();


        Map<Square,Integer> distance = new HashMap<>();

        Map<Square,Square> parent = new HashMap<>();
        int sayac=0;
        for (Square[] dizi:
                grid) {
            for (Square temp:
                    dizi) {
                if(temp!=null && !temp.obstacle) {
                    temp.shortestSeenFirst();
                    System.out.println(temp.adjacents);
                    minHeap.add(Integer.MAX_VALUE, temp);
                    sayac++;
                }

            }
        }
        System.out.println("sayac:"+sayac);
        minHeap.decrease(theSquare,0);


        distance.put(theSquare, 0);

        parent.put(theSquare, null);
        minHeap.printHeap();

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
        System.out.println("after");
        minHeap.printHeap();

        System.out.println("maxDist:"+maxDist);
        this.fartest=farOne;
        GameGraph.Square temp=this.fartest;
        Square old;

        do {
            old=temp;
            visitedCount++;
            visited.replace(temp,null);
            route.addFirst(temp);
            temp=parent.get(temp);
            if(temp!=null)steps.addFirst(new Step( distance.get(old)-distance.get(temp) )  );
        }while (temp!=null);
        System.out.println("squareCount:"+squareCount);
        while (visitedCount<squareCount) {
            refreshLink_triangulize();
        }

    }

    private void refreshLink_triangulize(){// A B -> A C B  yapar ama A B -> A C A B yapmaz!!! gerek var mı bilmiyorum -Emin
        ListIterator<Square> li = route.listIterator();
        ListIterator<Step> stepper = steps.listIterator();
        int index=-1;
        int smallestAdding=Integer.MAX_VALUE;
        boolean duble=false;

        Square a = li.next();
        Square b;
        Step step;
        int i=0;
        while (li.hasNext() && stepper.hasNext()){
            b = li.next();
            step =stepper.next();
            upStep(a,b,step);
            if(step.addingPrize<smallestAdding){
                index=i;
                smallestAdding=step.addingPrize;
            }
            a=b;
            i++;

        }
        li = route.listIterator();
        i=0;
        Step ara=null;
        do {
            a=li.next();

            for (Square.Target t:
                 a.adjacents) {
                if(visited.get(t.square)!=null){
                    int tempPrize=t.distance*2;
                    if(tempPrize < smallestAdding){
                        smallestAdding=tempPrize;
                        index=i;
                        duble=true;
                        ara = new Step(0);
                        ara.addingPrize=tempPrize;
                        ara.adding=t.square;
                        ara.step1=new Step(t.distance);
                        ara.step2=new Step(t.distance);

                    }
                }


            }

            i++;
        }while (li.hasNext());
        if(duble){
            steps.add(index,ara);
            route.add(index,route.get(index));

        }


        if(index==-1)return;
        step=steps.get(index);
        route.add(index+1,step.adding);
        visited.replace(step.adding,null);
        visitedCount++;
        steps.remove(index);
        steps.add(index,step.step2);
        steps.add(index,step.step1);


    }
    private void upStep(Square a,Square b,Step step){

        for (Square.Target t1:
             a.adjacents) {
            for (Square.Target t2:
                    b.adjacents) {
                if(t1.square==t2.square && visited.get(t1.square)!=null){
                    int tempPrize=t1.distance+t2.distance-step.size;
                    if(tempPrize < step.addingPrize){
                        step.addingPrize=tempPrize;
                        step.adding=t1.square;
                        step.step1=new Step(t1.distance);
                        step.step2=new Step(t2.distance);

                    }


                }
            }
        }
    }
    public String getRoute() {
        ArrayList<Integer> moves=new ArrayList<>();
        ListIterator<Square> li = route.listIterator();
        System.out.println("way size"+route.size());
        for (int i = 0; i < route.size(); i++) {
            System.out.println(route.get(i).x+" , "+route.get(i).y);
        }
        Square a =li.next();
        Square b;
        while (li.hasNext()){
            b = li.next();
            if(b.x-a.x > 0){
                for (int i = a.x; i < b.x; i++) {
                    moves.add(2);
                }
            }else {
                for (int i = b.x; i < a.x; i++) {
                    moves.add(-2);
                }
            }
            if(b.y-a.y > 0){
                for (int i = a.y; i < b.y; i++) {
                    moves.add(1);
                }
            }else {
                for (int i = b.y; i < a.y; i++) {
                    moves.add(-1);
                }
            }
            a = b;
        }
        System.out.println("moves size"+moves.size());
        char[] charArray = new char[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            switch (moves.get(i)){
                case 1:
                    charArray[i]='D';
                    break;
                case -1:
                    charArray[i]='U';
                    break;
                case 2:
                    charArray[i]='R';
                    break;
                case -2:
                    charArray[i]='L';
                    break;
                default:
                    charArray[i]='W';
            }
        }
        return new String(charArray);

    }







}
