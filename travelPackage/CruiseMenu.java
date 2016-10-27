package travelPackage;

public class CruiseMenu extends Menu{

    public CruiseMenu(Utility uP){
        super(uP);
        super.setType("CRUISE");
    }

    public int displayMenu(){
        System.out.printf("1. Load system from file." +
                "\n2. Backup system to file." +
                "\n3. Alter cost of given section for specified cruise route." +
                "\n4. Find available cruise routes provided origin, destination and time." +
                "\n5. Book cruise route given specified cabin." +
                "\n6. Book cruise route with seating preference." +
                "\n7. Display system details." +
                "\n8. Add port." +
                "\n9. Add cruise." +
                "\n10. Add cruise route with optional sections." +
                "\n11. Quit.\n");
        return super.getUtils().readUserInt(1, 11);
    }

}
