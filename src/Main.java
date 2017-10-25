import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private ArrayList<Integer> twentyfiveK = new ArrayList<>();
    private ArrayList<Integer> fiftyK = new ArrayList<>(50000);
    private ArrayList<Integer> onehundredK = new ArrayList<>(100000);
    private ArrayList<Integer> twohundredK = new ArrayList<>();
    private ArrayList<Integer> fourhundredK = new ArrayList<>();
    private ArrayList<Integer> eighthundredK = new ArrayList<>();

    private Utils utils = new Utils();

    public void run() {

        startup();

        // run the test for single threaded
//        testSingleThread();

        //run the test for two-threaded
//        testTwoThreaded();

        //run the tests for various cutOffs
        testCutOff(4000);
        testCutOff(2000);
        testCutOff(1000);
        testCutOff(500);
        testCutOff(250);
        testCutOff(150);
        testCutOff(100);
        testCutOff(50);
        testCutOff(35);
        testCutOff(20);
        testCutOff(10);
        testCutOff(5);
    }

    /**
     * startup method to get things up and rolling
     */
    private void startup() {
        utils.fillList(twentyfiveK, 25000);
        utils.fillList(fiftyK, 50000);
        utils.fillList(onehundredK, 100000);
        utils.fillList(twohundredK, 200000);
        utils.fillList(fourhundredK, 400000);
        utils.fillList(eighthundredK, 800000);

    }

    /**
     * Helper function to do the single-threaded test for all list-sizes
     */
    private void testSingleThread() {
        sortSingleThread(twentyfiveK);
        sortSingleThread(fiftyK);
        sortSingleThread(onehundredK);
        sortSingleThread(twohundredK);
        sortSingleThread(fourhundredK);
        sortSingleThread(eighthundredK);
    }

    /**
     * Helper function to run all two-threaded tests
     */
    private void testTwoThreaded(){
        sortTwoThreaded(twentyfiveK);
        sortTwoThreaded(fiftyK);
//        sortTwoThreaded(onehundredK);
//        sortTwoThreaded(twohundredK);
//        sortTwoThreaded(fourhundredK);
//        sortTwoThreaded(eighthundredK);
    }
    /**
     * Helper function to run all tests with certain cutOffs
     */
    private void testCutOff(int cutOff){
        sortWithCutOff(twentyfiveK, cutOff);
        sortWithCutOff(fiftyK, cutOff);
        sortWithCutOff(onehundredK, cutOff);
        sortWithCutOff(twohundredK, cutOff);
        sortWithCutOff(fourhundredK, cutOff);
        sortWithCutOff(eighthundredK, cutOff);
    }

    /**
     * Method to execute the sort 10 times with a single thread, and store the average
     * @param list
     */
    private void sortSingleThread(ArrayList<Integer> list) {
        // initialize all variables needed for the test
        long startTime;
        long duration;
        long total = 0, longest = Integer.MIN_VALUE, shortest = Integer.MAX_VALUE;

        for(int i = 0; i < 10; i++) {

            // make sure the list is filled and randomized before sorting
            utils.resetList(list);


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

            // make sure the list is filled and randomized before sorting
            utils.resetList(list);


            // split our list in two
            for (int j = 0; j < list.size() /2; j++) {
                sublist1.add(list.get(j));
                sublist2.add(list.get(list.size()-(j+1)));
            }

            // set up threads, and then set starttime
            InsertionsortThread sort1 = new InsertionsortThread(sublist1);
            InsertionsortThread sort2 = new InsertionsortThread(sublist2);
            Thread firstThread = new Thread(sort1);
            Thread secondThread = new Thread(sort2);

            startTime = System.nanoTime();

            //start threads
            firstThread.start();
            secondThread.start();

            // wait for result
            try {
                firstThread.join();
                secondThread.join();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.out.println("The single thread has been interrupted");
                return;
            }

            // combine result of threads
            ArrayList<Integer> mergedList = utils.mergeArrays(sublist1, sublist2);
            System.out.println(Arrays.toString(mergedList.toArray()));

            // end timer
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
        utils.storeResult("Sorted list: " + list.size() + " with two threads. Average was: " + average);
    }

    private void sortWithCutOff(ArrayList<Integer> list, int cutOff){
        long startTime;
        long duration;
        long total = 0, longest = Integer.MIN_VALUE, shortest = Integer.MAX_VALUE;

        for(int i = 0; i < 10; i++) {
            // make sure the list is filled and randomized before sorting
            utils.resetList(list);

            startTime = System.nanoTime();

            CutOffSort cutOffSort = new CutOffSort(list, cutOff);
            Thread thread = new Thread(cutOffSort);

            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // end timer
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
        System.out.println("cutoff: "+cutOff+"Longest: " + longest + " shortest: " + shortest + " total: " + total + " average: " + average);

        //Store the result in a text-file for easy readability
        utils.storeResult("Sorted list: " + list.size() + " with cutoff: "+ cutOff+". Average was: " + average);

    }


}