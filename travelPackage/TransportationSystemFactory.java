package travelPackage;

public abstract class TransportationSystemFactory {
    protected abstract TransportationContainer createContainer();
    protected abstract TransportationManager createManager();
    protected abstract TransportationDestination createDestination();
    protected abstract TransportationRoute createRoute();


    public TransportationManager makeManager(TransportationData data){
        TransportationManager tm = createManager();
        tm.finishCreation(data);
        return tm;
    }
    public TransportationDestination makeDestination(TransportationData data){
        TransportationDestination td = createDestination();
        td.finishCreation(data);
        return td;
    }
    public TransportationRoute makeRoute(TransportationData data){
        TransportationRoute tr = createRoute();
        tr.finishCreation(data);
        return tr;
    }
    public TransportationContainer makeContainer(TransportationData data){
        TransportationContainer tc = createContainer();
        tc.finishCreation(data);
        return tc;
    }

}
