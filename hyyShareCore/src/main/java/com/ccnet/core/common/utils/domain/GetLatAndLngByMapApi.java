package com.ccnet.core.common.utils.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/** 
* 获取经纬度/地址
*/ 
public class GetLatAndLngByMapApi { 
	
	/** 
	* @param addr 
	* 查询的地址 
	* @return 
	* @throws IOException 
	*/ 
	public Object[] getCoordinate(String addr) throws IOException { 
		String lng = null;//经度
		String lat = null;//纬度
		String address = null; 
		try { 
			address = java.net.URLEncoder.encode(addr, "UTF-8"); 
		}catch (UnsupportedEncodingException e1) { 
			e1.printStackTrace(); 
		} 
		String key = "f247cdb592eb43ebac6ccd27f796e2d2"; 
		String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key); 
		URL myURL = null; 
		URLConnection httpsConn = null; 
		try { 
			myURL = new URL(url); 
		} catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} 
		InputStreamReader insr = null;
		BufferedReader br = null;
		try { 
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
			if (httpsConn != null) { 
				insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
				br = new BufferedReader(insr); 
				String data = null; 
				int count = 1;
				while((data= br.readLine())!=null){ 
					if(count==5){
						lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
						count++;
					}else if(count==6){
						lat = data.substring(data.indexOf(":")+1);//纬度
						count++;
					}else{
						count++;
					}
				} 
			} 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if(insr!=null){
				insr.close();
			}
			if(br!=null){
				br.close();
			}
		}
		return new Object[]{lng,lat}; 
	} 
	
	
	/** 
	* @param addr 
	* 查询的地址 
	* @return 
	* @throws IOException 
	*/ 
	public String getAddrByCoordinate(String ak,String lng,String lat) throws IOException { 
		String address = null; 
		String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%s,%s&output=json&pois=1",ak, lng, lat); 
		URL myURL = null; 
		URLConnection httpsConn = null; 
		try { 
			myURL = new URL(url); 
		} catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} 
		InputStreamReader insr = null;
		BufferedReader br = null;
		try { 
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
			if (httpsConn != null) { 
				insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
				br = new BufferedReader(insr); 
				String result = null ;
				while((result= br.readLine())!=null){ 
					address += result;   
				} 
				//处理替换
				address = address.replaceAll("null", "");
			} 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if(insr!=null){
				insr.close();
			}
			if(br!=null){
				br.close();
			}
		}
		return address; 
	} 
	
	
	/** 
	* @param addr 
	* 高德地图查询的地址 
	* @return 
	* @throws IOException 
	*/ 
	public String getAddrByGEO(String key,String lng,String lat) throws IOException { 
		String address = null; 
		String url = String.format("http://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s,%s&extensions=base&roadlevel=1",key,lat,lng); 
		URL myURL = null; 
		URLConnection httpsConn = null; 
		try { 
			myURL = new URL(url); 
		} catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} 
		InputStreamReader insr = null;
		BufferedReader br = null;
		try { 
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
			if (httpsConn != null) { 
				insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
				br = new BufferedReader(insr); 
				String result = null ;
				while((result= br.readLine())!=null){ 
					address += result;   
				} 
				//处理替换
				address = address.replaceAll("null", "");
			} 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if(insr!=null){
				insr.close();
			}
			if(br!=null){
				br.close();
			}
		}
		return address; 
	} 
	
	
	public static void main(String[] args) throws IOException {
		GetLatAndLngByMapApi getLatAndLngByBaidu = new GetLatAndLngByMapApi();
		String addr = getLatAndLngByBaidu.getAddrByCoordinate("EgNXWfZWI8lCNDtR86NIauPoBZpF44IF","23.251562149071418","113.29674073691487");
		String addr1 = getLatAndLngByBaidu.getAddrByGEO("828546cbdc41fc391039c8bdcc2e5a39","23.251562149071418","113.29674073691487");
		System.out.println("addr=="+addr);//地址
		System.out.println("addr1=="+addr1);//地址
	}

}