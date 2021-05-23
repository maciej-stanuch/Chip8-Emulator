package vm;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class OpcodeHandler {
    private final Chip8 vm;
    private final List<OpcodeEntry> opcodeHandlers = new ArrayList<>();

    public OpcodeHandler(Chip8 vm) {
        this.vm = vm;
        opcodeHandlers.add(new OpcodeEntry((char) 0x0000, (char) 0xf000, this::_0NNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x00E0, (char) 0xffff, this::_00E0));
        opcodeHandlers.add(new OpcodeEntry((char) 0x00EE, (char) 0xffff, this::_00EE));
        opcodeHandlers.add(new OpcodeEntry((char) 0x1000, (char) 0xf000, this::_1NNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x2000, (char) 0xf000, this::_2NNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x3000, (char) 0xf000, this::_3XNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x4000, (char) 0xf000, this::_4XNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x5000, (char) 0xf00f, this::_5XY0));
        opcodeHandlers.add(new OpcodeEntry((char) 0x6000, (char) 0xf000, this::_6XNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x7000, (char) 0xf000, this::_7XNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8000, (char) 0xf00f, this::_8XY0));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8001, (char) 0xf00f, this::_8XY1));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8002, (char) 0xf00f, this::_8XY2));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8003, (char) 0xf00f, this::_8XY3));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8004, (char) 0xf00f, this::_8XY4));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8005, (char) 0xf00f, this::_8XY5));
    }

    public void handleOpcode(char opcode) {
        System.out.printf("Called opcode 0x%04X%n", (int) opcode);
        for (OpcodeEntry opcodeEntry : opcodeHandlers) {
            if ((char) (opcode & opcodeEntry.getMask()) == opcodeEntry.getOpcode()) {
                opcodeEntry.getHandler().accept(opcode);
                System.out.printf("Success opcode 0x%04X with mask 0x%04X%n", (int) opcode, (int) opcodeEntry.getMask());
                break;
            }
        }
    }

    // Call
    private void _0NNN(char opcode) {
        // Ignored
        System.out.println("The 'Call' opcode called. It shouldn't happen.");
        Chip8.finish = true;
    }

    // Display Clear
    private void _00E0(char opcode) {
        vm.display.clearScreen();
    }

    // Return
    private void _00EE(char opcode) {
        if (vm.sp < 0) {
            System.out.println("Stack is empty, cannot return.");
            exit(2137);
        }
        vm.pc = vm.stack[vm.sp];
        vm.sp--;
    }

    // Jump to NNN
    private void _1NNN(char opcode) {
        vm.pc = getNNNArg(opcode);
    }

    // Call subroutine at NNN
    private void _2NNN(char opcode) {
        if (vm.sp + 1 >= 16) {
            System.out.println("Stack overflow error.");
            exit(420);
        }
        vm.sp++;
        vm.stack[vm.sp] = vm.pc;
        vm.pc = getNNNArg(opcode);
    }

    // Skip next instruction if Vx = nn.
    private void _3XNN(char opcode) {
        byte x = getXArg(opcode);
        byte nn = getNNArg(opcode);
        if (vm.v[x] == nn) {
            vm.pc += 2;
        }
    }

    // Skip next instruction if Vx != kk.
    private void _4XNN(char opcode) {
        byte x = getXArg(opcode);
        byte nn = getNNArg(opcode);
        if (vm.v[x] != nn) {
            vm.pc += 2;
        }
    }

    // Skip next instruction if Vx = Vy.
    private void _5XY0(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        if (vm.v[x] == vm.v[y]) {
            vm.pc += 2;
        }
    }

    // Set Vx = nn.
    private void _6XNN(char opcode) {
        byte x = getXArg(opcode);
        byte nn = getNNArg(opcode);
        vm.v[x] = nn;
    }

    // Set Vx = Vx + nn.
    private void _7XNN(char opcode) {
        byte x = getXArg(opcode);
        byte nn = getNNArg(opcode);
        vm.v[x] = (byte) (vm.v[x] + nn);
    }

    // Set Vx = Vy.
    private void _8XY0(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        vm.v[x] = vm.v[y];
    }

    // Set Vx = Vx OR Vy
    private void _8XY1(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        vm.v[x] = (byte) (vm.v[x] | vm.v[y]);
    }

    // Set Vx = Vx AND Vy
    private void _8XY2(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        vm.v[x] = (byte) (vm.v[x] & vm.v[y]);
    }

    // Set Vx = Vx XOR Vy
    private void _8XY3(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        vm.v[x] = (byte) (vm.v[x] ^ vm.v[y]);
    }

    // Set Vx = Vx + Vy
    private void _8XY4(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);

        char vx = (char) (vm.v[x] & 0xff);
        char vy = (char) (vm.v[y] & 0xff);

        if (vx + vy > 255) {
            vm.v[0xF] = 1;
        } else {
            vm.v[0xF] = 0;
        }

        vm.v[x] = (byte) ((vx + vy) & 0xff);
    }

    // Set Vx = Vx - Vy
    private void _8XY5(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);

        char vx = (char) (vm.v[x] & 0xff);
        char vy = (char) (vm.v[y] & 0xff);

        if (vm.v[x] > vm.v[y]) {
            vm.v[0xF] = 1;
        } else {
            vm.v[0xF] = 0;
        }
        vm.v[x] = (byte) (vx - vy);
    }

    private char getNNNArg(char opcode) {
        return (char) (opcode & 0x0fff);
    }

    private byte getNNArg(char opcode) {
        return (byte) (opcode & 0x00ff);
    }

    private byte getNArg(char opcode) {
        return (byte) (opcode & 0x000f);
    }

    private byte getXArg(char opcode) {
        return (byte) ((opcode & 0x0f00) >> 8);
    }

    private byte getYArg(char opcode) {
        return (byte) ((opcode & 0x00f0) >> 4);
    }
}
