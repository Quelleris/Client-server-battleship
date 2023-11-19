/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Common.Validation;

import javax.swing.JOptionPane;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author sugil
 */
public class FormValidation {

    public static boolean validateNickname(String nickname) {
        if (nickname.trim().length() < 3) {
            JOptionPane.showMessageDialog(null, "Nazwa użytkownika musi mieć minimum 3 znaki");
            return false;
        }
        return true;
    }
    
    public static boolean validateIPAdress(String inputAdress) {
        if (inputAdress.length() > 0 && !inputAdress.equals("localhost")) {
            try {
                InetAddress inet = InetAddress.getByName(inputAdress);
                return true;
            }
            catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "Niepoprawny adres IP");
                return false;
            }
        }
        // zwraca true jak textBox jest pusty
        // TODO zmienić, żeby zwracało false?
        return true;
    }
        
    public static boolean validatePort(String port) {
        try {
            int portInt = Integer.parseInt(port.trim());
            return !(portInt < 0 || portInt > 65535);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Niepoprawny port");
            return false;
        }
    }
}
