import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private ArrayList<Integer> twentyfiveK = new ArrayList<>();
    private ArrayList<Integer> fiftyK = new ArrayList<>();
    private ArrayList<Integer> onehundredK = new ArrayList<>();
    private ArrayList<Integer> twohundredK = new ArrayList<>();
    private ArrayList<Integer> fourhundredK = new ArrayList<>();
    private ArrayList<Integer> eighthundredK = new ArrayList<>();


    public void run() {
        for(int i = 0; i < 100; i++ ) {
            twentyfiveK.add(randomInt());
        }

        for(int i = 0; i < 50000; i++) {
            fiftyK.add(randomInt());
        }

        sortTwoThreaded(twentyfiveK);
        //testSingleThread(fiftyK);
    }

    /**
     * Method to execute the sort 10 times with a single thread, and store the average
     * @param list
     */
    private void testSingleThread(ArrayList<Integer> list) {
        // initialize all variables needed for the test
        long startTime;
        long duration;
        long total = 0, longest = Integer.MIN_VALUE, shortest = Integer.MAX_VALUE;


        for(int i = 0; i < 10; i++) {

            // we initialize the list with random numbers
            // make sure the list is randomized again before sorting again
            if(i > 0) {
                resetList(list);
            }
            System.out.println(Arrays.toString(list.toArray()));

            // run the test now

            // get the starting time
            startTime = System.nanoTime();
            //sort the list
            insertionsort(list);
            //get the duration
            duration = ((System.nanoTime() - startTime) / 1000000);

            // print to check the duration: This way we can see if it still runs
            System.out.println("Duration on sort : " + duration + " ms ");
            // Keep track of the extremeties as we will not use these in our averages
            if (duration > longest) {
                longest = duration;
            } else if (duration < shortest) {
                shortest = duration;
            }
            // add the duration to the total
            total += duration;
        }
        long average = (total - (longest+shortest)) / 8;

        // A log to check if everything works correct
        //System.out.println("Longest: " + longest + " shortest: " + shortest + " total: " + total + " average: " + average);

        //Store the result in a text-file for easy readability
        storeResult("Sorted list: " + list.size() + " with a single thread. Average was: " + average);
    }


    /**
     *  Method to sort a list with two threads
     * @param list the list to be sorted
     */
    private void sortTwoThreaded(ArrayList<Integer> list) {
        long startTime;
        long duration;
        long total = 0, longest = Integer.MIN_VALUE, shortest = Integer.MAX_VALUE;

        for(int i = 0; i < 10; i++) {
            ArrayList<Integer> sublist1 = new ArrayList<>();
            ArrayList<Integer> sublist2 = new ArrayList<>();

            if(i > 0) {
                resetList(list);
            }
            // split our list in two
            for (int j = 0; j < list.size() /2; j++) {
                sublist1.add(list.get(j));
                sublist2.add(list.get(list.size()-(j+1)));
            }

            //TODO: Implement threads
        }
        long average = (total - (longest+shortest)) / 8;

        // A log to check if everything works correct
        //System.out.println("Longest: " + longest + " shortest: " + shortest + " total: " + total + " average: " + average);

        //Store the result in a text-file for easy readability
        storeResult("Sorted list: " + list.size() + " with a single thread. Average was: " + average);

    }

    /**
     * The method to do the insertionsort
     * @param list the list that has to be sorted
     */
    private void insertionsort(ArrayList<Integer> list) {
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
    private int randomInt() {
        Random RNG = new Random();
        return RNG.nextInt(100);
    }

    /**
     * reset our sorted list to randomize it again
     * we dont want to sort an already sorted list
     * @param list the sorted list that has to be randomized again
     */
    private void resetList(ArrayList<Integer> list) {
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
    private void storeResult(String result) {
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
