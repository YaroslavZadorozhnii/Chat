package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Message {
	private Date date = new Date();
	private String from;
	private String to;
	private String text;
    private static String status = "public";
    private String marker;
    private String forr;

	public Message(String from, String text, String marker) {
		this.from = from;
		this.text = text;
		this.marker = marker;
	}
	public Message(){};
    ///////////////////////////
	public Message(String from, String text, String to, String marker) {
		this.from = from;
		this.text = text;
		this.to = to;
		this.marker = marker;

	}
	public Message(String from, String text, String to, String marker, String forr) {
		this.from = from;
		this.text = text;
		this.to = to;
		this.marker = marker;
		this.forr = forr;

	}

	public String toJSON() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
	public static Message fromJSON(String s) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(s, Message.class);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date)
				.append(", From: ").append(from).append(", To: ").append(to).append(", Room: ").append(forr)
				.append("] ").append("<<").append(marker).append(">> ").append(text)
                .toString();
	}

	public int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
	
		OutputStream os = conn.getOutputStream();
		try {
			String json = toJSON();
			os.write(json.getBytes(StandardCharsets.UTF_8));
			return conn.getResponseCode();
		} finally {
			os.close();
		}
	}
	public void setStatus(String status){
		this.status = status;
	}

	public String getForr() {
		return forr;
	}

	public void setForr(String forr) {
		this.forr = forr;
	}

	public String getStatus() {
		return status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrom() {return from; }

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
