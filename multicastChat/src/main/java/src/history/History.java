package src.history;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

import src.MainClient;
import src.config.Config;
import src.tools.LogColor;

public class History { 
	
    //take the config file from Main
	private static Config c = MainClient.c;
	public ArrayList<String> history = new ArrayList<String>();
	
    //load the history when initialized
	public History() {
		loadHistory();
	}
	
    //if the history array is empty do nothing
    //if the history array contains stuff, append the new history to the history file, or create a new history file
	public void saveHistory() {
		
		if (history.isEmpty()) return;
		
        try {
            File f = new File(c.historyURL);
            if (!f.exists()) f.createNewFile();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write("\n ------------------ Chat " + new Date() + "------------------------\n");
            
            for (String s : history) {
                bw.write(s + "\n");
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        
    }
	
    //if the history file is not found, do nothing
    //if the history file is found, print to console and close
	public void loadHistory() {

        try {
        	
            File f = new File(c.historyURL);
            if (!f.exists()) {
            	if (c.colorText) LogColor.printlnRed("No history to load");
                else System.out.println("No history to load");
            	return;
            }
                
            BufferedReader br = new BufferedReader(new FileReader(f));
            String s;

            if (c.colorText) LogColor.printlnYellow("\n#### HISTORY ####");
            else System.out.println("\n#### HISTORY ####");

            while ((s = br.readLine()) != null) {
                if (c.colorText) LogColor.printlnYellow(s == null ? "" : s + "\n");
                else System.out.print(s == null ? "" : s + "\n");
                
            }
            
            System.out.println();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
