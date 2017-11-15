package com.alexvit.goodlistener.log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alexvit.goodlistener.R;
import com.alexvit.goodlistener.base.BaseActivity;
import com.alexvit.goodlistener.data.models.Event;
import com.alexvit.goodlistener.settings.SettingsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogActivity extends BaseActivity<LogViewModel>
        implements LogNavigator, SharedPreferences.OnSharedPreferenceChangeListener {

    @SuppressWarnings("unused")
    private static final String TAG = LogActivity.class.getSimpleName();

    @BindView(R.id.rv_log)
    RecyclerView rvLog;

    @Inject
    LogViewModel viewModel;

    @Inject
    SharedPreferences sharedPreferences;

    private LogRvAdapter adapter;
    private boolean shouldAutoScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        ButterKnife.bind(this);
        getComponent().inject(this);
        initRecyclerView();

        shouldAutoScroll = sharedPreferences.getBoolean(getString(R.string.pref_key_scroll), true);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        viewModel.setNavigator(this);
        viewModel.onViewInitialized();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(viewModel);
        viewModel.onSearchInitialized();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected LogViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }

    @Override
    public void onEvents(List<Event> events) {
        adapter.replace(events);
        if (shouldAutoScroll) autoScroll();
    }

    @Override
    public void onEvent(Event event) {
        adapter.add(event);
        if (shouldAutoScroll) autoScroll();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_key_scroll))) {
            shouldAutoScroll = sharedPreferences.getBoolean(key, true);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        rvLog.setLayoutManager(manager);
        adapter = new LogRvAdapter();
        rvLog.setAdapter(adapter);
    }

    private void autoScroll() {
        rvLog.scrollToPosition(adapter.getItemCount() - 1);
    }
}
