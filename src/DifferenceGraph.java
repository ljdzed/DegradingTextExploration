import java.awt.*;
import java.util.ArrayList;

public class DifferenceGraph {

    private FileIO.RepeatLocationPair pair;
    private ArrayList<Color> color;

    public DifferenceGraph(FileIO.RepeatLocationPair pair, ArrayList<Color> color) {
        this.pair = pair;
        this.color = color;
    }

    public ArrayList<Color> getColor() {
        return color;
    }

    public FileIO.RepeatLocationPair getPair() {
        return pair;
    }

    public String getPairFile(){
        return pair.getFileName();
    }

    public void setColor(ArrayList<Color> color) {
        this.color = color;
    }

    public void setPair(FileIO.RepeatLocationPair pair) {
        this.pair = pair;
    }
}
