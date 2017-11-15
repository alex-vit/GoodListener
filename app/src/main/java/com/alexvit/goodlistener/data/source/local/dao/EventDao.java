package com.alexvit.goodlistener.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alexvit.goodlistener.data.models.Event;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

@Dao
public interface EventDao {

    @Query("SELECT * FROM (SELECT * FROM events ORDER BY id DESC LIMIT :limit) ORDER BY id ASC")
    Single<List<Event>> events(int limit);

    @Query("SELECT * FROM "
            + "(SELECT * FROM events WHERE action LIKE :query ORDER BY id DESC LIMIT :limit) "
            + "ORDER BY id ASC")
    Single<List<Event>> eventsLike(String query, int limit);

    @Query("SELECT * FROM events ORDER BY id DESC LIMIT 1")
    Flowable<Event> latestEvent();

    @Query("SELECT * FROM events WHERE action LIKE :query ORDER BY id DESC LIMIT 1")
    Flowable<Event> latestEventLike(String query);

    @Insert(onConflict = REPLACE)
    long insert(Event event);

    @Insert(onConflict = REPLACE)
    long[] insert(List<Event> eventList);

    @Query("DELETE FROM events")
    int deleteAll();
}
