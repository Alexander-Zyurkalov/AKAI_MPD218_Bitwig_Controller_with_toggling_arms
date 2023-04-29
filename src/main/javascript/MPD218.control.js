   
 loadAPI(1);


host.defineController("Akai", "MPD218", "1.0.2", "dbd2b0bd-5271-467a-9595-333b05c9b8f2");
host.defineMidiPorts(1, 1);

if (host.platformIsWindows())
{
    host.addDeviceNameBasedDiscoveryPair(["MPD218"], ["MPD218"]);
}

else if (host.platformIsMac())  {
    host.addDeviceNameBasedDiscoveryPair(["MPD218 Port A"], ["MPD218 Port A"]);
}
else {
    host.addDeviceNameBasedDiscoveryPair(["MPD218"], ["MPD218"]);
}

/* Mpd218 MIDI Product ID */
 
function init()
{
	PadNotes = host.getMidiInPort(0).createNoteInput("MPD218 Pads", "89????","99????" ,"DF????","EF??", "AF????");
	host.getMidiInPort(0).setMidiCallback(onMidi);	
	cursorDevice = host.createEditorCursorDevice();
    println("Akai Profressional MPD218 Bitwig Controller Script");   
}


function onMidi(status,data1, data2)
 {
	if (status == 0xb1)
	{ 
		cursorDevice.getMacro(data1).getAmount().inc(uint7ToInt7(data2), 128);
	}
	if (status == 0xb2)
	{ 
		cursorDevice.getParameter(data1).inc(uint7ToInt7(data2), 128);
	}  
}
	


