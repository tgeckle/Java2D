
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Filename: Java2D.java
 * Author: Theresa Geckle
 * Date: Jan 16, 2017
 * Purpose: 
 */
public class Java2D extends JPanel{
    
    public static final int IMG_WIDTH = 25;
    public static final int IMG_HEIGHT = 25;
    private static final int B = Color.BLACK.getRGB();
    private static final int W = Color.WHITE.getRGB();
    
    private int[][] valuesT;
    private int[][] valuesE;
    private int[][] valuesA;
    
    BufferedImage imageT;
    BufferedImage imageE;
    BufferedImage imageA;
    
    BufferedImage curImage;
    
    AffineTransform tran = new AffineTransform();
    
    private int frameNumber;
    
    private float pixelSize;
    
    
    private void fillValues() {

        valuesT = new int[IMG_HEIGHT][IMG_WIDTH];
        valuesE = new int[IMG_HEIGHT][IMG_WIDTH];
        
        // Initialize all pixels to WHITE
        for (int i = 0; i < IMG_HEIGHT; i++) {
            for (int j = 0; j < IMG_WIDTH; j++) {
                valuesT[i][j] = W;
                valuesE[i][j] = W;
            }
        }

        // Build values to draw a 'T'
        for (int i = 0; i < IMG_WIDTH; i++) {
            valuesT[0][i] = B;
            valuesT[1][i] = B;
        }
        for (int i = 1; i < IMG_HEIGHT; i++) {
            valuesT[i][11] = B;
            valuesT[i][12] = B;
        }

        // Build values to draw an 'E'
        for (int i = 0; i < IMG_HEIGHT; i++) {
            valuesE[i][0] = B;
            valuesE[i][1] = B;
        }
        for (int i = 0; i < IMG_WIDTH; i++) {
            valuesE[0][i] = B;
            valuesE[1][i] = B;
            valuesE[IMG_HEIGHT - 2][i] = B;
            valuesE[IMG_HEIGHT - 1][i] = B;
            if (i < IMG_WIDTH * .8) {
                valuesE[IMG_HEIGHT / 2][i] = B;
                valuesE[IMG_HEIGHT / 2 + 1][i] = B;
            }
        }

        // Build values to draw an 'A' based on direct input
        valuesA = new int[][]{
            {W, W, W, W, W, W, W, W, W, W, W, W, B, W, W, W, W, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, W, W, W, B, B, B, W, W, W, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, W, W, B, B, W, B, B, W, W, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, W, W, B, W, W, W, B, W, W, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, W, B, B, W, W, W, B, B, W, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, W, B, W, W, W, W, W, B, W, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, B, B, W, W, W, W, W, B, B, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, W, B, W, W, W, W, W, W, W, B, W, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, B, B, W, W, W, W, W, W, W, B, B, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, W, B, W, W, W, W, W, W, W, W, W, B, W, W, W, W, W, W, W},
            {W, W, W, W, W, W, B, B, W, W, W, W, W, W, W, W, W, B, B, W, W, W, W, W, W},
            {W, W, W, W, W, W, B, B, B, B, B, B, B, B, B, B, B, B, B, W, W, W, W, W, W},
            {W, W, W, W, W, B, B, W, W, W, W, W, W, W, W, W, W, W, B, B, W, W, W, W, W},
            {W, W, W, W, W, B, W, W, W, W, W, W, W, W, W, W, W, W, W, B, W, W, W, W, W},
            {W, W, W, W, B, B, W, W, W, W, W, W, W, W, W, W, W, W, W, B, B, W, W, W, W},
            {W, W, W, W, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, W, W, W, W},
            {W, W, W, B, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, B, W, W, W},
            {W, W, W, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, W, W, W},
            {W, W, B, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, B, W, W},
            {W, W, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, W, W},
            {W, B, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, B, W},
            {W, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, W},
            {B, B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B, B},
            {B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B},
            {B, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, B},};
    }

    /* 
    Takes a 2D array of values for a binary image and builds a BufferedImage 
    object based on the data. 
     */
    public BufferedImage buildImage(int[][] values) {
        BufferedImage image = new BufferedImage(25, 25, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < IMG_WIDTH; x++) {
            for (int y = 0; y < IMG_HEIGHT; y++) {
                image.setRGB(x, y, values[y][x]);
            }
        }

        return image;
    }
    
    public void calcTransform() {
        int frameNum = frameNumber % 165;
        if (frameNum < 55) {
            curImage = imageT;
        } else if (frameNum < 110) {
            curImage = imageE;
        } else {
            curImage = imageA;
        }
        
        frameNum = frameNum % 55;
        
        if (frameNum == 0) {
            tran = new AffineTransform();
            tran.translate(100, 100);
        } else if (frameNum < 6) {
            tran.translate(-1, 0);
        } else if (frameNum < 13) {
            tran.translate(0, 1);
        } else if (frameNum < 22) {
            tran.rotate(-0.0872665);
        } else if (frameNum < 40) {
            tran.rotate(0.0872665);
        } else if (frameNum < 50) {
            tran.scale(1.073,1); // After 10 calls should be roughly doubled 
                                 // along the x-axis
        } else {
            tran.scale(1, 0.87);
        }
        
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setPaint(Color.WHITE);
        g2.fillRect(0,0,getWidth(),getHeight());
        
        g2.setTransform(tran);
        g2.drawImage(curImage, 0,0, this);
    }
      

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Project 1");
        Java2D panel = new Java2D();
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel.fillValues();
        panel.imageT = panel.buildImage(panel.valuesT);
        panel.imageE = panel.buildImage(panel.valuesE);
        panel.imageA = panel.buildImage(panel.valuesA);
        
        panel.curImage = panel.imageT; // Start with 'T' image
        panel.tran.translate(100, 100); // Sets initial position to 100, 100
        
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                panel.calcTransform();
                panel.repaint();
                panel.frameNumber++;
            }
        });
        
        frame.pack();
        frame.setVisible(true);
        timer.start();
    }

}
