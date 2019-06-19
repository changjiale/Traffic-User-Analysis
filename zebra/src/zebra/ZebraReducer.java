package zebra;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ZebraReducer extends Reducer<Text, HttpAppHost, Text, NullWritable> {

	@Override
	protected void reduce(Text key, Iterable<HttpAppHost> values,
			Reducer<Text, HttpAppHost, Text, NullWritable>.Context context) throws IOException, InterruptedException {
		
		//数据迭代遍历
		Iterator<HttpAppHost> it = values.iterator();
		if (it.hasNext()) {
			//属性获取
			HttpAppHost bean = it.next();
			while(it.hasNext()) {
				HttpAppHost otherBean = it.next();
				//累加总的尝试请求次数
				bean.setAttempts(otherBean.getAttempts() + bean.getAttempts());
				//累加总的接受次数
				bean.setAccepts(otherBean.getAccepts() + bean.getAccepts());
				//累加总的上行流量
				bean.setTrafficUL(otherBean.getTrafficUL() + bean.getTrafficUL());
				//累加总的下行流量 
				bean.setTrafficDL(otherBean.getTrafficDL() + bean.getTrafficDL());
				//累加重传上行
				bean.setRetranUL(otherBean.getRetranUL() + bean.getRetranUL());
				bean.setRetranDL(otherBean.getRetranDL() + bean.getRetranDL());
				
				//累加总用时
				bean.setTransDelay(otherBean.getTransDelay() + bean.getTransDelay());
				
				
			}
			
			//一些基本的字段进行封装 
			String time = bean.getReportTime();
			time = time.substring(0,4) + "-" + time.substring(4, 6) + "-" 
					+ time.subSequence(6, 8) + " " + time.substring(8, 10)+":"
					+ time.substring(10, 12) + ":" + time.substring(12, 14);
			
			String result = time+"|"+ bean.getCellid() +"|"+ bean.getAppType()+"|"
					+bean.getAppSubType() + "|" +bean.getUserIP() +"|" +bean.getUserPort()+"|"
					+bean.getAppServerIP()+"|"+bean.getAppServerPort()+"|"+bean.getHost()+"|"
					+bean.getAttempts()+"|"+bean.getAccepts()+"|"+bean.getTrafficUL()+"|"+bean.getTrafficDL()+"|"
					+bean.getRetranUL()+"|"+bean.getTrafficDL()+"|"+bean.getTransDelay();
			
			//输出数据
			context.write(new Text(result), NullWritable.get());
			
		}
	}

	

}


