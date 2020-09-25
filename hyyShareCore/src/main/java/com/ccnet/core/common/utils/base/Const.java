package com.ccnet.core.common.utils.base;

/**
 * 全局静态资源：
 * 
 */
public class Const {
	
	/**
	 * 格式化数值保留2位小数
	 */
	public static String FORMAT_DOUBLE_DIGIT_2="0.00";
	
	/**
	 * 系统默认编码格式
	 */
	public static String SYS_PAGE_ENCODE="UTF-8";
	
	/**
	 * 系统分页条数
	 */
	public static final int PAGER_SIZE = 20;
	
	/**
	 * 系统分页条数
	 */
	public static final int pageNum = 1;//页码，默认是第一页
	/**
	 * 全局验证码标识
	 */
	public static final String SESSION_SECURITY_CODE = "SESSION_SECURITY_CODE";
	/**
	 * 全局短信验证码标识
	 */
	public static final String MOBILE_CHECK_CODE = "MOBILE_CHECK_CODE";
	/**
	 * 全局短信验证码标识
	 */
	public static final String MOBILE_CHECK_TOKEN = "MOBILE_CHECK_TOKEN";
	//验证码类型 0 短信 1 语音
	public static final String MOBILE_CHECK_TYPE = "MOBILE_CHECK_TYPE";
	//注册语音验证
	public static final String MOBILE_REG_VOICE_CHECK = "MOBILE_REG_VOICE_CHECK";
	//提现语音验证
	public static final String MOBILE_PAY_VOICE_CHECK = "MOBILE_PAY_VOICE_CHECK";
	
	/**
	 * 全局菜单标识
	 */
	public static final String SESSION_MENULIST = "SESSION_MENULIST";
	/**
	 * 全局系统参数
	 */
	public static final String SYS_PARAM = "SYSPARAM";
	/**
	 * 账户锁定
	 */
	public static final String SESSION_USER_LOCK = "SESSION_USER_LOCK";
	/**
	 * 强制退出标识
	 */
	public static final String SESSION_FORCE_LOGOUT_KEY = "SESSION_FORCE_LOGOUT_KEY";
	/**
	 * 验证文章唯一访问
	 */
	public static final String CT_REQUEST_UNIQUE = "CT_REQUEST_UNIQUE";

	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	public static final String Exception_Head = "\n非常遗憾的通知您,程序发生了异常.\n" + "异常信息如下:\n";
	/**
	 * 当前访问模式
	 */
	public static final String IS_DEMO_MOD = "IS_DEMO_MODE";
	/**
	 * 域名配置参数
	 */
	public static final String BAK_BUY_PAGE_URL = "BAK_BUY_PAGE_URL";
	
	/**
	 * 全局SESSION标识值
	 */
	public static final String SESSION_USER = "SESSION_USER";
	/**
	 * 全局MSESSION标识值
	 */
	public static final String MSESSION_USER = "MSESSION_USER";

	/**
	 * 全局内存字典表标识值
	 */
	public static final String CT_CODE_LIST = "CT_CODE_LIST";

	/**
	 * 全局内存全局参数表标识值
	 */
	public static final String CT_PARAM_LIST = "CT_PARAM_LIST";
	
	/**
	 * 全局内存栏目信息表标识值
	 */
	public static final String CT_COLUMN_LIST = "CT_COLUMN_LIST";
	
	/**
	 * 全局内存广告信息表标识值
	 */
	public static final String CT_ADVERTISE_LIST = "CT_ADVERTISE_LIST";
	
	/**
	 * 全局内存广告标签信息表标识值
	 */
	public static final String CT_ADVERTISE_TAG_LIST = "CT_ADVERTISE_TAG_LIST";
	
	
	/**
	 * session中的栏目
	 */
	public static final String SN_COLUMN_LIST = "SN_COLUMN_LIST";
	
	/**
	 * 全局内存奖金总额标志
	 */
	public static final String SYS_DEFAULT_BOUNS = "SYS_DEFAULT_BOUNS";
	
	/**
	 * 当前用户
	 */
	public static final String CURRENT_USER = "loginUser";
	
