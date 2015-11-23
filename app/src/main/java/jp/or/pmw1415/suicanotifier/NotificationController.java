package jp.or.pmw1415.suicanotifier;

import android.content.Context;
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
	 * @param param
	 */
	public void setNotification(boolean enabled, NotificationParam param) {
		NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);

		if (enabled && param != null) {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
			builder.setContentTitle(param.title);
			builder.setContentText(param.contentText);
			builder.setSmallIcon(param.smallIcon);
			builder.setOngoing(param.ongoing);
			builder.setLargeIcon(param.largeIcon);
			if (param.pendingIntent != null) {
				builder.setContentIntent(param.pendingIntent);
			}
			builder.setAutoCancel(param.autoCancel);

			manager.notify(NotificationBarId, builder.build());
		}
		else {
			manager.cancel(NotificationBarId);
		}
	}
}
