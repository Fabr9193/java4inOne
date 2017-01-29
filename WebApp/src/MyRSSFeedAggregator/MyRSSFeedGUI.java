package MyRSSFeedAggregator;

import com.oracle.javafx.jmx.json.JSONException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;

/**
 * Created by alex on 29/01/2017.
 */
public class MyRSSFeedGUI extends javax.swing.JFrame {

    // Variable definitions

    MyRSSFeedSessionHandler j_session;
    DefaultListModel listModel;
    JSONArray listRSS;

    // GUI constructor

    public MyRSSFeedGUI(MyRSSFeedSessionHandler session) throws IOException, JSONException {
        this.j_session = session;
        this.updateRSSlist();
    }

    //List handler function

    public void updateRSSlist() throws JSONException, IOException {
        this.j_session.sendRequest("/feed", null, "GET", "", "http://www.mocky.io/v2/5889ee6f2500004719adcf61");
        this.listRSS = this.j_session.getResponse();
        this.listModel = new DefaultListModel();
        for (int i = 0; i < this.j_session.getResponse().length(); i++) {
            JSONObject item = this.j_session.getResponse().getJSONObject(i);
            String content = new String();
            if (item.has("webMaster") && item.getString("webMaster").length() > 0)
                content += " ("+item.getString("webMaster")+") ";
            content += item.getString("title");
            this.listModel.addElement(content);
        }
        this.jList1.setModel(this.listModel);
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int index = this.jList1.getSelectedIndex();
        this.listModel.removeElementAt(index);
        this.listRSS.remove(index);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jList1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jList1PropertyChange
    }//GEN-LAST:event_jList1PropertyChange

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (!evt.getValueIsAdjusting()){
            int item_id = this.jList1.getSelectedIndex();
            JSONObject rss;
            try {
                if (item_id >= 0){
                    rss = this.listRSS.getJSONObject(item_id);
                    String content = rss.getString("link");
                    if (rss.has("webMaster") && rss.getString("webMaster").length() > 0)
                        content += " ("+rss.getString("webMaster")+")";
                    if (rss.has("pubDate") && rss.getString("pubDate").length() > 0)
                        content += "\non "+rss.getString("pubDate");

                    content += "\n\n"+rss.getString("description");
                    this.jTextPane1.setText(content);
                }
            } catch (JSONException ex) {
                Logger.getLogger(RssAggregatorGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed

    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String name = this.jTextField1.getText();
        String url = this.jTextField2.getText();

        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("url", url);
        try {
            this.j_session.sendRequest("/feed/add", params, "POST", this.sess.getToken(), "http://www.mocky.io/v2/588a120f250000fc1aadcfc5");
            if (this.j_session.getResponseCode() == 201)
                javax.swing.JOptionPane.showMessageDialog(null,"Added");
            else
                javax.swing.JOptionPane.showMessageDialog(null,"Failed");
        } catch (JSONException ex) {
            Logger.getLogger(MyRSSFeedGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyRSSFeedGUI.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            this.updateRSSlist();
        } catch (JSONException ex) {
            Logger.getLogger(MyRSSFeedGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyRSSFeedGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.j_session.setMail("");
        this.j_session.setPassword("");
        this.j_session.setToken("");
        this.listRSS = null;
        this.dispose();
        new MyRSSFeedLoginHandler(this.j_session).setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
