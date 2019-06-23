/**�����ɶ��ַ������б��룬�����Ϳ��������еļ�����϶�ȡ���ַ�����*/
function ar_encode(str)
{
	//����URL����
	return encodeURI(str);
}


/**��Ļ�ֱ���*/
function ar_get_screen()
{
	var c = "";

	if (self.screen) {
		c = screen.width+"x"+screen.height;
	}

	return c;
}

/**��ɫ����*/
function ar_get_color()
{
	var c = ""; 

	if (self.screen) {
		c = screen.colorDepth+"-bit";
	}

	return c;
}

/**���ص�ǰ�����������*/
function ar_get_language()
{
	var l = "";
	var n = navigator;

	if (n.language) {
		l = n.language.toLowerCase();
	}
	else
	if (n.browserLanguage) {
		l = n.browserLanguage.toLowerCase();
	}

	return l;
}

/**�������������IE,Firefox*/
function ar_get_agent()
{
	var a = "";
	var n = navigator;

	if (n.userAgent) {
		a = n.userAgent;
	}

	return a;
}

/**�����ɷ���һ������ֵ����ֵָʾ������Ƿ�֧�ֲ�������Java*/
function ar_get_jvm_enabled()
{
	var j = "";
	var n = navigator;

	j = n.javaEnabled() ? 1 : 0;

	return j;
}

/**����������Ƿ�֧��(����)cookie */
function ar_get_cookie_enabled()
{
	var c = "";
	var n = navigator;
	c = n.cookieEnabled ? 1 : 0;

	return c;
}

/**���������Ƿ�֧��Flash����Flash���*/
function ar_get_flash_ver()
{
	var f="",n=navigator;

	if (n.plugins && n.plugins.length) {
		for (var ii=0;ii<n.plugins.length;ii++) {
			if (n.plugins[ii].name.indexOf('Shockwave Flash')!=-1) {
				f=n.plugins[ii].description.split('Shockwave Flash ')[1];
				break;
			}
		}
	}
	else
	if (window.ActiveXObject) {
		for (var ii=10;ii>=2;ii--) {
			try {
				var fl=eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash."+ii+"');");
				if (fl) {
					f=ii + '.0';
					break;
				}
			}
			 catch(e) {}
		}
	}
	return f;
} 

 
/**ƥ�䶥������*/
function ar_c_ctry_top_domain(str)
{
	var pattern = "/^aero$|^cat$|^coop$|^int$|^museum$|^pro$|^travel$|^xxx$|^com$|^net$|^gov$|^org$|^mil$|^edu$|^biz$|^info$|^name$|^ac$|^mil$|^co$|^ed$|^gv$|^nt$|^bj$|^hz$|^sh$|^tj$|^cq$|^he$|^nm$|^ln$|^jl$|^hl$|^js$|^zj$|^ah$|^hb$|^hn$|^gd$|^gx$|^hi$|^sc$|^gz$|^yn$|^xz$|^sn$|^gs$|^qh$|^nx$|^xj$|^tw$|^hk$|^mo$|^fj$|^ha$|^jx$|^sd$|^sx$/i";

	if(str.match(pattern)){ return 1; }

	return 0;
}

/**����������ַ*/
function ar_get_domain(host)
{
	//����������ȥ������ͷ�� "www."
	var d=host.replace(/^www\./, "");

	//ʣ�ಿ�ְ���"."����split��������ȡ����
	var ss=d.split(".");
	var l=ss.length;

	//�������Ϊ3����Ϊxxx.yyy.zz��ʽ
	if(l == 3){
		//���yyyΪ����������zzΪ�μ���������������
		if(ar_c_ctry_top_domain(ss[1]) && ar_c_ctry_domain(ss[2])){
		}
		//����ֻ����������
		else{
			d = ss[1]+"."+ss[2];
		}
	}
	//������ȴ���3
	else if(l >= 3){

		//���host�����Ǹ�ip��ַ����ֱ�ӷ��ظ�ip��ַΪ��������
		var ip_pat = "^[0-9]*\.[0-9]*\.[0-9]*\.[0-9]*$";
		if(host.match(ip_pat)){
			return d;
		}
		//���host������Ϊ�����������μ�����������������
		if(ar_c_ctry_top_domain(ss[l-2]) && ar_c_ctry_domain(ss[l-1])) {
			d = ss[l-3]+"."+ss[l-2]+"."+ss[l-1];
		}
		//������������
		else{
			d = ss[l-2]+"."+ss[l-1];
		}
	}
		
	return d;
}


