package travelPackage;

public abstract class Menu {
    private String type;
    private Utility utils;
    private SubSystem subSystem;

    private Menu(){}
    public Menu(Utility uP) {
        utils = uP;
    }

    public void startMenu(){
        int choice = -1;
        System.out.println("Please enter input filename:");
        utils.importFile(utils.readUserString()); // INITIAL IMPORT
        while(choice != 11){
            choice = displayMenu();
            try {   optionExecution(choice);    }
            catch(Exception e){ e.printStackTrace(); }
        }
    }

    public abstract int displayMenu();

    public void optionExecution(int selection) throws Exception{
        switch(selection){
            case 1:
                subSystem.reset(); // reset state
                System.out.println("Please enter input filename:");
                utils.importFile(utils.readUserString());
                break;// create airport system from given input file
            case 2:
                utils.export2File(subSystem.getSystemState()); // TO FILE
                break;
            case 3:
                // Change section price -- CONFIRMED WORKING
                subSystem.changeSectionPrice(utils.readUserRouteID(), utils.readUserSeatClass(), utils.readUserPrice());
                break;
            case 4:
                // list flights/prices that match ORIGIN/DEST ID and specified date
                System.out.println("Please enter origin destination ID:");
                String orig = utils.readUserString(), dest;
                System.out.println("Please enter the final destination ID:");
                dest = utils.readUserString();
                SeatClass sc = utils.readUserSeatClass();
                System.out.println("\n");
                subSystem.findAvailableRoutes(orig, dest, sc, utils.readUserDateNTime());
                break;
            case 5:
                // Book seat given specific seat and FLIGHTID -- CONFIRMED WORKING
                System.out.println("Please enter the row for the seat you wish to book:");
                int row = utils.readUserInt(1, 100);
                System.out.println("Please enter the column letter for the seat you wish to book:");
                char col = utils.readUserString().charAt(0);
                System.out.println("Please enter the route ID for the seat you wish to book:");
                String flight = utils.readUserString();
                //System.out.println("Please enter the seat classification:");
                SeatClass classific = utils.readUserSeatClass();
                subSystem.bookContainerByPosition(flight, classific, row, utils.convertColumn(col));
                break;
            case 6: // Book seat on flight given only Seating Preference & flightID
                System.out.println("Please enter the route ID for the seat you wish to book:");
                String flight6 = utils.readUserString();
                System.out.println("Please enter the seating preference 'AISLE' OR 'WINDOW':");
                String seatPref = utils.readUserString();
                subSystem.bookContainerByPreference(flight6, seatPref);
                break;
            case 7: subSystem.displaySystemDetails(); // Display details
                break;
            case 8: subSystem.addDestination(utils.readUserDestinationID());
                break;
            case 9: subSystem.addManager(utils.readUserManagerID());
                break;
            case 10: 
                System.out.println("Please enter the origin destination ID.");
                String origDest = utils.readUserString();
                System.out.println("Please enter the final destination ID.");
                String finDest = utils.readUserString();
                int[] dateNTime = utils.readUserDateNTime();
                String routeID = utils.readUserRouteID();
                subSystem.addRoute(utils.readUserManagerID(), origDest, finDest, dateNTime[0], dateNTime[1], dateNTime[2], dateNTime[3],dateNTime[4], routeID );
                TransportationRoute tr = subSystem.getRoute(routeID);
                if(tr != null) {
                    System.out.println("Please enter the number of containers(sections):");
                    int loopy = utils.readUserInt(0, 99), rows = -1, cols = -1, price = 0;
                    char layout = '-';
                    SeatClass classification = null;
                    for (int x = 0; x < loopy; x++) {
                        classification = utils.readUserSeatClass();
                        price = utils.readUserPrice();
                        System.out.println("How many rows of containers?");
                        rows = utils.readUserInt(1, 999);
                        if (type.equals("AIR") || type.equals("TRAIN")) {
                            System.out.println("Please enter the container layout(S, M, W)");
                            layout = utils.readUserString().charAt(0);
                            if(layout == 'S'){  cols = 3;   }
                            else if(layout == 'W'){ cols = 10;  }
                            else if(layout == 'M'){ cols = 4;   }
                            else{   layout = 'S';   cols = 3;   }
                            subSystem.addContainerWithLayout(classification, layout, rows, cols, price, routeID);
                        } else {
                            System.out.println("How many columns of containers?");
                            cols = utils.readUserInt(1, 999);
                            subSystem.addContainer(classification, rows, cols, price, routeID);
                        }
                    }
                }
                break;
            case 11: System.out.println("Exiting Program."); // QUIT
                break;
            default: System.out.println("Invalid menu option.");
        }

    }
    public void setManager(SubSystem SS){
        this.subSystem = SS;
    }
    public Utility getUtils(){  return this.utils;  }
    public void setType(String tP){  this.type = tP; }
}
