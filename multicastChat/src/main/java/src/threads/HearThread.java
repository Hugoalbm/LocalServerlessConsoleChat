package src.threads;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

import src.config.Config;
import src.history.History;
import src.tools.LogColor;

@SuppressWarnings("deprecation")
public class HearThread extends Thread {

    private Config c;
    private History h;

    private String name;
    private boolean stop = false;
    private DatagramPacket outPack;
    private DatagramPacket inPack;

    public HearThread(String name, Config c, History h) {
        this.c = c;
        this.name = name;
        this.h = h;
    }

    //save the history into its file, and kill the thread
    public void kill() {
    	h.saveHistory();
        this.stop = true;
        System.exit(0);
    }

    //infinietly check for UDP packets coming from the multicast group in the port and adress from the config
    public void run() {

        try {
            InetAddress adress = InetAddress.getByName(c.inetMulticast);
            InetSocketAddress group = new InetSocketAddress(adress, c.port);

            //create the multicast socket, set the interface so you can connect to the LAN and then join the multicast group
            try (MulticastSocket mSock = new MulticastSocket(c.port)) {
                mSock.setInterface(InetAddress.getLocalHost());

                if (c.colorText) LogColor.printlnGreen("(!) --- Reception thread connected");
                else System.out.println("(!) --- Reception thread connected");
                
                mSock.joinGroup(group, null);

                byte[] buffer = new byte[256];
                while (!stop) {

                    inPack = new DatagramPacket(buffer, buffer.length);
                    //freeze stack until a packet arrives
                    mSock.receive(inPack);
                    //trim the data from the packet for errors
                    String msg = new String(inPack.getData()).trim();

                    //add message to history and print it to console
                    if (msg != null || msg != "")
                        h.history.add(c.msgDecorator + msg);
                    
                    if (c.colorText) LogColor.printlnCyan(c.msgDecorator + msg);
                    else System.out.println(c.msgDecorator + msg);;

                    //command check, if a "/connected" message is recieved, send a "username is connected" message to the group
                    if (msg.contains(":"))
                        if (msg.split(":")[1].trim().equals("/connected")) {
                            msg = "\t* " + name + " is connected.";
                            outPack = new DatagramPacket(msg.getBytes(), msg.getBytes().length, adress, c.port);
                            mSock.send(outPack);
                            continue;
                        }
                    ;

                    //reset the message byte each iteration so they don't stack in the array
                    buffer = new byte[256];

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
