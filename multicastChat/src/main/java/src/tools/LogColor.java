package src.tools;

public class LogColor {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void printGreen(String msg) {
        System.out.print(ANSI_GREEN + msg + ANSI_RESET);
    }
    public static void printRed(String msg) {
        System.out.print(ANSI_RED + msg + ANSI_RESET);
    }
    public static void printYellow(String msg) {
        System.out.print(ANSI_YELLOW + msg + ANSI_RESET);
    }
    public static void printCyan(String msg) {
        System.out.print(ANSI_CYAN + msg + ANSI_RESET);
    }

    public static void printlnGreen(String msg) {
        System.out.println(ANSI_GREEN + msg + ANSI_RESET);
    }
    public static void printlnRed(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
    }
    public static void printlnYellow(String msg) {
        System.out.println(ANSI_YELLOW + msg + ANSI_RESET);
    }
    public static void printlnCyan(String msg) {
        System.out.println(ANSI_CYAN + msg + ANSI_RESET);
    }
    
}
