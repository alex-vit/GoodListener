package com.alexvit.goodlistener.data.source.background;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alexvit.goodlistener.App;
import com.alexvit.goodlistener.di.components.DaggerServiceComponent;

import javax.inject.Inject;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public class EventService extends Service {

    @SuppressWarnings("unused")
    private static final String TAG = EventService.class.getSimpleName();

    @Inject
    EventReceiver receiver;
    @Inject
    IntentFilter filter;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerServiceComponent.builder()
                .appComponent(App.get(this).component())
                .build()
                .inject(this);

        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
