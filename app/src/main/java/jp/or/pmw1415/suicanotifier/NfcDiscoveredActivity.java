package jp.or.pmw1415.suicanotifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

/**
 * NFC検出で遷移するActivity
 *
 * Created by pmw1415 on 2015/11/15.
 */
public class NfcDiscoveredActivity extends Activity {
	private static final String TAG = "NfcDiscoveredActivity";

	private String mKeyNotificationEnabled;
	private String mKeyKeepNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc_discovered);

		mKeyNotificationEnabled = this.getString((R.string.notification_enabled_key));
		mKeyKeepNotification = this.getString((R.string.keep_notification_key));

		TextView textView = (TextView)findViewById(R.id.textView);
		textView.setText("NFC Discovered.");

		// @note NFC検出で実行される

		// タグ取得
		Tag tag = getTag(getIntent());
		if (tag == null) {
			Log.e(TAG, "get NFC tag failed.");
			return;
		}

		FelicaConnection felicaConnection = new FelicaConnection();
		byte[] res = null;
		try {
			// コマンド送信、Rawデータ受信
			res = felicaConnection.sendCmdReadWithoutEncryption(tag, 1);

			int remain = felicaConnection.getRemain(res);
			textView.setText(String.format("Remain: %d yen\n", remain));
			showNotification(this, remain);

			//TODO 解析、データ保存

		} catch (Exception e) {
			res = null;
			Log.e(TAG, e.getMessage(), e);
			textView.setText(e.toString());
		}

		// TODO 通知バー表示
	}

	/**
	 * Intentからタグ取得
	 *
	 * @param intent
	 * @return
	 */
	private Tag getTag(Intent intent) {
		return (Tag)intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	}

	/**
	 * Notificationに残高を表示
	 *
	 * @param context
	 * @param remain
	 */
	private void showNotification(Context context, int remain) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		boolean enabled = sharedPref.getBoolean(mKeyNotificationEnabled, false);
		boolean keep = sharedPref.getBoolean(mKeyKeepNotification, false);

		NotificationController notificationController = new NotificationController(context);
		NotificationParam param = new NotificationParam(
				context, "Suica remain",
				String.format("%d yen\n", remain),
				R.mipmap.card, R.mipmap.card_black,
				keep, false, null
		);
		notificationController.setNotification(enabled, param);
	}
}
