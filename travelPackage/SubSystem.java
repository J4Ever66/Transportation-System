package travelPackage;
import java.util.ArrayList;

public class SubSystem {
    private Utility utils;
    private TransportationSystemFactory factory;
    private ArrayList<TransportationDestination> destinations; // airport, station, port
    private ArrayList<TransportationManager> managers;    // airline, trainline, cruise

    public SubSystem(){
        reset(); // initialize arraylists
    }

    public void addManager(String managerID) throws Exception{
        if (managerID.length() < 6 && managerID.length() != 0 && !checkManagers(managerID)) {
            TransportationData dc = new TransportationData();
            dc.setManagerID(managerID);
            this.managers.add(factory.makeManager(dc));
        }else{
            System.out.println("Cannot add manager. Invalid input.");
        }
    }

    public void addDestination(String destinationID) throws Exception{
        if(destinationID.length() == 3  &&  !checkDestinations(destinationID)) {
            TransportationData dc = new TransportationData();
            dc.setDestID(destinationID);
            this.destinations.add((factory.makeDestination(dc)));
        }else{
            System.out.println("Sorry, airport already exists or has an invalid ID.");
        }
    }

    public void addRoute(String managerID, String firstDestination, String lastDestination, int year, int month, int day, int hour, int minutes, String routeID) throws Exception{
        if (utils.checkTime(hour, minutes) && checkManagers(managerID) && checkDestinations(firstDestination) && checkDestinations(lastDestination) && !checkRoutes(routeID) && utils.checkDate(year, month, day)) {
            TransportationManager tm = getManager(managerID);
            if(tm != null){
                TransportationData dc = new TransportationData();
                dc.setRouteID(routeID);
                dc.setDestinations(getDestination(firstDestination), getDestination(lastDestination));
                dc.setDate(year, month, day);
                dc.setTime(hour, minutes);
                TransportationRoute route = factory.makeRoute(dc);
                tm.addRoute(route);
            }
        }else{
            System.out.println("Route " + routeID + " could not be added. Invalid input.");
        }
    }

    public void addContainerWithLayout(SeatClass tc, char layout, int rows, int cols, int price, String routeID) throws Exception{
        if (checkRoutes(routeID)) {
            TransportationRoute tr = getRoute(routeID);
            TransportationData td = new TransportationData();
            td.setPrice(price);
            td.setRowVal(rows);
            td.setColVal(cols);
            td.setClassSection(tc);
            td.setClassLayout(layout);
            TransportationContainer container = factory.makeContainer(td);
            tr.addContainer(container);
        }else{
            System.out.println("Container could not be added to route " + routeID + ". Invalid input.");
        }
    }

    public void addContainer(SeatClass tc, int rows, int cols, int price, String routeID) throws Exception{
        if (checkRoutes(routeID)) {
            TransportationRoute tr = getRoute(routeID);
            TransportationData td = new TransportationData();
            td.setPrice(price);
            td.setRowVal(rows);
            td.setColVal(cols);
            td.setClassSection(tc);
            TransportationContainer container = factory.makeContainer(td);
            tr.addContainer(container);
        }else{
            System.out.println("Container could not be added to route " + routeID + ". Invalid input.");
        }
    }


    public void addSection(String managerID, String routeID, int rowCnt, char classLayout, int price, SeatClass classID)throws Exception{
        if(checkManagers(managerID) && checkRoutes(routeID) && price > 0 && rowCnt <= 100  && checkClassLayout(classLayout)){
            TransportationData dc = new TransportationData();
            dc.setRowVal(rowCnt);
            dc.setClassLayout(classLayout);
            dc.setClassSection(classID);
            dc.setPrice(price);
            TransportationRoute newFlight = getRoute(routeID);
            if(newFlight != null) {
                if (!newFlight.checkClassExistence(classID)) {
                    (newFlight).addContainer(factory.makeContainer(dc));
                }
            }else{
                System.out.println("Route " + routeID + " could not be found.");
            }
        }else{
            System.out.println("Section for " + managerID + ", " + routeID + " could not be added. Invalid input.");
        }
    }

    public void changeSectionPrice(String routeID, SeatClass s, int price){
        try {
            TransportationRoute route = getRoute(routeID);
            if(route != null) {
                route.changeSectionPrice(s, price);
            }
        }catch(Exception e){ e.printStackTrace(); }
    }

    public void findAvailableRoutes(String origID, String destID, SeatClass classification, int[] dateNTime) throws Exception{ // OVERLOADED -- includes date & time
        boolean found = false;
        if(checkDestinations(origID) && checkDestinations(destID) && utils.checkDate(dateNTime[0], dateNTime[1], dateNTime[2]) && utils.checkTime(dateNTime[3], dateNTime[4])) {
            System.out.println("Available routes from " + origID + " to " + destID + " :\n");
            for (int x = 0; x < managers.size(); x++) {
                TransportationManager air = managers.get(x); // INDIVIDUAL AIRLINE
                for (int y = 0; y < air.getRouteCount() && !found; x++) {
                    TransportationRoute tr = air.getRouteByIndex(y); // INDIVIDUAL Route
                    if (tr.getOriginID().equals(origID) && tr.getFinalDestinationID().equals(destID) && tr.matchDateNTime(dateNTime)) {
                        TransportationContainer sc = tr.getContainerByClass(classification);
                        if (sc.hasAvailableSeats()) {
                            System.out.printf("Manager: " + air.getID() + " -- Price: " + sc.getPrice() + " - Route: " + tr.getID() + ": " + origID + " to " + destID + "\n");
                            found = true;
                        }
                    }
                }
            }
        }
        if(!found){
            System.out.println("Sorry, no available routes from " + origID + " to " + destID + " could be found.");
        }
    }

