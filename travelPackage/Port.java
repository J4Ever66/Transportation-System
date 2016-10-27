package travelPackage;

public class Port extends TransportationDestination{

    @Override
    public void finishCreation(TransportationData dc) {
        super.setName("Port");
        super.setID(dc.getDestID());
    }
}
