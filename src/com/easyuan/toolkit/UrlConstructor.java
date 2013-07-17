package com.easyuan.toolkit;

public class UrlConstructor {
	private static String baseUrl = "http://api.easyuan.com/rest/";
	
	public static String WishNewerRequestUrl() {
		return baseUrl+"wish/newer";
	}

	public static String WishOlderRequestUrl() {
		return baseUrl+"wish/older";
	}
	
	public static String WishListRequestUrl() {
		return baseUrl+"wish";
	}
	
	public static String ReleaseWishUrl() {
		return baseUrl+"wish/";
	}
	
	public static String LoginRequestUrl() {
		return baseUrl+"token";
	}
	
	public static String UserInfoRequestUrl() {
		return baseUrl+"user/";
	}
	
	public static String AdoptRequestUrl() {
		return baseUrl+"wish/";
	}
	
	public static String RegisterRequestUrl() {
		return baseUrl+"user";
	}
	
	public static String RegisterInfoRequsetUrl() {
		return baseUrl+"user/me";
	}
	
	public static String LeaveMsgUrl(int id) {
		return baseUrl+"wish/"+id+"/message";
	}
	
	public static String GetMsgUrl(int id) {
		return baseUrl+"wish/"+id+"/message";
	}
	
	public static String AfrimWishUrl(int id) {
		return baseUrl+"wish/"+id;
	}
	
	public static String MyAdoptWishUrl() {
		return baseUrl+"wish/adopter/me/";
	}
	
	public static String MyReleaseWishUrl() {
		return baseUrl+"wish/me/";
	}
	
	public static String PushUrl() {
		return "ws://api.easyuan.com:4321/";
	}
}
