package com.alexvit.goodlistener.data.source.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alexvit.goodlistener.data.EventsRepository;
import com.alexvit.goodlistener.data.models.Event;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public class EventReceiver extends BroadcastReceiver {

    @SuppressWarnings("unused")
    private static final String TAG = EventReceiver.class.getSimpleName();

    private final EventsRepository repository;

    public EventReceiver(EventsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Event event = new Event();
        event.action = intent.getAction();
        event.timestamp = System.currentTimeMillis();
        repository.insert(event);
    }
}
