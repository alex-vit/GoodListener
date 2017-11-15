package com.alexvit.goodlistener.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.goodlistener.App;
import com.alexvit.goodlistener.di.components.ActivityComponent;
import com.alexvit.goodlistener.di.components.DaggerActivityComponent;
import com.alexvit.goodlistener.di.modules.ActivityModule;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public abstract class BaseActivity<ViewModel extends BaseViewModel> extends AppCompatActivity {

    private ActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder()
//                .activityModule(new ActivityModule(this))
                .appComponent(App.get(this).component())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModel().onDestroy();
    }

    protected abstract ViewModel getViewModel();

    protected final ActivityComponent getComponent() {
        return component;
    }

}
