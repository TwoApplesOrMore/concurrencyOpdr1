import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    private Utils utils = new Utils();

    public void run() {
        for(int i = 0; i < 1000; i++ ) {
            twentyfiveK.add(utils.randomInt());
        }

        for(int i = 0; i < 50000; i++) {
            fiftyK.add(utils.randomInt());
        }

        testSingleThread(twentyfiveK);
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
                utils.resetList(list);
            }

            // TODO: If you want to see what list is going to be sorted, uncomment this next line
            //System.out.println(Arrays.toString(list.toArray()));
            // run the test now

            //build our single thread
            InsertionsortThread insertionsort = new InsertionsortThread(list);
            Thread singlethread = new Thread(insertionsort);

            // get the starting time
            startTime = System.nanoTime();

            //start sorting
            singlethread.start();

            // wait for it to finish
            try {
                singlethread.join();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.out.println("The single thread has been interrupted");
                return;
            }

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
        System.out.println("Longest: " + longest + " shortest: " + shortest + " total: " + total + " average: " + average);

        //Store the result in a text-file for easy readability
        utils.storeResult("Sorted list: " + list.size() + " with a single thread. Average was: " + average);
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
                utils.resetList(list);
            }
            // split our list in two
            for (int j = 0; j < list.size() /2; j++) {
                sublist1.add(list.get(j));
                sublist2.add(list.get(list.size()-(j+1)));
            }

            //TODO: Implement threads
            // set up threads, and then set starttime
            InsertionsortThread sort1 = new InsertionsortThread(sublist1);
            InsertionsortThread sort2 = new InsertionsortThread(sublist2);
            Thread firstThread = new Thread(sort1);
            Thread secondThread = new Thread(sort2);
            //start threads

            // wait for result

            // combine result of threads
            // merge result

            // end timer

            // wrap up if needed

        }


        long average = (total - (longest+shortest)) / 8;

        // A log to check if everything works correct
        //System.out.println("Longest: " + longest + " shortest: " + shortest + " total: " + total + " average: " + average);

        //Store the result in a text-file for easy readability
        utils.storeResult("Sorted list: " + list.size() + " with a single thread. Average was: " + average);

    }
}