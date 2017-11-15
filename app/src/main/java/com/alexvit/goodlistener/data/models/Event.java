package com.alexvit.goodlistener.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Aleksandrs Vitjukovs on 9/11/2017.
 */

@Entity(tableName = "events", indices = {@Index("timestamp"), @Index("action")})
public class Event {

    @PrimaryKey
    public Long id;

    public String action;

    public Long timestamp;

    @Override
    public String toString() {
        return String.format(
                "Event( id = %s, action = %s, timestamp = %s )",
                id, action, timestamp
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (action != null ? !action.equals(event.action) : event.action != null) return false;
        return timestamp != null ? timestamp.equals(event.timestamp) : event.timestamp == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
