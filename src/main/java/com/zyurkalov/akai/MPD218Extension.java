package com.zyurkalov.akai;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorDevice;
import com.bitwig.extension.controller.api.MidiIn;
import com.bitwig.extension.controller.api.NoteInput;

public class MPD218Extension extends ControllerExtension {

   private MidiIn midiInPort;
   private NoteInput padNotes;
   private CursorDevice cursorDevice;

   protected MPD218Extension(MPD218ExtensionDefinition definition, ControllerHost host) {
      super(definition, host);
   }

   @Override
   public void init() {
      midiInPort = getHost().getMidiInPort(0);
      padNotes = midiInPort.createNoteInput("MPD218 Pads", "89????", "99????", "DF????", "EF??", "AF????");
      midiInPort.setMidiCallback(this::onMidi);
      cursorDevice = getHost().createEditorCursorDevice();
      getHost().println("Akai Professional MPD218 Bitwig Controller Script");
   }

   private void onMidi(int status, int data1, int data2) {
      if (status == 0xb1) {
         cursorDevice.getMacro(data1).getAmount().inc(uint7ToInt7(data2), 128);
      }
   }

   private int uint7ToInt7(int value) {
      return value - 64;
   }

   @Override
   public void exit() {
   }

   @Override
   public void flush() {
   }
}
