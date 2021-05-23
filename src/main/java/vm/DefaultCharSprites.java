package vm;

/**
 * This class contains all the default sprites available in the virtual machine.
 * They're placed in memory starting at 0x000 address and ending at at most 0x200.
 * <p>
 * Java reflections is used to extract fields from this class, fields sorted by name alphabetically.
 * {@link Chip8#loadDefaultFontSpritesToMemory()}
 */
public class DefaultCharSprites {
    public static final int DEFAULT_SPRITE_SIZE = 5;

    public static final byte[] sprite0 = new byte[]{
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0x90,
            (byte) 0x90,
            (byte) 0xF0
    };

    public static final byte[] sprite1 = new byte[]{
            (byte) 0x20,
            (byte) 0x60,
            (byte) 0x20,
            (byte) 0x20,
            (byte) 0x70
    };

    public static final byte[] sprite2 = new byte[]{
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0xF0
    };

    public static final byte[] sprite3 = new byte[]{
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0xF0
    };

    public static final byte[] sprite4 = new byte[]{
            (byte) 0x90,
            (byte) 0x90,
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0x10
    };

    public static final byte[] sprite5 = new byte[]{
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0xF0
    };

    public static final byte[] sprite6 = new byte[]{
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0xF0
    };

    public static final byte[] sprite7 = new byte[]{
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0x20,
            (byte) 0x40,
            (byte) 0x40
    };

    public static final byte[] sprite8 = new byte[]{
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0xF0
    };

    public static final byte[] sprite9 = new byte[]{
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0xF0,
            (byte) 0x10,
            (byte) 0xF0
    };

    public static final byte[] spriteA = new byte[]{
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0xF0,
            (byte) 0x90,
            (byte) 0x90
    };

    public static final byte[] spriteB = new byte[]{
            (byte) 0xE0,
            (byte) 0x90,
            (byte) 0xE0,
            (byte) 0x90,
            (byte) 0xE0
    };

    public static final byte[] spriteC = new byte[]{
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0x80,
            (byte) 0x80,
            (byte) 0xF0
    };

    public static final byte[] spriteD = new byte[]{
            (byte) 0xE0,
            (byte) 0x90,
            (byte) 0x90,
            (byte) 0x90,
            (byte) 0xE0
    };

    public static final byte[] spriteE = new byte[]{
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0xF0
    };

    public static final byte[] spriteF = new byte[]{
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0xF0,
            (byte) 0x80,
            (byte) 0x80
    };
}
