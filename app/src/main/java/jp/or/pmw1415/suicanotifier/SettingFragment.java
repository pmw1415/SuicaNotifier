package jp.or.pmw1415.suicanotifier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;

/**
 * Created by pmw1415 on 2015/11/22.
 */
public class SettingFragment extends PreferenceFragment
		implements Preference.OnPreferenceClickListener {
	// key
	private String mKeyNfcSetting;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);

		Context context = this.getActivity();
		mKeyNfcSetting = context.getString(R.string.nfc_setting_key);

		Preference prefButtonNfc = (Preference)findPreference(mKeyNfcSetting);
		prefButtonNfc.setOnPreferenceClickListener(this);
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
}
