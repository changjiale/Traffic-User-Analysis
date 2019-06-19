package cn.zebra.data;

import java.util.HashMap;
import java.util.Map;

public class APP01 {
	private static Map<Integer,String> app01Map = new HashMap<Integer,String>();

	private APP01() {
	}

	static{
		app01Map.put(1, "��ʱͨ��");
		app01Map.put(2, "�Ķ�");
		app01Map.put(3, "΢��");
		app01Map.put(4, "����");
		app01Map.put(5, "��Ƶ");
		app01Map.put(6, "����");
		app01Map.put(7, "Ӧ���̵�");
		app01Map.put(8, "��Ϸ");
		app01Map.put(9, "֧��");
		app01Map.put(10, "����");
		app01Map.put(11, "����");
		app01Map.put(12, "P2Pҵ��");
		app01Map.put(13, "VoIPҵ��");
		app01Map.put(14, "����");
		app01Map.put(15, "�������");
		app01Map.put(16, "�ƾ�");
		app01Map.put(17, "��ȫɱ��");
		app01Map.put(18, "����ҵ��");
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
