package com.alexvit.goodlistener;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.alexvit.goodlistener.data.source.background.EventService;
import com.alexvit.goodlistener.di.components.AppComponent;
import com.alexvit.goodlistener.di.components.DaggerAppComponent;
import com.alexvit.goodlistener.di.modules.ContextModule;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public class App extends Application {

    private AppComponent component;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        startService(new Intent(this, EventService.class));
    }

    public AppComponent component() {
        return component;
    }

}
