package com.alexvit.goodlistener.log;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexvit.goodlistener.R;
import com.alexvit.goodlistener.Util;
import com.alexvit.goodlistener.data.models.Event;

import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

public class LogRvAdapter extends RecyclerView.Adapter<LogRvAdapter.LogEntryViewHolder> {

    private static final int LIMIT = 1000;

    private final SortedList<Event> eventSortedList = new SortedList<Event>(Event.class,
            new SortedListCallback());

    @Override
    public LogEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_log, parent, false);
        return new LogEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LogEntryViewHolder holder, int position) {
        holder.tvEventTimestamp.setText(Util.formatTimestamp(getEvent(position).timestamp));
        holder.tvEventAction.setText(Util.formatAction(getEvent(position).action));
    }

    @Override
    public int getItemCount() {
        return eventSortedList.size();
    }

    public void add(Event event) {
        eventSortedList.add(event);

        if (eventSortedList.size() > LIMIT) {
            eventSortedList.removeItemAt(0);
        }
    }

    public void replace(List<Event> newEvents) {

        eventSortedList.beginBatchedUpdates();

        for (int i = eventSortedList.size() - 1; i >= 0; i--) {
            if (!newEvents.contains(eventSortedList.get(i))) {
                eventSortedList.removeItemAt(i);
            }
        }

        eventSortedList.addAll(newEvents);

        int overflow = eventSortedList.size() - LIMIT;
        if (overflow > 0) {
            for (int i = 0; i < overflow; i++) {
                eventSortedList.removeItemAt(0);
            }
        }

        eventSortedList.endBatchedUpdates();
    }

    private Event getEvent(int position) {
        return eventSortedList.get(position);
    }

    class LogEntryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_event_timestamp)
        TextView tvEventTimestamp;

        @BindView(R.id.tv_event_action)
        TextView tvEventAction;

        LogEntryViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class SortedListCallback extends SortedList.Callback<Event> {

        private final Comparator<Event> comparator = (a, b) -> a.id.compareTo(b.id);

        @Override
        public int compare(Event o1, Event o2) {
            return comparator.compare(o1, o2);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Event oldItem, Event newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Event item1, Event item2) {
            return item1.id.equals(item2.id);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    }
}
