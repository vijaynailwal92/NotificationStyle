package com.vijay.notificationstyle;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnNotificationActions, btnHeadsUpNotification, btnBigTextStyle, btnBigPictureStyle,
            btnInboxStyle, btnMessageStyle;
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        clearNotification();
        createNotificationChannel();
        btnNotificationActions = findViewById(R.id.btnNotificationActions);
        btnHeadsUpNotification = findViewById(R.id.btnHeadsUp);
        btnBigTextStyle = findViewById(R.id.btnBigTextStyle);
        btnBigPictureStyle = findViewById(R.id.btnBigPictureStyle);
        btnInboxStyle = findViewById(R.id.btnInboxStyle);
        btnMessageStyle = findViewById(R.id.btnMessageStyle);


        btnNotificationActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationActions();
            }
        });

        btnHeadsUpNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headsUpNotification();
            }
        });

        btnBigTextStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigTextStyleNotification();
            }
        });
        btnBigPictureStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigPictureStyleNotification();
            }
        });

        btnInboxStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inboxStyleNotification();
            }
        });
        btnMessageStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageStyleNotification();
            }
        });


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel
                    (getString(R.string.NEWS_CHANNEL_ID), getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void notificationActions() {
        builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID));
        builder.setSmallIcon(R.drawable.jd);
        builder.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jd));
        builder.setContentTitle("Notification Actions");
        builder.setContentText("Tap View to launch our website");
        builder.setAutoCancel(true);

        PendingIntent launchIntent = getLaunchIntent(getResources().getInteger(R.integer.notificationId), getBaseContext());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.journaldev.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent buttonIntent = new Intent(getBaseContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", getResources().getInteger(R.integer.notificationId));
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getBaseContext(), 0, buttonIntent, 0);


        builder.setContentIntent(launchIntent);
        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());


    }

    public PendingIntent getLaunchIntent(int notificationId, Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notificationId", notificationId);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void clearNotification() {
        int notificationId = getIntent().getIntExtra("notificationId", 0);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }

    private void headsUpNotification() {
        builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.jd)
                .setContentTitle("Heads Up Notification")
                .setContentText("View the latest Swift Tutorial")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.journaldev.com/15126/swift-function"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent buttonIntent = new Intent(getBaseContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", getString(R.string.NEWS_CHANNEL_ID));
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getBaseContext(), 0, buttonIntent, 0);

        builder.addAction(android.R.drawable.ic_menu_view, "VIEW", pendingIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());

    }

    private void bigTextStyleNotification() {


        PendingIntent launchIntent = getLaunchIntent(getResources().getInteger(R.integer.notificationId), getBaseContext());
        Intent buttonIntent = new Intent(getBaseContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", getResources().getInteger(R.integer.notificationId));
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getBaseContext(), 0, buttonIntent, 0);
        builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID));
        builder.setSmallIcon(R.drawable.jd);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jd));
        builder.setContentTitle("Big Text Style");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getResources().getString(R.string.lorem_ipsum)));
        builder.setAutoCancel(true);
        builder.setContentIntent(launchIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);
        builder.addAction(android.R.drawable.ic_menu_send, "OPEN APP", launchIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());

    }

    private void bigPictureStyleNotification() {


        Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.bg);


        Intent buttonIntent = new Intent(getBaseContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", getResources().getInteger(R.integer.notificationId));
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getBaseContext(), 0, buttonIntent, 0);
        PendingIntent launchIntent = getLaunchIntent(getResources().getInteger(R.integer.notificationId), getBaseContext());


        builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID));
        builder.setSmallIcon(R.drawable.jd);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jd));
        builder.setContentTitle("Big Picture Style");
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(pic));
        builder.setAutoCancel(true);
        builder.setContentIntent(launchIntent);
        builder.addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());

    }

    private void inboxStyleNotification() {


        PendingIntent launchIntent = getLaunchIntent(getResources().getInteger(R.integer.notificationId), getBaseContext());
        builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID));
        builder.setSmallIcon(R.drawable.jd);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jd));
        builder.setContentTitle("Messages");
        builder.setStyle(new NotificationCompat.InboxStyle().addLine("Hello").addLine("Are you there?").addLine("How's your day?").setBigContentTitle("3 New Messages for you").setSummaryText("Inbox"));
        builder.setAutoCancel(true);
        builder.setContentIntent(launchIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());

    }

    private void messageStyleNotification() {


        PendingIntent launchIntent = getLaunchIntent(getResources().getInteger(R.integer.notificationId), getBaseContext());
        builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID));
        builder.setSmallIcon(R.drawable.jd);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.jd));
        builder.setContentTitle("Messages");
        builder.setStyle(new NotificationCompat.MessagingStyle("Teacher").setConversationTitle("Q&A Group")
                .addMessage("This type of notification was introduced in Android N. Right?", 0, "Student 1")
//                .addMessage("Yes", 0, null)
                .addMessage("The constructor is passed with the name of the current user. Right?", 0, "Student 2"));
//                .addMessage("True", 0, null));
        builder.setAutoCancel(true);
        builder.setContentIntent(launchIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());

    }

}
