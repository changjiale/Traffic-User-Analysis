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
		//��ȡ������  ���� | 
		String line = value.toString();
		String[] data = line.split("\\|");
		//��ȡ�û���
		FileSplit fileSplit = (FileSplit) context.getInputSplit();
		String fileName = fileSplit.getPath().getName();
		//����
		HttpAppHost bean = new HttpAppHost();
		//����С��id
		bean.setCellid(data[16]);
		//Ӧ�ô���
		bean.setAppType(Integer.parseInt(data[22]));
		//С��
		bean.setAppSubType(Integer.parseInt(data[23]));
		
		bean.setUserIP(data[26]);
		bean.setUserPort(Integer.parseInt(data[28]));
		
		bean.setAppServerIP(data[30]);
		bean.setAppServerPort(Integer.parseInt(data[32]));
		bean.setHost(data[58]);
		
		//��־����ʱ��  --- �����ļ����ƻ�ȡ 
		bean.setReportTime(fileName.split("_")[1]);
		//��ȡ����״̬��  103-����ɹ� ��ʾ����ɹ�
		int appTypeCode = Integer.parseInt(data[18]);
		//����״̬�루������Ӧ�룩
		String transStatus = data[54];
		
		if(bean.getCellid() == null || bean.getCellid().equals("")) {
			//���С��idΪnull����Ϊ��,��ǰ�ֶβ�9��0
			bean.setCellid("000000000");
		}
		//������ɹ� -103   ���Դ���Ϊ1
		if(appTypeCode == 103) {
			bean.setAttempts(1);
		}
		//������ɹ� -103   ��Ӧ�ɹ� ��������Ϊ1
		if (appTypeCode==103
				&&"10,11,12,13,14,15,32,33,34,35,36,37,38,48,49,50,51,52,53,54,55,199,200,201,202,203,204,205,206,302,304,306".contains(transStatus)) {
			bean.setAccepts(1);
		}else {
			bean.setAccepts(0);
		}
		
		if(appTypeCode == 103) {
			//�������ɹ� --������������
			bean.setTrafficUL(Long.parseLong(data[33]));
			
			//�������ɹ��� ������������ 
			bean.setTrafficDL(Long.parseLong(data[34]));
			
			bean.setRetranUL(Long.parseLong(data[39]));
			bean.setRetranDL(Long.parseLong(data[40]));
			
			//��ȡ��������ʱ
			bean.setTransDelay(Long.parseLong(data[20])-Long.parseLong(data[19]));
		}
		
		//bean.setAttempts(Integer.parseInt(data[20]));
		//bean.setAccepts(Integer.parseInt(data[18]));
		//bean.setTrafficUL(Long.parseLong(data[33]));
		//bean.setTrafficDL(Long.parseLong(data[34]));
		//bean.setRetranUL(Long.parseLong(data[39]));
		//bean.setRetuanDL(Long.parseLong(data[40]));
		//bean.setTransDelay(Long.parseLong(data[54]));
		
		//�����ֶν��㣬 ���з�װ
		String userKey = bean.getReportTime()+"|"+bean.getAppType()+"|"+bean.getAppSubType()
		+"|"+bean.getUserIP()+"|"+bean.getUserPort()+"|"+bean.getAppServerIP()+"|"+bean.getAppServerPort()
		+"|"+bean.getHost()+"|"+bean.getCellid();
		//String userKey = bean.toString();
		
		context.write(new Text(userKey), bean);
				
	}


	

}
