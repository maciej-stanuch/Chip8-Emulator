package vm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OpcodeHandler {
    private final Chip8 vm;
    private final Map<String, Consumer> opCodeHandlers = new HashMap<>();

    public OpcodeHandler(Chip8 vm) {
        this.vm = vm;
    }

    // Call
    private void _0NNN(char opcode) {
        // Ignore
    }

    // Display Clear
    private void _00E0(char opcode) {
        vm.display.clearScreen();
        vm.pc++;
    }

    // Return
    private void _00EE(char opcode) {
        //TODO(ms)
    }

    // Jump to NNN
    private void _1NNN(char opcode) {
        vm.pc = getNNNArg(opcode);
    }

    private void _2NNN(char opcode) {
        //TODO(ms)
    }

    private char getNNNArg(char opcode) {
        return (char) (opcode & 0x0fff);
    }

    private char getNNArg(char opcode) {
        return (char) (opcode & 0x00ff);
    }

    private char getNArg(char opcode) {
        return (char) (opcode & 0x000f);
    }

    private char getXArg(char opcode) {
        return (char) ((opcode & 0x0f00) >> 8);
    }

    private char getYArg(char opcode) {
        return (char) ((opcode & 0x00f0) >> 4);
    }
}
