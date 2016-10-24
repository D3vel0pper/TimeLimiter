package d3vel0pper.com.timelimiter.realm;

import android.content.Context;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by D3vel0pper on 2016/10/10.
 */
public class RealmManager {
    /**
     * Constructor but this is single instance cuz multi Access  is not allowed
     */
    private RealmManager(){
    }

    /**
     * unique instance
     */
    private static RealmManager instance;

    /**
     * get instance method for single instance
     */
    public static RealmManager getInstance(){
        if(instance == null){
            instance = new RealmManager();
        }
        return instance;
    }

    /**
     * private members
     */
//    private Context context;
    private Realm realm;
    private RealmMigration migration;
    private RealmConfiguration configuration;
//    private RealmQuery<DBData> query;
//    private RealmResults<DBData> results;

    /**
     * SCHEMA_VERSION is int number that showing schema version of Realm
     */
    private static final int SCHEMA_VERSION = 1;

    public Realm getRealm(Context context){
        if(realm == null){
            realm = Realm.getInstance(getConfiguration(context));
        }
        return realm;
    }

    /**
     * If you upgraded modelClass, you must change SCHEMA_VERSION
     */
    public RealmConfiguration getConfiguration(Context context){
        configuration = new RealmConfiguration.Builder(context)
                .schemaVersion(SCHEMA_VERSION).migration(runMigration(context)).build();
        return configuration;
    }

    /**
     *
     * @param context: the context which is parent of calling this
     * @return RealmMigration that going to execute
     */
    private RealmMigration runMigration(Context context){
        migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                /*
                if you changed modelClass, you must add migration process at here
                 */

                //ex.)
                // DynamicRealmから変更可能なスキーマオブジェクトを取得します
                RealmSchema schema = realm.getSchema();

                // バージョン1へのマイグレーション: クラスの追加
                // Example:
                // public Person extends RealmObject {
                //     private String name;
                //     private int age;
                //     // getterとsetterは省略
                // }

                //バージョン2へのマイグレーション: プライマリキーと関連の追加
                // Example:
                // public Person extends RealmObject {
                //     private String name;
                //     @PrimaryKey
                //     private int age;
                //     private Dog favoriteDog;
                //     private RealmList<Dog> dogs;
                //     //getterとsetterは省略
                // }
//                if (oldVersion == 1) {
//                    schema.get("Person")
//                            .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
//                            .addRealmObjectField("favoriteDog", schema.get("Dog"))
//                            .addRealmListField("dogs", schema.get("Dog"));
//                    oldVersion++;
//                }
                if(oldVersion == 1){
                    schema.get("DBData")
                            .addField("isComplete",boolean.class);
                    oldVersion++;
                }
            }
        };
        return migration;
    }

//    public RealmQuery<DBData> getQuery(Context context){
//        if(query == null){
//            query = getRealm(context).where(DBData.class);
//        }
//        return query;
//    }

    /**
     * you must close the realm if you won't handle after
     */
    public void closeRealm(){
        if(realm != null) {
            realm.close();
        }
        realm = null;
    }

}