	/**
	 * 全局内存派单域名标识值
	 */
	public static final String CT_DOMAIN_LIST = "CT_DOMAIN_LIST";
	public static final String CT_ZS_DOMAIN_LIST = "CT_ZS_DOMAIN_LIST";
	//非专属
	public static final String CT_TGYM_DOMAIN_LIST = "CT_TGYM_DOMAIN_LIST";
	public static final String CT_TZYM_DOMAIN_LIST = "CT_TZYM_DOMAIN_LIST";
	public static final String CT_TGBY_DOMAIN_LIST = "CT_TGBY_DOMAIN_LIST";
	public static final String CT_TZBY_DOMAIN_LIST = "CT_TZBY_DOMAIN_LIST";
	//专属
	public static final String CT_ZS_TGYM_DOMAIN_LIST = "CT_ZS_TGYM_DOMAIN_LIST";
	public static final String CT_ZS_TZYM_DOMAIN_LIST = "CT_ZS_TZYM_DOMAIN_LIST";
	public static final String CT_ZS_TGBY_DOMAIN_LIST = "CT_ZS_TGBY_DOMAIN_LIST";
	public static final String CT_ZS_TZBY_DOMAIN_LIST = "CT_ZS_TZBY_DOMAIN_LIST";
	
	//微信群二维码标题集合
	public static final String CT_WECHAT_GROUP_LIST = "CT_WECHAT_GROUP_LIST";
	//微信群标题字体大小
	public static final String CT_WECHAT_GROUP_TITLE_SIZE = "CT_WECHAT_GROUP_TITLE_SIZE";
	
	//跳转入口
	public static final String CT_FORWARD_LINK_LIST = "CT_FORWARD_LINK_LIST";
	//推广地址
	public static final String CT_PROMOTE_LINK_LIST = "CT_PROMOTE_LINK_LIST";
	
	//文章hashkey缓存保存时间
	public static final String CT_CONTENT_HASHKEY_CACHE_TIME = "CT_CONTENT_HASHKEY_CACHE_TIME";
	//文章session中间缓存保存时间
	public static final String CT_CONTENT_READ_CACHE_TIME = "CT_CONTENT_READ_CACHE_TIME";
	//文章访问心跳缓存保存时间
	public static final String CT_CONTENT_HEART_BEAT_CACHE_TIME = "CT_CONTENT_HEART_BEAT_CACHE_TIME";
	
	
	//文章集合
	public static final String CT_CONTENT_INFO_LIST = "CT_CONTENT_INFO_LIST";
	//会员集合
	public static final String CT_TD_MEMBER_LIST = "CT_TD_MEMBER_LIST";//徒弟
	public static final String CT_TS_MEMBER_LIST = "CT_TS_MEMBER_LIST";//徒孙
	//全局会员标志
	public static final String CT_SYSTEM_MEMBER_LIST = "CT_SYSTEM_MEMBER_LIST";
	public static final String CT_MEMBER_LIST = "CT_MEMBER_LIST";
	//文章访问hashkey
	public static final String CT_CONTENT_HASHKEY_PARAM = "CT_CONTENT_HASHKEY_PARAM";
	//文章访问心跳总和
	public static final String CT_CONTENT_HEART_BEAT_TOTAL = "CT_CONTENT_HEART_BEAT_TOTAL";
	//文章访问心跳
	public static final String CT_CONTENT_HEART_BEAT = "CT_CONTENT_HEART_BEAT";
	//文章访问缓存变量
    public static final String CT_CONTENT_READ_PARAM = "CT_CONTENT_READ_PARAM";
	//全局会员注册默认奖励金额
	public static final String CT_MEMBER_REGISTER_MONEY = "CT_MEMBER_REGISTER_MONEY";
	//全局会员邀请上级奖励金额
	public static final String CT_RECOM_REGISTER_REWARD = "CT_RECOM_REGISTER_REWARD";
	//全局签到基金
	public static final String CT_DEFAULT_SIGN_BONUS = "CT_DEFAULT_SIGN_BONUS";	
	//连续签到叠加金额
	public static final String CT_DEFAULT_SIGN_ADD_BONUS = "CT_DEFAULT_SIGN_ADD_BONUS";	
	//全局默认会员日推广阅读收益上限
	public static final String CT_MAX_DAILY_READ_MONEY = "CT_MAX_DAILY_READ_MONEY";
	//全局默认上级提成比例  默认20%
	public static final String CT_PARENT_MONEY_PERCENT = "CT_PARENT_MONEY_PERCENT";
	
	//全局默认上上级提成比例  默认5%
	public static final String CT_PARENT_PARENT_MONEY_PERCENT = "CT_PARENT_PARENT_MONEY_PERCENT";
	
	//全局虚增量
	public static final String CT_AD_SHAM_PERCENT = "CT_AD_SHAM_PERCENT";
	
