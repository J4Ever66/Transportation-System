package travelPackage;

public class SystemManager {
    private SubSystem sub; // COULD TURN INTO AN ARRAYLIST TO MANAGE AS MANY SYSTEMS AS WE WANT... DANGEROUS?
    private Utility utils;
    private Menu menu;

    public SystemManager(){
        this.sub = new SubSystem();
        utils = new Utility(sub);
        sub.setUtils(utils);
    }

    public void start(){ // THE MOST TOP LAYER OF THE APPLICATION, EVERYTHING EVENTUALLY COMES BACK TO HERE(TRY/CATCH)
        try {
            determineSubSystem();
            menu.setManager(sub);
            menu.startMenu();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void determineSubSystem() throws Exception{
        int userChoice = 0;
        while(userChoice < 1 || userChoice > 3){
            System.out.print("Please choose the desired transportation subsystem:\n1. Air\n2. Train\n3. Cruise\n> ");
            userChoice = utils.readUserInt(1, 3);
        }
        switch(userChoice){
            case 1: sub.setFactory(new AirSystemFactory());
                utils = new Utility(sub);
                menu = new AirMenu(utils);
                break;
            case 2: sub.setFactory(new TrainSystemFactory());
                utils = new Utility(sub);
                menu = new TrainMenu(utils);
                break;
            case 3: sub.setFactory(new CruiseSystemFactory());
                utils = new Utility(sub);
                menu = new CruiseMenu(utils);
                break;
        }
    }
}
