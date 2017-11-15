package com.alexvit.goodlistener.data;

import com.alexvit.goodlistener.data.models.Event;
import com.alexvit.goodlistener.data.source.local.EventsDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

public class EventsRepository {

    private final EventsDataSource db;

    public EventsRepository(EventsDataSource db) {
        this.db = db;
    }

    public void insert(Event event) {
        db.insert(event).subscribeOn(Schedulers.io()).subscribe();
    }

    public Observable<List<Event>> events(int limit) {
        return db.events(limit).toObservable();
    }

    public Observable<List<Event>> eventsLike(String query, int limit) {
        return db.eventsLike(query, limit).toObservable();
    }

    public Observable<Event> latestEvent() {
        return db.latestEvent().toObservable();
    }

    public Observable<Event> latestEventLike(String query) {
        return db.latestEventLike(query).toObservable();
    }

    public void deleteAll() {
        db.deleteAll().subscribeOn(Schedulers.io()).subscribe();
    }
}
