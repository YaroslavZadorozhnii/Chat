package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by anton on 11.04.2020.
 */
public class RegistrParser {
    private String login;
    private String password;
    private String marker;


    public RegistrParser(){

    }
    public String[] parse(ByteArrayOutputStream bos) throws IOException {

        String some = new String(bos.toByteArray(), StandardCharsets.UTF_8);
        System.out.println(some);
        Gson g = new Gson();
        RegistrParser reg = new RegistrParser();
        reg = g.fromJson(some, RegistrParser.class);
        String[] array = new String[]{reg.getLogin(), reg.getPassword(), reg.getMarker()};
        return array;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public String toJSON( String parsel) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(parsel);
    }

    private class GS{
       private String parsel;
    }
}
