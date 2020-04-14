package com.company;



import java.io.*;

import java.util.Arrays;
import java.util.Scanner;



public class Main {
	public static void main(String[] args)  {
		Scanner scanner = new Scanner(System.in);
		Validator validator = new Validator();
		if (validator.validation()) {
			try {
				String name = validator.getLog();
				if(name.indexOf("registration") == 0){
					name = name.substring("registration".length());
				}
				String login = name;

				Thread th = new Thread(new GetThread());
				th.setDaemon(true);
				th.start();

				System.out.println("Enter your message: ");
				while (true) {
					int res = 0;
					String text = scanner.nextLine();
					if (text.isEmpty()) {
						break;
					}
					if(text.charAt(0) == '@'){
						String[] array = text.split("@");
						Message m = new Message();
						m = new Message(login, array[2], array[1], m.getStatus());
						res = m.send(Utils.getURL() + "/add");
						continue;
					}if(text.equals(">private")){
                        Message con = new Message();
                        con.setStatus("private");
                        continue;
					}if(text.equals(">public")) {
						Message con = new Message();
						con.setStatus("public");
						continue;
					} if(text.equals(">getList")){
						AddList list = new AddList();
						Thread th1 = new Thread(list);
						th1.start();
						continue;
					}
						else {

						Message m = new Message();
						if(m.getStatus().equals("private")){
							m = new Message(login,text,"private");
						}else {
							m = new Message(login,text,"public");
						}
						res = m.send(Utils.getURL() + "/add");
					}
					if (res != 200) { // 200 OK
						System.out.println("HTTP error occured: " + res);
						return;
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				scanner.close();
			}
		}
	}
}