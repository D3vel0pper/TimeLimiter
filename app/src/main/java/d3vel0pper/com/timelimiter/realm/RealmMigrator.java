package d3vel0pper.com.timelimiter.realm;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by D3vel0pper on 2016/10/03.
 *
 * This class should be single instance cuz don't need any othenr instance to use
 */
public final class RealmMigrator {

    private static RealmMigrator instance;
    private static RealmConfiguration configuration;

    private RealmMigrator(){}
    private static int SCHEMA_VERSION = 2;

    public Realm getRealm(Context context){
//        if(configuration == null) {
//            return Realm.getInstance(getConfig(context));
//        }
        return Realm.getInstance(configuration);
    }

//    private RealmConfiguration getConfig(Context context){
//        return  new RealmConfiguration.Builder(context)
//                .schemaVersion(SCHEMA_VERSION)
//                .migration(getInstance().runMigration())
//                .build();
//    }

    public static RealmMigrator getInstance(Context context){
        if(instance == null){
            instance = new RealmMigrator();
        }
        if(configuration == null){
            configuration = new RealmConfiguration
                    .Builder(context)
                    .schemaVersion(SCHEMA_VERSION)
                    .migration(instance.runMigration())
                    .build();
        }
        return instance;
    }

    public RealmMigration runMigration(){
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
//                if(oldVersion == 0) {
//                    schema.get("DBData")
//                            .addField("dateCreatedAt",Date.class)
//                            .setNullable("dateCreatedAt",true)
//                            .addField("dateStartDate", Date.class)
//                            .setNullable("dateStartDate",true)
//                            .addField("dateEndDate", Date.class)
//                            .setNullable("dateEndDate",true)
//                            .addField("dateStartDay",Date.class)
//                            .setNullable("dateStartDay",true)
//                            .addField("dateEndDay",Date.class)
//                            .setNullable("dateEndDay",true)
//                    .transform(new RealmObjectSchema.Function(){
//                        @Override
//                        public void apply(DynamicRealmObject obj){
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
//                            try {
//                                obj.setDate("dateCreatedAt",sdf.parse(obj.getString("createdAt")));
//                                obj.setDate("dateStartDate",sdf.parse(obj.getString("startDate")));
//                                obj.setDate("dateEndDate",sdf.parse(obj.getString("endDate")));
//                                obj.setDate("dateStartDay",sdf.parse(obj.getString("startDay")));
//                                obj.setDate("dateEndDay",sdf.parse(obj.getString("endDay")));
//                            } catch(ParseException e){
//                                Log.e("PE","Parse has not finished correctly.");
//                            }
//                        }
//                    });
//
//                    oldVersion++;
//                }
//                else if(oldVersion == 1){
//                    schema.get("DBData")
//                            .setNullable("dateCreatedAt",true)
//                            .setNullable("dateStartDate",true)
//                            .setNullable("dateEndDate",true)
//                            .setNullable("dateStartDay",true)
//                            .setNullable("dateEndDay",true)
//                            .transform(new RealmObjectSchema.Function(){
//                                @Override
//                                public void apply(DynamicRealmObject obj){
//                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
//                                    try {
//                                        obj.setDate("dateCreatedAt",sdf.parse(obj.getString("createdAt")));
//                                        obj.setDate("dateStartDate",sdf.parse(obj.getString("startDate")));
//                                        obj.setDate("dateEndDate",sdf.parse(obj.getString("endDate")));
//                                        obj.setDate("dateStartDay",sdf.parse(obj.getString("startDay")));
//                                        obj.setDate("dateEndDay",sdf.parse(obj.getString("endDay")));
//                                    } catch(ParseException e){
//                                        Log.e("PE","Parse has not finished correctly.");
//                                    }
//                                }
//                            });
//
//                    oldVersion++;
//                }
            }
        };
        return migration;
    }

}
