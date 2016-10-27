package travelPackage;

public class TrainStation extends TransportationDestination {
    @Override
    public void finishCreation(TransportationData dc) {
        super.setName("Train Station");
        super.setID(dc.getDestID());
    }
}
