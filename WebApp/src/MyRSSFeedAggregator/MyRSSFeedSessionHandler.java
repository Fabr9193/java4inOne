package MyRSSFeedAggregator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.lang.Object;

/**
 * Created by alex on 26/01/2017.
 */
public class MyRSSFeedSessionHandler {

    // Variable definitions
    private String j_api;
    private String j_token;
    private String u_email;
    private String u_pwd;

    private int response_code;
    private JSONArray js_response;

    private Boolean debug;

    // Session Constructor ; receiving api url

    public MyRSSFeedSessionHandler(String api_url) throws IOException {
        this.j_api = api_url;
        this.j_token = null;
        this.j_debug = false;
    }

    // Getters

    public String getMail(){
        return this.u_email;
    }

    public String getToken(){
        return this.j_token;
    }

    public String getPassword(){
        return this.u_pwd;
    }

    public JSONArray getResponse(){
        return this.js_response;
    }

    public int getResponseCode(){
        return this.response_code;
    }

    // Setters

    public void setMail(String mail){
        this.u_email = mail;
    }

    public void setToken(String token){
        this.j_token = token;
    }

    public void setPassword(char[] password){
        this.u_pwd = password;
    }

    // Comm function: Sending request

    public void sendRequest(String route, Map<String, String> params, String method, String cookie,
                            String urldebug) throws JSONException, IOException {
        if (this.j_debug)
            this.j_api = urldebug;

        URL url = new URL(this.j_api + route);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");
        if (cookie.length() > 0)
            con.setRequestProperty("Cookie","token="+this.getToken());
        if (!"GET".equals(method))
        {
            JSONObject js_data = new JSONObject();

            for (Map.Entry<String, String> entry : params.entrySet())
                js_data.put(entry.getKey(), entry.getValue());

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(js_data.toString());
                wr.flush();
            }
        }
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer resp = new StringBuffer();
        while ((output = in.readLine()) != null) { resp.append(output); }

        String js_data = null;
        if (resp.toString().length() == 0){
            this.response_code = responseCode;
            return;
        }
        else if (resp.toString().charAt(0) == '[')
            js_data = resp.toString();
        else if (resp.toString().length() > 0)
            js_data = "["+resp.toString()+"]";
        this.js_response = new JSONArray(js_data);
        this.response_code = responseCode;
    }
// Registration function: register route

    public int Register() throws JSONException, IOException {
        Map<String, String> params = new HashMap<>();
        params.put("mail", this.getMail());
        params.put("password", this.getPassword());

        Validate.notNull(params, "The email or password cannot be null");

        this.sendRequest("/register", params, "POST", "","http://www.mocky.io/v2/588a188e3000009600fa8b69");

        if (this.getResponse().getJSONObject(0).has("message") && this.getResponseCode() == 201)
        {
            this.Login();
            return this.getResponseCode();
        }
        return (-1);
    }

    // Login function: authentication route

    public int Login() {
        try {
            Map<String, String> params = new HashMap<>();

            params.put("mail", this.getMail());
            params.put("password", this.getPassword());

            Validate.notNull(params, "The email or password cannot be null");

            this.sendRequest("/auth", params, "POST", "","http://www.mocky.io/v2/5883b4490f00008d0b31c043");

            if (this.getResponse().getJSONObject(0).has("token") && this.getResponseCode() == 200)
            {
                this.setToken(this.getResponse().getJSONObject(0).getString("token"));
                return this.getResponseCode();
            }
            return this.getResponseCode();

        } catch (ProtocolException | JSONException ex) {
            Logger.getLogger(RssAggregatorLogin.class.getName()).log(Level.SEVERE, "Protocol Exception", ex);
        } catch (IOException ex) {
            Logger.getLogger(RssAggregatorLogin.class.getName()).log(Level.SEVERE, "IOException", ex);
        }
        return (-1);
    }
}
