package travelPackage;

public class Airport extends TransportationDestination{
    public void finishCreation(TransportationData dc){
        super.setName("Airport");
        super.setID(dc.getDestID());
    }

}
