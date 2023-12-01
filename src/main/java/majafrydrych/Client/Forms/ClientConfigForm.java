package majafrydrych.Client.Forms;

import majafrydrych.Client.Logic.Client;
import majafrydrych.Common.Validation.FormValidation;
import majafrydrych.Events.UsernameListener;

import javax.swing.*;

/**
 *
 * @author Maja Frydrych
 */
public class ClientConfigForm extends javax.swing.JFrame {
    
    public boolean isClientStarted = false;
    private String nickname;
    private Client client;
    RoomBrowser roomBrowser = null;

    /**
     * Creates new form ClientConfigForm
     */
    public ClientConfigForm() {

        initComponents();
    }
    
    private boolean validateClienrConfig() {
        return FormValidation.validateIPAdress(TbIPAddress.getText()) 
                && FormValidation.validateNickname(TbUsername.getText())
                && FormValidation.validatePort(TbPort.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        LbUsername = new javax.swing.JLabel();
        TbUsername = new javax.swing.JTextField();
        BtnConnect = new javax.swing.JButton();
        LbIPAddress = new javax.swing.JLabel();
        LbPort = new javax.swing.JLabel();
        TbIPAddress = new javax.swing.JTextField();
        TbPort = new javax.swing.JTextField();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        LbUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LbUsername.setText("Twój pseudonim");

        BtnConnect.setText("Połącz");
        BtnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConnectActionPerformed(evt);
            }
        });

        LbIPAddress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LbIPAddress.setText("Adress IP serwera:");
        LbIPAddress.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        LbPort.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LbPort.setText("Port serwera:");
        LbPort.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        TbIPAddress.setText("localhost");

        TbPort.setText("20049");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TbUsername)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LbIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TbIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TbPort)
                            .addComponent(LbPort, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(LbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(LbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LbIPAddress)
                    .addComponent(LbPort))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TbIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TbPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(BtnConnect)
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConnectActionPerformed
        if (!isClientStarted) {
            if (validateClienrConfig()) {
                client = new Client(TbUsername.getText(), Integer.parseInt(TbPort.getText()));
                client.run();
                client.setUsernameListener(new UsernameListener() {
                    @Override
                    public void usernameValidated(Boolean isValid) {
                        if (!isValid) {
                            JOptionPane.showMessageDialog(null, "Nazwa użytkownika jest już zajęta.");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "połączono z serwerem");
                            isClientStarted = true;
                            roomBrowser = new RoomBrowser(client);
                            roomBrowser.show();
                            hide();
                        }

                    }
                });
            }
        }
    }//GEN-LAST:event_BtnConnectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientConfigForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientConfigForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientConfigForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientConfigForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientConfigForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnConnect;
    private javax.swing.JLabel LbIPAddress;
    private javax.swing.JLabel LbPort;
    private javax.swing.JLabel LbUsername;
    private javax.swing.JTextField TbIPAddress;
    private javax.swing.JTextField TbPort;
    private javax.swing.JTextField TbUsername;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}
