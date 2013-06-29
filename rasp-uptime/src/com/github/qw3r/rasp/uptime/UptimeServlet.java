package com.github.qw3r.rasp.uptime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UptimeServlet
 */
@WebServlet("/")
public class UptimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UptimeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessBuilder builder = new ProcessBuilder("uptime");
    	Process ps = builder.start();
    	try {
			ps.waitFor();
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
	    	String uptime = reader.readLine();
	    	Writer out = response.getWriter();
	    	out.write("<html><body>"+uptime+"</body></html>");
	    	out.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
