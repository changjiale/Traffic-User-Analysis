package cn.tedu.web;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LogServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(LogServlet.class);;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String qs = URLDecoder.decode(request.getQueryString(),"utf-8");
		//System.out.println(qs);
		String[] kvs = qs.split("&");
		StringBuffer sb = new StringBuffer();
		for (String kv : kvs) {
			String [] arr = kv.split("=");
			sb.append((arr.length==2 ? arr[1]: "") + "|");
		}
		//String ip = request.getRemoteAddr();
		sb.append(request.getRemoteAddr());
		System.out.println(sb.toString());
		
		logger.info(sb.toString());;
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
