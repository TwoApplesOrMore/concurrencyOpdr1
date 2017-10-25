import java.util.ArrayList;

public class InsertionsortThread implements Runnable {
    private ArrayList<Integer> list;

    public InsertionsortThread(ArrayList<Integer> list) {
        this.list = list;
    }

    public void run() {
        insertionsort();
    }


    /**
     * The method to do the insertionsort
     */
    public void insertionsort() {
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

    public ArrayList<Integer> getList() {
        return list;
    }
}
