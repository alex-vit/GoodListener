package com.alexvit.goodlistener.data.source.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

/**
 * Created by Aleksandrs Vitjukovs on 9/12/2017.
 */

public class Migrations {

    // Add trigger to limit row count. 1000 is hardcoded deliberately
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TRIGGER cap_row_count "
                    + "AFTER INSERT ON events BEGIN "
                    + "DELETE FROM events WHERE id NOT IN "
                    + "(SELECT id FROM events ORDER BY id DESC LIMIT 1000); "
                    + "END;"
            );
        }
    };

    // Change timestamp resolution to milliseconds
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("UPDATE events "
                    + "SET timestamp = timestamp * 1000;"
            );
        }
    };

    // Only clean up around every 100th insert. This is 10-15 times faster
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TRIGGER IF EXISTS cap_row_count");
            database.execSQL("CREATE TRIGGER cap_row_count "
                    + "AFTER INSERT ON events "
                    + "WHEN (new.id % 100 == 0) BEGIN "
                    + "DELETE FROM events WHERE id NOT IN "
                    + "(SELECT id FROM events ORDER BY id DESC LIMIT 1000); "
                    + "END;"
            );
        }
    };
}