    public void findAvailableRoutes(String origID, String destID) throws Exception{
        boolean found = false;
        if(checkDestinations(origID) && checkDestinations(destID)) {
            System.out.println("Available routes from " + origID + " to " + destID + " :\n");
            for (int x = 0; x < managers.size(); x++) {
                TransportationManager air = managers.get(x); // INDIVIDUAL AIRLINE
                for (int y = 0; y < air.getRouteCount() && !found; x++) {
                    TransportationRoute tr = air.getRouteByIndex(y); // INDIVIDUAL FLIGHT
                    if (tr.getOriginID().equals(origID) && tr.getFinalDestinationID().equals(destID)) {
                        if (tr.checkForAvailableSeat()) {
                            System.out.printf("Manager: " + air.getID() + " - Route: " + tr.getID() + ": " + origID + " to " + destID + "\n");
                            found = true;
                        }
                    }
                }
            }
        }
        if(!found){
            System.out.println("Sorry, no available routes from " + origID + " to " + destID + " could be found.");
        }
    }


    public void bookContainerByPosition(String routeID, SeatClass sc, int row, int col){ // maybe put these down one? Abstract them out?
        TransportationRoute route = getRoute(routeID);
        if(route != null){
            TransportationContainer tc = route.getContainerByClass(sc);
            if(tc.hasAvailableSeats()){
                tc.bookContainer(row, col);
            }else{
                System.out.println("Sorry, the requested seating class is currently full.");
            }
        }else{
            System.out.println("The requested route " + routeID + " could not be found.");
        }

    }

    public void bookContainerByPreference(String routeID, String seatPref){
        TransportationRoute route = getRoute(routeID);
        if(route != null){
            route.getContainerByPreference(seatPref);
        }else{
            System.out.println("The requested route " + routeID + " could not be found.");
        }

    }

    public void displaySystemDetails(){
        System.out.println(":[DESTINATIONS]:\n");
        for(int x = 0; x < destinations.size(); x++){
            System.out.println(destinations.get(x).toString() );
        }
        System.out.println("\n:[MANAGERS]:\n");
        for(int x = 0; x < managers.size(); x++){
            System.out.println(managers.get(x).toString()  + '\n');
        }

    }

    public String getSystemState(){
        String ret = "[";
        for(int x = 0; x < destinations.size(); x++){
            ret += destinations.get(x).toFile(); // iterate through objects and request information to save state
            if((x + 1) < destinations.size()){  ret += ", "; // NOT LAST ELEMENT
            }else{  ret += "]"; } // LAST ELEMENT
        }
        ret += "{";
        for(int x = 0; x < managers.size(); x++){
            ret += managers.get(x).toFile();
            if((x + 1) < managers.size()){  ret += ", ";    }
            else{ }
        }
        ret += "}";
        return ret;
    }

    public void reset(){ // erase system state, prep for post use.
        this.destinations = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    public TransportationRoute getRoute(String routeID){
        for(int x = 0; x < this.managers.size(); x++){
            TransportationRoute tr = managers.get(x).getRouteByID(routeID);
            if(tr != null){
                return tr;
            }
        }
        return null;
    }

    public TransportationRoute getRoute(String managerID, String routeID) throws Exception{
        for(int x = 0; x < this.managers.size(); x++){
            if((managers.get(x).getID().equals(managerID))){
                return (managers.get(x)).getRouteByID(routeID);
            }
        }
        return null;
    }
    
    private TransportationManager getManager(String managerID)throws Exception{
        for(int x = 0; x < this.managers.size(); x++){
            if((managers.get(x).getID().equals(managerID))){
                return (managers.get(x));
            }
        }
        return null;
    }

    private TransportationDestination getDestination(String airID) throws Exception{
        if(this.destinations != null) {
            for (int x = 0; x < this.destinations.size(); x++) {
                if(destinations.get(x).getID().equals(airID)){
                    return destinations.get(x);
                }
            }
        }
        return null;
    }

    private boolean checkDestinations(String id) throws Exception{
        if(destinations.size() > 0) {
            for (int x = 0; x < this.destinations.size(); x++) {
                if ((destinations.get(x).getID().equals(id))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkManagers(String managerID) throws Exception{
        if(managers.size() > 0) {
            for (int x = 0; x < this.managers.size(); x++) {
                if ((managers.get(x).getID().equals(managerID))) { // NULL PTR EXC
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkRoutes(String routeID) throws Exception{
        for(int x = 0; x < this.managers.size(); x++){
            TransportationManager manager = managers.get(x);
            if(manager.checkRouteExistence(routeID)){
                return true;
            }
        }
        return false;
    }

    private boolean checkClassLayout(char classLayout){
        if(classLayout == 'S' || classLayout == 'W' || classLayout == 'M'){
            return true;
        }
        return false;
    }

    protected void setFactory(TransportationSystemFactory fP){ this.factory = fP;   }

    public void setUtils(Utility uP){   this.utils = uP;    }
}
