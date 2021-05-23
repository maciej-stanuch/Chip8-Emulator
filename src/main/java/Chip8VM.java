import errors.ErrorCollection;
import vm.Chip8;
import vm.Display;

import javax.swing.*;

import java.awt.*;

import static java.lang.System.exit;

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
        byte[] Fsprite = {(byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0x80};
        for (int i = 0; i < 1280; i++) {
            vm.display.fillCanvas(Color.BLACK);
            vm.display.drawSprite(Fsprite, i, i);
            Thread.sleep(75);
        }


    }
}
