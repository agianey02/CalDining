package com.abhinavgianey.caldining;
 
 
import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
                            
 
public class MyAlarmService extends Service 
{
      
   private NotificationManager mManager;
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
    @Override
    public void onCreate() {
       super.onCreate();
    }
 
   @Override
   public void onStart(Intent intent, int startId) {
       super.onStart(intent, startId);
      
       mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
       Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);
     
       Notification notification = new Notification(R.drawable.ic_launcher,"This is a test message!", System.currentTimeMillis());
       intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
       PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
       notification.flags |= Notification.FLAG_AUTO_CANCEL;
       notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);
       
       Log.d("alarm time", Calendar.getInstance().getTime().toString());
       mManager.notify(0, notification);
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
 
}