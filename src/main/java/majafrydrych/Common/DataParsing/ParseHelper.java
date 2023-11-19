/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Common.DataParsing;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Maja Frydrych
 */
public class ParseHelper {
    
    public static InetAddress parseIPAddress(String address) {
        InetAddress serverAddress = null;
        if (address == "" || address == "localhost") {
            try {
                serverAddress = InetAddress.getLocalHost();
            }
            catch (UnknownHostException e) {}
        }
        else {
            try {
                serverAddress = InetAddress.getByName(address);
            }
            catch (UnknownHostException e) {}
            
        }
        return serverAddress;
    }
}
