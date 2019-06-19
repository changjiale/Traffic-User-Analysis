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

public class APP01Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//查询日期
			String dateStr = request.getParameter("date");
			System.out.println(dateStr);
			//查询类型
			int type = Integer.parseInt(request.getParameter("type"));
			String sql = null;
			String title = null;
			switch (type) {
				case 1://总流量
					title = "应用大类总流量top10";
					sql = "select " +
							"apptype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(totalTraffic) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";
					break;
				case 2://上行流量
					title = "应用大类上行总流量top10";
					sql = "select " +
							"apptype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficUL) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";
					break;
				case 3://下行流量
					title = "应用大类下行总流量top10";
					sql = "select " +
							"apptype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficDL) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";
					break;
				case 4://尝试次数
					title = "应用大类尝试次数top10";
					sql = "select " +
							"apptype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(attempts) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,dateid " +
							"having dateid ='"+dateStr+"' " +
							"order by su desc " +
							"limit 0,10";
					break;
				case 5://接受次数
					title = "应用大类接受次数top10";
					sql = "select " +
							"apptype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(accepts) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,dateid " +
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
			
			//处理结果 -- 图例
			StringBuffer legendBuf = new StringBuffer();
			//处理结果 -- 数据
			StringBuffer seriesBuf = new StringBuffer();
			
			int sum = 0;
			for(Map<String,Object> m : list){
				String app01Name = APP01.getName((Integer) m.get("apptype"));
				legendBuf.append("'"+app01Name+"',");
				
				int data = ((BigDecimal) m.get("su")).intValue();
				sum += data;
				seriesBuf.append("{value:"+data+", name:'"+app01Name+"'},");
			}
			String legend = "legend: {orient : 'vertical',x : 'left',data:["+(legendBuf.length() == 0 ? "" : legendBuf.substring(0, legendBuf.length()-1))+"]}";
			String series = "series : [{name:'应用大类',type:'pie',radius : '55%',center: ['50%', '60%'],data:["+(seriesBuf.length() == 0 ? "" : seriesBuf.substring(0,seriesBuf.length()-1))+"]}]";
		  
			
			//整合
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("date", dateStr);
			resultMap.put("title", 	"title : {text: '"+title+"',subtext: '',x:'center'}");
			resultMap.put("legend", legend);
			resultMap.put("series", series);
			resultMap.put("sum", sum*0.75+"");
			//转发
			request.setAttribute("map", resultMap);
			request.getRequestDispatcher("/app01.jsp").forward(request,response);
			
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
