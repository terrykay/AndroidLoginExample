package uk.co.tezk.login;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by tezk on 07/04/17.
 */

public class LoginApplication extends Application implements RealmMigration {
    @Override
    public void onCreate() {
        super.onCreate();

            Realm.init(this);

            RealmConfiguration config = new RealmConfiguration.Builder()
                    .schemaVersion(1)
                    .migration(this)
                    .build();
            Realm.setDefaultConfiguration(config);


    }
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        if (oldVersion == 1) {
            // Nothing to do as we haven't updated yet!
            oldVersion++;
        }
    }

}
