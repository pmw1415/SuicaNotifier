package jp.or.pmw1415.suicanotifier;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
	private static final int MenuIdSetting = 0;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MenuIdSetting, Menu.NONE, mContext.getString(R.string.setting));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == MenuIdSetting) {
			// 設定画面を表示
			Intent intent = new Intent(MainActivity.this, SettingActivity.class);
			mContext.startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
