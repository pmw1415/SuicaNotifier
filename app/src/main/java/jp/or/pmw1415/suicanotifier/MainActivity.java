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
		notificationController = new NotificationController(this);
		notificationController.setNotification(true);
	}

	@Override
	protected void onStop() {
		super.onStop();

		// Notification非表示
		notificationController.setNotification(false);
	}

}
