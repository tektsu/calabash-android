package sh.calaba.instrumentationbackend.actions.system;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

/**
* <p>You can enable/disable wifi with this action.</p>
*
* You need to add the following permissions to the AndroidManifest.xml for the app under test:
* <uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
* <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
* <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
* <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
*
*/
public class Wifi implements Action {

	@Override
	public Result execute(String... args) {
		String command = args[0];
		WifiManager wifiManager = (WifiManager)InstrumentationBackend.instrumentation.getContext().getSystemService(Context.WIFI_SERVICE);

		String message = "";

		if (command.equals("status")) {
			if (wifiManager.isWifiEnabled()) {
				Log.d("WIFI", "Status: Enabled");
				message = "Wifi is enabled";
			}
			else {
				Log.d("WIFI", "Status: Disabled");
				message = "Wifi is disabled";
			}
		}

		else if (command.equals("enable")) {
			if (wifiManager.isWifiEnabled()) {
				Log.d("WIFI", "Wifi is already enabled");
				message = "Wifi is already enabled";
			}
			else {
				wifiManager.setWifiEnabled(true);
				Log.d("WIFI", "Enabled");
				message = "Wifi is enabled";
			}
		}

		else if (command.equals("disable")) {
			if (!wifiManager.isWifiEnabled()) {
				Log.d("WIFI", "Already disabled");
				message = "Wifi is already disabled";
			}
			else {
				wifiManager.setWifiEnabled(false);
				Log.d("WIFI", "Disabled");
				message = "Wifi is disabled";
			}
		}

		else {
			return new Result(false, "Invalid command [" + command + "]");
		}

		Result result = Result.successResult();
		result.setMessage(message);
		return result;
	}

	@Override
	public String key() {
		return "wifi";
	}
}

