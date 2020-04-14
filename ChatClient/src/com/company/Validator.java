package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by anton on 10.04.2020.
 */
public class Validator {
    private String log;
    private String pass;

    public String getLog() {
        return log;
    }

    public boolean validation() {
        Scanner scanner = new Scanner(System.in);
        boolean check = false;
        for (; ; ) {
            System.out.println("Input your login or input \">registration\"");
            log = scanner.nextLine();
            if (!">registration".equals(log)) {
                System.out.println("Input your password");
                pass = scanner.nextLine();
            } else {
                System.out.println("Meke app some login");
                log = "registration" + scanner.nextLine();
                System.out.println("Meke app some password");
                pass = scanner.nextLine();
            }
            try {
                Connector connector = new Connector(log, pass);
                String result = connector.send(new Utils().getURL() + "/registr");
                if (result.length() > 0) {
                    if ("ok".equals(result)) {
                        check = true;
                        break;
                    } else if ("close".equals(result)) {
                        System.out.println("A user with the same name already exists.");
                    } else if ("error".equals(result)) {
                        System.out.println("This password is invalid.");
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return check;
    }
}
