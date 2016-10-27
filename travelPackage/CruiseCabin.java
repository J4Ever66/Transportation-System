package travelPackage;

public class CruiseCabin extends TransportationContainer{

    @Override
    public void finishCreation(TransportationData dc) {
        super.setPrice(dc.getPrice());
        super.setClassification(dc.getClassSection());
        super.setName("CruiseCabin");
    }
}
