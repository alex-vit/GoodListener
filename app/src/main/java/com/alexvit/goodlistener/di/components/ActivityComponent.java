package com.alexvit.goodlistener.di.components;

import com.alexvit.goodlistener.di.modules.ActivityModule;
import com.alexvit.goodlistener.di.scopes.ActivityScope;
import com.alexvit.goodlistener.log.LogActivity;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(LogActivity logActivity);
}
