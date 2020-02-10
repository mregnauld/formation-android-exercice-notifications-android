package com.formationandroid.notificationsandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class MainActivity extends AppCompatActivity
{
	
	// Constantes :
	private static final String ID_CHANNEL = "groupe123";
	private static final int ID_NOTIFICATION = 1234;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initialisation du groupe de notifications (Android 8+ uniquement) :
		final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && manager != null)
		{
			// description du groupe :
			NotificationChannel notificationChannel = new NotificationChannel(ID_CHANNEL, getString(R.string.main_nom_channel), NotificationManager.IMPORTANCE_HIGH);
			notificationChannel.setDescription(getString(R.string.main_description_channel));
			notificationChannel.enableLights(true);
			
			// façon dont la notification se manifeste (selon les capacités de l'appareil) :
			notificationChannel.setLightColor(Color.RED);
			notificationChannel.enableVibration(true);
			notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
			manager.createNotificationChannel(notificationChannel);
		}
	}
	
	/**
	 * Listener bouton afficher détail.
	 * @param view Bouton
	 */
	public void onClickBoutonAfficherDetail(View view)
	{
		// construction notification :
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID_CHANNEL)
				.setSmallIcon(R.drawable.icone_notification)
				.setContentTitle(getString(R.string.main_nom_notification))
				.setAutoCancel(true)
				.setContentText(getString(R.string.main_description_notification));
		
		// action simple, avec gestion de la back stack :
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(DetailActivity.class);
		stackBuilder.addNextIntent(new Intent(this, DetailActivity.class));
		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		
		// affichage notification :
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if (manager != null)
		{
			manager.notify(ID_NOTIFICATION, builder.build());
		}
	}
	
}
