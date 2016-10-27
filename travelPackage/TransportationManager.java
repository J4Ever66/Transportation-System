package travelPackage;

import java.util.ArrayList;

public abstract class TransportationManager extends Transportation {
    private boolean isCapped; // NOT LOADED WITH FILE
    private int routeCap;
    private ArrayList<TransportationRoute> availRoutes = new ArrayList<>();

    public TransportationManager(){this.isCapped = false;}

    public TransportationRoute getRouteByID(String routeID){
        for(int x = 0; x < this.availRoutes.size(); x++){
            if((availRoutes.get(x).getID().equals(routeID))){
                return availRoutes.get(x);
            }
        }
        return null;
    }

    public void addRoute(TransportationRoute tr){
        if(!isCapped && checkRouteExistence(tr.getID())) {
            this.availRoutes.add(tr);
        }else{
            if(routeCap >= availRoutes.size()){
                this.availRoutes.add(tr);
            }

        }

    }

    public TransportationRoute getRouteByIndex(int index){
        return this.availRoutes.get(index);
    }

    public int getRouteCount(){
        return this.availRoutes.size();
    }

    public boolean checkRouteExistence(String routeID){
        if(availRoutes != null){
            for(int x = 0; x < availRoutes.size(); x++){
                if(routeID.equals(availRoutes.get(x).getID())){
                    return true;
                }
            }
        }
        return false;
    }

    public void setDestinationCap(int cap){
        this.routeCap = cap;
        this.isCapped = true;
    }

    @Override
    public String toString() {
        String ret = "--- " + super.toString() + " ---";
        if(this.availRoutes.size() > 0) {
            ret += "\nAvailable Routes: ";
        }
        for(int x = 0; x < availRoutes.size(); x++){
            ret += availRoutes.get(x).toString();
        }
        return ret;
    }

    public String toFile(){
        String ret = super.getID() + "[";
        for(int x = 0; x < this.availRoutes.size(); x++){
            ret += availRoutes.get(x).toFile();
            if((x + 1) < availRoutes.size()){   ret += ", ";    }
            else{  ret += "]"; }
        }
        return ret;
    }

}
