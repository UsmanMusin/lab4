import static java.lang.Math.abs;

public class BusinessCenter {
    private int liftFloor = 1 + (int) (Math.random() * 10);
    private Visitor visitorAtControl;
    private Visitor visitorInLift;
    private boolean liftFree = true;
    public static long startTime = System.currentTimeMillis();

    BusinessCenter(){
    }

    synchronized public boolean enterControl(Visitor v){

        if(visitorAtControl!=null){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            visitorAtControl = v;
        }
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" - Посетитель "+ v.getNum()+ " показывает документы");
        return true;
    }

    synchronized public void passControl(Visitor v){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" - Посетитель "+ v.getNum() + " показал документы");
        visitorAtControl = null;
        notify();

    }

    synchronized public boolean callLift(Visitor v){

        if(!liftFree) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" - Посетитель "+ v.getNum() + " ждёт лифт");
        return true;
    }

    public void moveLift(Visitor v, int targetFloor){
        liftFree = false;
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" - Лифт едет для посетителя "+ v.getNum() + " с " +
        liftFloor + " на " + targetFloor);
        try {
            Thread.sleep(500 * abs(liftFloor - targetFloor));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        liftFloor = targetFloor;
    }

    public void enterLift(Visitor v){
        visitorInLift = v;
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" - Посетитель "+ v.getNum() + " зашёл в лифт");
    }

    synchronized public void exitLift(Visitor v){
        System.out.print(System.currentTimeMillis() - startTime);
        System.out.println(" - Посетитель "+ v.getNum() + " вышел из лифта");
        visitorInLift = null;
        liftFree = true;
        notify();
    }

    public static void main(String []args){
        BusinessCenter businessCenter= new BusinessCenter();
        int visitorsCount = 50;
        for (int i = 1; i <= visitorsCount; i++){
            new Thread(new Visitor(businessCenter)).start();
            System.out.print(System.currentTimeMillis() - startTime);
            System.out.println(" - Появился посетитель " + i);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван");
            }
        }
    }


}
