package cn.zebra.data;

import java.util.HashMap;
import java.util.Map;

public class APP01 {
	private static Map<Integer,String> app01Map = new HashMap<Integer,String>();

	private APP01() {
	}

	static{
		app01Map.put(1, "即时通信");
		app01Map.put(2, "阅读");
		app01Map.put(3, "微博");
		app01Map.put(4, "导航");
		app01Map.put(5, "视频");
		app01Map.put(6, "音乐");
		app01Map.put(7, "应用商店");
		app01Map.put(8, "游戏");
		app01Map.put(9, "支付");
		app01Map.put(10, "动漫");
		app01Map.put(11, "邮箱");
		app01Map.put(12, "P2P业务");
		app01Map.put(13, "VoIP业务");
		app01Map.put(14, "彩信");
		app01Map.put(15, "浏览下载");
		app01Map.put(16, "财经");
		app01Map.put(17, "安全杀毒");
		app01Map.put(18, "其他业务");
	}
	
	public static String getName(int id){
		return app01Map.get(id);
	}
	
	public static Integer getId(String name){
		for(Map.Entry<Integer, String> entry : app01Map.entrySet()){
			if(entry.getValue().equals(name)) return entry.getKey();
		}
		return null;
	}
}
