package src;

import java.util.Scanner;

import src.config.Config;
import src.config.ConfigReader;
import src.history.History;
import src.threads.HearThread;
import src.threads.SendThread;
import src.tools.LogColor;

public class MainClient {

    public static HearThread hearthread;
    public static SendThread sendthread;
    public static Config c;
    
    @SuppressWarnings("resource")
    public static void main(String[] args) {

        //import the configuration
        c = ConfigReader.read();

        //read the name as input, can't contain ":" because it is used to distinguish the body of the message from the sender in the hear thread
        Scanner e = new Scanner(System.in);
        String name;
        do {
            
            if (c.colorText) LogColor.printCyan("Username: ");
            else System.out.print("Username: ");

            name = e.nextLine();

            if (name.contains(":")) {
                if (c.colorText) LogColor.printlnRed("Username can't contain \":\"");
                else System.out.println("Username can't contain \":\"");
            }

        } while (name.contains(":"));

        //load the history and put a random guest name if the name input was empty
        History h = new History();
        String fname = name.equals("") ? "Default" + ((int) (Math.random() * 100)) : name;
        
        //create the threads, the hear thread needs the history so it saves it when killed
        sendthread = new SendThread(fname, c);
        hearthread = new HearThread(fname, c, h);

        //initialize the threads
        hearthread.start();
        sendthread.start();
    }
}
