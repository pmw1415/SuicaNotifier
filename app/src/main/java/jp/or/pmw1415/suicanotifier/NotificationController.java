package jp.or.pmw1415.suicanotifier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by pmw1415 on 2015/11/21.
 */
public class NotificationController {
	private static final int NotificationBarId = 1415;
	private Context mContext;

	public NotificationController(Context context) {
		mContext = context;
	}

	/**
	 * Notification表示/非表示
	 *
	 * @param enabled
	 */
	public void setNotification(boolean enabled) {
		NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);

		if (enabled) {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
			builder.setContentTitle("Title");
			builder.setContentText("Text");
			builder.setSmallIcon(R.mipmap.ic_launcher);
			builder.setOngoing(false);
			Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
			builder.setLargeIcon(largeIcon);
//			builder.setContentIntent();
			builder.setAutoCancel(false);

			manager.notify(NotificationBarId, builder.build());
		}
		else {
			manager.cancel(NotificationBarId);
		}
	}
}
