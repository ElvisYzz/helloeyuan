package com.easyuan.toolkit;

public class Constant {
	public static final String EWISH_VERSION = "1.1";
	//默认游客令牌
	public static final String UNKNOWN = "Unknown";
	//sharePreferences key
	public static final String TAG = "EWish";
	public static final String FILTER_STATE = "filter";
	public static final String PUSH_SERVICE = "PushService";
	public static final String LOGIN_STATE = "LogInState";
	public static final String LATEST_WISH_UPDATE_TIME = "LatestUpdateTime";
	public static final String OLDEST_WISH_UPDATE_TIME = "OldestUpdateTime";
	public static final String USER_ID = "UserId";
	public static final String USER_TOKEN = "UserToken";
	public static final String DEVICE_ID = "DeviceID";
	//完善信息后回传用户信息
	public static final int GET_USERINFO  = 1;
	public static final int RESULT_USERINFO = 1;
	//登录状态
	//已登录
	public static final int LOGGED = 1;
	//未登录
	public static final int UNLOGGED = 0;
	
	//愿望状态
	//未认领
	public static final int UNADOPT = 1;
	//已认领
	public static final int ADOPTED = 2;
	//已实现
	public static final int REALLIZED = 3;
	
	//性别ID
	public static final int FAMALE = 0;
	public static final int MALE = 1;
	
	//自定义请求头部
	public static final String latestTime = "Eyuan-Latest-Wish-Update-Time";
	public static final String oldestTime = "Eyuan-Oldest-Wish-Update-Time";
	public static final String wishCount = "Eyuan-Expect-Wish-Count";
	public static final String ifModified = "Eyuan-If-Modified-Since";
	//请求content-type
	public static final String contentType = "application/json";
	//HTTP请求状态码定义
	//用户登录成功
	public static final int LOGIN_SUCCESS = 200;
	//用户名密码错误
	public static final int USERNAME_PWD_ERROR = 401;
	
	//获取个人资料成功
	public static final int REQUEST_MINEINFO_SUCCESS = 200;
	//获取用户资料成功
	public static final int REQUEST_USERINFO_SUCCESS = 200;
	//注册成功
	public static final int REGISTER_SUCCESS = 200;
	//注册失败，用户名已存在
	public static final int REGISTER_ERROR = 400;
	//用户信息修改成功
	public static final int USERINFO_ALTER_SUCCESS = 200;
	//用户请求验证邮箱，验证邮件发送成功
	public static final int EMAIL_CONFIRM_SUCCESS = 200;
	//用户请求找回密码，邮件发送成功
	public static final int EMAIL_ALTER_SUCCESS = 200;
	
	//获取愿望列表成功
	public static final int REQUEST_WISHLIST_SUCCESS = 200;
	//获取我发布的所有愿望成功
	public static final int REQUEST_MYWISH_SUCCESS = 200;
	//获取我认领的所有愿望成功
	public static final int REQUEST_MYADOPTEDWISH_SUCCESS = 200;
	
	//认领愿望成功
	public static final int ADOPT_WISH_SUCCESS = 200;
	//认领愿望失败，愿望是自己的或者愿望已被认领
	public static final int ADOPT_WISH_ERROR = 300;

	//确认任务成功
	public static final int CONFIRM_WISH_SUCCESS = 200;
	//确认任务失败，愿望不是自己的或者愿望未被领取
	public static final int CONFIRM_WISH_ERROR = 403;

	//愿望延期成功
	public static final int DELAY_WISH_SUCCESS = 200;
	//愿望延期失败，validTimespan小于已有值
	public static final int DELAY_WISH_ERROR = 403;
	
	//愿望不存在
	public static final String REQUEST_WISH_INEXIST = "404";
	
	//发布愿望成功
	public static final int RELEASE_WISH_SUCCESS = 200;
	
	//获取留言成功
	public static final int REQUEST_COMMENTS_SUCCESS = 200;
	//获取留言失败，留言不存在
	public static final int REQUEST_COMMENTS_INEXIST = 404;
	
	//对愿望留言成功
	public static final int RELEASE_COMMENTS_SUCCESS = 200;
	//对愿望留言失败，愿望不允许留言
	public static final int RELEASE_COMMENTS_ERROR = 403;
}
