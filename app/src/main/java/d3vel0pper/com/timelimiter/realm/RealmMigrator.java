package d3vel0pper.com.timelimiter.realm;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by D3vel0pper on 2016/10/03.
 *
 * This class should be single instance cuz don't need any othenr instance to use
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
                /*
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
                 if (oldVersion == 0) {
                    schema.create("Person")
                        .addField("name", String.class)
                        .addField("age", int.class);
                    oldVersion++;
                 }

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
                 if (oldVersion == 1) {
                    schema.get("Person")
                        .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                        .addRealmObjectField("favoriteDog", schema.get("Dog"))
                        .addRealmListField("dogs", schema.get("Dog"));
                    oldVersion++;
                 }
                 */
                RealmSchema schema = realm.getSchema();
                if(oldVersion == 0) {
                    schema.get("DBData")
                            .addField("dateCreatedAt", Date.class)
                            .addField("dateStartDate",Date.class)
                            .addField("dateEndDate",Date.class);
                    oldVersion++;
                }
            }
        };
    }

}
