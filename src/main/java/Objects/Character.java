package Objects;

public class Character {
    int ID;
    Location loc;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Character(int id, Location loc){
        this.ID = id;
        this.loc = loc;
    }


}
