package travelPackage;

public class TrainCar extends TransportationContainer{

    @Override
    public void finishCreation(TransportationData dc) {
        super.setPrice(dc.getPrice());
        super.setClassification(dc.getClassSection());
        super.setName("TrainCar");
        setLayout(dc.getClassLayout(), dc.getRowVal()); // UNIQUE CONTAINER LAYOUTS
    }

}
