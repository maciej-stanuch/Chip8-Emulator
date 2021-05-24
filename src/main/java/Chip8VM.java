import errors.ErrorCollection;
import vm.Chip8;


import static java.lang.System.exit;

public class Chip8VM {
    public static void main(String[] args) {
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
        vm.mainLoop(Chip8.DEFAULT_CLOCK);
    }
}