	//全局用户扣量
	public static final String CT_USER_DISS_PERCENT = "CT_USER_DISS_PERCENT";
	
	//全局默认文章阅读收益
	public static final String CT_ARTICLE_READ_MONEY = "CT_ARTICLE_READ_MONEY";
	//全局默认文章分享朋友收益
    public static final String CT_ARTICLE_SHARE_MONEY = "CT_ARTICLE_SHARE_MONEY";
    //全局默认文章分享朋友圈收益
    public static final String CT_ARTICLE_TIME_LINE_SHARE_MONEY = "CT_ARTICLE_TIME_LINE_SHARE_MONEY";
    //全局默认一周分享收益上限
    public static final String CT_USER_WEEK_TOTAL_MONEY = "CT_USER_WEEK_TOTAL_MONEY";
	//全局默认广告开关
    public static final String CT_ARTICLE_ADVERTISE_SHOW = "CT_ARTICLE_ADVERTISE_SHOW";
    //全局默认广告短链接开关
    public static final String CT_ADVERTISE_SHORT_URL = "CT_ADVERTISE_SHORT_URL";
    //全局限制只能微信访问
    public static final String PAGE_WECHAT_READ = "PAGE_WECHAT_READ";
    //开启301跳转
    public static final String PAGE_DOMAIN_FORWARD = "PAGE_DOMAIN_FORWARD";
    //开启域名泛解析
    public static final String GENERIC_PARSE_DOMAIN = "GENERIC_PARSE_DOMAIN";
    //泛解析字符类型
    public static final String DOMAIN_FOR_GENERIC_PARSE_TYPE = "DOMAIN_FOR_GENERIC_PARSE_TYPE";
    //泛解析字符长度
    public static final String DOMAIN_FOR_GENERIC_PARSE_LENGTH = "DOMAIN_FOR_GENERIC_PARSE_LENGTH";
    
    //小说推广关闭全部广告
    public static final String CLOSE_NOVEL_PAGE_ADVERTISE = "CLOSE_NOVEL_PAGE_ADVERTISE";
    //小说推广关闭二维码跳转
    public static final String CLOSE_NOVEL_FORWARD_BARCODE = "CLOSE_NOVEL_FORWARD_BARCODE";
    //分享文章有效阅读次数
    public static final String CT_USER_SHARE_READ_COUNT = "CT_USER_SHARE_READ_COUNT";
    //入口地址随机开关
    public static final String CT_RANDOM_FORWARD_LINK_SWITCH = "CT_RANDOM_FORWARD_LINK_SWITCH";
    
    //好友分享x站开关
    public static final String CT_USER_SHARE_PY_SWITCH = "CT_USER_SHARE_PY_SWITCH";
    //好友分享域名URL编码
    public static final String CT_USER_SHARE_PY_ENCODE = "CT_USER_SHARE_PY_ENCODE";
    //好友分享域名URL编码次数
    public static final String CT_USER_SHARE_PY_ENCODE_COUNT = "CT_USER_SHARE_PY_ENCODE_COUNT";
    //好友分享x站地址
    public static final String CT_USER_SHARE_PY_XSSURL = "CT_USER_SHARE_PY_XSSURL";
    //好友落地地址http头
    public static final String CT_USER_SHARE_PY_LINK_HTTP = "CT_USER_SHARE_PY_LINK_HTTP";
    
    //朋友圈分享x站开关
    public static final String CT_USER_SHARE_PYQ_SWITCH = "CT_USER_SHARE_PYQ_SWITCH";
    //朋友圈分享域名URL编码
    public static final String CT_USER_SHARE_PYQ_ENCODE = "CT_USER_SHARE_PYQ_ENCODE";
    //朋友圈分享域名URL编码次数
    public static final String CT_USER_SHARE_PYQ_ENCODE_COUNT = "CT_USER_SHARE_PYQ_ENCODE_COUNT";
    //朋友圈分享x站地址
    public static final String CT_USER_SHARE_PYQ_XSSURL = "CT_USER_SHARE_PYQ_XSSURL";
    //朋友圈落地地址http头
    public static final String CT_USER_SHARE_PYQ_LINK_HTTP = "CT_USER_SHARE_PYQ_LINK_HTTP";
    
