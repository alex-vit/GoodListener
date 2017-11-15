package com.alexvit.goodlistener.log;

import com.alexvit.goodlistener.base.BaseNavigator;
import com.alexvit.goodlistener.data.models.Event;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

public interface LogNavigator extends BaseNavigator {

    void onEvents(List<Event> events);

    void onEvent(Event event);
}