/**����cookie��Ϣ*/
function ar_get_cookie(name)
{
	//��ȡ����cookie��Ϣ
	var co=document.cookie;
	
	//��������Ǹ��� ��������cookie��Ϣ
	if (name == "") {
		return co;
	}
	
	//���ֲ�Ϊ�� �������е�cookie�в���������ֵ�cookie
	var mn=name+"=";
	var b,e;
	b=co.indexOf(mn);

	//û���ҵ�������ֵ�cookie �򷵻ؿ�
	if (b < 0) {
		return "";
	}

	//�ҵ���������ֵ�cookie ��ȡcookie��ֵ����
	e=co.indexOf(";", b+name.length);
	if (e < 0) {
		return co.substring(b+name.length + 1);
	}
	else {
		return co.substring(b+name.length + 1, e);
	}
}

/**
 	����cookie��Ϣ
	��������
		0 ��ʾ�����ó�ʱʱ�� cookie��һ���Ự�����cookie  cookie��Ϣ������������ڴ浱�� ������ر�ʱcookie��ʧ
		1 ��ʾ���ó�ʱʱ��Ϊ10���Ժ� cookie��һֱ���������������ʱ�ļ����� ֱ����ʱʱ�䵽�� ���û��ֶ����cookieΪֹ
		2 ��ʾ���ó�ʱʱ��Ϊ1��Сʱ�Ժ� cookie��һֱ���������������ʱ�ļ����� ֱ����ʱʱ�䵽�� ���û��ֶ����cookieΪֹ
 * */
function ar_set_cookie(name, val, cotp) 
{ 
	var date=new Date; 
	var year=date.getFullYear(); 
	var hour=date.getHours(); 

	var cookie="";

	if (cotp == 0) { 
		cookie=name+"="+val+";"; 
	} 
	else if (cotp == 1) { 
		year=year+10; 
		date.setYear(year); 
		cookie=name+"="+val+";expires="+date.toGMTString()+";"; 
	} 
	else if (cotp == 2) { 
		hour=hour+1; 
		date.setHours(hour); 
		cookie=name+"="+val+";expires="+date.toGMTString()+";"; 
	} 

	var d=ar_get_domain(document.domain);
	if(d != ""){
		cookie +="domain="+d+";";
	}
	cookie +="path="+"/;";

	document.cookie=cookie;
}



/**���ؿͻ���ʱ��*/
function ar_get_stm() 
{ 
	return new Date().getTime();
} 


/**����ָ��������������ִ�*/
function ar_get_random(n) {
	var str = "";
	for (var i = 0; i < n; i ++) {
		str += String(parseInt(Math.random() * 10));
	}
	return str;
}

