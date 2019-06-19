package cn.zebra.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.zebra.data.APP01;
import cn.zebra.utils.DBUtils;

public class APP03Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//查询日期
			String dateStr = request.getParameter("date");
			//查询类型
			int type = Integer.parseInt(request.getParameter("type"));

			String sql = null;
			String title = null;
			switch (type) {
				case 1://尝试次数
					title = "网站表现尝试次数top10";
					sql = "select " +
							"host,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(attempts) su " +
							"from D_H_HTTP_HOST " +
							"group by host,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";	
					break;
				case 2://接受次数
					title = "网站表现接受次数top10";
					sql = "select " +
							"host,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(accepts) su " +
							"from D_H_HTTP_HOST " +
							"group by host,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";	
					break;
				case 3://总流量
					title = "网站总流量top10";
					sql = "select " +
							"host,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(totaltraffic) su " +
							"from D_H_HTTP_HOST " +
							"group by host,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";	
					break;
				case 4://上行流量
					title = "网站上行流量top10";
					sql = "select " +
							"host,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficUL) su " +
							"from D_H_HTTP_HOST " +
							"group by host,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";	
					break;
				case 5://上行流量
					title = "网站上行流量top10";
					sql = "select " +
							"host,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficDL) su " +
							"from D_H_HTTP_HOST " +
							"group by host,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";	
					break;

				default:
					throw new RuntimeException("未知操作码");
			}
			
			//查询数据库
			QueryRunner runner = new QueryRunner(DBUtils.getSource());
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler());
			
			//处理结果 -- 横坐标
			StringBuffer xAxisBuf = new StringBuffer();
			
			//处理结果 -- 数据
			StringBuffer seriesBuf = new StringBuffer();
			
			for(Map<String,Object> m : list){
				String hostName = (String) m.get("host");
				xAxisBuf.append("'"+hostName+"',");
				
				int data = ((BigDecimal) m.get("su")).intValue();
				seriesBuf.append("{value:"+data+", name:'"+hostName+"'},");
			}
			String xAxis = "xAxis: {data:["+(xAxisBuf.length() == 0 ? "" : xAxisBuf.substring(0, xAxisBuf.length()-1))+"]}";
			String series = "series : [{name:'网站表现',type:'bar',data:["+(seriesBuf.length() == 0 ? "" : seriesBuf.substring(0,seriesBuf.length()-1))+"]}]";
		  
			
			//整合
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("date", dateStr);
			resultMap.put("title", 	"title : {text: '"+title+"',subtext: '',x:'center'}");
			resultMap.put("xAxis", xAxis);
			resultMap.put("series", series);
			//转发
			request.setAttribute("map", resultMap);
			request.getRequestDispatcher("/app03.jsp").forward(request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
