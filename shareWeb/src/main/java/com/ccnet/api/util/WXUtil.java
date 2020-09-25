package com.ccnet.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.core.common.utils.wxpay.MD5Util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.*;

public class WXUtil {
    private static Logger MAIN_LOGGER = LoggerFactory.getLogger(WXUtil.class);

    //提交订单
    public static String _URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //提交退款订单
    public static String REFUNDURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取支付所需签名
     *
     * @return
     * @throws Exception
     */
    public static String getPayCustomSign(Map bizObj, String key) throws Exception {

        String bizString = FormatBizQueryParaMap(bizObj, false);
        return sign(bizString, key);
    }

    public static String FormatBizQueryParaMap(Map paraMap, boolean urlencode) throws Exception {

        String buff = "";
        try {
            List<Map.Entry> infoIds = new ArrayList(paraMap.entrySet());

            Collections.sort(infoIds, new Comparator<Map.Entry>() {
                public int compare(Map.Entry o1,
                                   Map.Entry o2) {
                    return (o1.getKey()).toString().compareTo((String) o2.getKey());
                }
            });

            for (int i = 0; i < infoIds.size(); i++) {
                Map.Entry item = infoIds.get(i);
                //System.out.println(item.getKey());
                if (item.getKey() != "") {
                    String key = (String) item.getKey();
                    String val = (String) item.getValue();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    buff += key + "=" + val + "&";
                }
            }

            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
            MAIN_LOGGER.info("加密参数排序后:{}", buff);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return buff;
    }

    public static String sign(String content, String key)
            throws Exception {
        String signStr = "";
        signStr = content + "&key=" + key;
        MAIN_LOGGER.info("加上key后参数:{}", signStr);

        return MD5(signStr, TenpayConstant.INPUT_CHARSET).toUpperCase();

    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws java.security.SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public final static String MD5(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 中文字符有问题 暂停使用
     *
     * @param s
     * @return
     */
    @Deprecated
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String ArrayToXml(Map arr) throws Exception {
        String xml = "<xml>";

        Iterator iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            xml += "<" + key + ">" + val + "</" + key + ">";
        }

        xml += "</xml>";
        return xml;
    }

    public static boolean IsNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPrepayid(String url, String xml) {
        try {
            //	Log.debug("xml:"+xml);
            JSONObject jo = getPrepayJson(url, xml);
            MAIN_LOGGER.info("微信响应数据:{}", jo);
            JSONObject element = jo.getJSONObject("xml");
            String prepayid = ((JSONArray) element.get("prepay_id")).get(0).toString();
            return prepayid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCodeUrl(String url, String xml) {
        try {
            //	Log.debug("xml:"+xml);
            JSONObject jo = getPrepayJson(url, xml);
            MAIN_LOGGER.info("微信响应数据:{}", jo);
            JSONObject element = jo.getJSONObject("xml");
            String prepayid = ((JSONArray) element.get("code_url")).get(0).toString();
            return prepayid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static JSONObject getPrepayJson(String url, String xml) {

        // return httpsRequest(_URL,"POST",xml);
        HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());

        InputStream is = null;
        PostMethod method = null;
        try {
            method = new PostMethod(url);
            //  method.setParameter("input_charset","UTF-8");
            method.addRequestHeader("Content-Type", "text/xml");
            StringRequestEntity postEntity = new StringRequestEntity(xml, "text/xml", "UTF-8");

            method.setRequestEntity(postEntity);
            // method.setRequestBody(xml);
            httpClient.executeMethod(method);
            //读取响应
            is = method.getResponseBodyAsStream();
            JSONObject o = xml2JSON(is);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 转换一个xml格式的字符串到json格式
     * <p>
     * xml格式的字符串
     *
     * @return 成功返回json 格式的字符串;失败反回null
     */
    @SuppressWarnings("unchecked")
    public static JSONObject xml2JSON(InputStream is) {
        JSONObject obj = new JSONObject();
        try {
            SAXReader sb = new SAXReader();

            Document doc = sb.read(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj;
        } catch (Exception e) {
            //Log.error("传入XML后转换JSON出现错误===== Xml2JsonUtil-->xml2JSON============>>" + e.getMessage());
            return null;
        }
    }

    public static String weixinPayResultXml(String status) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        if ("SUCCESS".equals(status)) {
            xml.append("<return_code><![CDATA[").append(status).append("]]>");
            xml.append("</return_code>");
            xml.append("<return_msg><![CDATA[OK]]></return_msg>");
        } else {
            xml.append("<return_code><![CDATA[").append(status).append("]]>");
            xml.append("</return_code>");
            xml.append("<return_msg><![CDATA[FAIL]]></return_msg>");
        }
        xml.append("</xml>");

        return xml.toString();
    }
    /*public static  Map xmlMap(String xml) {
        //创建一个新的字符串
		StringReader read = new StringReader(xml);
		//创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		//创建一个新的SAXBuilder
		SAXBuilder saxbBuilder = new SAXBuilder();
		try {
			//通过输入源构造一个Document

			org.jdom.Document doc =  saxbBuilder.build(source);

			//取的根元素
			org.jdom.Element root =  doc.getRootElement();
			List<?> node = root.getChildren();
			Map map = new HashMap();
			for (int i = 0; i < node.size(); i++) {
				org.jdom.Element element=(org.jdom.Element)node.get(i);
				map.put(element.getName(),element.getText());
			}
			return map;


		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();

		return retMap;
	}*/

    /**
     * 一个迭代方法
     *
     * @param element : org.jdom.Element
     * @return java.util.Map 实例
     */
    @SuppressWarnings("unchecked")
    private static Map iterateElement(Element element) {
        List jiedian = element.elements();
        Element et = null;
        Map obj = new HashMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.elements().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

    public static Map<String, String> wxResultToMap(JSONObject json) {
        JSONObject element = json.getJSONObject("xml");
        Map<String, String> param = new HashMap<String, String>();
        Set<String> keys = element.keySet();
        Iterator<String> it = keys.iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            param.put(key, element.getJSONArray(key).getString(0));
        }
        return param;
    }

    public static JSONObject httpsRequest(String requestUrl, String xml) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream instream = new FileInputStream(new File("/etc/99shopping/wxcer/apiclient_cert.p12"));
            try {
                keyStore.load(instream, TenpayConstant.APP_SECRET.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, TenpayConstant.APP_SECRET.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            try {

                HttpPost httppost = new HttpPost(requestUrl);
                StringEntity postEntity = new StringEntity(xml, "UTF-8");
                httppost.addHeader("Content-Type", "text/xml");
                httppost.setEntity(postEntity);
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    HttpEntity entity = response.getEntity();

                    InputStream inputStream = entity.getContent();
                    JSONObject o = xml2JSON(inputStream);
                    inputStream.close();

                    return o;
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }

// 释放资源
        } catch (ConnectException ce) {
            MAIN_LOGGER.error("连接超时:{}", ce);
        } catch (Exception e) {
            MAIN_LOGGER.error("https请求异常:{}", e);
        }
        return null;
    }

}
