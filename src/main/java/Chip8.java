import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Collections;

public class Chip8 {
    public static final int PROGRAM_START_IN_MEMORY = 512;
    public static final int MEMORY_SIZE = 4096;

    public byte[] memory = new byte[MEMORY_SIZE];
    public byte[] v = new byte[16];

    // char is 16-bit wide and unsigned
    public char i = 0;
    public char pc = 0;

    public ErrorCollection loadProgramToMemory(String filename) {
        try {
            byte[] buffer = Files.readAllBytes(Paths.get(filename));
            if (buffer.length > MEMORY_SIZE - PROGRAM_START_IN_MEMORY) {
                return ErrorCollection.of("Program is too big to fit into the memory.");
            }

            System.arraycopy(buffer, 0, memory, PROGRAM_START_IN_MEMORY, buffer.length);
        } catch (IOException e) {
            return ErrorCollection.of(e.toString());
        }

        return ErrorCollection.empty();
    }
}
