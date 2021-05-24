package vm.peripherals;

import vm.Chip8;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Speaker {
    private final int channel = 1;
    private final int volume = 100;

    private final Chip8 vm;

    public Speaker(Chip8 vm) {
        this.vm = vm;
    }

    public void playSound() {
        new Thread(() -> {
            try {
                Synthesizer synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
                MidiChannel[] channels = synthesizer.getChannels();

                channels[channel].noteOn(60, volume);

                while (vm.soundTimer.get() > 0) {

                }
                channels[channel].allNotesOff();

                synthesizer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
