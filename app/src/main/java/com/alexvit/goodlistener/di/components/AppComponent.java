package com.alexvit.goodlistener.di.components;

import android.content.Context;

import com.alexvit.goodlistener.data.EventsRepository;
import com.alexvit.goodlistener.di.modules.EventsRepositoryModule;
import com.alexvit.goodlistener.di.qualifiers.ApplicationContext;
import com.alexvit.goodlistener.di.scopes.ApplicationScope;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

@ApplicationScope
@Component(modules = EventsRepositoryModule.class)
public interface AppComponent {

    EventsRepository eventsRepository();

    @ApplicationContext
    Context context();
}
