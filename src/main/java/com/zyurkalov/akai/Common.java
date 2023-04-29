package com.zyurkalov.akai;

public class Common {

    /* Midi note numbers for arrow */
    public static class NAVKeys{
        public static final int UP = 34;
        public static final int DOWN = 35;
        public static final int LEFT = 36;
        public static final int RIGHT = 37;
    }

    /* MPK2 Pad Color Identifiers */
    public static class PadColor {
        public static final int OFF = 0x00;
        public static final int RED = 0x01;
        public static final int ORANGE = 0x02;
        public static final int AMBER = 0x03;
        public static final int YELLOW = 0x04;
        public static final int GREEN = 0x05;
        public static final int GREEN_BLUE = 0x06;
        public static final int AQUA = 0x07;
        public static final int LIGHT_BLUE = 0x08;
        public static final int BLUE = 0x09;
        public static final int PURPLE = 0x0A;
        public static final int PINK = 0x0B;
        public static final int HOT_PINK = 0x0C;
        public static final int PASTEL_PURPLE = 0x0D;
        public static final int PASTEL_GREEN = 0x0E;
        public static final int PASTEL_PINK = 0x0F;
        public static final int GREY = 0x10;
    }


    /* Pad Status byte */
    public static final int PAD_STATUS = 0x99;
    public static int[] padNotes;


    /* Status bytes used on each bank */
    public static final int BANK_A_STATUS = 0xB1;
    public static final int BANK_B_STATUS = 0xB2;
    public static final int BANK_C_STATUS = 0xB3;

    public static final class ClipStatus {
        public static final int OFF = 0x00;
        public static final int OCCUPIED = 0x01;
        public static final int PLAYING = 0x02;
        public static final int RECORDING = 0x03;
    }



}
