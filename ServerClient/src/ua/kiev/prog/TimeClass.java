package ua.kiev.prog;

/**
 * Created by anton on 13.04.2020.
 */
public class TimeClass implements Runnable{
    private static int number = 0;
    public void setNumber(int number){
        this.number = number;
    }
    @Override
    public synchronized void run() {
     ListController lc = new ListController();
     Thread thread = new Thread(lc);
     if(number == 0){
         number++;
         thread.start();
         try {
             thread.sleep(45000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         number--;
     }
    }
}
