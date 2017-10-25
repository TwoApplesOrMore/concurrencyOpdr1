import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Utils {

    public Utils() {

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

    /**
     * Simple method to return a number between 0 and the max value of an Integer
     * @return the randomly generated number.
     */
    public int randomInt() {
        Random RNG = new Random();
        return RNG.nextInt(100);
    }

    /**
     * reset our sorted list to randomize it again
     * we dont want to sort an already sorted list
     * @param list the sorted list that has to be randomized again
     */
    public void resetList(ArrayList<Integer> list) {
        int size = list.size();
        list.clear();
        for (int i = 0; i < size; i++) {
            list.add(randomInt());
        }
    }


    /**
     * method to write our result to a .txt file when a sort is done, so we can let it run
     * This way we don't have to monitor the prints or logs.
     * @param result The string we want to print
     */
    public void storeResult(String result) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("results-sorting.txt", true));
            writer.write(result);
            writer.newLine();
            writer.flush();

        } catch (Exception e) {
            System.out.println("Could not write to file: " + e.getMessage());
        } finally {

            // always close the file again
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException ioe) {
                    System.out.println("Could not close writer: " + ioe.getMessage());
                }
            }
        }

    }
}
