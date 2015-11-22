package jp.or.pmw1415.suicanotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	NotificationController notificationController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Notification表示
		NotificationParam param = new NotificationParam(
				this, "Title", "ContentText",
				R.mipmap.ic_launcher, R.mipmap.ic_launcher,
				false, false, null
		);
		notificationController = new NotificationController(this);
		notificationController.setNotification(true, param);
	}

	@Override
	protected void onStop() {
		super.onStop();

		// Notification非表示
		notificationController.setNotification(false, null);
	}

}
