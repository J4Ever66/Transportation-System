package travelPackage;

public class FlightSection extends TransportationContainer {

    public SeatClass getClassification(){
        return super.getClassification();
    }

    @Override
    public void finishCreation(TransportationData dc) {
        super.setPrice(dc.getPrice());
        super.setClassification(dc.getClassSection());
        super.setName("FlightSection");
        setLayout(dc.getClassLayout(), dc.getRowVal()); // UNIQUE CONTAINER LAYOUTS
    }
}