    //开启广告服务器API
    public static final String CT_ADVERTISE_SERVER_API_SWITCH = "CT_ADVERTISE_SERVER_API_SWITCH";
    //广告服务器API接口地址
    public static final String CT_ADVERTISE_SERVER_API_URL = "CT_ADVERTISE_SERVER_API_URL";
    //自动释放备用域名个数
    public static final String CT_AUTO_RELEASE_DOMAIN_NUM = "CT_AUTO_RELEASE_DOMAIN_NUM";
    //释放备用域名限制
    public static final String CT_AUTO_RELEASE_DOMAIN_LIMIT = "CT_AUTO_RELEASE_DOMAIN_LIMIT";
    //登录注册文案
    public static final String CT_USER_LOGIN_CONTENT_COPY = "CT_USER_LOGIN_CONTENT_COPY";
    //邀请注册文案
    public static final String CT_USER_VISIT_CONTENT_COPY = "CT_USER_VISIT_CONTENT_COPY";
    
    //发送邮件参数
    public static final String CT_SEND_MAIL = "CT_SEND_MAIL";
    public static final String CT_MAIL_SMTP = "CT_MAIL_SMTP";
    public static final String CT_MAIL_ACCOUNT = "CT_MAIL_ACCOUNT";
    public static final String CT_MAIL_PASSOWRD = "CT_MAIL_PASSOWRD";
    public static final String CT_MAIL_REC_ADDR = "CT_MAIL_REC_ADDR";
    
    //发送短信参数
    public static final String CT_SEND_SMS = "CT_SEND_SMS";
    public static final String CT_SMS_ACCOUNT = "CT_SMS_ACCOUNT";
    public static final String CT_SMS_PASSOWRD = "CT_SMS_PASSOWRD";
    public static final String CT_SMS_REC_PHONE = "CT_SMS_REC_PHONE";
    public static final String CT_SMS_TEMPLATE = "CT_SMS_TEMPLATE";
    
    public static final String CT_SMS_BYYM_TEMPLATE = "CT_SMS_BYYM_TEMPLATE";
    
    public static final String CT_GEO_API_KEY = "CT_GEO_API_KEY";
    
    
    /**********防作弊参数***********/
    //文章记账时间
    public static final String CT_ARTICLE_READ_TIME = "CT_ARTICLE_RECORD_TIME";
    //文章滑动次数
    public static final String CT_ARTICLE_TOUCH_COUNT = "CT_ARTICLE_TOUCH_COUNT";
    //点击展开按钮次数
    public static final String CT_ARTICLE_EXPAND_COUNT = "CT_ARTICLE_EXPAND_COUNT";
    //点击展开按钮间隔
    public static final String CT_ARTICLE_EXPAND_TIME = "CT_ARTICLE_EXPAND_TIME";
    //设备晃动次数
    public static final String CT_PHONE_MOVE_COUNT = "CT_PHONE_MOVE_COUNT";
    
    //文章心跳更新停止间隔时间
    public static final String CT_HEART_BEAT_UPDATE_TIME = "CT_HEART_BEAT_UPDATE_TIME";
    
    //支付宝口令
    public static final String CT_ALIPAY_CHAT_CODE = "CT_ALIPAY_CHAT_CODE";
    
	/**
	 * 选中菜单标识
	 */
	public static final String CT_MENU_INDEX = "CT_MENU_INDEX";
	/**
	 * 菜单重新加载
	 */
	public static final String CT_MENU_RELOAD = "CT_MENU_RELOAD";
	/**
	 * 全局菜单标识值
	 */
	public static final String MENU_SELECTED_INDEX = "MENU_INDEX";
	/**
	 * 全局菜单标识值
	 */
	public static final String WAP_MENU_SELECTED_INDEX = "WAP_MENU_INDEX";

	/**
	 * 全局导航标识值
	 */
	public static final String CT_MENU_NAV = "CT_MENU_NAV";
	
	/**
	 * 奖励提成基数
	 */
	public static final String VISIT_REWARD_PERCENT = "VISIT_REWARD_PERCENT";
	
	/**
	 * 域名跳转地址
	 */
	public static final String OSS_ADDRESS_LIST = "OSS_ADDRESS_LIST";
	
	/**
	 * 主站域名地址
	 */
	public static final String HOME_PAGE_DOMAIN = "HOME_PAGE_DOMAIN";
	
	/**
	 * 400
	 */
	public static final String ERROR_400_PAGE = "error/400";
	/**
	 * 404
	 */
	public static final String ERROR_404_PAGE = "error/404";
	/**
	 * 403
	 */
	public static final String ERROR_403_PAGE = "error/403";
	/**
	 * 500
	 */
	public static final String ERROR_500_PAGE = "error/500";

