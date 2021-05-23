package vm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Display extends JPanel {
    public static final int PIXEL_SIZE = 16;

    private final BufferedImage canvas;

    public Display(int width, int height) {
        canvas = new BufferedImage(width * PIXEL_SIZE, height * PIXEL_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        fillCanvas(Color.BLACK);
    }

    public void drawSprite(byte[] sprite, int x, int y) {
        int currentY = y;
        for (byte spriteLine : sprite) {
            for (int bitIndex = 7; bitIndex >= 0; bitIndex--) {
                if (isBitSet(spriteLine, bitIndex)) {
                    putPixel(x + 7 - bitIndex, currentY, Color.white);
                }
            }
            currentY++;
        }
        repaint();
    }

    private static Boolean isBitSet(byte b, int bit) {
        return (b & (1 << bit)) != 0;
    }

    public void putPixel(int x, int y, Color c) {
        int realX = (x * PIXEL_SIZE) % getWidth();
        int realY = (y * PIXEL_SIZE) % getHeight();

        for (int i = realX; i < realX + PIXEL_SIZE; i++) {
            for (int j = realY; j < realY + PIXEL_SIZE; j++) {
                canvas.setRGB(i, j, c.getRGB());
            }
        }
    }

    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(canvas, null, null);
    }
}
