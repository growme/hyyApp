/**
 * created by 朱施健  上午11:55:39
 */
package com.ccnet.api.entity;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ccnet.api.util.TokenUtil;

/**
 * @author 朱施健 上午11:55:39
 *
 */
public class Headers implements Serializable {
	// a、identity：设备硬件唯一标识（必须有值）
	// b、time：客户端时间戳（必须有值，10位的int型）
	// c、token：头必须存在，值为空字符串时表示用户未登陆，值存在是表示用户已经登陆
	// d、client：客户端类型，（必须有值,值枚举： ios | android ）
	// e、version：客户端版本号（必须有值）
	// f、market：应用市场（必须有值，官方为official，其他为各种市场的英文名称）
	// e、network 0:未知 1:蜂窝 2:wifi
	// f、vpn 1:使用vpn 0:未使用vpn
	// g、gyro 1:有陀螺仪 0:无陀螺仪

	private static final long serialVersionUID = 1L;
	public String user_agent;
	public String ip;
	public String identity;
	public String time;
	public String token;
	public String client;
	public String version;
	public String userid;
	public boolean isblack;
	public int network;
	public boolean vpn;
	public boolean gyro;

	public Headers() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		update(request);
	}

	public Headers(HttpServletRequest request) {
		update(request);
	}

	public Headers update(HttpServletRequest request) {
		this.setUser_agent(request.getHeader("User-Agent"));
		String token = request.getHeader("token");
		this.setToken(token);
		if(StringUtils.isNotBlank(token)){
			String userid = TokenUtil.getToken(token);
			this.setUserid(userid);
		}
		return this;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isIsblack() {
		return isblack;
	}

	public void setIsblack(boolean isblack) {
		this.isblack = isblack;
	}

	public int getNetwork() {
		return network;
	}

	public void setNetwork(int network) {
		this.network = network;
	}

	public boolean isVpn() {
		return vpn;
	}

	public void setVpn(boolean vpn) {
		this.vpn = vpn;
	}

	public boolean isGyro() {
		return gyro;
	}

	public void setGyro(boolean gyro) {
		this.gyro = gyro;
	}

	@Override
	public String toString() {
		return new StringBuilder("[").append("user_agent=").append(this.user_agent).append(" , ").append("ip=")
				.append(this.ip).append(" , ").append("identity=").append(this.identity).append(" , ").append("time=")
				.append(this.time).append(" , ").append("token=").append(this.token).append(" , ").append("client=")
				.append(this.client).append(" , ").append("version=").append(this.version).append(" , ")
				.append("userid=").append(this.userid).append(" , ").append("isblack=").append(this.isblack).append("]")
				.toString();
	}
}
