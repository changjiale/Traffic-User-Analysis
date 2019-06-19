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
import cn.zebra.data.APP02;
import cn.zebra.utils.DBUtils;

public class APP04Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String hostname = new String(request.getParameter("hostname").getBytes("iso8859-1"),"utf-8");
		String dateStr = request.getParameter("date");
		int type = Integer.parseInt(request.getParameter("type"));
		
		try {

			String sql = null;
			String title = null;
			switch (type) {
				case 1://���Դ���
					title = hostname+"��վ���Դ���top10";
					sql = "select " +
							"host,DATE_FORMAT(hourid,'%H') hour,sum(attempts) su " +
							"from D_H_HTTP_HOST " +
							"group by host,hour " +
							"having host ='"+ hostname +"'" +
							"order by su desc ";
					break;
//				case 2://��������
//					title = app01name+"������������top10";
//					sql = "select " +
//							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficUL) su " +
//							"from D_H_HTTP_APPTYPE " +
//							"group by apptype,appsubtype,dateid " +
//							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
//							"order by su desc " +
//							"limit 0,10";
//					break;
//				case 3://��������
//					title = app01name+"������������top10";
//					sql = "select " +
//							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficDL) su " +
//							"from D_H_HTTP_APPTYPE " +
//							"group by apptype,appsubtype,dateid " +
//							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
//							"order by su desc " +
//							"limit 0,10";
//					break;
//				case 4://���Դ���
//					title = app01name+"�ೢ�Դ���top10";
//					sql = "select " +
//							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(attempts) su " +
//							"from D_H_HTTP_APPTYPE " +
//							"group by apptype,appsubtype,dateid " +
//							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
//							"order by su desc " +
//							"limit 0,10";
//					break;
//				case 5://���ܴ���
//					title = app01name+"����ܴ���top10";
//					sql = "select " +
//							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(accepts) su " +
//							"from D_H_HTTP_APPTYPE " +
//							"group by apptype,appsubtype,dateid " +
//							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
//							"order by su desc " +
//							"limit 0,10";
//					break;
//	
				default:
					throw new RuntimeException("δ֪������");
			}
			
			//��ѯ���ݿ�
			QueryRunner runner = new QueryRunner(DBUtils.getSource());
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler());
			
			//������ -- ����
			StringBuffer xAxisBuf = new StringBuffer();
			//������ -- ����
			StringBuffer seriesBuf = new StringBuffer();
			
			int sum = 0;
			for(Map<String,Object> m : list){
				String hour = (String) m.get("hour");
				xAxisBuf.append("'"+hour+"',");
				
				int data = ((BigDecimal) m.get("su")).intValue();
				seriesBuf.append("{value:"+data+", name:'"+hour+"'},");
			}
			String xAxis = "xAxis: {data:["+(xAxisBuf.length() == 0 ? "" : xAxisBuf.substring(0, xAxisBuf.length()-1))+"]}";
			String series = "series : [{name:'"+hostname+"��վ',type:'line',data:["+(seriesBuf.length() == 0 ? "" : seriesBuf.substring(0,seriesBuf.length()-1))+"]}]";
		  
			
			//����
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("date", dateStr);
			resultMap.put("title", 	"title : {text: '"+title+"',subtext: '',x:'center'}");
			resultMap.put("xAxis", xAxis);
			resultMap.put("series", series);
			//ת��
			request.setAttribute("map", resultMap);
			request.getRequestDispatcher("/app04.jsp").forward(request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
