package travelPackage;

public class AirMenu extends Menu {
    public AirMenu(Utility uP) {
        super(uP);
        super.setType("AIR");
    }

    public int displayMenu(){
        System.out.printf("1. Load system from file." +
                "\n2. Backup system to file." +
                "\n3. Alter cost of given section for specified flight." +
                "\n4. Find available flights provided origin, destination and time." +
                "\n5. Book flight given specific seat." +
                "\n6. Book flight with seating preference." +
                "\n7. Display system details." +
                "\n8. Add airport." +
                "\n9. Add airline." +
                "\n10. Add flight with optional sections." +
                "\n11. Quit.\n");
        return super.getUtils().readUserInt(1, 11);
    }


}