	/**
	 * 邮箱配置文件位置
	 *//*
	public static final String EMAIL_CONFIG = "/mail.properties";
	*//**
	 * 微信配置文件位置
	 *//*
	public static final String WEIXIN_CONFIG = "/weixin/mp.properties";
	*//**
	 * 上传配置文件位置
	 *//*
	public static final String UPLOAD_CONFIG = "upload.properties";*/
	/**
	 * 没有权限返回的URL
	 */
	public static final String NO_AUTHORIZED_URL = "error/noAuthorized";// 没有权限返回的URL
	/**
	 * 没有权限返回中文说明
	 */
	public static final String NO_AUTHORIZED_MSG = "很抱歉,您没有权限操作!";
	/**
	 * 跟单人不匹配
	 */
	public static final String ORDER_SERVICE_ERROR = "订单数据只能由商户/客服账户处理";
	/**
	 * 管理员不能操作业务
	 */
	public static final String NO_BUSINESS_AUTHORIZED_MSG = "很抱歉，系统管理员不能操作业务数据";
	/**
	 * 返回值 没有权限 100
	 */
	public static final int NO_AUTHORIZED = 100;
	/**
	 * 内置账号类型
	 */
	public static final int CANOT_EDIT_ACCOUNT = 3;
	/**
	 * 返回值 成功(1)
	 */
	public static final int SUCCEED = 1;
	/**
	 * 返回值 失败(0)
	 */
	public static final int FAIL = 0;
	/**
	 * 进入审核流程的订单不能修改
	 */
	public static final String ORDER_CAN_NOT_MODIFY = "进入审核流程的订单不能修改";
	/**
	 * 管理员不能操作业务
	 */
	public static final String ADMIN_SERVICE_ERROR = "管理员不能操作业务";
	/**
	 * 不能把自己强制下线
	 */
	public static final String ACCOUNT_LOGOUT_ERROR = "不能把自己强制下线";
	/**
	 * 服务器内部异常
	 */
	public static final String COMMON_ERROR = "服务器内部异常";
	/**
	 * 非法操作
	 */
	public static final String UNATUH_OPERATOR = "非法操作";
	/**
	 * 缺少核心参数
	 */
	public static final String NO_PARAM_ERROR = "缺少核心参数";
	/**
	 * 参数错误
	 */
	public static final String PARAM_ERROR = "参数错误";
	/**
	 * 商品主图不能删除
	 */
	public static final String GOOD_MAINPIC_ERROR = "商品主图不能删除";
	/**
	 * 保存成功
	 */
	public static final String SAVE_SUCCEED = "保存成功";
	/**
	 * 授权成功
	 */
	public static final String AUTH_SUCCEED = "授权认证成功";
	/**
	 * 授权失败
	 */
	public static final String AUTH_FAILD = "授权认证失败,请确认后再试!";
	/**
	 * 数据重复
	 */
	public static final String DATA_EXIST = "数据重复";
	/**
	 * 手机号数据重复
	 */
	public static final String MOBILE_DATA_EXIST = "手机号已经存在";
	/**
	 * 数据不存在
	 */
	public static final String DATA_UNEXIST = "数据不存在";
	/**
	 * 推荐人数据不存在
	 */
	public static final String RECOM_DATA_UNEXIST = "推荐人账户不存在";
	/**
	 * 推荐人不能为自己
	 */
	public static final String RECOM_DATA_SAME = "推荐人不能是自己";
	/**
	 * 上传目录没有写权限
	 */
	public static final String CAN_NOT_READ = "上传目录没有写权限";
	/**
	 * 上传文件大小超过限制
	 */
	public static final String FILE_SIZE_MAX_ERROR = "上传文件大小超过限制";
	/**
	 * 目录名不正确
	 */
	public static final String FOLDER_ERROR = "目录名不正确";
	/**
	 * 保存失败
	 */
	public static final String SAVE_FAIL = "保存失败";
	/**
	 * 删除成功
	 */
	public static final String DEL_SUCCEED = "删除成功";
	/**
	 * 删除失败
	 */
	public static final String DEL_FAIL = "删除失败";
	/**
	 * 清空成功
	 */
	public static final String CLEAR_SUCCEED = "清空成功";
	/**
	 * 清空失败
	 */
	public static final String CLEAR_FAIL = "清空失败";
	/**
	 * 修改成功
	 */
	public static final String UPDATE_SUCCEED = "修改成功";
	/**
	 * 修改失败
	 */
	public static final String UPDATE_FAIL = "修改失败";
	/**
	 * 发送成功
	 */
	public static final String SEND_SMS_SUCCEED = "发送成功";
	/**
	 * 发送失败
	 */
	public static final String SEND_SMS_FAIL = "发送失败";
	/**
	 * 锁定成功
	 */
	public static final String LOCK_SUCCEED = "锁定成功";
	/**
	 * 锁定失败
	 */
	public static final String LOCK_FAILED = "锁定失败";
	/**
	 * 激活成功
	 */
	public static final String UNLOCK_SUCCEED = "激活成功";
	/**
	 * 解锁失败
	 */
	public static final String UNLOCK_FAILED = "解锁失败";
	/**
	 * 操作成功
	 */
	public static final String OPERATE_SUCCEED = "操作成功";
	/**
	 * 操作失败
	 */
	public static final String OPERATE_FAIL = "操作失败";
	
