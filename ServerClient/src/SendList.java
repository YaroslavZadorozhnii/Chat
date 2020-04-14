import ua.kiev.prog.GetListServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by anton on 13.04.2020.
 */
@WebServlet(name = "SendList")
public class SendList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        GetListServlet gls = new GetListServlet();
        System.out.println(gls.fromList());
        pw.print("List" + "\n" + gls.fromList());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        GetListServlet gls = new GetListServlet();
        System.out.println(gls.fromList());
        pw.print("List" + "\n" + gls.fromList());
    }
}
