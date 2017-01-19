
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Filename: Java2D.java
 * Author: Theresa Geckle
 * Date: Jan 16, 2017
 * Purpose: 
 */
public class Java2D extends JComponent{
    
    public static final int IMG_WIDTH = 25;
    public static final int IMG_HEIGHT = 25;
    private static final int B = Color.BLACK.getRGB();
    private static final int W = Color.WHITE.getRGB();
    
    private int[][] valuesT;
    private int[][] valuesE;
    private int[][] valuesA;
    
    
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

    @Override
    public void paint(Graphics g) {
        fillValues();
        BufferedImage imageT = buildImage(valuesT);
        BufferedImage imageE = buildImage(valuesE);
        BufferedImage imageA = buildImage(valuesA);

        g.drawImage(imageE, 0, 0, this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Project 1");
        frame.getContentPane().add(new Java2D());
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
