package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {
	private static String logg;
	private MessageList msgList = MessageList.getInstance();
	private static HashMap<String, String> publicList = new HashMap<>();
	private static String stat;


    public String getStat(){
    	return stat;
	}

	public void clearMap() {
		publicList.clear();
	}

	public static String getLogg() {
		return logg;
	}

	private void setLogg(String str) {
		this.logg = str;
	}

	public String fromList() {
		String result = "";
		for (String key : publicList.keySet()) {
			result += "> " + publicList.get(key) + "\n";
		}

		return result;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromStr = req.getParameter("from");
		String login = req.getParameter("login");
		String status = req.getParameter("status");
		stat = status;
		if (login.indexOf("registration") == 0) {
			login = login.substring("registration".length());
		}
		if (status.equals("public")) {
			publicList.put(login, login);
		}
		if (status.equals("private")) {
			publicList.remove(login);
		}
		TimeClass timeClass = new TimeClass();
		Thread thread = new Thread(timeClass);
		thread.start();
		setLogg(login);
		int from = 0;
		try {
			from = Integer.parseInt(fromStr);
			if (from < 0) from = 0;
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		resp.setContentType("application/json");

        /// send will be here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		String json = msgList.toJSON(from);
		System.out.println(json);
		if (json != null) {
			OutputStream os = resp.getOutputStream();
			byte[] buf = json.getBytes(StandardCharsets.UTF_8);
			os.write(buf);

			//PrintWriter pw = resp.getWriter();
			//pw.print(json);
		}
	}
}
