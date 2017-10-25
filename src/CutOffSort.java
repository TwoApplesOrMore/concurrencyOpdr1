import java.util.ArrayList;
import java.util.Arrays;

public class CutOffSort implements Runnable {

    private ArrayList<Integer> list;
    private int cutOff;
    private Utils utils;

    public CutOffSort(ArrayList<Integer> list, int cutOff){
        this.cutOff = cutOff;
        this.list = list;
        this.utils = new Utils();
    }

    @Override
    public void run() {



        if(list.size() > cutOff){
            ArrayList<Integer> sublist1 = new ArrayList<>();
            ArrayList<Integer> sublist2 = new ArrayList<>();


            // split our list in two
            for (int j = 0; j < list.size() /2; j++) {
                sublist1.add(list.get(j));
                sublist2.add(list.get(list.size()-(j+1)));
            }




            CutOffSort cutOffSort1 = new CutOffSort(sublist1, cutOff);
            CutOffSort cutOffSort2 = new CutOffSort(sublist2, cutOff);

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

            list = utils.mergeArrays(cutOffSort1.getList(), cutOffSort2.getList());
        }else{
            InsertionsortThread sort1 = new InsertionsortThread(list);

            Thread firstThread = new Thread(sort1);

            //start threads
            firstThread.start();

            // wait for result
            try {
                firstThread.join();

            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            list = sort1.getList();

        }






    }

    public ArrayList<Integer> getList(){
        return list;
    }


}
