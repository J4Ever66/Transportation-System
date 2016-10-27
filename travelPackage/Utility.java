package travelPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {  // Class meant for all calculations and input / output
    private SubSystem subSystem;

    public Utility(SubSystem smP){  this.subSystem = smP;    }

    public boolean checkDate(int year, int monthP, int day){
        int month = monthP - 1; // 0 - 11
        Calendar cur = new GregorianCalendar(), user = new GregorianCalendar(year, month, day);
        cur.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH - 1);
        if(day < 1){
            System.out.println("Invalid date.");
            return false;
        }
        if(!checkDaysForMonth(year, month, day)){
            System.out.println("Invalid date, exceeds days in month.");
            return false;
        }
        if(cur.after(user)){
            System.out.println("Invalid date, cannot create flight for past date.");
            return false;
        }
        return true;
    }

    public int convertColumn(char c){
        switch(c){
            case 'A': return 0;
            case 'B': return 1;
            case 'C': return 2;
            case 'D': return 3;
            case 'E': return 4;
            case 'F': return 5;
            case 'G': return 6;
            case 'H': return 7;
            case 'I': return 8;
            case 'J': return 9;
            default: return -1; // handled inside seat
        }
    }

    private boolean checkDaysForMonth(int year, int month, int day){
        int febDays = 28;
        if(month == 1){ // LEAP YEAR CHECK
            if((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))){ // divisible by 4 but not (100 -- unless divisible by 400)
                febDays = 29;
            }
        }

        switch(month){
            case 0: if(day > 31){ return false; }
                break;
            case 1: if(day > febDays){ return false; }
                break;
            case 2: if(day > 31){ return false; }
                break;
            case 3: if(day > 30){ return false; }
                break;
            case 4: if(day > 31){ return false; }
                break;
            case 5: if(day > 30){ return false; }
                break;
            case 6: if(day > 31){ return false; }
                break;
            case 7: if(day > 31){ return false; }
                break;
            case 8: if(day > 30){ return false; }
                break;
            case 9: if(day > 31){ return false; }
                break;
            case 10: if(day > 30){ return false; }
                break;
            case 11: if(day > 31){ return false; }
                break;
            default: return false;
        }
        return true;
    }

    public boolean checkTime(int hours, int minutes){
        if(hours > 0 && hours < 25 && minutes > 0 && minutes < 61){
            return true;
        }
        return false;
    }

    public int readUserInt(int min, int max){
        int user = -99999;
        Scanner kb = new Scanner(System.in);

        while(!((user >= min) && (user <= max))) { // CHECK [0 - 9]
            String s = kb.nextLine();
            try {
                user = Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("INVALID INPUT! Please try again.");
                e.printStackTrace();
            }
            if(!((user >= min) && (user <= max))){  System.out.println("Invalid range for input. Please try again.");   }
        }
        return user;
    }

    public String readUserString(){
        Scanner kb = new Scanner(System.in);
        return kb.nextLine();
    }

    public int[] readUserDateNTime(){
        Scanner kb = new Scanner(System.in);
        int[] dateNtime = new int[5];
        System.out.println("Please enter the route year:");
        dateNtime[0] = readUserInt(2016, 9999);
        System.out.println("Please enter the route month:");
        dateNtime[1] = readUserInt(1, 12);
        System.out.println("Please enter the route day:");
        dateNtime[2] = readUserInt(1, 31);
        System.out.println("Please enter the route hour:");
        dateNtime[3] = readUserInt(0, 24);
        System.out.println("Please enter the route minute:");
        dateNtime[4] = readUserInt(0, 60);
        return dateNtime;
    }

    public String readUserDestinationID(){
        System.out.println("Please enter the destination ID.");
        return readUserString();
    }

    public String readUserRouteID() {
        System.out.println("Please enter the route ID:");
        return readUserString();
    }

    public String readUserManagerID() {
        System.out.println("Please enter the manager ID:");
        return readUserString();
    }

    public SeatClass readUserSeatClass(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Please enter the seating classification value(E, F, B):");
        char temp = kb.nextLine().charAt(0);
        switch(temp){
            case 'F': return SeatClass.first;
            case 'E': return SeatClass.economy;
            case 'B': return SeatClass.business;
            default: System.out.println("Invalid seat class. Defaulting to economy class.");
                return SeatClass.economy;
        }
    }

    public int readUserPrice(){
        System.out.println("Please enter the new price:");
        return readUserInt(0, 99999);
    }

    public void export2File(String systemState){
        System.out.println("Please enter output filename:");
        String fileName = this.readUserString();
        PrintStream fout;
        try {
            fout = new PrintStream(new FileOutputStream(fileName));
            fout.write(systemState.getBytes()); // convert String to byte array
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void importFile(String fileName){
        try {
            String ret = readFile(fileName);
            parseFileString(ret);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String readFile(String fileName){
        File f = new File(fileName); // file outside of package. CHECK WHERE CURRENT WORKING DIRECTORY IS FOR PROGRAM OR ELSE WONT FIND FILES!
        String ret = null;
        boolean good2go = false;
        Scanner fin = null;
        while(!good2go) {
            try {
                fin = new Scanner(f);
                good2go = true;
            } catch (FileNotFoundException e) {
                System.out.println("File was not found. Please re-enter the filename");
                fileName = readUserString();
            }
        }
        //now we for sure have a valid file
        while(fin.hasNext()){
            ret += fin.nextLine(); // read in EVERYTHING
        }
        return ret;
    }

    protected void parseFileString(String sP) throws Exception { // RESTORE SAVED AIRPORT STATE
        String src = sP, currentSection = "", subSection = "", airlineID = "", flightID = "", airportOrig = "", airportDest = "";
        int year, month, day, hour, minute, x;
        char[] seatClasses = new char[10], layout = new char[10];
        int[] prices = new int[10], rowCount = new int[10];
        StringTokenizer st = new StringTokenizer(src, "[]{}"), miniNizer = null, nanoNizer = null, airlineNizer = null;
        currentSection = st.nextToken(); // null
        currentSection = st.nextToken(); // not null stuff ( AIRPORTS )
        miniNizer = new StringTokenizer(currentSection, ", ");
        while (miniNizer.hasMoreTokens()) {
            subSystem.addDestination(miniNizer.nextToken());
        }
        // CREATED ALL AIRPORTS
        while (st.hasMoreTokens()) {
            subSection = st.nextToken(); // AA1|2011, 10, 8, 16, 30|DEN|LAX
            if (subSection.length() < 10) { //must be an airlineID
                if (subSection.contains(", ")) {
                    String temp = subSection, temp2 = "";
                    airlineNizer = new StringTokenizer(subSection, ", ");
                    temp2 = airlineNizer.nextToken();
                    if (temp2.length() > 2) {
                        airlineID = temp2;
                    } else {
                        airlineID = airlineNizer.nextToken();
                    }
                } else {
                    airlineID = subSection;
                }
                subSystem.addManager(airlineID);
                subSection = st.nextToken();
            }
            nanoNizer = new StringTokenizer(subSection, "|, ");
            flightID = nanoNizer.nextToken();
            year = Integer.parseInt(nanoNizer.nextToken());
            month = Integer.parseInt(nanoNizer.nextToken());
            day = Integer.parseInt(nanoNizer.nextToken());
            hour = Integer.parseInt(nanoNizer.nextToken());
            minute = Integer.parseInt(nanoNizer.nextToken());
            airportOrig = nanoNizer.nextToken();
            airportDest = nanoNizer.nextToken();
            x = 0;
            subSystem.addRoute(airlineID, airportOrig, airportDest, year, month, day, hour, minute, flightID);
            TransportationRoute curFlight = subSystem.getRoute(airlineID, flightID); // if needed for boats / train then import String for type at beginning of file mngmnt
            subSection = st.nextToken(); // E:200:S:4,F:500:S:2
            miniNizer = new StringTokenizer(subSection, "]");
            nanoNizer = new StringTokenizer(subSection, ":, ");

            while (nanoNizer.hasMoreTokens()) {
                seatClasses[x] = (nanoNizer.nextToken()).charAt(0);
                prices[x] = Integer.parseInt(nanoNizer.nextToken());
                layout[x] = (nanoNizer.nextToken()).charAt(0);
                rowCount[x] = Integer.parseInt(nanoNizer.nextToken());
                x++; // signify spot in data buffer arrays
            } // while
            for(int y = 0; y < x; y++) {
                if (seatClasses[y] == 'F'){
                    subSystem.addSection(airlineID, flightID, rowCount[y], layout[y], prices[y], SeatClass.first);
                }else if(seatClasses[y] == 'B'){
                    subSystem.addSection(airlineID, flightID, rowCount[y], layout[y], prices[y], SeatClass.business);
                }else{
                    subSystem.addSection(airlineID, flightID, rowCount[y], layout[y], prices[y], SeatClass.economy);
                }
            } // for
        }//WHILE
    }

}
