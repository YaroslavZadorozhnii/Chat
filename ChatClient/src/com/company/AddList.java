package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by anton on 13.04.2020.
 */
public class AddList implements Runnable {

public AddList(){}

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(Utils.getURL() + "/get-my-list");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
           /* try (OutputStream os = conn.getOutputStream();) {
                Gson gson = new GsonBuilder().create();
                String time = gson.toJson("?");
                os.write(time.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            String reply = "";
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
            conn.disconnect();
            System.out.println(reply);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
