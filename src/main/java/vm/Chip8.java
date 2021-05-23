package vm;

import errors.ErrorCollection;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Chip8 {
    public static final int PROGRAM_START_IN_MEMORY = 512;
    public static final int MEMORY_SIZE = 4096;
    public static final int DISPLAY_WIDTH = 64;
    public static final int DISPLAY_HEIGHT = 32;

    public static boolean finish = false;

    // memory
    public byte[] memory = new byte[MEMORY_SIZE];

    // registers
    public byte[] v = new byte[16];
    public char i = 0;
    public char pc = 0x200;

    // stack
    public byte sp = -1;
    public char[] stack = new char[160];

    // timers
    public byte delayTimer = 0;
    public byte soundTimer = 0;

    // peripherals
    public Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);

    // opcode handler
    private final OpcodeHandler opcodeHandler = new OpcodeHandler(this);

    public Chip8() {
        loadDefaultFontSpritesToMemory();
    }

    public void mainLoop() {
        while (!finish) {

            if (pc + 1 >= MEMORY_SIZE) {
                throw new ArrayIndexOutOfBoundsException("Program counter tried to access data outside the memory. [pc = " + pc);
            }

            // Big endian opcode load
            char opcode = (char) (memory[pc] << 8);
            opcode = (char) (opcode | (memory[pc + 1] & 0xff));
            pc += 2;

            opcodeHandler.handleOpcode((char) opcode);
        }
    }

    public ErrorCollection loadProgramToMemory(String filename) {
        try {
            byte[] buffer = Files.readAllBytes(Paths.get(filename));
            if (buffer.length > MEMORY_SIZE - PROGRAM_START_IN_MEMORY) {
                return ErrorCollection.of("Program is too big to fit into the memory.");
            }

            System.arraycopy(buffer, 0, memory, PROGRAM_START_IN_MEMORY, buffer.length);
            pc = PROGRAM_START_IN_MEMORY;
        } catch (IOException e) {
            return ErrorCollection.of(e.toString());
        }

        return ErrorCollection.empty();
    }

    public void initDisplay() {
        JFrame frame = new JFrame("Chip-8 Emulator");
        frame.add(display);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void loadDefaultFontSpritesToMemory() {
        List<byte[]> sprites = Arrays.stream(DefaultCharSprites.class.getDeclaredFields())
                .filter(field -> field.getType() == byte[].class)
                .sorted(Comparator.comparing(Field::getName))
                .map(field -> {
                    try {
                        return (byte[]) field.get(byte[].class);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        int memoryIndex = 0;
        for (byte[] sprite : sprites) {
            System.arraycopy(sprite, 0, memory, memoryIndex, DefaultCharSprites.DEFAULT_SPRITE_SIZE);
            memoryIndex += 5;
        }
    }
}
