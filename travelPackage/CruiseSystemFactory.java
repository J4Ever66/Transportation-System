package travelPackage;

public class CruiseSystemFactory extends TransportationSystemFactory{
    @Override
    protected TransportationContainer createContainer() {
        return new CruiseCabin();
    }

    @Override
    protected TransportationManager createManager() {
        return new Cruiseline();
    }

    @Override
    protected TransportationDestination createDestination() {
        return new Port();
    }

    @Override
    protected TransportationRoute createRoute() {
        return new CruiseTrip();
    }
}
