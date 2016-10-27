package travelPackage;

public class AirSystemFactory extends TransportationSystemFactory{

    @Override
    protected TransportationContainer createContainer() { // TransportationData param?
        return new FlightSection();
    }

    @Override
    protected TransportationManager createManager() {
        return new Airline();
    }

    @Override
    protected TransportationDestination createDestination() {
        return new Airport();
    }

    @Override
    protected TransportationRoute createRoute() {
        return new Flight();
    }
}
