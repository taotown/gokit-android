package com.xpg.gokit.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.xpg.gokit.sdk.MessageCenter;
import com.xpg.gokit.setting.SettingManager;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiDeviceList;
import com.xtremeprog.xpgconnect.XPGWifiDeviceListener;
import com.xtremeprog.xpgconnect.XPGWifiQueryHardwareInfoStruct;
import com.xtremeprog.xpgconnect.XPGWifiSDKListener;
import com.xtremeprog.xpgconnect.XPGWifiSSIDList;

/**
 * 所有activity的基类。该基类实现了XPGWifiDeviceListener和XPGWifiSDKListener两个监听器，并提供全局的回调方法。
 * 
 * @author Lien Li
 * */
public class BaseActivity extends Activity {
	protected ActionBar actionBar;
	protected static XPGWifiDeviceList xpgwifidevicelist;
	protected static List<XPGWifiDevice> deviceslist = new ArrayList<XPGWifiDevice>();
	static boolean isInit = false;
	protected MessageCenter mCenter;
	protected SettingManager setmanager;
	/**
	 * XPGWifiDeviceListener
	 * <P>
	 * 设备属性监听器。 设备连接断开、获取绑定参数、获取设备信息、控制和接受设备信息相关
	 * */
	protected XPGWifiDeviceListener deviceListener = new XPGWifiDeviceListener() {
		public void onBindDevice(int error, String errorMessage) {
			BaseActivity.this.onBindDevice(error, errorMessage);
		};

		public void onUnbindDevice(int error, String errorMessage) {
			BaseActivity.this.onUnbindDevice(error, errorMessage);
		};


		public void onConnectFailed() {
			BaseActivity.this.onConnectFailed();
		};

		public void onLogin(int result) {
			BaseActivity.this.onLogin(result);
		};

		public void onReceiveAlertsAndFaultsInfo(
				com.xtremeprog.xpgconnect.Vector_XPGWifiReceiveInfo alerts,
				com.xtremeprog.xpgconnect.Vector_XPGWifiReceiveInfo faults) {
			BaseActivity.this.onReceiveAlertsAndFaultsInfo(alerts, faults);
		};

		public void onDeviceOnline(boolean isOnline) {
			BaseActivity.this.onDeviceOnline(isOnline);
		};

		public void onGetPasscode(int result) {
			BaseActivity.this.onGetPasscode(result);
		};;

		public void onDisconnected() {
			BaseActivity.this.onDisconnected();
		}

		public boolean onReceiveData(String data) {
			return BaseActivity.this.onReceiveData(data);
		};

		public void onConnected() {
			BaseActivity.this.onConnected();
		}


		public void onLoginMQTT(int result) {
			BaseActivity.this.onLoginMQTT(result);
		}

		@Override
		public void onQueryHardwareInfo(int error,
				XPGWifiQueryHardwareInfoStruct pInfo) {
			BaseActivity.this.onQueryHardwareInfo(error, pInfo);
		}
		
		
	};
	/**
	 * XPGWifiSDKListener
	 * <P>
	 * sdk监听器。 配置设备上线、注册登录用户、搜索发现设备、用户绑定和解绑设备相关
	 * */
	private XPGWifiSDKListener sdkListener = new XPGWifiSDKListener() {

		
		public void onChangeUserPassword(int error, String errorMessage) {
			BaseActivity.this.onChangeUserPassword(error, errorMessage);
		};

		@Override
		public void onRequestSendVerifyCode(int error, String errorMessage) {
			BaseActivity.this.onRequestSendVerifyCode(error, errorMessage);
		};

		public void onDiscovered(int result, XPGWifiDeviceList devices) {
			BaseActivity.this.onDiscovered(result, devices);
		}

		public void onGetDeviceInfo(int error, String errorMessage,
				String productKey, String did, String mac, String passCode,
				String host, int port, int isOnline) {
			BaseActivity.this.onGetDeviceInfo(error, errorMessage, productKey,
					did, mac, passCode, host, port, isOnline);
		};


		public void onRegisterUser(int error, String errorMessage, String uid,
				String token) {
			BaseActivity.this.onRegisterUser(error, errorMessage, uid, token);
		};


		public void onUserLogin(int error, String errorMessage, String uid,
				String token) {
			BaseActivity.this.onUserLogin(error, errorMessage, uid, token);
		};

		@Override
		public void onSetAirLink(XPGWifiDevice device) {
			Log.e("airlink", "success");
			BaseActivity.this.onSetAirLink(device);
		}

		public void onGetSSIDList(XPGWifiSSIDList list, int result) {
			BaseActivity.this.onGetSSIDList(list, result);
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setmanager = new SettingManager(this);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		mCenter = MessageCenter.getInstance(this.getApplicationContext());
		mCenter.getXPGWifiSDK().setListener(sdkListener);
	}

	/**
	 * 通过did和mac在列表寻找对应的device
	 * */
	public static XPGWifiDevice findDeviceByMac(String mac, String did) {
		XPGWifiDevice xpgdevice = null;
		Log.i("count", BaseActivity.deviceslist.size() + "");
		for (int i = 0; i < BaseActivity.deviceslist.size(); i++) {
			XPGWifiDevice device = deviceslist.get(i);
			if (device != null) {
				Log.i("deivcemac", device.GetMacAddress());
				if (device != null && device.GetMacAddress().equals(mac)
						&& device.GetDid().equals(did)) {
					xpgdevice = device;
					break;
				}
			}

		}

		return xpgdevice;
	}

	public void onResume() {
		super.onResume();

	}

	public void onPause() {
		super.onPause();

	}



	/**
	 * 修改用户名密码回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            结果信息
	 * */
	protected void onChangeUserPassword(int error, String errorMessage) {
	};

	/**
	 * 发送验证码结果回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            结果信息
	 * */
	protected void onRequestSendVerifyCode(int error, String errorMessage) {
	};

	protected void onDiscovered(int result, XPGWifiDeviceList devices) {

	}

	protected void onGetDeviceInfo(int error, String errorMessage,
			String productKey, String did, String mac, String passCode,
			String host, int port, int isOnline) {
	};


	/**
	 * 用户注册结果回调接口
	 * 
	 * @param error
	 *            注册结果码
	 * @param errorMessage
	 *            注册结果信息
	 * @param uid
	 *            用户id
	 * @param token
	 *            用户令牌
	 * */
	protected void onRegisterUser(int error, String errorMessage, String uid,
			String token) {
	};


	/**
	 * 用户登陆结果回调接口
	 * 
	 * @param error
	 *            登陆结果码
	 * @param errorMessage
	 *            登陆结果信息
	 * @param uid
	 *            用户id
	 * @param token
	 *            用户令牌
	 * */
	protected void onUserLogin(int error, String errorMessage, String uid,
			String token) {
	};

	protected void onUpdateProduct(int result) {
	};

	/**
	 * 通过airlink成功配置模块后，回调该函数。
	 * 
	 * @param device
	 *            成功配置的设备实体
	 * */
	protected void onSetAirLink(XPGWifiDevice device) {
	}

	protected void onGetSSIDList(XPGWifiSSIDList list, int result) {
	};
	
	/**
	 * 绑定设备结果回调
	 * @param error 错误码
	 * @param errorMessage 错误信息
	 * */
	public void onBindDevice(int error, String errorMessage) {
	};

	/**
	 * 解绑设备结果回调
	 * @param error 错误码
	 * @param errorMessage 错误信息
	 * */
	public void onUnbindDevice(int error, String errorMessage) {
	};


	/**
	 * socket 连接失败
	 * */
	public void onConnectFailed() {
	};

	/**
	 * 小循环授权登陆设备
	 * @param result 返回结果 0＝成功 2=失败
	 * */
	public void onLogin(int result) {
	};
	
	/**
	 * 获取模块和mcu协议版本等属性
	 * */
	public void onQueryHardwareInfo(int error,
			com.xtremeprog.xpgconnect.XPGWifiQueryHardwareInfoStruct pInfo) {
	};
    
	/**
	 * 设备报警回调
	 * */
	public void onReceiveAlertsAndFaultsInfo(
			com.xtremeprog.xpgconnect.Vector_XPGWifiReceiveInfo alerts,
			com.xtremeprog.xpgconnect.Vector_XPGWifiReceiveInfo faults) {
	};

	/**
	 * 设备上下线通知
	 * */
	public void onDeviceOnline(boolean isOnline) {
	};

	/**
	 * 获取Passcode
	 * */
	public void onGetPasscode(int result) {
	};;

	/**
	 * socket 连接被断开
	 * */
	public void onDisconnected() {
	}

	/**
	 * 收到设备数据
	 * */
	public boolean onReceiveData(String data) {
		return true;
	};

	/**
	 * socket 连接成功
	 * */
	public void onConnected() {
	}


	/**
	 * 大循环授权登陆结果
	 * @param result 结果码
	 * */
	public void onLoginMQTT(int result) {
	}

}
