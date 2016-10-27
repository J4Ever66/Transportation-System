package travelPackage;

public class TrainSystemFactory extends TransportationSystemFactory {

    @Override
    protected TransportationContainer createContainer() {
        return new TrainCar();
    }

    @Override
    protected TransportationManager createManager() {
        return new Trainline();
    }

    @Override
    protected TransportationDestination createDestination() {
        return new TrainStation();
    }

    @Override
    protected TransportationRoute createRoute() {
        return new TrainTrip();
    }
}
