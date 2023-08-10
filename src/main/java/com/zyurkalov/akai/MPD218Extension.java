package com.zyurkalov.akai;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.*;

public class MPD218Extension extends ControllerExtension {

    private MidiIn midiInPort;
    private MidiOut midiOutPort;
    private NoteInput noteInput;
    private CursorDevice cursorDevice;
    TrackBank trackBank;
    private CursorRemoteControlsPage remoteControlsPage;

    protected MPD218Extension(MPD218ExtensionDefinition definition, ControllerHost host) {
        super(definition, host);
    }

    @Override
    public void init() {
        midiInPort = getHost().getMidiInPort(0);
        noteInput = midiInPort.createNoteInput("MPD218 Pads", "89????", "99????", "DF????", "EF??", "AF????", "D?????");
        midiInPort.setMidiCallback(this::onMidi);

        midiOutPort = getHost().getMidiOutPort(0);
        cursorDevice = getHost().createCursorTrack("MPD218_CursorTrack", "MPD218 Cursor Track", 0, 0, true).createCursorDevice("MPD218_CursorDevice", "MPD218 Cursor Device", 0, CursorDeviceFollowMode.FIRST_DEVICE);
        remoteControlsPage = cursorDevice.createCursorRemoteControlsPage(6);
        for (int i = 0; i < 6; i++) {
            remoteControlsPage.getParameter(i).markInterested();
            remoteControlsPage.getParameter(i).setIndication(true);
        }

        trackBank = getHost().createTrackBank(4 * 4, 0, 0);
        for (int i = 0; i < trackBank.getSizeOfBank(); i++) {
            trackBank.getItemAt(i).arm().markInterested();
            trackBank.getItemAt(i).mute().markInterested();
            int finalI = i;
            trackBank.getItemAt(i).arm().addValueObserver((value) -> {
                midiOutPort.sendMidi(0x90, 36 + finalI, value ? 127 : 0);
            });
            trackBank.getItemAt(i).mute().addValueObserver((value) -> {
                midiOutPort.sendMidi(0x90, 52 + finalI, value ? 127 : 0);
            });
        }
        getHost().println("Akai Professional MPD218 Bitwig Controller Script");
        getHost().showPopupNotification("MPD218 Initialized");
    }

    private void onMidi(int status, int data1, int data2) {
        if (status == 0xb1) {
            remoteControlsPage.getParameter(data1).inc(uint7ToInt7(data2), 128);
        } else if (status == 0x80) {
            int armNum = data1 - 36;
            if (armNum >= 0 && armNum < trackBank.getSizeOfBank()) {
                trackBank.getItemAt(armNum).arm().toggle();
            }
            int muteNum = data1 - 52;
            if (muteNum >= 0 && muteNum < trackBank.getSizeOfBank()) {
                trackBank.getItemAt(muteNum).mute().toggle();
            }
        }
    }

    private int uint7ToInt7(int value) {
        return value >= 64 ? value - 128 : value;
    }

    @Override
    public void exit() {
        getHost().showPopupNotification("MPD218 Exited");
    }

    @Override
    public void flush() {
    }
}
