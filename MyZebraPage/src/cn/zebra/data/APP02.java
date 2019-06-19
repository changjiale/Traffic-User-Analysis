package cn.zebra.data;

import java.util.HashMap;
import java.util.Map;

public class APP02 {
	private static Map<Integer,String> app02Map = new HashMap<Integer,String>();

	private APP02() {
	}

	static{
		app02Map.put(1, "飞聊");
		app02Map.put(2, "飞信");
		app02Map.put(3, "Gtalk");
		app02Map.put(4, "MSN");
		app02Map.put(5, "QQ");
		app02Map.put(6, "TM");
		app02Map.put(7, "阿里旺旺");
		app02Map.put(8, "米聊");
		app02Map.put(9, "微信");
		app02Map.put(10, "人人桌面");
		app02Map.put(11, "AOL AIM");
		app02Map.put(12, "Gadu_Gadu");
		app02Map.put(13, "go聊");
		app02Map.put(14, "ICQ");
		app02Map.put(15, "IMVU");
		app02Map.put(16, "Lava-Lava");
		app02Map.put(17, "NetChat");
		app02Map.put(18, "Paltalk");
		app02Map.put(19, "PowWow");
		app02Map.put(20, "TeamSpeak");
		app02Map.put(21, "Trillian");
		app02Map.put(22, "VZOchat");
		app02Map.put(23, "Xfire");
		app02Map.put(24, "百度Hi");
		app02Map.put(25, "都秀");
		app02Map.put(26, "陌陌");
		app02Map.put(27, "天翼Live");
		app02Map.put(28, "翼聊");
		app02Map.put(29, "网易泡泡");
		app02Map.put(30, "新浪UC");
		app02Map.put(31, "新浪UT");
		app02Map.put(32, "雅虎通");
	}
	
	public static String getName(int id){
		return app02Map.get(id);
	}
	
	public static Integer getId(String name){
		for(Map.Entry<Integer, String> entry : app02Map.entrySet()){
			if(entry.getValue().equals(name)) return entry.getKey();
		}
		return null;
	}
}
