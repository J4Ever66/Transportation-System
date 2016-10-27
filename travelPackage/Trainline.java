package travelPackage;

public class Trainline extends TransportationManager{


    @Override
    public void finishCreation(TransportationData dc) {
        super.setID(dc.getManagerID());
        super.setDestinationCap(99); // origin and final destination
        super.setName("Trainline");
    }
}
