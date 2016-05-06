package classes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by admin on 06-05-2016.
 */
public class controller extends HttpServlet  {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //output plain text
        response.setContentType("text/plain");
        //get printwriter

            PrintWriter out = response.getWriter();

            //print parameters
        Enumeration params=request.getParameterNames();
        //output each parameter
        while(params.hasMoreElements()){
            String param=(String)params.nextElement();
            out.print(request.getParameter(param));
        }
    }
}
