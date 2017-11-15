package com.alexvit.goodlistener.di.components;

import com.alexvit.goodlistener.data.source.background.EventService;
import com.alexvit.goodlistener.di.modules.ServiceModule;
import com.alexvit.goodlistener.di.scopes.ServiceScope;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

@ServiceScope
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(EventService eventService);
}
