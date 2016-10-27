package travelPackage;

public abstract class TransportationContainer extends Transportation {
    private boolean seatsBooked;
    private SeatClass classification;
    private Container[][] containers; // specifies SEAT or CABIN -- whatever is capable of occupying humanoid(s)
    private int availableSpaces, bookedSpaces, price, rows;
    private char layoutID;


    public  TransportationContainer(){
        this.seatsBooked = false;
        this.availableSpaces = 0;
        this.bookedSpaces = 0;
        layoutID = '-';
        containers = null;
    }
    public SeatClass getClassification(){
        return this.classification;
    }

    protected void setClassification(SeatClass scp){
        this.classification = scp;
    }

    public boolean hasAvailableSeats() {
        return (!seatsBooked);
    }

    public boolean bookAnything(){
        if(containers != null){
            for(int x = 0; x < containers.length; x++) {
                for(int y = 0; y < containers[0].length; y++){
                    if(containers[x][y] == null){ // not booked
                        containers[x][y] = new Container();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void bookContainer(int row, int col){
        if(containers != null) {
            if(row < containers.length && col < containers[0].length || row < 0 || col < 0) {
                Container s = containers[row - 1][col];
                if (s != null) { //ALREADY BOOKED
                    System.out.println("The requested Container is booked, please select another Container to book.");
                } else {
                    containers[row - 1][col] = new Container();
                }
            }else{
                System.out.println("The Container requested does not exist.");
            }
        }
        if(!seatsBooked) {
            checkContainer();
        }
    }


    
    public String getClassificationFileValue() {
        if(getClassification() == SeatClass.first){
            return "F";
        }else if(getClassification() == SeatClass.business){
            return "B";
        }else{
            return "E";
        }
    }

    
    public boolean findSeatGivenPreference(String preference) {
        if(preference.compareToIgnoreCase("WINDOW") == 0){
            for(int z = 0; z < containers[0].length; z++){
                if(containers[0][z] == null) {
                    System.out.println("0 &" + z);
                    setContainer(0, z);
                    return true;
                }
            }
            for (int z = 0; z < containers[0].length; z++) {
                if (containers[containers[z].length][z] == null) {
                    System.out.println("n-1 &" + z);
                    setContainer(containers[z].length, z);
                    return true;
                }
            }
            return bookAnything();
        }else{ // AISLE
            switch(getLayoutID()){
                case 'S':   int[] resS = scanColForAvailSeat(0, 1);
                    if(resS[0] != -1){
                        bookContainer(resS[0], resS[1]);
                        return true;
                    }
                    break; // COL1 or COL2
                case 'M':   int[] resM = scanColForAvailSeat(1, 2);
                    if(resM[0] != -1){
                        bookContainer(resM[0], resM[1]);
                        return true;
                    }
                    break; // COL2 or COL3
                case 'W':   int[] resW = scanColForAvailSeat(2, 3, 6, 7);
                    if(resW[0] != -1){
                        bookContainer(resW[0], resW[1]);
                        return true;
                    }
                    break; // COL3 or COL4 or COL7 or COL8
                default: System.out.println("Invalid layout.");
            }
        }

        return bookAnything();
    }

    
    protected void setLayout(char layoutP, int rowsP) {
        setRows(rowsP);
        switch(layoutP){
            case 'S': // [COL1](aisle)[COL2][COL3]
                setSeats(new Container[rowsP][3]);
                setLayoutID(layoutP);
                setContainerCount(rowsP * 3);
                break;
            case 'M': // [COL][COL](aisle)[COL][COL]
                setSeats(new Container[rowsP][4]);
                setLayoutID(layoutP);
                setContainerCount(rowsP * 4);
                break;
            case 'W': // [COL][COL][COL](aisle)[COL][COL][COL][COL](aisle)[COL][COL][COL]
                setSeats(new Container[rowsP][10]);
                setLayoutID(layoutP);
                setContainerCount(rowsP * 10);
                break;
            default: System.out.println("Invalid layout.");
        }
    }

    private int[] scanColForAvailSeat(int... cols){
        int[] coords = new int[2];
        for(int x = 0; x < cols.length; x++){
            int curCol = cols[x];
            for(int y = 0; y < containers.length; y++){
                if(containers[y][curCol] == null){ // not booked
                    System.out.println("ROW: " + y + " COL:" + curCol);
                    coords[0] = (y + 1); // +1 to NORMALIZE ROW (0 -> 1)
                    coords[1] = curCol; // col
                    return coords;
                }
            }
        }
        coords[0] = -1;
        coords[1] = -1;
        return coords;
    }




    private void checkContainer(){
        ++bookedSpaces;
        if(bookedSpaces == availableSpaces){
            this.seatsBooked = true;
        }
    }

    
    public String toString() {
        return super.toString() +
                "containers Available: " + !seatsBooked +
                "\nSeating Class: " + classification +
                "\nLayout: " + layoutID +
                "\nPrice: " + price +
                "\nTotal containers: " + availableSpaces +
                "\nBooked containers: " + bookedSpaces +
                "\nSeating status: \n" + printContainers() + "\n";
    }

    private String printContainers() { // ADD ONE
        String ret = "";
        if(containers != null) {
            for (int x = 0; x < containers.length; x++) {
                for (int y = 0; y < containers[x].length; y++) {
                    ret += "Container " + "[" + x + "]" + "[" + y + "]: ";
                    Container s = containers[x][y];
                    if (s != null) {
                        ret += "Booked.\n";
                    } else {
                        ret += "Available.\n";
                    }
                }
            }
        }
        return ret;
    }

    public String toFile(){
        String ret = getClassificationFileValue();
        ret += ":" + this.price + ":" + layoutID + ":" + rows;
        return ret;
    }

    protected int getRows(){ return this.rows;  }
    protected void setRows(int rowP){   this.rows = rowP;   }
    protected Container[][] getSeats(){ return this.containers;   }
    protected void setSeats(Container[][] s){    this.containers = s; }
    protected void setLayoutID(char layP){ this.layoutID = layP;    }
    public char getLayoutID(){ return this.layoutID;    }
    protected void setPrice(int priceP){
        this.price = priceP;
    }
    public int getPrice(){
        return this.price;
    }
    protected void setContainerCount(int sCount){    this.availableSpaces = sCount;    }
    protected void setContainer(int x, int y){   this.containers[x][y] = new Container();  }
}

