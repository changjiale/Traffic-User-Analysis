package zebra;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class ZebraMapper extends Mapper<LongWritable, Text, Text, HttpAppHost> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, HttpAppHost>.Context context)
			throws IOException, InterruptedException {
		//获取行数据  按照 | 
		String line = value.toString();
		String[] data = line.split("\\|");
		//获取用户名
		FileSplit fileSplit = (FileSplit) context.getInputSplit();
		String fileName = fileSplit.getPath().getName();
		//遍历
		HttpAppHost bean = new HttpAppHost();
		//创建小区id
		bean.setCellid(data[16]);
		//应用大类
		bean.setAppType(Integer.parseInt(data[22]));
		//小类
		bean.setAppSubType(Integer.parseInt(data[23]));
		
		bean.setUserIP(data[26]);
		bean.setUserPort(Integer.parseInt(data[28]));
		
		bean.setAppServerIP(data[30]);
		bean.setAppServerPort(Integer.parseInt(data[32]));
		bean.setHost(data[58]);
		
		//日志生成时间  --- 根据文件名称获取 
		bean.setReportTime(fileName.split("_")[1]);
		//获取请求状态码  103-请求成功 表示请求成功
		int appTypeCode = Integer.parseInt(data[18]);
		//传输状态码（事物相应码）
		String transStatus = data[54];
		
		if(bean.getCellid() == null || bean.getCellid().equals("")) {
			//如果小区id为null或者为空,当前字段补9个0
			bean.setCellid("000000000");
		}
		//当请求成功 -103   尝试次数为1
		if(appTypeCode == 103) {
			bean.setAttempts(1);
		}
		//当请求成功 -103   响应成功 次数设置为1
		if (appTypeCode==103
				&&"10,11,12,13,14,15,32,33,34,35,36,37,38,48,49,50,51,52,53,54,55,199,200,201,202,203,204,205,206,302,304,306".contains(transStatus)) {
			bean.setAccepts(1);
		}else {
			bean.setAccepts(0);
		}
		
		if(appTypeCode == 103) {
			//如果请求成功 --设置上行流量
			bean.setTrafficUL(Long.parseLong(data[33]));
			
			//如果请求成功， 设置下行流量 
			bean.setTrafficDL(Long.parseLong(data[34]));
			
			bean.setRetranUL(Long.parseLong(data[39]));
			bean.setRetranDL(Long.parseLong(data[40]));
			
			//获取传输总用时
			bean.setTransDelay(Long.parseLong(data[20])-Long.parseLong(data[19]));
		}
		
		//bean.setAttempts(Integer.parseInt(data[20]));
		//bean.setAccepts(Integer.parseInt(data[18]));
		//bean.setTrafficUL(Long.parseLong(data[33]));
		//bean.setTrafficDL(Long.parseLong(data[34]));
		//bean.setRetranUL(Long.parseLong(data[39]));
		//bean.setRetuanDL(Long.parseLong(data[40]));
		//bean.setTransDelay(Long.parseLong(data[54]));
		
		//进行字段结算， 进行封装
		String userKey = bean.getReportTime()+"|"+bean.getAppType()+"|"+bean.getAppSubType()
		+"|"+bean.getUserIP()+"|"+bean.getUserPort()+"|"+bean.getAppServerIP()+"|"+bean.getAppServerPort()
		+"|"+bean.getHost()+"|"+bean.getCellid();
		//String userKey = bean.toString();
		
		context.write(new Text(userKey), bean);
				
	}


	

}
