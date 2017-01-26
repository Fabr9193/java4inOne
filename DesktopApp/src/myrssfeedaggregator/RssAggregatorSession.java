/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myrssfeedaggregator;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;
import org.json.*;
import java.lang.Object;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author fr0g
 */
public class RssAggregatorSession {
    private String mail;
    private String token;
    private String password;
    private String api;
    private JSONArray response;
    private int response_code;
    private Boolean debug;
    
    public RssAggregatorSession(String api_url) throws MalformedURLException, IOException{
        this.token = null;
        this.api = api_url;
        this.debug = true;
    }
    
    public void sendRequest(String route, Map<String, String> params, String method, String cookie,
            String urldebug) throws ProtocolException, JSONException, IOException{
        if (this.debug)
            this.api = urldebug;
                
        URL url = new URL(this.api+route);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");
        if (cookie.length() > 0)
            con.setRequestProperty("Cookie","token="+this.getToken());
        if (!"GET".equals(method))
        {
            JSONObject data = new JSONObject();
        
            for (Map.Entry<String, String> entry : params.entrySet())
                data.put(entry.getKey(), entry.getValue());
        
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(data.toString());
                wr.flush();
            }
        }
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer resp = new StringBuffer();
        while ((output = in.readLine()) != null) { resp.append(output); }

        String data = null;
        if (resp.toString().length() == 0){
            this.response_code = responseCode;
            return; 
        }
        else if (resp.toString().charAt(0) == '[')
            data = resp.toString();
        else if (resp.toString().length() > 0)
            data = "["+resp.toString()+"]";
        this.response = new JSONArray(data);
        this.response_code = responseCode;
    }
    public String getMail(){
        return this.mail;
    }

    public String getToken(){
        return this.token;
    }
    
    public String getPassword(){
        return this.password;
    }

    public JSONArray getResponse(){
        return this.response;
    }
    
    public int getResponseCode(){
        return this.response_code;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public int Login(){
     try {   
            Map<String, String> params = new HashMap<>();
            params.put("mail", this.getMail());
            params.put("password", this.getPassword());
            this.sendRequest("/auth", params, "POST", "","http://www.mocky.io/v2/5883b4490f00008d0b31c043");
            
            if (this.getResponse().getJSONObject(0).has("token") && this.getResponseCode() == 200)
            {
                this.setToken(this.getResponse().getJSONObject(0).getString("token"));
                return this.getResponseCode();
            }
            return this.getResponseCode();
                
        } catch (ProtocolException | JSONException ex) {
            Logger.getLogger(RssAggregatorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RssAggregatorLogin.class.getName()).log(Level.SEVERE, null, ex);
        }    
     return (-1);
    }
    
    public int Register() throws JSONException, IOException
    {
        Map<String, String> params = new HashMap<>();
        params.put("mail", this.getMail());
        params.put("password", this.getPassword());
        this.sendRequest("/register", params, "POST", "","http://www.mocky.io/v2/588a188e3000009600fa8b69");
        
        if (this.getResponse().getJSONObject(0).has("message") && this.getResponseCode() == 201)
        {
            this.Login();
            return this.getResponseCode();
        }
        return (-1);
    }
}
