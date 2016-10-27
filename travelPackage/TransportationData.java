package travelPackage;

public class TransportationData { // transportation data
    /*
        THIS CLASS IS MEANT TO STORE FREQUENTLY NEEDED DATA FOR A TEMPORARY AMOUNT OF TIME.
        MERELY PROVIDING AN EASY WAY TO PASS AROUND DATA, CONTEXT WILL DETERMINE VALUES USED.
    */
    private String managerID, routeID, destID;
    private TransportationDestination[] destinations;
    private int rowVal, colVal, minutes, hours, price;
    private int[] date;
    private SeatClass classVal;
    private char classLayout;

    public TransportationData(){
        this.date = new int[3];
    }

    // SET / GET METHODS

    public String getDestID(){
        return this.destID;
    }
    public void setDestID(String idP){
        this.destID = idP;
    }
    public void setManagerID(String idP){
        this.managerID = idP;
    }
    public String getManagerID(){
        return this.managerID;
    }
    public String getRouteID(){
        return this.routeID;
    }
    public void setRouteID(String idP){
        this.routeID = idP;
    }
    public void setRowVal(int val){
        this.rowVal = val;
    }
    public int getRowVal(){
        return this.rowVal;
    }
    public void setColVal(int val){
        this.colVal = val;
    }
    public int getColVal(){
        return this.colVal;
    }
    public SeatClass getClassSection(){
        return this.classVal;
    }
    public void setClassSection(SeatClass sc){
        this.classVal = sc;
    }
    public void setDestinations(TransportationDestination... destP){
        this.destinations = new TransportationDestination[destP.length];
        for(int x = 0; x < destP.length; x++){
            destinations[x] = destP[x];
        }
    }
    public TransportationDestination[] getDestinations(){
        return this.destinations;
    }

    public int[] getDate(){
        return this.date;
    }

    public void setDate(int y, int m, int d){
        date[0] = y;
        date[1] = m;
        date[2] = d;
    }
    public void setTime(int hoursP, int minutesP){
        this.hours = hoursP;
        this.minutes = minutesP;
    }

    public int getHours(){
        return this.hours;
    }

    public int getMinutes(){
        return this.minutes;
    }

    public void setClassLayout(char cP){
        this.classLayout = cP;
    }
    public char getClassLayout(){
        return this.classLayout;
    }
    public void setPrice(int priceP){
        this.price = priceP;
    }
    public int getPrice(){
        return this.price;
    }
}
