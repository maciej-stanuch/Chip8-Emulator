# Chip8-Emulator

Implementation of the Chip-8 based on
* https://en.wikipedia.org/wiki/CHIP-8
* http://devernay.free.fr/hacks/chip8/C8TECH10.HTM

It is capable of running Chip-8 roms that use only all of the Chip-8 opcodes (excluding Super Chip-48 extended instructions). 
### Usage
`chip8 <rom_name>` or if you're using jar `java -jar chip8 <rom_name>`. 
ROM must be written in Chip-8 programming language and is loaded to the memory at location `0x200 (512)`. It must fit into `4096` bytes of memory, including `512` bytes reserved for the interpreter. 

### Default characters sprites 
Default sprites are loaded into memory starting at `0x0 (0)` memory location. There are sixteen sprites loaded, every single one of them is `5` bytes in size. 
Available characters are: `0`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `A`, `B`, `C`, `D`, `E`, `F`. 

### Keypad mapping

```
CHIP-8      PC
Keypad - Keyboard
  0    -    B
  1    -    4
  2    -    5   // This may be swaped with G
  3    -    6
  4    -    R
  5    -    T
  6    -    Y
  7    -    F
  8    -    G   // This may be swapped with 5
  9    -    H
  A    -    V
  B    -    N
  C    -    7
  D    -    U
  E    -    J
  F    -    M
```
