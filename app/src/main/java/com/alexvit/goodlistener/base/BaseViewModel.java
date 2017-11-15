package com.alexvit.goodlistener.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public abstract class BaseViewModel<Navigator extends BaseNavigator> {

    private final CompositeDisposable compositeSub = new CompositeDisposable();
    private Navigator navigator;

    public abstract void onViewInitialized();

    public void onDestroy() {
        compositeSub.clear();
        compositeSub.dispose();
    }

    protected final Navigator getNavigator() {
        return navigator;
    }

    public final void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    public final CompositeDisposable getCompositeSub() {
        return compositeSub;
    }

    protected final <T> void subscribe(Observable<T> observable,
                                       Consumer<? super T> onNext) {

        subscribe(observable, onNext, getNavigator()::onError);
    }

    protected final <T> void subscribe(Observable<T> observable,
                                       Consumer<? super T> onNext,
                                       Consumer<? super Throwable> onError) {

        Disposable subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

        compositeSub.add(subscription);
    }

}