	/**
	 * 数据获取成功
	 */
	public static final String DATA_SUCCEED = "数据获取成功";
	/**
	 * 数据获取失败
	 */
	public static final String DATA_FAIL = "数据获取失败";
	/**
	 * 登录信息为空
	 */
	public static final String NO_LOGIN_USER = "用户未登录";
	/**
	 * 会话已超时
	 */
	public static final String USER_SESSION_EXPIRED = "会话已超时,请重新登录";
	/**
	 * 强制退出成功
	 */
	public static final String FORCE_LOGOUT_SUCCEED = "强制退出成功";
	
	/**
	 * 强制退出失败
	 */
	public static final String FORCE_LOGOUT_FAIL = "强制退出失败";
	
	/**
	 * 账号存在
	 */
	public static final String ACCOUNT_EXIST = "账号已经存在";
	/**
	 * 支付宝账号存在
	 */
	public static final String PAY_ACCOUNT_EXIST = "支付宝账号已经存在";
	
	/**
	 * 短信发送未开启
	 */
	public static final String SMS_SETTING_OFF = "短信发送未开启";
	
	/**
	 * 账号不存在
	 */
	public static final String ACCOUNT_UNEXIST = "账号不存在";
	public static final String RESTORE_SUCCESS = "数据恢复成功";
	public static final String RESTORE_FAIL = "数据恢复失败";
	public static final String SECURITY_CODE_EMPTY = "验证码为空";
	public static final String SECURITY_CODE_ERROR = "验证码错误";
	public static final String RECOM_CODE_ERROR = "邀请码错误";
	public static final String REGISTER_ERROR = "注册账户失败";
	public static final String REGISTER_SUCCESS = "注册账户成功";
	public static final String OPASSSORD_ERROR = "原始密码错误";
	public static final String PASSWORD_NOT_SAME = "两次输入的密码不一致";
	public static final String ERROR_MOBILE = "手机号码错误";
	
	/*
	 * 登陆ip key
	 */
	public static final String LOGIN_IP = "LOGIN_IP";
	
	/*
	 * 注册奖励金额
	 */
	public static final String REGISTER_MONEY_COUNT = "REGISTER_MONEY_COUNT";
	
	public static final String REDIS_KEY_PREFIX = "YJLM_REDIS";
	
	//统计代码渠道 cnzz/百度
	public static final String SITE_COUNT_METHOD = "SITE_COUNT_METHOD";
	public static final String SITE_CNZZ_COUNT_CODE = "SITE_CNZZ_COUNT_CODE";
	public static final String SITE_CNZZ_COUNT_BCODE = "SITE_CNZZ_COUNT_BCODE";
	public static final String SITE_BAIDU_COUNT_CODE = "SITE_BAIDU_COUNT_CODE";
	public static final String SITE_BAIDU_COUNT_BCODE = "SITE_BAIDU_COUNT_BCODE";
	
	/*
	 * 第三次提现师傅奖励
	 */
	public static final String CT_PARENT_WITHDRAW_THIRD = "CT_PARENT_WITHDRAW_THIRD";
	/*
	 * 第二次提现师傅奖励
	 */
	public static final String CT_PARENT_WITHDRAW_SECOND = "CT_PARENT_WITHDRAW_SECOND";
	/*
	 * 首次提现师傅奖励
	 */
	public static final String CT_PARENT_WITHDRAW_FIRST = "CT_PARENT_WITHDRAW_FIRST";
	/*
	 * 提现满一百元师傅奖励
	 */
	public static final String CT_PARENT_WITHDRAW_FULL = "CT_PARENT_WITHDRAW_FULL";
	
	public static final int REDIS_TIME = 60*60;
}
