package com.ccnet.core.common.utils.qiniu.upload;

import com.ccnet.core.common.UZoneType;
import com.ccnet.core.common.utils.qiniu.auth.UserAuthManager;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;

/**
 * 七牛文件上传工具类
 * @author jackie wang
 *
 */
public class UserPicUpload {
	//请求路径
	public static String QN_URL = "http://rs.qiniu.com";
	
	/**
	 * 简单文件上传
	 * @param accessKey
	 * @param secretKey
	 * @param buketName
	 * @param filePath
	 * @param fileName
	 */
	public static void simpleUpload(String accessKey,String secretKey,String bucketName,String filePath,String fileName,UZoneType zone) {
		
		try {
			//获取config配置
			Configuration config = UserAuthManager.getZConfig(zone);
			//创建上传对象
			UploadManager uploadManager = new UploadManager(config);
            //调用put方法上传
			String authToken =  UserAuthManager.getAuthToken(accessKey, secretKey, bucketName);
            Response res = uploadManager.put(filePath,fileName,authToken);
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
            }
        }
		
	}
	
	/**
	 * 抓取文件路径
	 * @param accessKey
	 * @param secretKey
	 * @param bucketName
	 * @param fileName
	 * @param zone
	 */
	public static FetchRet fetchFilePath(String accessKey,String secretKey,String bucketName,String fileName,String url,UZoneType zone) {
		FetchRet fetchRet = null;
		//获取config配置
		Configuration config = UserAuthManager.getZConfig(zone);
		//授权
		Auth auth = UserAuthManager.getUserAuth(accessKey, secretKey);
		//实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, config);
        try {
            //调用fetch方法抓取文件
            fetchRet = bucketManager.fetch(url, bucketName, fileName);
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
        return fetchRet;
	}
	
	
	public static void main(String[] args) {
		String ACCESS_KEY = "DBKZE1grO96wFptywe1KpokZIT-Pp54-pWecK8Xz";
	    String SECRET_KEY = "PcIu6nL1uxOqhnp9n5qMg-EFuxhymsOtxAb_dhNq";
	    String filePath = "D:\\43627966_1.jpg";
	    String fileName = "43627966_1.jpg";
		
		UserPicUpload.simpleUpload(ACCESS_KEY, SECRET_KEY, "pic22213", filePath, fileName,UZoneType.Z_AUTO);
	}

}
