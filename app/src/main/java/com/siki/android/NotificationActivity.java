package com.siki.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity extends Fragment{

	private static final int NOTIFY_ME_ID=1337;

	private TextView tv_title;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.activity_notification, container, false);

		tv_title =(TextView)v.findViewById(R.id.tv_title);
		tv_title.setText("Tentang IRIS");
		
		//---- tambahan ---
		final Button notify = (Button) v.findViewById(R.id.button1);

		notify.setOnClickListener(new OnClickListener() {
			public void onClick(View w) {

			/*********** Create notification ***********/
			final NotificationManager mgr=
					(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

			Notification note=new Notification(R.drawable.logo_small,
					"Android Example Status message!",
					System.currentTimeMillis());

// This pending intent will open after notification click : error
/*
			PendingIntent i = PendingIntent.getActivity(getBaseContext(), 0, 
					                         new Intent(getBaseContext(), NotifyMessage.class),
													0);


			note.setLatestEventInfo(getBaseContext(), "Android Example Notification Title",
					"This is the android example notification message", i);

					//After uncomment this line you will see number of notification arrived
					//note.number=2;
					mgr.notify(NOTIFY_ME_ID, note);

*/
			
			//----------
			}

			private NotificationManager getSystemService(
					String notificationService) {
				// TODO Auto-generated method stub
				return null;
			} 
		});
		
		
		return v;
	}

	
}
