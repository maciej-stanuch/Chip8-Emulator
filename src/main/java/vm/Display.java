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

    public boolean drawSprite(byte[] sprite, int x, int y) {
        int currentY = y;
        boolean anyFlipped = false;
        for (byte spriteLine : sprite) {
            for (int bitIndex = 7; bitIndex >= 0; bitIndex--) {
                if (isBitSet(spriteLine, bitIndex)) {
                    anyFlipped |= putPixelXOR(x + 7 - bitIndex, currentY, Color.white);
                }
            }
            currentY++;
        }
        repaint();
        return anyFlipped;
    }

    /**
     * XORs the pixel at x,y coordinates and returns whether the pixel was flipped or not.
     *
     * Explanation:
     * Sprite pixels are XOR'd with corresponding screen pixels.
     * In other words, sprite pixels that are set flip the color of the corresponding screen pixel,
     * while unset sprite pixels do nothing. The carry flag (VF) is set to 1 if any screen pixels
     * are flipped from set to unset when a sprite is drawn and set to 0 otherwise.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param c pixel color
     * @return whether the pixel was flipped from set to unset.
     */
    public boolean putPixelXOR(int x, int y, Color c) {
        int realX = (x * PIXEL_SIZE) % getWidth();
        int realY = (y * PIXEL_SIZE) % getHeight();

        boolean pixelFlipped = false;

        for (int i = realX; i < realX + PIXEL_SIZE; i++) {
            for (int j = realY; j < realY + PIXEL_SIZE; j++) {
                if (canvas.getRGB(i, j) == Color.BLACK.getRGB()) {
                    canvas.setRGB(i, j, c.getRGB());
                } else {
                    canvas.setRGB(i, j, Color.BLACK.getRGB());
                    pixelFlipped = true;
                }
            }
        }
        return pixelFlipped;
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

    public void clearScreen() {
        fillCanvas(Color.BLACK);
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

    private boolean isBitSet(byte b, int bit) {
        return (b & (1 << bit)) != 0;
    }

    private void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }
}
