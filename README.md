# Chip8-Emulator

Implementation of the Chip-8 based on
* https://en.wikipedia.org/wiki/CHIP-8
* http://devernay.free.fr/hacks/chip8/C8TECH10.HTM

It is capable of running Chip-8 roms that use only all of the Chip-8 opcodes (excluding Super Chip-48 extended instructions). 

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
