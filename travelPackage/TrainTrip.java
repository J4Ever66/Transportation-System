package travelPackage;

public class TrainTrip extends TransportationRoute{

    @Override
    public void finishCreation(TransportationData dc) {
        super.setID(dc.getRouteID());
        int[] date = dc.getDate();
        super.setYear(date[0]);
        super.setMonth(date[1]);
        super.setDay(date[2]);
        super.setName("Train Trip");
        super.addDestination(dc.getDestinations());
        super.setHour(dc.getHours());
        super.setMinute(dc.getMinutes());
    }
}
