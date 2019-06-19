package zebra;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class HttpAppHost implements Writable{
	
	
	private String reportTime;
	private String cellid;
	//应用大类和小类 
	private int appType;
	private int appSubType;
	
	private String userIP; //用户ip
	private int userPort;  //端口
	
	private String appServerIP; //服务器端ip
	private int appServerPort;
	private String host; //域名
	
	private int attempts;  //请求次数
	private int accepts;  //相应次数
	
	private long trafficUL;  //上行流量
	private long trafficDL; //下行流量 
	
	private long retranUL;  //重传上行
	private long retranDL; //重传下行
	
	private long transDelay;  //传输延迟
	
	
	
	public HttpAppHost() {
	}

	public HttpAppHost(String reportTime, String cellid, int appType, int appSubType, String userIP, int userPort,
			String appServerIP, int appServerPort, String host, int attempts, int accepts, long trafficUL,
			long trafficDL, long retranUL, long retuanDL, long transDelay) {
		this.reportTime = reportTime;
		this.cellid = cellid;
		this.appType = appType;
		this.appSubType = appSubType;
		this.userIP = userIP;
		this.userPort = userPort;
		this.appServerIP = appServerIP;
		this.appServerPort = appServerPort;
		this.host = host;
		this.attempts = attempts;
		this.accepts = accepts;
		this.trafficUL = trafficUL;
		this.trafficDL = trafficDL;
		this.retranUL = retranUL;
		this.retranDL = retranDL;
		this.transDelay = transDelay;
	}
	

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getAppSubType() {
		return appSubType;
	}

	public void setAppSubType(int appSubType) {
		this.appSubType = appSubType;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public int getUserPort() {
		return userPort;
	}

	public void setUserPort(int userPort) {
		this.userPort = userPort;
	}

	public String getAppServerIP() {
		return appServerIP;
	}

	public void setAppServerIP(String appServerIP) {
		this.appServerIP = appServerIP;
	}

	public int getAppServerPort() {
		return appServerPort;
	}

	public void setAppServerPort(int appServerPort) {
		this.appServerPort = appServerPort;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getAccepts() {
		return accepts;
	}

	public void setAccepts(int accepts) {
		this.accepts = accepts;
	}

	public long getTrafficUL() {
		return trafficUL;
	}

	public void setTrafficUL(long trafficUL) {
		this.trafficUL = trafficUL;
	}

	public long getTrafficDL() {
		return trafficDL;
	}

	public void setTrafficDL(long trafficDL) {
		this.trafficDL = trafficDL;
	}

	public long getRetranUL() {
		return retranUL;
	}

	public void setRetranUL(long retranUL) {
		this.retranUL = retranUL;
	}

	public long getRetranDL() {
		return retranDL;
	}

	public void setRetranDL(long retuanDL) {
		this.retranDL = retranDL;
	}

	public long getTransDelay() {
		return transDelay;
	}

	public void setTransDelay(long transDelay) {
		this.transDelay = transDelay;
	}

	@Override
	public String toString() {
		return "HttpAppHost [reportTime=" + reportTime + ", cellid=" + cellid + ", appType=" + appType + ", appSubType="
				+ appSubType + ", userIP=" + userIP + ", userPort=" + userPort + ", appServerIP=" + appServerIP
				+ ", appServerPort=" + appServerPort + ", host=" + host + ", attempts=" + attempts + ", accepts="
				+ accepts + ", trafficUL=" + trafficUL + ", trafficDL=" + trafficDL + ", retranUL=" + retranUL
				+ ", retuanDL=" + retranDL + ", transDelay=" + transDelay + "]";
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.reportTime = in.readUTF();
		this.cellid = in.readUTF();
		this.appType = in.readInt();
		this.appSubType = in.readInt();
		this.userIP = in.readUTF();
		this.userPort = in.readInt();
		this.appServerIP = in.readUTF();
		this.appServerPort = in.readInt();
		this.host = in.readUTF();
		this.attempts = in.readInt();
		this.accepts = in.readInt();
		this.trafficUL = in.readLong();
		this.trafficDL = in.readLong();
		this.retranUL = in.readLong();
		this.retranDL = in.readLong();
		this.transDelay = in.readLong();
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(reportTime);
		out.writeUTF(cellid);
		out.writeInt(appType);
		out.writeInt(appSubType);
		out.writeUTF(userIP);
		out.writeInt(userPort);
		out.writeUTF(appServerIP);
		out.writeInt(appServerPort);
		out.writeUTF(host);
		out.writeInt(attempts);
		out.writeInt(accepts);
		out.writeLong(trafficUL);
		out.writeLong(trafficDL);
		out.writeLong(retranUL);
		out.writeLong(trafficDL);
		out.writeLong(transDelay);
		
	}
	
	
	
	
	
	
	

}
