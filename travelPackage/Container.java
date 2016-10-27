package travelPackage;

public class Container{
    private boolean booked;
    public Container(){
        this.booked = true;
    }

    public void setBooked(){
        this.booked = true;
    }

    public boolean getStatus(){ return this.booked; }

    public String toString(){
        if(booked){
            return "Booked";
        }else{
            return "Available";
        }
    }


}
