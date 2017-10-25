import java.util.ArrayList;
import java.util.Arrays;

public class InsertionsortThread implements Runnable {
    private ArrayList<Integer> list;

    public InsertionsortThread(ArrayList<Integer> list) {
        this.list = list;
    }

    public void run() {
        System.out.println("Thread has been started");
        System.out.println("Received list is: " + Arrays.toString(list.toArray()));
    }


    /**
     * The method to do the insertionsort
     * @param list the list that has to be sorted
     */
    public void insertionsort(ArrayList<Integer> list) {
        int temp;
        for (int i = 1; i < list.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(list.get(j) < list.get(j-1)){
                    temp =  list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1, temp);
                }
            }
        }
    }
}
