
package myrssfeedaggregator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyRssFeedAggregator {
    
    public static void main(String args[]) throws IOException {
        RssAggregatorSession sess = new RssAggregatorSession("http://www.mocky.io/v2/5883b4490f00008d0b31c043");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RssAggregatorLogin(sess).setVisible(true);
            }
        });
    }
}