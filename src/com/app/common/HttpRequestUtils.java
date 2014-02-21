package com.app.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.man.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;

public class HttpRequestUtils {
	public static String BASE_HTTP_CONTEXT = "http://192.168.1.101:8080/manWear/";

	public static String BUNDLE_KEY_ISPOST = "isPost";
	public static String BUNDLE_KEY_HTTPURL = "httpUrl";
	public static String BUNDLE_KEY_PARAMS = "params";

	public static String getResFromHttpUrl(Message msg) {
		// 先获取传入的参数
		Bundle bundle = msg.getData();
		Boolean isPost = bundle.getBoolean(BUNDLE_KEY_ISPOST);
		String httpUrl = bundle.getString(BUNDLE_KEY_HTTPURL);
		String params = bundle.getString(BUNDLE_KEY_PARAMS);
		return getResFromHttpUrl(isPost, httpUrl, params);
	}

	public static String getNetworkWrongStr() {
		// JSONObject jsonObject = new JSONObject();
		// try {
		// jsonObject.put("success", false);
		// jsonObject.put("errorCode", -1001);
		// jsonObject.put("errorMessage", R.string.base_response_error);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// return jsonObject.toString();
		return "{" + "\"success\": false" + ",\"errorCode\": \"-1001\""
				+ ",\"errorMessage\": \"" + R.string.base_response_error + "\""
				+ "}";

	}

	public static void main(String[] args) {
		System.out.println(getNetworkWrongStr());
	}

	/**
	 * 根据参数，获取对应的http返回的内容
	 * 
	 * @param isPost
	 * @param httpUrl
	 * @param params
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getResFromHttpUrl(Boolean isPost, String httpUrl,
			String params) {
		String resultStr;
		// 校验关键参数的非空
		if (isPost == null) {
			return null;
		}
		if (httpUrl == null || httpUrl.trim().length() == 0) {
			return null;
		}
		if (params == null) {
			params = "";
		}
		// 目前测试，在2.2以上的模拟器里，不加下面这段，会报错，主要意思就是在主线程中不能去执行这种可能引起堵塞的http的请求
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		if (isPost) {
			resultStr = HttpRequestUtils.sendPost(httpUrl, params);
		} else {
			resultStr = HttpRequestUtils.sendGet(httpUrl);
		}
		return resultStr;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// // 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getNetworkWrongStr();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

}