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

public class APP02Servlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String app01name = new String(request.getParameter("app01name").getBytes("iso8859-1"),"utf-8");
		int app01id = APP01.getId(app01name);
		String dateStr = request.getParameter("date");
		int type = Integer.parseInt(request.getParameter("type"));
		
		try {

			String sql = null;
			String title = null;
			switch (type) {
				case 1://������
					title = app01name+"��������top10";
					sql = "select " +
							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(totalTraffic) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,appsubtype,dateid " +
							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
							"order by su desc " +
							"limit 0,10";
					break;
				case 2://��������
					title = app01name+"������������top10";
					sql = "select " +
							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficUL) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,appsubtype,dateid " +
							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
							"order by su desc " +
							"limit 0,10";
					break;
				case 3://��������
					title = app01name+"������������top10";
					sql = "select " +
							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(trafficDL) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,appsubtype,dateid " +
							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
							"order by su desc " +
							"limit 0,10";
					break;
				case 4://���Դ���
					title = app01name+"�ೢ�Դ���top10";
					sql = "select " +
							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(attempts) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,appsubtype,dateid " +
							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
							"order by su desc " +
							"limit 0,10";
					break;
				case 5://���ܴ���
					title = app01name+"����ܴ���top10";
					sql = "select " +
							"apptype,appsubtype,DATE_FORMAT(hourid,'%Y-%m-%d') dateid,sum(accepts) su " +
							"from D_H_HTTP_APPTYPE " +
							"group by apptype,appsubtype,dateid " +
							"having dateid ='"+dateStr+"' and apptype= '"+app01id+"'" +
							"order by su desc " +
							"limit 0,10";
					break;
	
				default:
					throw new RuntimeException("δ֪������");
			}
			
			//��ѯ���ݿ�
			QueryRunner runner = new QueryRunner(DBUtils.getSource());
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler());
			
			//������ -- ͼ��
			StringBuffer legendBuf = new StringBuffer();
			//������ -- ����
			StringBuffer seriesBuf = new StringBuffer();
			
			int sum = 0;
			for(Map<String,Object> m : list){
				String app02Name = APP02.getName((Integer) m.get("appsubtype"));
				legendBuf.append("'"+app02Name+"',");
				
				int data = ((BigDecimal) m.get("su")).intValue();
				sum += data;
				seriesBuf.append("{value:"+data+", name:'"+app02Name+"'},");
			}
			String legend = "legend: {orient : 'vertical',x : 'left',data:["+(legendBuf.length() == 0 ? "" : legendBuf.substring(0, legendBuf.length()-1))+"]}";
			String series = "series : [{name:'"+app01name+"��',type:'pie',radius : '55%',center: ['50%', '60%'],data:["+(seriesBuf.length() == 0 ? "" : seriesBuf.substring(0,seriesBuf.length()-1))+"]}]";
		  
			
			//����
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("date", dateStr);
			resultMap.put("title", 	"title : {text: '"+title+"',subtext: '',x:'center'}");
			resultMap.put("legend", legend);
			resultMap.put("series", series);
			resultMap.put("sum", sum*0.75+"");
			//ת��
			request.setAttribute("map", resultMap);
			request.getRequestDispatcher("/app02.jsp").forward(request,response);
			
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
