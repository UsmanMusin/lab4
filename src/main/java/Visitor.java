public class Visitor implements Runnable {

    private BusinessCenter place;
    private static int totalCount;
    private int num;
    private int floor;

    Visitor(BusinessCenter p){
        place = p;
        totalCount++;
        num = totalCount;
        floor = 1 + (int) (Math.random() * 10);
    }

    public void run(){
        enterBuilding();
        goUp();
        doSomeWork();
        goDown();
    }

    public void enterBuilding(){
        if(place.enterControl(this)){
            place.passControl(this);
        }
    }

    public void goUp(){
        if(place.callLift(this)){
            place.moveLift(this, 1);
            place.enterLift(this);
            place.moveLift(this, this.floor);
            place.exitLift(this);
        }
    }

    public void doSomeWork(){
        System.out.print(System.currentTimeMillis() - BusinessCenter.startTime);
        System.out.println(" - Посетитель "+ this.num + " занимается делами");
        try {
            Thread.sleep((1 + (int) (Math.random() * 5)) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goDown(){
        if(place.callLift(this)){
            place.moveLift(this, this.floor);
            place.enterLift(this);
            place.moveLift(this, 1);
            place.exitLift(this);
            System.out.print(System.currentTimeMillis() - BusinessCenter.startTime);
            System.out.println(" - Посетитель "+ num + " покинул здание");
        }

    }

    public int getNum(){
        return num;
    }

    public int getFloor(){
        return floor;
    }
}
