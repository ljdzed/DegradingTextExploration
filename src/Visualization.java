import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Visualization extends JFrame {
    public Visualization(ArrayList<Color> colors) {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel(colors);
        this.add(panel);
        this.setVisible(true);
        panel.requestFocusInWindow(); // Request focus for the panel
        this.setTitle("DegTex");
    }


    public class Panel extends JPanel {
        ArrayList<Color> data = new ArrayList<>();
        public Panel(ArrayList data) {
            setFocusable(true);
            this.data = data;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawDifferences(g);
        }

        private void drawDifferences (Graphics g){
            int resolution = 1 ;
            int width = 20;
            for (int i = 0; i < data.size(); i ++){
                    //System.out.println(data.get(i));
                    g.setColor(data.get(i));
                    g.fillRect(50+ resolution* Math.floorMod(i,width), 30 + resolution* Math.floorDiv(i,width), resolution, resolution);

            }
        }

    }
}
