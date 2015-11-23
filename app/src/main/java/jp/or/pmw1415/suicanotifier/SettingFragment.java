package jp.or.pmw1415.suicanotifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by pmw1415 on 2015/11/22.
 */
public class SettingFragment extends PreferenceFragment
		implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
	private Context mContext;
	// key
	private String mKeyNfcSetting;
	private String mKeyNotificationEnabled;
	private String mKeyKeepNotification;

	private boolean mBeforeSettingEnabled;
	private boolean mBeforeSettingKeep;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);

		mContext = this.getActivity();
		mKeyNfcSetting = mContext.getString(R.string.nfc_setting_key);
		mKeyNotificationEnabled = this.getString((R.string.notification_enabled_key));
		mKeyKeepNotification = this.getString((R.string.keep_notification_key));

		Preference prefButtonNfc = (Preference)findPreference(mKeyNfcSetting);
		prefButtonNfc.setOnPreferenceClickListener(this);
		CheckBoxPreference prefEnabled = (CheckBoxPreference)findPreference(mKeyNotificationEnabled);
		prefEnabled.setOnPreferenceChangeListener(this);
		CheckBoxPreference prefKeep = (CheckBoxPreference)findPreference(mKeyKeepNotification);
		prefKeep.setOnPreferenceChangeListener(this);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		mBeforeSettingEnabled = sharedPref.getBoolean(mKeyNotificationEnabled, false);
		mBeforeSettingKeep = sharedPref.getBoolean(mKeyKeepNotification, false);
	}

	@Override
	public void onStop() {
		super.onStop();

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		boolean nowSettingEnabled = sharedPref.getBoolean(mKeyNotificationEnabled, false);
		boolean nowSettingKeep = sharedPref.getBoolean(mKeyKeepNotification, false);

		if (nowSettingEnabled != mBeforeSettingEnabled ||
				nowSettingKeep != mBeforeSettingKeep) {
			// Notification非表示
			NotificationController notificationController = new NotificationController(mContext);
			notificationController.setNotification(false, null);

			if (nowSettingEnabled) {
				// "カードのタッチ待ち状態"メッセージ表示
				Toast.makeText(mContext, R.string.message_wait_card, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		String key = preference.getKey();

		if (key.equals(mKeyNfcSetting)) {
			// NFC設定画面
			Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
			startActivity(intent);
		}

		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		boolean ret = false;
		String key = preference.getKey();

		if (newValue == null) {
			return false;
		}

		if (key.equals(mKeyNotificationEnabled) ||
				key.equals(mKeyKeepNotification)) {
			ret = true;
		}

		return ret;
	}
}
