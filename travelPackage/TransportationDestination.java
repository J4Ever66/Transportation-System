package travelPackage;

public abstract class TransportationDestination extends Transportation {
    //  AIRPORTS / CRUISE PORTS / TRAIN STATIONS
    public String toString(){
        return super.toString(); // print destination ID
    }

    public String toFile(){
        return super.getID();
    }

}
