package com.pluralsight;

import static com.pluralsight.ColorCodes.*;

public class StyledUI {
    
    public static void styledHeader(String header){
        String line = "=".repeat(header.length() + 10);
        System.out.println(line);
        System.out.printf(BLACK_BACKGROUND + CYAN + "     %s%n", header + "     " + RESET);
        System.out.println(line);
    }
    
    public static String FormattedTextHeader(){
        String header = String.format("%-6s | %-6s | %-12s | %-14s | %-12s | %-10s | %10s | %12s"
                ,"VIN" ,"YEAR" ,"MAKE" ,"MODEL" ,"TYPE" ,"COLOR" ,"MILEAGE" ,"PRICE ($)");
        
        String border = "+" + "-".repeat(header.length() + 2) + "+";
        return border + "\n" + "| " + header + " |" + "\n" + border;
    }
    
    public static String styledBoxTitle(String title){
        
        // calculating space to center the title
        int totalWidth = 50; // total width of output
        title = title.trim();
        
        // managing title length
        if(title.length() > totalWidth){
            title = title.substring(0, totalWidth);
        }
        
        int padding = (totalWidth - title.length()) / 2;
        
        String centeredTitle = " ".repeat(Math.max(0, padding)) + title;
        
        // create a divider based on header length
        String divider = "-".repeat(totalWidth);
        
        return divider + "\n" + centeredTitle + "\n" + divider;
    }
    
    
}