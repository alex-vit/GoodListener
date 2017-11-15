package com.alexvit.goodlistener.di.modules;

import android.content.Context;

import com.alexvit.goodlistener.di.qualifiers.ApplicationContext;
import com.alexvit.goodlistener.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context context() {
        return context;
    }

}
