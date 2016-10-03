package d3vel0pper.com.timelimiter.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by D3vel0pper on 2016/10/03.
 *
 * This class should be single instance cuz don't need any instance to use
 */
public class RealmMigrator {

    private RealmMigrator instance;

    private RealmMigrator(){

    }

    public RealmMigrator getInstance(){
        if(instance == null){
            instance = new RealmMigrator();
        }
        return instance;
    }

    public void runMigration(){
        RealmMigration migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                //add here code to migrate Realm Model
            }
        };
    }

}
