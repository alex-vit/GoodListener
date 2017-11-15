package com.alexvit.goodlistener.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.alexvit.goodlistener.data.EventsRepository;
import com.alexvit.goodlistener.data.source.local.EventsDataSource;
import com.alexvit.goodlistener.data.source.local.EventsDatabase;
import com.alexvit.goodlistener.data.source.local.Migrations;
import com.alexvit.goodlistener.di.qualifiers.ApplicationContext;
import com.alexvit.goodlistener.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

@Module(includes = ContextModule.class)
public class EventsRepositoryModule {

    @Provides
    @ApplicationScope
    EventsRepository eventsRepository(EventsDataSource eventsDataSource) {
        return new EventsRepository(eventsDataSource);
    }

    @Provides
    @ApplicationScope
    EventsDataSource eventsDataSource(EventsDatabase eventsDatabase) {
        return new EventsDataSource(eventsDatabase);
    }

    @Provides
    @ApplicationScope
    EventsDatabase eventsDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, EventsDatabase.class, "events.db")
                .addMigrations(
                        Migrations.MIGRATION_1_2,
                        Migrations.MIGRATION_2_3,
                        Migrations.MIGRATION_3_4
                )
                .fallbackToDestructiveMigration()
                .build();
    }
}
