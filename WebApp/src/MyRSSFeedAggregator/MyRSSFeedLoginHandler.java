package MyRSSFeedAggregator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alex on 26/01/2017.
 */
public class MyRSSFeedLoginHandler extends javax.swing.JFrame {

    // Variable definition

    MyRSSFeedSessionHandler j_session;

    // Login constructor

    public MyRSSFeedLoginHandler(MyRSSFeedSessionHandler session) {
        this.j_session = session;
    }

    // Form initialization
    @SuppressWarnings("unchecked")

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.jLabel5.setText("Connecting session...");
        this.j_session.setMail(this.jTextField1.getText());
        this.j_session.setPassword(this.jPasswordField1.getPassword());
        int r = this.j_session.Login();
        if (r == 200)
        {
            this.jLabel5.setText("Connected.");
            this.setVisible(false);
            try {
                new MyRSSFeedGUI(j_session).setVisible(true);
            } catch (IOException | JSONException ex) {
                Logger.getLogger(MyRSSFeedLoginHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            this.jLabel5.setText("Registration in process...");
            this.j_session.setMail(this.jTextField1.getText());
            this.j_session.setPassword(this.jPasswordField1.getPassword());

            int r = this.j_session.Register();
            if (r == 200)
            {
                this.jLabel5.setText("Connected.");
                this.dispose();
                try {
                    new MyRSSFeedGUI(j_session).setVisible(true);
                } catch (IOException | JSONException ex) {
                    Logger.getLogger(MyRSSFeedLoginHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (JSONException | IOException ex) {
            Logger.getLogger(MyRSSFeedLoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}