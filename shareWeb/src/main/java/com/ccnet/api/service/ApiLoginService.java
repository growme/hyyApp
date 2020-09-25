package com.ccnet.api.service;

import java.io.IOException;

import org.apache.http.ParseException;

import com.ccnet.api.Params.LoninParams.LoginParam;
import com.ccnet.api.entity.LoginResult;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.cps.entity.MemberInfo;

/**
 * API登陆接口
 *
 */
public interface ApiLoginService {

	void getValidVerifyCode(String phone);

	ResultDTO<LoginResult<MemberInfo>> userLogin(LoginParam param, String identity) throws ParseException, IOException;

	void getCeshi(String recomCode, Integer userid);
	
	
}
