package jp.or.pmw1415.suicanotifier;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by pmw1415 on 2015/11/21.
 */
public class NotificationParam {
	public String title;
	public String contentText;
	public int smallIcon;
	public Bitmap largeIcon;
	public boolean ongoing;
	public boolean autoCancel;
	public PendingIntent pendingIntent;

	/**
	 * コンストラクタ
	 *
	 * @param context
	 * @param title_
	 * @param contentText_
	 * @param smallIcon_
	 * @param largeIcon_
	 * @param ongoing_
	 * @param autoCancel_
	 * @param pendingIntent_
	 */
	public NotificationParam(Context context,
							 String title_, String contentText_,
							 int smallIcon_, int largeIcon_,
							 boolean ongoing_, boolean autoCancel_,
							 PendingIntent pendingIntent_) {
		title = title_;
		contentText = contentText_;
		smallIcon = smallIcon_;
		Bitmap largeIconBitmap =
		largeIcon = BitmapFactory.decodeResource(context.getResources(), largeIcon_);
		ongoing = ongoing_;
		autoCancel = autoCancel_;
		pendingIntent = pendingIntent_;
	}
}
