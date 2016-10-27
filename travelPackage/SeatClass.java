package travelPackage;

public enum SeatClass {
    first(1), business(2), economy(3);
    private int val;

    private SeatClass(int n){
        this.val = n;
    }

}
