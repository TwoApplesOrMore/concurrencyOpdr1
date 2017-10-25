import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Utils {

    public Utils() {

    }


    /**
     * Utilfunction to merge two sorted arrays( note: this does not work if they are not sorted)
     * @param list1 the first list
     * @param list2 the second list to merge with the first
     * @return the full merged list
     */
    public ArrayList<Integer> mergeArrays(ArrayList<Integer> list1, ArrayList<Integer> list2) {

        ArrayList<Integer> combinedlist = new ArrayList<>();

        int i = 0, x = 0;

        while (i < list1.size() && x < list2.size()) {
            if (list1.get(i) < list2.get(x)) {
                combinedlist.add(list1.get(i));
                i++;
            } else {
                combinedlist.add(list2.get(x));
                x++;
            }
        }
        while (i < list1.size()) {
            combinedlist.add(list1.get(i));
        }
        while (x < list2.size()) {
            combinedlist.add(list2.get(x));
        }

        return combinedlist;
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
