package cn.zebra.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import cn.zebra.data.APP01;
import cn.zebra.utils.DBUtils;

public class MonitorServlet extends HttpServlet {

	@SuppressWarnings("unused")
	public void doGet(HttpServletRequest request, final HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		
		final CountDownLatch cdl = new CountDownLatch(1);
		ZooKeeper zk =null;
		try {
			if(zk == null){
				zk = new ZooKeeper("192.168.164.129:2181", 2000, new Watcher() {
					public void process(WatchedEvent event) {
						cdl.countDown();
					}
				});
			}
			cdl.await();
			
			List<String> list1 = zk.getChildren("/zebra/engin1", null);
			List<String> list2 = zk.getChildren("/zebra/engin2", null);
			List<String> list = new ArrayList<String>();
			list.addAll(list1);
			list.addAll(list2);
			String s = "";
			for(String str : list){
				s += str;
				s += ",";
			}
			String st = s.substring(0, s.length()-2);
			if(list1!=null && list2 != null){
				resp.getWriter().println(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
