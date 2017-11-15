package com.alexvit.goodlistener.data.source.local;

import com.alexvit.goodlistener.data.models.Event;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

public class EventsDataSource {

    private final EventsDatabase db;

    public EventsDataSource(EventsDatabase db) {
        this.db = db;
    }

    public Single<List<Event>> events(int limit) {
        return db.eventDao().events(limit);
    }

    public Single<List<Event>> eventsLike(String query, int limit) {
        query = addPercent(query);
        return db.eventDao().eventsLike(query, limit);
    }

    public Flowable<Event> latestEvent() {
        return db.eventDao().latestEvent();
    }

    public Flowable<Event> latestEventLike(String query) {
        query = addPercent(query);
        return db.eventDao().latestEventLike(query);
    }

    public Observable<Long> insert(Event event) {
        return Observable.fromCallable(() -> db.eventDao().insert(event));
    }

    public Observable<long[]> insert(List<Event> eventList) {
        return Observable.fromCallable(() -> db.eventDao().insert(eventList));
    }

    public Observable<Integer> deleteAll() {
        return Observable.fromCallable(() -> db.eventDao().deleteAll());
    }

    private String addPercent(String query) {
        return "%" + query + "%";
    }
}
