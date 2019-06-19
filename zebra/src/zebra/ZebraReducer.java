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
		
		//���ݵ�������
		Iterator<HttpAppHost> it = values.iterator();
		if (it.hasNext()) {
			//���Ի�ȡ
			HttpAppHost bean = it.next();
			while(it.hasNext()) {
				HttpAppHost otherBean = it.next();
				//�ۼ��ܵĳ����������
				bean.setAttempts(otherBean.getAttempts() + bean.getAttempts());
				//�ۼ��ܵĽ��ܴ���
				bean.setAccepts(otherBean.getAccepts() + bean.getAccepts());
				//�ۼ��ܵ���������
				bean.setTrafficUL(otherBean.getTrafficUL() + bean.getTrafficUL());
				//�ۼ��ܵ��������� 
				bean.setTrafficDL(otherBean.getTrafficDL() + bean.getTrafficDL());
				//�ۼ��ش�����
				bean.setRetranUL(otherBean.getRetranUL() + bean.getRetranUL());
				bean.setRetranDL(otherBean.getRetranDL() + bean.getRetranDL());
				
				//�ۼ�����ʱ
				bean.setTransDelay(otherBean.getTransDelay() + bean.getTransDelay());
				
				
			}
			
			//һЩ�������ֶν��з�װ 
			String time = bean.getReportTime();
			time = time.substring(0,4) + "-" + time.substring(4, 6) + "-" 
					+ time.subSequence(6, 8) + " " + time.substring(8, 10)+":"
					+ time.substring(10, 12) + ":" + time.substring(12, 14);
			
			String result = time+"|"+ bean.getCellid() +"|"+ bean.getAppType()+"|"
					+bean.getAppSubType() + "|" +bean.getUserIP() +"|" +bean.getUserPort()+"|"
					+bean.getAppServerIP()+"|"+bean.getAppServerPort()+"|"+bean.getHost()+"|"
					+bean.getAttempts()+"|"+bean.getAccepts()+"|"+bean.getTrafficUL()+"|"+bean.getTrafficDL()+"|"
					+bean.getRetranUL()+"|"+bean.getTrafficDL()+"|"+bean.getTransDelay();
			
			//�������
			context.write(new Text(result), NullWritable.get());
			
		}
	}

	

}


