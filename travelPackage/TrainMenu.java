package travelPackage;

public class TrainMenu extends Menu {

    public TrainMenu(Utility uP){
        super(uP);
        super.setType("TRAIN");
    }

    public int displayMenu(){
        System.out.printf("1. Load system from file." +
                "\n2. Backup system to file." +
                "\n3. Alter cost of given section for specified train route." +
                "\n4. Find available train routes provided origin, destination and time." +
                "\n5. Book train route given specified seat." +
                "\n6. Book train route with seating preference." +
                "\n7. Display system details." +
                "\n8. Add train station." +
                "\n9. Add train line." +
                "\n10. Add train route with optional sections." +
                "\n11. Quit.\n");
        return super.getUtils().readUserInt(1, 11);
    }

}
