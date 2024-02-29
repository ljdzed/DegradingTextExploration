import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        FileIO fileIO = new FileIO();

        String testFile = "the1.txt";
        try {
            System.out.println("Repeated Word: " + fileIO.findInitialWord(testFile).getWord());

        } catch (IOException e){
            System.out.println("Error: Initial Word Search");
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                ArrayList<DifferenceGraph> input = (fileIO.RollingBufferDegradation(fileIO.findInitialWord(testFile)));
                input.add(fileIO.SetIntervalDegradation(fileIO.findInitialWord(testFile)).get(0));
                new Visualization(input);
                //new Visualization(fileIO.RollingBufferDegradation(fileIO.findInitialWord(testFile)), testFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
