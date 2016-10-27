package travelPackage;

public abstract class Transportation{
    private String ID, name;

    public abstract void finishCreation(TransportationData dc);

    public String getID(){
        return this.ID;
    }

    public void setID(String idP){
        this.ID = idP;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String nameP){
        this.name = nameP;
    }


    public String toString(){
        if(getID() != null && getName() != null) {
            return ('[' + this.getName() + " " + this.getID() + ']');
        }
        return "";
    }

}
