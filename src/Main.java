import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileIO fileIO = new FileIO();

        String testFile = "mail2.txt";
        try {

            System.out.println("Repeated Word: " + fileIO.findInitialWord(testFile).getWord());

        } catch (IOException e){
            System.out.println("Error");
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {

                new Visualization(fileIO.SetIntervalDegradation(fileIO.findInitialWord(testFile)));
                new Visualization(fileIO.RollingBufferDegradation(fileIO.findInitialWord(testFile)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
