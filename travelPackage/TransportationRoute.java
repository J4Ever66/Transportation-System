package travelPackage;

import java.util.ArrayList;

public abstract class TransportationRoute extends Transportation{   //  FLIGHT / CRUISE / TRAINRIDE

    private ArrayList<TransportationDestination> routeDestinations; // AIRPORTS / CRUISE PORTS / TRAIN STATIONS
    private ArrayList<TransportationContainer> containers = new ArrayList<>();
    private int year, month, day, hour, minute;

    public TransportationRoute(){ this.routeDestinations = new ArrayList<>(); }


    public void addContainer(TransportationContainer tcp){
        this.containers.add(tcp);
    }

    public Transportation getRoute(String idP){
        for(int x = 0; x < routeDestinations.size(); x++){
            if(routeDestinations.get(x).getID().equals(idP)){
                return routeDestinations.get(x);
            }
        }
        return null;
    }

    public String getOriginID(){
        Transportation t = routeDestinations.get(0);
        if(t != null){
            return t.getID();
        }
        return "NULL";
    }

    public String getFinalDestinationID(){
        Transportation t = routeDestinations.get(routeDestinations.size() - 1); // - 1 check
        if(t != null){
            return t.getID();
        }
        return "NULL";
    }

    public boolean checkClassExistence(SeatClass classP){
        if(containers != null) {
            for (int x = 0; x < containers.size(); x++) {
                if(containers.get(x).getClassification() == classP){
                    return true;
                }
            }
        }
        return false;
    }

    public TransportationContainer getContainerByClass(SeatClass classP){
        if(containers != null) {
            for (int x = 0; x < containers.size(); x++) {
                if(containers.get(x).getClassification() == classP){
                    return containers.get(x);
                }
            }
        }
        return null;
    }

    public void getContainerByPreference(String preference){
        boolean good2go = false;
        for(int x = 0; x < containers.size(); x++){
            if(containers.get(x).hasAvailableSeats()){
                    if(containers.get(x).findSeatGivenPreference(preference)){
                        good2go = true;
                        x = containers.size();
                    }
                if((x + 1) == containers.size()) {

                }
            }
        }
        if(!good2go){ // NO PREFERRED SEATS FOUND -- BOOK ANYTHING!
            for(int x = 0; x < containers.size(); x++){
                if(containers.get(x).hasAvailableSeats()){
                    if(containers.get(x).bookAnything()){
                        good2go = true;
                        x = containers.size();
                    }
                }
            }
            if(!good2go){   System.out.println("No available seats could be found on this flight.");    }
        }

    }

    public boolean checkForAvailableSeat(){
        if(containers != null) {
            for (int x = 0; x < containers.size(); x++) {
                if(containers.get(x).hasAvailableSeats()){
                    return true;
                }
            }
        }
        return false;
    }

    public void addDestination(TransportationDestination... tdP){
        for(int x = 0; x < tdP.length; x++){
            this.routeDestinations.add(tdP[x]);
        }
    }

    public void changeSectionPrice(SeatClass s, int price){
        TransportationContainer tc = getContainerByClass(s);
        tc.setPrice(price);
    }

    public boolean matchDateNTime(int[] dateNTime){
        if(dateNTime[0] == this.year && dateNTime[1] == this.month && dateNTime[2] == this.day && dateNTime[3] == this.hour && dateNTime[4] == this.minute){
            return true;
        }else{
            return false;
        }
    }

    public void setYear(int y){
        this.year = y;
    }
    public void setMonth(int m){
        this.month = m;
    }
    public void setDay(int d){
        this.day = d;
    }
    public void setHour(int h){ this.hour = h; }
    public void setMinute(int m){ this.minute = m; }

    @Override
    public String toString() {
        String ret = super.toString() +
                "\nYear: " + year +
                " - Month: " + month +
                " - Day: " + day +
                " - Hour: " + hour +
                " - Minute: " + minute +
                "\nDestinations: ";
                for(int x = 0; x < routeDestinations.size(); x++){
                    ret += routeDestinations.get(x).toString();
                }
                ret += "\n\t[:Containers:] \n";
                for(int x = 0; x < containers.size(); x++){
                    ret += containers.get(x).toString();
                }

        return ret;
    }

    public String toFile(){ // AA1|2011, ... S:2]
        String ret = super.getID() + "|" + this.year + ", " + this.month + ", " + this.day +
                ", " + this.hour + ", " + this.minute + "|" + this.getOriginID() + "|" +
                getFinalDestinationID() + "[";
        for(int x = 0; x < containers.size(); x++){
            ret += containers.get(x).toFile();
            if((x + 1) < containers.size()){ ret += ",";    }
            else{  ret += "]"; }
        }
        return ret;
    }


}
