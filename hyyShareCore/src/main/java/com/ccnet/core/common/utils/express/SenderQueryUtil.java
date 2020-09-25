package com.ccnet.core.common.utils.express;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import com.ccnet.core.common.cache.ConfigCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.UniqueID;

/**
 * 获取物流单号
 * @author jackie
 *
 */
public class SenderQueryUtil {
	
	/**
     * 获取URL返回的字符串
     * @param callurl
     * @param charset
     * @return
     */
    private static String callUrlByGet(String callurl,String charset){   
        String result = "";   
        try {   
            URL url = new URL(callurl);   
            URLConnection connection = url.openConnection();   
            connection.connect();   
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));   
            String line;   
            while((line = reader.readLine())!= null){    
                result += line;   
                result += "\n";
            }
        } catch (Exception e) {   
            e.printStackTrace();   
            return "";
        }
        if(CPSUtil.isNotEmpty(result)){
        	result = result.replaceAll("wlxx|\\&", "");
        	result = result.replaceAll("\\(", "");
        	result = result.replaceAll("\\)", "");
        }
        return result;
    }
	
	/**
     * 获取URL返回的字符串
     * @param callurl
     * @param charset
     * @return
     */
    private static String callUrlByPOST(String callurl,String charset){   
    	StringBuffer result = new StringBuffer("");  
        try {   
            URL url = new URL(callurl);   
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();   
            conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.connect();   
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));   
            String line;   
            while((line = reader.readLine())!= null){    
            	result.append(line);
            }
        } catch (Exception e) {   
            e.printStackTrace();   
            return "";
        }
        return result.toString();
    }
    
    
    /**
     * 物流查询
     * @param dh 快递单号
     * @param gs 快递公司
     * @return
     */
    public static String getWuliu(String cus_id,String cus_key,String shipperCode,String logisticCode){
    	String expressStr = null;
	    try {
			//expressStr = KdniaoSubscribeAPI.newInstance(cus_id, cus_key).getOrderTracesByJson(shipperCode.toLowerCase(), logisticCode);
		} catch (Exception e) {
			CPSUtil.xprint("获取快递信息失败:"+e.getMessage());
			e.printStackTrace();
		}
		return expressStr;
    }
    
    /**
     * 物流查询
     * @param dh 快递单号
     * @param gs 快递公司
     * @return
     */
    public static String getWuliu2(String cus_id,String cus_key,String dh,String gs){
    	String rteString = null;
    	String COM_ID = ConfigCache.getConfig("zto_com_id");
    	String COM_KEY = ConfigCache.getConfig("zto_com_key");
    	String CUST_ID = ConfigCache.getConfig("kdn_com_id");
    	String CUST_KEY = ConfigCache.getConfig("kdn_com_key");
		if(CPSUtil.isNotEmpty(COM_ID) && CPSUtil.isNotEmpty(COM_KEY) && (gs.contains("zhongtong")||gs.contains("ZTO"))){
			rteString = getZTOJson(dh.split(","));
		}else{
		    try {
				//rteString = KdniaoSubscribeAPI.newInstance(CUST_ID, CUST_KEY).getOrderTracesByJson(dh, gs.toLowerCase());
			} catch (Exception e) {
				CPSUtil.xprint("获取快递信息失败:"+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return rteString;
    	
    }
    
    /**
     * 物流查询
     * @param dh 快递单号
     * @param gs 快递公司
     * @return
     */
    public static String getWuliu2(String dh,String gs){
        if(CPSUtil.isNotEmpty(dh)){
        	String enMailNo =UniqueID.getUniqueID(12, 2);//随机字符串
        	long cuttime = System.currentTimeMillis();
        	if(gs.indexOf("shunfeng")!=-1){
        		gs = "shunfeng";
        		enMailNo = "8f3ciffglidl";
        	}
			if(gs.indexOf("huitong")!=-1){
				gs = "huitong";
        		enMailNo = "cb9amblepejj";
        	}
			if(gs.indexOf("yuantong")!=-1){
				gs = "yuantong";
        		enMailNo = "cf5amfhepifj";
        	}
			if(gs.indexOf("zhongtong")!=-1){
				gs = "zhongtong";
        		enMailNo = "aecfkeojnhmo";
        	}
			if(gs.indexOf("shentong")!=-1){
				gs = "shentong";
        		enMailNo = "cd3amdfepgdj";
        	}
			if(gs.indexOf("yunda")!=-1){
				gs = "yunda";
        		enMailNo = "5f9affleiijj";
        	}
			if(gs.indexOf("ems")!=-1){
				gs = "ems";
        		enMailNo = "6j76gjjajmhf";
        	}
			if(gs.indexOf("tiantian")!=-1){
				gs = "tiantian";
        		enMailNo = "9e79jejdmhhi";
        	}
			String COM_ID = ConfigCache.getConfig("zto_com_id");
	    	String COM_KEY = ConfigCache.getConfig("zto_com_key");
			String queryUrl = "";
			if(CPSUtil.isNotEmpty(COM_ID) && CPSUtil.isNotEmpty(COM_KEY) && gs.contains("zhongtong")){
				return getZTOJson(dh.split(","));
			}else{
				queryUrl = "http://biz.trace.ickd.cn/"+gs+"/"+dh+"?ts="+cuttime+"&enMailNo="+enMailNo+"&callback=wlxx&_"+cuttime+"=";
				return callUrlByGet(queryUrl,"GBK");
			}
			
        }else{
        	return null;
        }

    }
    
    /**
     * 处理物流单号
     * @param dh
     * @return
     */
    private static String convertOderNo(String [] dh) {
    	int count = 0;
    	StringBuilder dataString = new StringBuilder("[");
		if(CPSUtil.isNotEmpty(dh)){
			for (String d : dh) {
				dataString.append("\"").append(d).append("\"");
				count++;
				if(count<dh.length){
					dataString.append(",");
				}
			}
		}
		dataString.append("]");
		CPSUtil.xprint("中通查询单号："+dataString.toString());
		return dataString.toString();
	}
    
    /**
     * 获取中通物流接口信息
     * @param dh 单号支持多个
     * @return
     */
    public static String getZTOJson(String [] dh) {
    	String COM_ID = ConfigCache.getConfig("zto_com_id");
    	String COM_KEY = ConfigCache.getConfig("zto_com_key");
    	//判断参数
		if(CPSUtil.isEmpty(COM_ID)||CPSUtil.isEmpty(COM_KEY)||CPSUtil.isEmpty(dh)){
			return null;
		}
		try {
		    //开始处理业务逻辑
		    String data = convertOderNo(dh);
			String data_digest = DigestUtil.digest(data, COM_KEY, DigestUtil.UTF8);
			CPSUtil.xprint("加密："+data_digest);
			String queryUrl = "http://japi.zto.cn/zto/api_utf8/traceInterface";
			if(CPSUtil.isNotEmpty(data_digest)){
				queryUrl+="?company_id="+COM_ID;
				queryUrl+="&data="+data;
				queryUrl+="&msg_type=TRACES";
				queryUrl+="&data_digest="+data_digest;
			}
			CPSUtil.xprint("url="+queryUrl);
			//请求接口
			return callUrlByPOST(queryUrl, DigestUtil.UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
	
    /**
     * 查询物流信息
     */
    public static String getWuliu1(String dh,String gs){
        if(CPSUtil.isNotEmpty(dh)){
        	String queryKey ="";//查询key
        	long cuttime = System.currentTimeMillis();
        	//快递接口类型
        	String apiType = "0";
        	//快递接口key
        	String apiKeys = "";
        	
        	if(gs.contains("shunfeng")||gs.equalsIgnoreCase("SF")){
        		gs = "shunfeng";
        	}
			if(gs.indexOf("huitong")!=-1){
				gs = "huitongkuaidi";
        	}
			if(gs.contains("yuantong")||gs.equalsIgnoreCase("YTO")){
				gs = "yuantong";
        	}
			if(gs.indexOf("zhongtong")!=-1){
				gs = "zhongtong";
        	}
			if(gs.indexOf("shentong")!=-1||gs.equalsIgnoreCase("STO")){
				gs = "shentong";
        	}
			if(gs.contains("yunda")){
				gs = "yunda";
        	}
			if(gs.contains("ems")){
				gs = "ems";
        	}
			if(gs.contains("tiantian")){
				gs = "tiantian";
        	}
			
			if(gs.contains("youshuwuliu")||gs.equalsIgnoreCase("YS")){
				gs = "youshuwuliu";
        	}
			
			if(gs.contains("zhaijisong")||gs.equalsIgnoreCase("ZJS")){
				gs = "zhaijisong";
        	}
			
			String queryUrl = "";
			String COM_ID = ConfigCache.getConfig("zto_com_id");
	    	String COM_KEY = ConfigCache.getConfig("zto_com_key");
			if(CPSUtil.isNotEmpty(COM_ID) && CPSUtil.isNotEmpty(COM_KEY) && gs.contains("zhongtong")){
				return getZTOJson(dh.split(","));
			}else{
			
				if(CPSUtil.isEmpty(apiType)||CPSUtil.isEmpty(apiKeys)){
					queryUrl = "http://m.kuaidi100.com/query?type="+gs+"&postid="+dh+"&id=1&valicode=&temp=0."+cuttime; 
				}else{//不为空
					if(apiType.equals("1")){
						//随机获取一个key
						String [] keys = null;
						Random rnd = new Random();
						if(apiKeys.contains("|")){
						   keys = apiKeys.split("\\|");
						   int rn = rnd.nextInt(keys.length);
						   queryKey = keys[rn];
						}
						CPSUtil.xprint("查询获取的key为:【"+queryKey+"】");
						queryUrl = "http://api.kuaidi100.com/api?id="+queryKey+"&com="+gs+"&nu="+dh+"&show=0&muti=1&order=desc"; 
					}else{
						queryUrl = "http://m.kuaidi100.com/query?type="+gs+"&postid="+dh+"&id=1&valicode=&temp="+cuttime; 
					}
				}
				CPSUtil.xprint("url="+queryUrl);
				return callUrlByPOST(queryUrl,"UTF-8");
			}
        }else{
        	return null;
        }

    }
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://biz.trace.ickd.cn/huitong/280284827323?ts=1421165104619&enMailNo=cb9amblepejj&callback=_jqjsp&_1421165104634=
		//http://biz.trace.ickd.cn/yuantong/280284827323?ts=1421166244259&enMailNo=cf5amfhepifj&callback=_jqjsp&_1421166244259=
		//http://biz.trace.ickd.cn/zhongtong/280284827323?ts=1421166269947&enMailNo=aecfkeojnhmo&callback=_jqjsp&_1421166269947=
		//http://biz.trace.ickd.cn/shentong/280284827323?ts=1421166304039&enMailNo=cd3amdfepgdj&callback=_jqjsp&_1421166304039=
		//http://biz.trace.ickd.cn/yunda/280284827323?ts=1421166334652&enMailNo=5f9affleiijj&callback=_jqjsp&_1421166334652=
		//http://biz.trace.ickd.cn/shunfeng/280284827323?ts=1421166666055&enMailNo=8f3ciffglidl&callback=_jqjsp&_1421166666055=
		//http://biz.trace.ickd.cn/ems/280284827323?ts=1421166840493&enMailNo=6j76gjjajmhf&callback=_jqjsp&_1421166840508=
		//http://biz.trace.ickd.cn/tiantian/280284827323?ts=1421166963446&enMailNo=9e79jejdmhhi&callback=_jqjsp&_1421166963446=
		try {
			/*long cur = 921655722139L;
			Map cmap = null;
			for (int i = 0; i < 1; i++) {
				Thread.sleep(1000);
				cur = cur+i;
				CPSUtil.xprint("wuxx="+getWuliu("761868316586", "ZTO"));
				//cmap = JsonHelper.toMap(getWuliu(String.valueOf(cur), "huitong"));
				//CPSUtil.xprint("cmap["+i+"]="+cmap);
				
			}*/
			
			//CPSUtil.xprint("wuxx="+getWuliu1("886325199202", "tiantian"));
			//String urlString = "http://www.yzm1.com/api/do.php?action=getMessage&sid=1008&phone=15102049743&token=ac239630-72ee-4233-9103-fa5d3507700b";
			//String str = "http://japi.zto.cn/zto/api_utf8/traceInterface?company_id=2464380e4f904c6f996c2f8520bcbd00&data_digest=TnAMfR58J1pdBQ4MgMQUuw==&data=[\"778564698005\",\"761868316586\"]&msg_type=TRACES";
			//CPSUtil.xprint(callUrlByPOST(str, "UTF-8"));
			
			
			String comID = "2464380e4f904c6f996c2f8520bcbd00";
			String comKey = "E67D6D54AE5ED8DF31749C1EA17EEE57";
			String []dh = new String []{"531977474240","761868316586"};
			String reString = getZTOJson(dh);
			CPSUtil.xprint("reString="+reString);
			
			//CPSUtil.xprint("====>"+callUrlByGet("http://wx4.95nm.com/touch/users/location?latitude=123.48574535&longitude=43.342342124", "UTF-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
