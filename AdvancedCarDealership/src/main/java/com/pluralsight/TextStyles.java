package com.pluralsight;

public class TextStyles {
    // May not work in all terminals
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String REVERSED = "\u001B[7m";
    public static final String BLINK = "\u001B[5m";
    public static final String HIDDEN = "\u001B[8m";  // Text won't display, useful for hiding
}