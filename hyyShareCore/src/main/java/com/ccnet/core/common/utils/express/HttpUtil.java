package com.ccnet.core.common.utils.express;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

	public static String post(String url, String charset,
			Map<String, Object> params) throws IOException {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer result = new StringBuffer();
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			out = new OutputStreamWriter(conn.getOutputStream(), charset);
			out.write(buildQuery(params, charset));
			out.flush();
			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				result.append(tempLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return result.toString();
	}

	public static String buildQuery(Map<String, Object> params, String charset)
			throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}
		StringBuffer data = new StringBuffer();
		boolean flag = false;
		for (Entry<String, Object> entry : params.entrySet()) {
			if (flag) {
				data.append("&");
			} else {
				flag = true;
			}
			data.append(entry.getKey()).append("=").append(
					URLEncoder.encode(entry.getValue().toString(), charset));
		}
		return data.toString();
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String data = "[\"778564698005\",\"751816295422\"]";
		map.put("data", data);
		map.put("msg_type", "TRACES");
		System.out.println(DigestUtil.digest(data, "E67D6D54AE5ED8DF31749C1EA17EEE57",DigestUtil.UTF8));
		map.put("data_digest", DigestUtil.digest(data, "E67D6D54AE5ED8DF31749C1EA17EEE57",DigestUtil.UTF8));
		map.put("company_id", "2464380e4f904c6f996c2f8520bcbd00");
		try {
			String traceString = post("http://japi.zto.cn/zto/api_utf8/traceInterface", DigestUtil.UTF8,map);
			System.out.println(traceString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}