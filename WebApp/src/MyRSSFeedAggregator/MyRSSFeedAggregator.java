package MyRSSFeedAggregator;

import java.io.IOException;

/**
 * Created by alex on 22/01/2017.
 */
public class MyRSSFeedAggregator {

    public static void main(String args[]) throws IOException {
        MyRSSFeedSessionHandler j_session = new MyRSSFeedSessionHandler("http://www.mocky.io/v2/5883b4490f00008d0b31c043");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RssAggregatorLogin(j_session).setVisible(true);
            }
        });
    }

}
