package travelPackage;

public class Airline extends TransportationManager{
    @Override
    public void finishCreation(TransportationData dc) {
        super.setID(dc.getManagerID());
        super.setDestinationCap(2); // origin and final destination
        super.setName("Airline");
    }

}
