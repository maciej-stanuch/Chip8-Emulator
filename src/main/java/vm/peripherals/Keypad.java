package vm.peripherals;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * I'm not proud of this one
 */
public class Keypad extends KeyAdapter {
    private static final int[] keys = new int[]{
            KeyEvent.VK_B,
            KeyEvent.VK_4,
            KeyEvent.VK_G,
            KeyEvent.VK_6,
            KeyEvent.VK_R,
            KeyEvent.VK_T,
            KeyEvent.VK_Y,
            KeyEvent.VK_F,
            KeyEvent.VK_5,
            KeyEvent.VK_H,
            KeyEvent.VK_V,
            KeyEvent.VK_N,
            KeyEvent.VK_7,
            KeyEvent.VK_U,
            KeyEvent.VK_J,
            KeyEvent.VK_M
    };

    private final Set<Integer> pressedKeys = new HashSet<>();

    public boolean isKeyPressed(byte key) {
        boolean result = pressedKeys.contains(keys[key]);
        pressedKeys.remove(keys[key]);
        return result;
    }

    public boolean isAnyKeyPressed() {
        return !pressedKeys.isEmpty();
    }

    public byte getFirstPressedKey() {
        byte result = -1;
        for (byte i = 0; i < 16; i++) {
            if (pressedKeys.stream().findFirst().orElse(-1).equals(keys[i])) {
                result = i;
                pressedKeys.remove(keys[i]);
            }
        }
        return result;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getID() == KeyEvent.KEY_PRESSED) {
            pressedKeys.add(event.getKeyCode());
        }
    }
}
