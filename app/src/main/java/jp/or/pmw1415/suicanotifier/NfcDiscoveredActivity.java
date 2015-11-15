package jp.or.pmw1415.suicanotifier;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by pmw1415 on 2015/11/15.
 */
public class NfcDiscoveredActivity extends Activity {
	private static final String TAG = "NfcDiscoveredActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc_discovered);

		TextView textView = (TextView)findViewById(R.id.textView);
		textView.setText("NFC Discovered.");

		// @note NFC検出で実行される

		// TODO タグ取得

		// TODO ReadWithoutEncryption

		// TODO Suica情報(履歴、残高)を保存

		// TODO 通知バー表示
	}

}
