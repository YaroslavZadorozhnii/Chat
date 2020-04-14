package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by anton on 09.04.2020.
 */
@WebServlet(name = "Registration")
public class Registration extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        PrintWriter printWriter = response.getWriter();
        InputStream is = request.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;
        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);
        RegistrParser rp = new RegistrParser();
        String[] l = rp.parse(bos);

        System.out.println(Arrays.toString(l));
        File file = new File("C:\\Users\\anton\\Desktop\\base.txt");
        HashMap<String, String> base = new HashMap<>();
        String login = l[0];
        String password = l[1];
        try (Scanner scanner = new Scanner(file)) {
            for (; scanner.hasNext(); ) {
                String temp = scanner.nextLine();
                String[] array = temp.split("&");
                base.put(array[0], array[1]);
            }
        }
        if (login.indexOf("registration") == 0) {
            login = login.substring("registration".length());
            if (base.get(login) != null) {
                if (!base.get(login).equals(password)) {
                    System.out.println(">>>>" + rp.toJSON("ok"));
                    printWriter.println(gson.toJson("close"));
                }
            } else {
                try (FileWriter fileWriter = new FileWriter("C:\\Users\\anton\\Desktop\\base.txt", true)) {
                    fileWriter.write(login + "&" + password + "\n");
                    System.out.println(">>>>" + rp.toJSON("ok"));
                    printWriter.print(rp.toJSON("ok"));
                }

            }
        } else if (base.get(login).equals(password)) {
            System.out.println(">>>>" + rp.toJSON("ok"));
            printWriter.print(rp.toJSON("ok"));
        } else {
            System.out.println("no connect");
            printWriter.println(gson.toJson("error"));
        }
    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File("C:\\Users\\anton\\Desktop\\base.txt");
    HashMap<String, String> base = new HashMap<>();
    PrintWriter printWriter = response.getWriter();
    String login = request.getParameter("log").toString();
    String password = request.getParameter("pass").toString();
        try (Scanner scanner = new Scanner(file)) {
        for (; scanner.hasNext(); ) {
            String temp = scanner.nextLine();
            String[] array = temp.split("&");
            base.put(array[0],array[1]);
        }
        if (login.indexOf("registration") == 0) {
            login = login.substring("registration".length());
            if (base.get(login).equals(null)) {
                try (FileWriter fileWriter = new FileWriter("C:\\Users\\anton\\Desktop\\base.txt", true)) {
                    fileWriter.write(login + "&" + password + "\n");
                    printWriter.println("ok");
                }
            } else if (base.get(login).equals(password)) {
                printWriter.println("ok");
            } else if(!base.get(login).equals(password)){
                printWriter.println("close");
            }

        }
        if (base.get(login).equals(password)) {
            printWriter.println("ok");
        }else {
            printWriter.println("error");
        }
    }

}

}