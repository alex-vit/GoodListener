package com.alexvit.goodlistener.di.modules;

import android.content.Context;
import android.content.IntentFilter;

import com.alexvit.goodlistener.R;
import com.alexvit.goodlistener.data.EventsRepository;
import com.alexvit.goodlistener.data.source.background.EventReceiver;
import com.alexvit.goodlistener.di.qualifiers.ApplicationContext;
import com.alexvit.goodlistener.di.scopes.ServiceScope;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

@Module
public class ServiceModule {

    @Provides
    @ServiceScope
    EventReceiver eventReceiver(EventsRepository eventsRepository) {
        return new EventReceiver(eventsRepository);
    }

    @Provides
    @ServiceScope
    IntentFilter eventReceiverIntentFilter(List<String> actions) {
        final IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        return filter;
    }

    @Provides
    @ServiceScope
    List<String> actionList(@ApplicationContext Context context) {
        final InputStream inputStream = context.getResources()
                .openRawResource(R.raw.broadcast_actions_26);
        final Scanner scanner = new Scanner(inputStream);
        final List<String> list = new ArrayList<>();

        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        scanner.close();

        return list;
    }
}
