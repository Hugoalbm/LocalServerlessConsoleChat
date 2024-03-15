package src.config;

import java.io.Serializable;

public class Config implements Serializable { 

	private static final long serialVersionUID = 1L;
	
	public int port;
    public String historyURL;
    public String inetMulticast;
    public boolean colorText;
    public String msgDecorator;

    public Config(int port, String historyURL, String inetMulticast, boolean colorText, String msgDecorator) {
        this.port = port;
        this.historyURL = historyURL;
        this.inetMulticast = inetMulticast;
        this.colorText = colorText;
        this.msgDecorator = msgDecorator;
    }

    //if constructed empty, put on the default config
    public Config() {
        port = 6666;
        historyURL = "../multicastChat/history/history.txt";
        inetMulticast = "224.24.24.24";
        colorText = true;
        msgDecorator = "";
    }
}
