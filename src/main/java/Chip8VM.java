import errors.ErrorCollection;
import vm.Chip8;

import java.util.Arrays;

import static java.lang.System.exit;
import static vm.DefaultCharSprites.*;

public class Chip8VM {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.out.println("Usage: chip8 <filename>");
            exit(1);
        }

        final String filename = args[0];

        Chip8 vm = new Chip8();

        ErrorCollection loadErrors = vm.loadProgramToMemory(filename);
        if (loadErrors.hasAnyErrors()) {
            System.out.println(loadErrors.getErrors());
            exit(2);
        }

        vm.initDisplay();

        //vm.display.clearScreen();
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x0 * DEFAULT_SPRITE_SIZE, 0x1 * DEFAULT_SPRITE_SIZE), 0, 0));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x1 * DEFAULT_SPRITE_SIZE, 0x2 * DEFAULT_SPRITE_SIZE), 9, 0));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x2 * DEFAULT_SPRITE_SIZE, 0x3 * DEFAULT_SPRITE_SIZE), 18, 0));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x3 * DEFAULT_SPRITE_SIZE, 0x4 * DEFAULT_SPRITE_SIZE), 27, 0));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x4 * DEFAULT_SPRITE_SIZE, 0x5 * DEFAULT_SPRITE_SIZE), 36, 0));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x5 * DEFAULT_SPRITE_SIZE, 0x6 * DEFAULT_SPRITE_SIZE), 45, 0));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x6 * DEFAULT_SPRITE_SIZE, 0x7 * DEFAULT_SPRITE_SIZE), 0, 12));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x7 * DEFAULT_SPRITE_SIZE, 0x8 * DEFAULT_SPRITE_SIZE), 9, 12));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x8 * DEFAULT_SPRITE_SIZE, 0x9 * DEFAULT_SPRITE_SIZE), 18, 12));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0x9 * DEFAULT_SPRITE_SIZE, 0xA * DEFAULT_SPRITE_SIZE), 27, 12));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0xA * DEFAULT_SPRITE_SIZE, 0xB * DEFAULT_SPRITE_SIZE), 36, 12));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0xB * DEFAULT_SPRITE_SIZE, 0xC * DEFAULT_SPRITE_SIZE), 0, 24));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0xC * DEFAULT_SPRITE_SIZE, 0xD * DEFAULT_SPRITE_SIZE), 9, 24));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0xD * DEFAULT_SPRITE_SIZE, 0xE * DEFAULT_SPRITE_SIZE), 18, 24));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0xE * DEFAULT_SPRITE_SIZE, 0xF * DEFAULT_SPRITE_SIZE), 27, 24));
        System.out.println(vm.display.drawSprite(Arrays.copyOfRange(vm.memory, 0xF * DEFAULT_SPRITE_SIZE, 0x10 * DEFAULT_SPRITE_SIZE), 36, 24));
    }
}