/* main function */
function ar_main() {
	
	//�ռ�����־ �ύ����·��
	var dest_path   = "http://localhost/FluxLogServer/servlet/LogServlet?"; 
	var expire_time = 30 * 60 * 1000;//�Ự��ʱʱ��

	//����uv
	//--��ȡcookie ar_stat_uv��ֵ
	var uv_str = ar_get_cookie("ar_stat_uv");
	var uv_id = "";
	//--���cookie ar_stat_uv��ֵΪ��
	if (uv_str == ""){
		//--Ϊ�����uv����id��Ϊһ������20���������
		uv_id = ar_get_random(20);
		//--����cookie ar_stat_uv ����ʱ��Ϊ10��
		ar_set_cookie("ar_stat_uv", uv_id, 1);
	}
	//--���cookie ar_stat_uv��ֵ��Ϊ��
	else{
		//--��ȡuv_id
		uv_id  = uv_str;
	}

	//����ss
	//--��ȡcookie ar_stat_ss
	var ss_stat = ar_get_cookie("ar_stat_ss"); 
	var ss_id = "";  //sessin id
	var ss_count = 0;   //session��Ч���ڷ���ҳ��Ĵ���
	var ss_time = "";
	//--���cookie�в�����ar_stat_ss ˵����һ���µĻỰ
	if (ss_stat == ""){
		//--������ɳ���Ϊ10��session id
		ss_id = ar_get_random(10);
		//--session��Ч����ҳ����ʴ���Ϊ0
		ss_count = 0;
		//--��ǰ�¼�
		ss_time = ar_get_stm()
	} else { //--���cookie�д���ar_stat_ss
		//��ȡss�����Ϣ
		var items = ss_stat.split("_");
		//--ss_id
		ss_id  = items[0];
		//--ss_count
		ss_count  = parseInt(items[1]);
		//--ss_stm
		ss_time = items[2];

		//�����ǰʱ��-��ǰ�Ự��һ�η���ҳ���ʱ��>30����,��Ȼcookie�����ڣ�������ʵ�Ѿ���ʱ�ˣ���Ȼ��Ҫ��������cookie
		if (ar_get_stm() - ss_time > expire_time) { 
			//--�������ɻỰid
			ss_id = ar_get_random(10);
			//--���ûỰ�е�ҳ����ʴ���Ϊ0
			ss_count = 0;
			//--��ǰ�¼�
			ss_time = ar_get_stm();
		}else{//--����Ựû�г�ʱ
			//--�Ựid����
			//--���ûỰ�е�ҳ�淽λ����+1
			ss_count = ss_count + 1;
			ss_time = ar_get_stm();
		}
	}
	//--����ƴ��cookie ar_stat_ss��ֵ 
	value = ss_id+"_"+ss_count+"_"+ss_time;
	ar_set_cookie("ar_stat_ss", value, 0); 
	
	//��ǰ��ַ
	var url = document.URL; 
	url = ar_encode(String(url)); 
	
	//��ǰ��Դ��
	var urlname = document.URL.substring(document.URL.lastIndexOf("/")+1);
	urlname = ar_encode(String(urlname)); 
	
    //���ص�������ǰ��ҳ�ĳ�����������ҳ��URL
	var ref = document.referrer; 
	ref = ar_encode(String(ref)); 


	//��ҳ����
	var title = document.title;
	title = ar_encode(String(title)); 

	//��ҳ�ַ���
	var charset = document.charset;
	charset = ar_encode(String(charset)); 

	//��Ļ��Ϣ
	var screen = ar_get_screen(); 
	screen = ar_encode(String(screen)); 

	//��ɫ��Ϣ
	var color =ar_get_color(); 
	color =ar_encode(String(color)); 

	//������Ϣ
	var language = ar_get_language(); 
	language = ar_encode(String(language));

 	//���������
	var agent =ar_get_agent(); 
	agent =ar_encode(String(agent));

	//������Ƿ�֧�ֲ�������java
	var jvm_enabled =ar_get_jvm_enabled(); 
	jvm_enabled =ar_encode(String(jvm_enabled)); 

	//������Ƿ�֧�ֲ�������cookie
	var cookie_enabled =ar_get_cookie_enabled(); 
	cookie_enabled =ar_encode(String(cookie_enabled)); 

	//�����flash�汾
	var flash_ver = ar_get_flash_ver();
	flash_ver = ar_encode(String(flash_ver)); 

	
	//��ǰss״̬ ��ʽΪ"�Ựid_�Ự����_��ǰʱ��"
	var stat_ss = ss_id+"_"+ss_count+"_"+ss_time;
	//ƴ�ӷ��ʵ�ַ ����������Ϣ
	dest=dest_path+"url="+url+"&urlname="+urlname+"&title="+title+"&chset="+charset+"&scr="+screen+"&col="+color+"&lg="+language+"&je="+jvm_enabled+"&ce="+cookie_enabled+"&fv="+flash_ver+"&cnv="+String(Math.random())+"&ref="+ref+"&uagent="+agent+"&stat_uv="+uv_id+"&stat_ss="+stat_ss;


    //ͨ������ͼƬ���ʸõ�ַ
    document.getElementsByTagName("body")[0].innerHTML += "<img src=\""+dest+"\" border=\"0\" width=\"1\" height=\"1\" />";
    
}

window.onload = function(){
	//����main����
	ar_main();
}

