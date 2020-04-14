package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by anton on 11.04.2020.
 */
public class Connector {
    private String login;
    private String password;
    private static String pars;
    private static String marker = "public";

    public Connector(String login, String password) {
        this.login = login;
        this.password = password;
        this.pars = login;
        this.marker = marker;
    }

    public Connector(){
        login =this.getLogin();
    }

    public String getMarker(){
        return marker;
    }

    public void setMarker(String value){
        this.marker = value;
    }

    public static String getPars(){ return pars;};

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static Connector fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, Connector.class);
    }

    public String send(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        String reply = "";
        try (OutputStream os = conn.getOutputStream();) {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
        try (InputStream is = conn.getInputStream()) {
            byte[] buffer = new byte[8192];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int received; (received = is.read(buffer)) != -1; ) {
                baos.write(buffer, 0, received);
            }
            reply = baos.toString("UTF-8");
        }catch (IOException i){
            System.out.println("You inputed invalid value.");
        }
        return reply.replace("\"","").replace("\r\n","");
    }
}


