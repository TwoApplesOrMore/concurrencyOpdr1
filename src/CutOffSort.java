import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CutOffSort implements Runnable {

    private ArrayList list;
    private int cutOff;
    private Utils utils;
    private Boolean sorted;

    public CutOffSort(ArrayList list, int cutOff, Boolean sorted){
        this.list = list;
        this.cutOff = cutOff;
        this.utils = new Utils();
        this.sorted = sorted;
    }

    @Override
    public void run() {
        //sort list
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if(list.size()/2 < i){
                list1.add(list.get(i));
            }else{
                list2.add(list.get(i));
            }
        }
        if(sorted){

        }
        else if(list1.size() < cutOff){

            InsertionsortThread insertionsort1 = new InsertionsortThread(list1);
            InsertionsortThread insertionsort2 = new InsertionsortThread(list2);
            Thread insertionsortThread1 = new Thread(insertionsort1);
            Thread insertionsortThread2 = new Thread(insertionsort2);

            insertionsortThread1.start();
            insertionsortThread2.start();

            try {
                insertionsortThread1.join();
                insertionsortThread2.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }

            //merge list1 en list2
            ArrayList sortedList = utils.mergeArrays(list1, list2);

            System.out.println(Arrays.toString(sortedList.toArray()));

        }else {
            CutOffSort cutOffSort1 = new CutOffSort(list1, cutOff, false);
            CutOffSort cutOffSort2 = new CutOffSort(list2, cutOff, false);

            Thread thread1 = new Thread(cutOffSort1);
            Thread thread2 = new Thread(cutOffSort2);

            thread1.start();
            thread2.start();

            try{
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
