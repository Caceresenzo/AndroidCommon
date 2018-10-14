package caceresenzo.android.libs.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import caceresenzo.libs.exception.UtilityClassCantBeInstanced;

/**
 * Network Utilities
 * 
 * @author Enzo CACERES
 */
public class NetworkUtils {
	
	/* Constructor */
	private NetworkUtils() {
		throw new UtilityClassCantBeInstanced();
	}
	
	/**
	 * @param context
	 *            Application context
	 * @return A {@link ConnectivityManager} instance
	 * @throws NullPointerException
	 *             If the context is null
	 */
	public static ConnectivityManager getConnectivityManager(Context context) {
		if (context == null) {
			throw new NullPointerException("Context can't be null.");
		}
		
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	/**
	 * Get the network info
	 *
	 * @param context
	 *            Application context
	 * @return {@link ConnectivityManager#getActiveNetworkInfo()}
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		return getConnectivityManager(context).getActiveNetworkInfo();
	}
	
	/**
	 * Check if there is any connectivity
	 *
	 * @param context
	 *            Application context
	 * @return If the device is connected to the internet (mobile or wifi)
	 */
	public static boolean isConnected(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected());
	}
	
	/**
	 * Check if there is any connectivity to a Wifi network
	 *
	 * @param context
	 *            Application context
	 * @return If the device is connected to the internet by wifi
	 */
	public static boolean isConnectedWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
	}
	
	/**
	 * Check if there is any connectivity to a mobile network
	 *
	 * @param context
	 *            Application context
	 * @return If the device is connected to the internet by mobile data
	 */
	public static boolean isConnectedMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
	}
	
	/**
	 * Check if there is fast connectivity
	 *
	 * @param context
	 * @return
	 */
	public static boolean isConnectedFast(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected() && isConnectionFast(info.getType(), info.getSubtype()));
	}
	
	/**
	 * Check if the connection is fast
	 *
	 * @param type
	 *            Connectivity type
	 * @param subType
	 *            Network type
	 * @return If the transfer speed is above 100 kbps
	 */
	public static boolean isConnectionFast(int type, int subType) {
		if (type == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		
		if (type == ConnectivityManager.TYPE_MOBILE) {
			switch (subType) {
				/* ~ 50-100 kbps */
				case TelephonyManager.NETWORK_TYPE_1xRTT: {
					return false;
				}
				
				/* ~ 14-64 kbps */
				case TelephonyManager.NETWORK_TYPE_CDMA: {
					return false;
				}
				
				/* ~ 50-100 kbps */
				case TelephonyManager.NETWORK_TYPE_EDGE: {
					return false;
				}
				
				/* ~ 400-1000 kbps */
				case TelephonyManager.NETWORK_TYPE_EVDO_0: {
					return true;
				}
				
				/* ~ 600-1400 kbps */
				case TelephonyManager.NETWORK_TYPE_EVDO_A: {
					return true;
				}
				
				/* ~ 100 kbps */
				case TelephonyManager.NETWORK_TYPE_GPRS: {
					return false;
				}
				
				/* ~ 2-14 Mbps */
				case TelephonyManager.NETWORK_TYPE_HSDPA: {
					return true;
				}
				
				/* ~ 700-1700 kbps */
				case TelephonyManager.NETWORK_TYPE_HSPA: {
					return true;
				}
				
				/* ~ 1-23 Mbps */
				case TelephonyManager.NETWORK_TYPE_HSUPA: {
					return true;
				}
				
				/* ~ 400-7000 kbps */
				case TelephonyManager.NETWORK_TYPE_UMTS: {
					return true;
				}
				
				/* Above API level 7, make sure to set android:targetSdkVersion to appropriate level to use these */
				/* ~ 1-2 Mbps */
				case TelephonyManager.NETWORK_TYPE_EHRPD: { /* API level 11 */
					return true;
				}
				
				/* ~ 5 Mbps */
				case TelephonyManager.NETWORK_TYPE_EVDO_B: { /* API level 9 */
					return true;
				}
				
				/* ~ 10-20 Mbps */
				case TelephonyManager.NETWORK_TYPE_HSPAP: { /* API level 13 */
					return true;
				}
				
				/* ~25 kbps */
				case TelephonyManager.NETWORK_TYPE_IDEN: { /* API level 8 */
					return false;
				}
				
				/* ~ 10+ Mbps */
				case TelephonyManager.NETWORK_TYPE_LTE: { /* API level 11 */
					return true;
				}
				
				/* Unknown */
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				default: {
					return false;
				}
			}
		}
		
		return false;
	}
	
}