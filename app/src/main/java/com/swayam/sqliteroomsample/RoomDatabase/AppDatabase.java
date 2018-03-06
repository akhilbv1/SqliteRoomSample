package com.swayam.sqliteroomsample.RoomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.swayam.sqliteroomsample.Dao.DaoUser;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;

/*Created by akhil on 13/2/18.*/

/**
 * this is database class and all entities and dao classes haev to be mentioned here.
 */

@Database(entities = {EntityUserPojo.class}, version =12, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase Instance;

    public abstract DaoUser userDao();


    /**
     * Migration_8_10 is used to update table if we add a new column
     */
  private static final Migration MIGRATION_8_10 = new Migration(8,10) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN userid INTEGER NOT NULL");        }
    };


    /**
     * this is used to create sqlite database using room
      * @param context(context of calling activity)
     * @return(returns instance of the Database)
     * when version changes to remove old table and create new one use fallbacktoDestructivemigration
     * to add migrations use addmigrations
     */
    public static AppDatabase getAppDatabase(Context context) {
        Instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "credentials")
                .fallbackToDestructiveMigration()
                .build();
        return Instance;

    }

}
