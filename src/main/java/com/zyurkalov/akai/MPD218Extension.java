package com.zyurkalov.akai;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.*;

public class MPD218Extension extends ControllerExtension {

   private MidiIn midiInPort;
   private NoteInput padNotes;
   private CursorDevice cursorDevice;
   private CursorRemoteControlsPage remoteControlsPage;

   protected MPD218Extension(MPD218ExtensionDefinition definition, ControllerHost host) {
      super(definition, host);
   }

   @Override
   public void init() {
      midiInPort = getHost().getMidiInPort(0);
      padNotes = midiInPort.createNoteInput("MPD218 Pads", "89????", "99????", "DF????", "EF??", "AF????");
      midiInPort.setMidiCallback(this::onMidi);
      cursorDevice = getHost().createCursorTrack("MPD218_CursorTrack", "MPD218 Cursor Track", 0, 0, true).createCursorDevice("MPD218_CursorDevice", "MPD218 Cursor Device", 0, CursorDeviceFollowMode.FIRST_DEVICE);
      remoteControlsPage = cursorDevice.createCursorRemoteControlsPage( 8);

      getHost().println("Akai Professional MPD218 Bitwig Controller Script");
      getHost().showPopupNotification("MPD218 Initialized");
   }

   private void onMidi(int status, int data1, int data2) {
      if (status == 0xb1) {
         double delta = uint7ToInt7(data2) / 128.0;
         remoteControlsPage.getParameter(data1).inc(delta, 128);

      }
   }

   private int uint7ToInt7(int value) {
      return value - 64;
   }

   @Override
   public void exit() {
        getHost().showPopupNotification("MPD218 Exited");
   }

   @Override
   public void flush() {
   }
}
