package src.threads;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

import src.MainClient;
import src.config.Config;
import src.tools.LogColor;

@SuppressWarnings("deprecation")
public class SendThread extends Thread {

    private Config c;

    private String name;
    private String welcome;
    private DatagramPacket outPack;

    //initialize the thread and create the welcome message
    public SendThread(String name, Config c) {
        this.c = c;
        this.name = name;
        welcome = "(!) - " + name + " connected!";
    }

    //check infinietly for scanner inputs and send them
    //End the loop when the user inputs "bye" and kill the hearing thread
    public void run() {

        Scanner e = new Scanner(System.in);
        String in = "";

        try {
            InetAddress adress = InetAddress.getByName(c.inetMulticast);
            InetSocketAddress group = new InetSocketAddress(adress, c.port);

            //create the multicast socket, set the interface so you can acces LAN and join the group
            try (MulticastSocket mSock = new MulticastSocket(c.port)) {
                mSock.setInterface(InetAddress.getLocalHost());
                mSock.joinGroup(group, null);

                if (c.colorText) LogColor.printlnGreen("(!) --- Sender thread connected. Write /connected to see all connected users.");
                else System.out.println("(!) --- Sender thread connected. Write /connected to see all connected users.");

                //send the welcome message
                outPack = new DatagramPacket(welcome.getBytes(), welcome.getBytes().length, adress, c.port);
                mSock.send(outPack);

                do {
                    try {
                        //read a line written in the console, so it ends when the user presses intro
                        in = e.nextLine();

                        if (in.equals(""))
                            continue;
                        //command check, if it is not a valid command, do not send it. The command character is "/".
                        //TODO: make the command list a static final map in another file instead of a local switch
                        if (in.charAt(0) == '/') {
                            switch (in) {
                                case "/connected":
                                    break;
                                default:
                                    if (c.colorText) LogColor.printlnRed("Command not found...");
                                    else System.out.println("Command not found...");
                                    continue;
                            }
                        }

                        //trim the message for errors and send it 
                        String msg = (name + ": " + in).trim();
                        outPack = new DatagramPacket(msg.getBytes(), msg.getBytes().length, adress, c.port);
                        mSock.send(outPack);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } while (!in.equalsIgnoreCase("bye"));

            } catch (Exception ex) {
                ex.printStackTrace();

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            e.close();
            MainClient.hearthread.kill();
        }
    }
}
