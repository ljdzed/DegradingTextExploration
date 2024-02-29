import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Visualization extends JFrame {

    //TODO Implement Scrolling
    //TODO Doxygen Commenting
    ArrayList<DifferenceGraph> graphs;

    public Visualization(ArrayList<DifferenceGraph> graphs) {
        this.graphs = graphs;

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Panel panel = new Panel(graphs);
        panel.setBounds(0, 0, 800, 1000); // Determines how much of these are shown
        this.add(panel);

/*
        JLabel localTitle = new JLabel(graphs.get(0).getPair().getFile());
        localTitle.setBounds(50, 30  , 80, 20);
        this.add(localTitle,0 ); // 0 ensures its on top
*/

        this.setVisible(true);
        panel.requestFocusInWindow(); // Request focus for the panel
        this.setTitle("DegTex");
    }

    // Depreciated Constructors
    /*
    public Visualization(ArrayList<Color> colors, String title) {

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Panel panel = new Panel(colors);
        panel.setBounds(0, 0, 800, 600);
        this.add(panel);

        JLabel localTitle = new JLabel(title);
        localTitle.setBounds(50, 30  , 80, 20);
        this.add(localTitle,0 ); // 0 ensures its on top




        this.setVisible(true);
        panel.requestFocusInWindow(); // Request focus for the panel
        this.setTitle("DegTex");
    }


    public Visualization(ArrayList<ArrayList<Color>> colors, ArrayList<String> titles) {


        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Panel panel = new Panel(colors);
        panel.setBounds(0, 0, 800, 600);
        this.add(panel);

        JLabel localTitle = new JLabel(titles.get(1));
        localTitle.setBounds(10, 30  , 60, 20);
        this.add(localTitle);



        this.setVisible(true);
        panel.requestFocusInWindow(); // Request focus for the panel
        this.setTitle("DegTex");
    }

     */

    public static class Panel extends JPanel {
        ArrayList<DifferenceGraph> graphs;
        ArrayList<Color> data = new ArrayList<>();

        // Deprecated Constructor
        /*
        public Panel(ArrayList<Color> data) {
            setFocusable(true);
            this.data = data;

        }
         */

        public Panel(ArrayList<DifferenceGraph> graphs) {
            setFocusable(true);
            if (graphs != null){
                this.graphs = graphs;
            } else {
                // TODO deal with this
            }

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawDifference(g);
        }


        /**
         * Draw Differences
         * @param g: required to make graphics work)
         * @return difference graph
         *
        **/
        private void drawDifference (Graphics g){
            //System.out.println("DIFFERENCES DRAWN");
            int resolution = 4 ;
            int width = 20;
            for (int j = 0; j < graphs.size(); j ++){
                g.setColor(Color.BLACK); // Set the text color
                g.drawString(graphs.get(j).getPairFile(), 100 *j + 50, 50); // Draw the string at position (50, 50)
                for (int i = 0; i < graphs.get(j).getColor().size(); i++) {
                    //System.out.println("DIFFERENCES DRAWN: " + i);

                    g.setColor(graphs.get(j).getColor().get(i));
                    g.fillRect(100 * j + 50 + resolution * Math.floorMod(i, width), 60 + resolution * Math.floorDiv(i, width), resolution, resolution);
                }

            }

        }


        // TODO maintain this unused function

        private void drawDifferences (Graphics g){
            int resolution = 4 ;
            int width = 20;

            for (int i = 0; i < data.size(); i ++){
                //System.out.println(data.get(i));
                g.setColor(data.get(i));
                g.fillRect(50+ resolution* Math.floorMod(i,width), 60 + resolution* Math.floorDiv(i,width), resolution, resolution);

            }
            g.setColor(Color.BLACK); // Set the text color
            g.drawString("<Demo Text>", 50, 50); // Draw the string at position (50, 50)
        }

    }
}
