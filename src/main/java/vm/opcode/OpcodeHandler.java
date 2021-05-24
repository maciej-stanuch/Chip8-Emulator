package vm.opcode;

import vm.Chip8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.System.exit;
import static vm.Chip8.STACK_SIZE;
import static vm.DefaultCharSprites.DEFAULT_SPRITE_SIZE;

public class OpcodeHandler {
    private final Chip8 vm;
    private final List<OpcodeEntry> opcodeHandlers = new ArrayList<>();

    public OpcodeHandler(Chip8 vm) {
        this.vm = vm;
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
        opcodeHandlers.add(new OpcodeEntry((char) 0x8006, (char) 0xf00f, this::_8XY6));
        opcodeHandlers.add(new OpcodeEntry((char) 0x8007, (char) 0xf00f, this::_8XY7));
        opcodeHandlers.add(new OpcodeEntry((char) 0x800E, (char) 0xf00f, this::_8XYE));
        opcodeHandlers.add(new OpcodeEntry((char) 0x9000, (char) 0xf00f, this::_9XY0));
        opcodeHandlers.add(new OpcodeEntry((char) 0xA000, (char) 0xf000, this::_ANNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0xB000, (char) 0xf000, this::_BNNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0xC000, (char) 0xf000, this::_CXNN));
        opcodeHandlers.add(new OpcodeEntry((char) 0xD000, (char) 0xf000, this::_DXYN));
        opcodeHandlers.add(new OpcodeEntry((char) 0xE09E, (char) 0xf0ff, this::_EX9E));
        opcodeHandlers.add(new OpcodeEntry((char) 0xE0A1, (char) 0xf0ff, this::_EXA1));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF007, (char) 0xf0ff, this::_FX07));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF00A, (char) 0xf0ff, this::_FX0A));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF015, (char) 0xf0ff, this::_FX15));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF018, (char) 0xf0ff, this::_FX18));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF01E, (char) 0xf0ff, this::_FX1E));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF029, (char) 0xf0ff, this::_FX29));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF033, (char) 0xf0ff, this::_FX33));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF055, (char) 0xf0ff, this::_FX55));
        opcodeHandlers.add(new OpcodeEntry((char) 0xF065, (char) 0xf0ff, this::_FX65));

        // The "Call" opcode, we don't like this one.
        opcodeHandlers.add(new OpcodeEntry((char) 0x0000, (char) 0xf000, this::_0NNN));
    }

    public void handleOpcode(char opcode) {
        //System.out.printf("Called opcode 0x%04X%n", (int) opcode);
        //System.out.println("PC = " + (int) vm.pc);
        for (OpcodeEntry opcodeEntry : opcodeHandlers) {
            if ((char) (opcode & opcodeEntry.getMask()) == opcodeEntry.getOpcode()) {
                opcodeEntry.getHandler().accept(opcode);
                //System.out.printf("Success opcode 0x%04X with mask 0x%04X%n", (int) opcode, (int) opcodeEntry.getMask());
                break;
            }
        }
    }

    // Call
    private void _0NNN(char opcode) {
        // Ignored
        System.out.println("The 'Call' opcode called. It shouldn't happen.");
        System.out.println("PC = " + (int) vm.pc);
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
            exit(3);
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
        if (vm.sp + 1 >= STACK_SIZE) {
            System.out.println("Stack overflow error.");
            exit(3);
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

        if (vx > vy) {
            vm.v[0xF] = 1;
        } else {
            vm.v[0xF] = 0;
        }
        vm.v[x] = (byte) ((vx - vy) & 0xff);
    }

    // Set Vx = Vx SHR 1
    private void _8XY6(char opcode) {
        byte x = getXArg(opcode);
        // y ignored
        vm.v[0xF] = (byte) (x & 1);
        vm.v[x] >>= 1;
    }

    // Set Vx = Vy - Vx
    private void _8XY7(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);

        char vx = (char) (vm.v[x] & 0xff);
        char vy = (char) (vm.v[y] & 0xff);

        if (vy > vx) {
            vm.v[0xF] = 1;
        } else {
            vm.v[0xF] = 0;
        }
        vm.v[x] = (byte) ((vy - vx) & 0xff);
    }

    // Set Vx = Vx SHR 1
    private void _8XYE(char opcode) {
        byte x = getXArg(opcode);
        // y ignored
        vm.v[0xF] = (byte) ((vm.v[x] & (1 << 7)) >> 7);
        vm.v[x] <<= 1;
    }

    // Skip if Vx != Vy
    private void _9XY0(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        if (vm.v[x] != vm.v[y]) {
            vm.pc += 2;
        }
    }

    // LD I, nnn
    private void _ANNN(char opcode) {
        vm.i = getNNNArg(opcode);
    }

    // Jump to V0 + NNN
    private void _BNNN(char opcode) {
        vm.pc = (char) (getNNNArg(opcode) + (char) vm.v[0]);
    }

    // Set Vx = random byte and NN
    private void _CXNN(char opcode) {
        byte x = getXArg(opcode);
        byte nn = getNNArg(opcode);
        vm.v[x] = (byte) (nn & (byte) (new Random().nextInt(256)));
    }

    // Draw sprite
    private void _DXYN(char opcode) {
        byte x = getXArg(opcode);
        byte y = getYArg(opcode);
        byte n = getNArg(opcode);
        boolean collision = vm.display.drawSprite(Arrays.copyOfRange(vm.memory, vm.i, vm.i + n), vm.v[x], vm.v[y]);
        vm.v[0xF] = (byte) (collision ? 1 : 0);
    }

    // Skip next instruction if key with the value of Vx is pressed
    private void _EX9E(char opcode) {
        byte x = getXArg(opcode);
        if (vm.keypad.isKeyPressed(vm.v[x])) {
            vm.pc += 2;
        }
    }

    // Skip next instruction if key with the value of Vx is not pressed
    private void _EXA1(char opcode) {
        byte x = getXArg(opcode);
        if (!vm.keypad.isKeyPressed(vm.v[x])) {
            vm.pc += 2;
        }
    }

    // Set Vx = delay timer value
    private void _FX07(char opcode) {
        byte x = getXArg(opcode);
        vm.v[x] = (byte) (vm.delayTimer & 0xff);
    }

    // Wait for a key press, store the value of the key in Vx. Blocking.
    private void _FX0A(char opcode) {
        byte x = getXArg(opcode);
        while (!vm.keypad.isAnyKeyPressed()) { }
        vm.v[x] = vm.keypad.getFirstPressedKey();
    }

    // Set delay timer = Vx.
    private void _FX15(char opcode) {
        byte x = getXArg(opcode);
        vm.delayTimer = vm.v[x];
    }

    // Set sound timer = Vx.
    private void _FX18(char opcode) {
        byte x = getXArg(opcode);
        vm.soundTimer.getAndSet(vm.v[x]);
        vm.speaker.playSound();
    }

    // Set I = I + Vx.
    private void _FX1E(char opcode) {
        byte x = getXArg(opcode);
        vm.i = (char) (vm.i + ((char) vm.v[x] & 0xff));
    }

    // Set I = location of sprite for digit Vx.
    private void _FX29(char opcode) {
        byte x = getXArg(opcode);
        vm.i = (char) (vm.v[x] * DEFAULT_SPRITE_SIZE);
    }

    // Store BCD representation of Vx in memory locations I, I+1, and I+2.
    private void _FX33(char opcode) {
        byte x = getXArg(opcode);
        vm.memory[vm.i] = (byte) (vm.v[x] % 1000 / 100);    // hundreds
        vm.memory[vm.i + 1] = (byte) (vm.v[x] % 100 / 10);  // tens
        vm.memory[vm.i + 2] = (byte) (vm.v[x] % 10);        // ones
    }

    // Store registers V0 through Vx in memory starting at location I.
    private void _FX55(char opcode) {
        byte x = getXArg(opcode);
        if (x + 1 >= 0) System.arraycopy(vm.v, 0, vm.memory, vm.i, x + 1);
    }

    // Read registers V0 through Vx from memory starting at location I.
    private void _FX65(char opcode) {
        byte x = getXArg(opcode);
        if (x + 1 >= 0) System.arraycopy(vm.memory, vm.i, vm.v, 0, x + 1);
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
