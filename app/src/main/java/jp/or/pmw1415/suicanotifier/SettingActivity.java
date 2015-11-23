package jp.or.pmw1415.suicanotifier;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by pmw1415 on 2015/11/22.
 */
public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingFragment())
				.commit();
	}
}
