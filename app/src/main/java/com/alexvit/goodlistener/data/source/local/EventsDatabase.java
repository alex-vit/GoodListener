package com.alexvit.goodlistener.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alexvit.goodlistener.data.models.Event;
import com.alexvit.goodlistener.data.source.local.dao.EventDao;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

@Database(entities = {Event.class}, version = 4)
public abstract class EventsDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
}
