package com.alexvit.goodlistener.di.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.alexvit.goodlistener.data.EventsRepository;
import com.alexvit.goodlistener.di.qualifiers.ActivityContext;
import com.alexvit.goodlistener.di.scopes.ActivityScope;
import com.alexvit.goodlistener.log.LogViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

@Module
public class ActivityModule {

    private final Context context;

    public ActivityModule(Activity activity) {
        this.context = activity;
    }

    @Provides
    @ActivityScope
    LogViewModel logViewModel(EventsRepository eventsRepository) {
        return new LogViewModel(eventsRepository);
    }

    @Provides
    @ActivityScope
    public SharedPreferences sharedPreferences(@ActivityContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context context() {
        return context;
    }
}
