import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileIO {

    // TODO implement analyses of set blocks based on iitial word size, difference based on characters bounded by ' ' characters and "similarites to initial word" ??Levenshtein Distance??

    /*
        The Levenshtein distance is a string metric for measuring difference between two sequences.
        Informally, the Levenshtein distance between two words is the minimum number of single-character edits
        (i.e. insertions, deletions or substitutions) required to change one word into the other.

     */



    // TODO address edge cases where initial word is capitalized and subsequent ones are not
    public RepeatLocationPair findInitialWord  (String file) throws IOException {
        FileReader fileReader = null;
        try{
            fileReader = new FileReader("responses/"+file);
        } catch (FileNotFoundException er0){
            try {
                fileReader = new FileReader("responses/jan31"+file);
            }catch (FileNotFoundException er1){
                return  null;
            }

        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // Vairables for determining repeat word
        String repeatedWord = "";
        String currentWord = "";
        int repeatCounter = 0;
        int repeatDepth = 0;
        int liveDepth = 0;
        // int currentDepth = 0; // Unused

        // Variables for ending loop
        int endBuffer;
        boolean exitCondition = false; // Set to true when while loop should terminate
        while((endBuffer=bufferedReader.read())!=-1 && exitCondition == false){ // Terminate at end of file (EOF) or exitCondition

            if ((char)endBuffer == ' ' || (char)endBuffer == '\n') {
                if (!currentWord.equals(repeatedWord)){
                    //System.out.println(currentWord + " VS " + repeatedWord);
                    repeatedWord = currentWord;
                    repeatDepth = liveDepth - currentWord.length();
                    currentWord = "";
                    repeatCounter = 0;
                } else {
                    repeatCounter++;
                    currentWord = "";
                    //System.out.println("EXIT TRUE");
                    if (repeatCounter == 2){
                        exitCondition = true; // Lock in repeated word when a word is repeated 3 times in a row (first is equal to next 2)
                        //System.out.println("EXIT TRUE");
                    }
                }
            } else {
                currentWord += (char)endBuffer;
                // currentDepth = liveDepth - currentWord.length(); // Unused
            }
            liveDepth++;
        }

        fileReader.close();
        bufferedReader.close();


        //System.out.println("Repeat Depth: " + repeatDepth);
        //System.out.println("Live Depth: " + liveDepth);
        return new RepeatLocationPair(repeatDepth, repeatedWord,"responses/"+file);
    }

    //Return arraylist of datapoints that can be used to visualize the degree that each successive word or letter combination strays from the original word

    public ArrayList<Color> SetIntervalDegradation(RepeatLocationPair pair) throws IOException {
        String word = pair.getWord();
        int location = pair.getDepth();
        System.out.println("Location" + location);
        FileReader fileReader = new FileReader(pair.getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int endBuffer = 0;

        ArrayList retrunList = new ArrayList<>();

        // TODO find way to get to here without this for loop
        // Jump to location
        for (int i = 0; i < location; i ++){
            bufferedReader.read();
        }

        String nextBlock = "";
        int length = word.length();
        word += ' ';
        boolean exitCondition = false;
        while(!exitCondition){
            for (int i = 0; i <= length; i++) {
                endBuffer = bufferedReader.read();
                if (endBuffer == -1){
                    exitCondition = true;
                    break;
                }
                nextBlock += (char)endBuffer;

            }
            System.out.println("'" + nextBlock + "' VS '" + word + "'");
            if (nextBlock.equals(word)){
                retrunList.add(new Color(0,255,0));
            } else {
                int distance = calculateLevenshtein(nextBlock, word);
                System.out.println(distance);
                int green = 200 - Math.abs(distance) * 20;
                if (green < 0) {
                    green = 1;
                }

                int red =  56 + distance * 20;
                if (red > 255) {red = 255;}
                retrunList.add(new Color (red, green  , 1));
            }

            nextBlock = "";


        }




        fileReader.close();
        bufferedReader.close();

        return retrunList;
    }


    // Rolling Buffer analysis that analyzes the correctness of the past X characters (X = length of Repeat Word)
    public ArrayList<Color> RollingBufferDegradation(RepeatLocationPair pair) throws IOException {
        String word = pair.getWord();
        int location = pair.getDepth();
        System.out.println(location);
        //char[] bufferChar; // Requires shifting all values of array foward and appending to the bottom
        String bufferStr = "";

        String filePath = pair.getFile();
        File file = new File(filePath);

        int totalChars = getFileCharCount(file) - location;

        System.out.println("Array Size: "+ totalChars); // accurate


        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int endBuffer;

        ArrayList returnList = new ArrayList<>();
        for (int i = 0; i <= totalChars; i++) {
            returnList.add(new Color (255,0,0));
        }

        for (int i = 0; i < location; i ++){
            bufferedReader.read();
        }
        int index = 0;
        // TODO ***MAYBE*** optimize this so that the first three words are assumed to be matches (CAREFULE DUE TO CHANCE THAT CAPITALIZATION COMES INTO PLAY)
        // Load initial characters into string (should be a match)
        for (int i = 0; i < word.length(); i++){
            bufferStr += (char)bufferedReader.read();
            index++;
            System.out.println(index + " -- " + bufferStr);

        }


        boolean exitCondition = false;
        while(!exitCondition){
            // if the buffer (last word.length) characters = word
            if (bufferStr.equals(word)){
                System.out.println("Match");
                for (int i = 0; i < word.length(); i++){
                    returnList.set(index-i-1, new Color (0, 255  , 0));

                }
                bufferStr = "";
                System.out.println(bufferStr);
                System.out.println(bufferStr.length());
                for (int i = 0; i < word.length(); i++){
                    int next = bufferedReader.read();

                    if(next == -1){
                        System.out.println("BREAK");
                        System.out.println(returnList);
                        exitCondition = true;
                        break;
                    }
                    index++;
                    bufferStr += (char)next;
                }
            } else if (bufferStr.equalsIgnoreCase(word)){
                System.out.println("Match Equals Ignore Case");
                for (int i = 0; i < word.length(); i++){
                    returnList.set(index-i-1, new Color (100, 200  , 0));

                }
                bufferStr = "";
                System.out.println(bufferStr);
                System.out.println(bufferStr.length());
                for (int i = 0; i < word.length(); i++){
                    int next = bufferedReader.read();

                    if(next == -1){
                        System.out.println("BREAK");
                        System.out.println(returnList);
                        exitCondition = true;
                        break;
                    }
                    index++;
                    bufferStr += (char)next;
                }
            }





            System.out.println("bufferStr: " + bufferStr);

            int next2 = bufferedReader.read();
            if(next2 == -1){
                System.out.println("BREAK");
                System.out.println(returnList);
                exitCondition = true;
                break;
            }
            bufferStr += (char)next2;
            index++;
            bufferStr = bufferStr.substring(1);
        }


        fileReader.close();
        bufferedReader.close();

        return returnList;
    }



    // TODO Finish
    public ArrayList<Color> SimpleWordDegradation(RepeatLocationPair pair) throws IOException {
        String word = pair.getWord();
        int location = pair.getDepth();

        FileReader fileReader = new FileReader(pair.getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int endBuffer = 0;

        for (int i = 0; i < pair.getDepth(); i ++){
            bufferedReader.read();
        }
        String nextBlock = "";

        while((endBuffer=bufferedReader.read())!=-1){
            System.out.println("loop");
            if ((char)endBuffer == ' '){
                System.out.println("SearchFinds: " + nextBlock);
                nextBlock = "";
            } else {
                nextBlock += (char)endBuffer;
            }
        }




        fileReader.close();
        bufferedReader.close();

        return null;
    }

    class RepeatLocationPair{
        int depth = 0;
        String word = "";
        String file = null;
        RepeatLocationPair (int depth, String word, String file){
            this.word = word;
            this.depth = depth;
            this.file = file;
        }

        //Mutators

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }

    // TODO get to first instance of word



    //https://www.baeldung.com/java-levenshtein-distance
    static int calculateLevenshtein(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }
    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    public static int getFileCharCount (File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);



        int charCount = 0;
        int endBuffer =0;
        while((endBuffer=bufferedReader.read())!=-1){
            charCount++;
        }

        /*
        String dump = "";
        if ((dump = bufferedReader.readLine()) != null){
            charCount += dump.length();
        }
        while((dump = bufferedReader.readLine()) != null){
            charCount += dump.length() + 1;
            System.out.println("length inside getFileCharCount: " + charCount);
        }

        */
        fileReader.close();
        bufferedReader.close();
        return charCount;
    }

}
