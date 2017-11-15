package com.alexvit.goodlistener.log;

import android.support.v7.widget.SearchView;

import com.alexvit.goodlistener.base.BaseViewModel;
import com.alexvit.goodlistener.data.EventsRepository;
import com.alexvit.goodlistener.data.models.Event;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public class LogViewModel extends BaseViewModel<LogNavigator>
        implements SearchView.OnQueryTextListener {

    private static final int LIMIT = 1000;

    private final EventsRepository eventsRepository;
    private final BehaviorSubject<String> searchQuery;

    public LogViewModel(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;

        searchQuery = BehaviorSubject.create();
        searchQuery
                .map(String::toLowerCase)
                .debounce(400, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onQuery);
    }

    @Override
    public void onViewInitialized() {
        subscribe(
                eventsRepository.events(LIMIT),
                getNavigator()::onEvents);
        subscribe(
                eventsRepository.latestEvent(),
                getNavigator()::onEvent);
    }

    public void onSearchInitialized() {

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchQuery.onNext(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    private void onQuery(String query) {

        getCompositeSub().clear();

        Observable<List<Event>> listObservable;
        Observable<Event> latestObservable;
        if (query.isEmpty()) {
            listObservable = eventsRepository.events(LIMIT);
            latestObservable = eventsRepository.latestEvent();
        } else {
            listObservable = eventsRepository.eventsLike(query, LIMIT);
            latestObservable = eventsRepository.latestEventLike(query);
        }

        subscribe(listObservable, getNavigator()::onEvents);
        subscribe(latestObservable, getNavigator()::onEvent);
    }
}
