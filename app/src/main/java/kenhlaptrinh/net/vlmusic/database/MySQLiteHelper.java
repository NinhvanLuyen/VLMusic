//package kenhlaptrinh.net.vlmusic.database;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.provider.MediaStore;
//import android.util.Log;
//
///**
// * Created by ninhv on 1/10/2017.
// */
//
//public class MySQLiteHelper extends SQLiteOpenHelper {
//
//    public static final String DATABASE_NAME = "vlmusic";
//
//
//    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
////    MediaStore.Audio.Media.TITLE,
////    MediaStore.Audio.Media.ARTIST,
////            "("+MediaStore.Audio.Media.DURATION+")ASC"
////            ,MediaStore.Audio.Media.ALBUM_KEY,
////            "("+MediaStore.Audio.Media.DATA+")ASC"
////            ,MediaStore.Audio.Media.ALBUM_ID
////},null,null,
//public final String DATABASE_CREATE_CHUPANH="create table "+TABLE_CHUPANH
//        +"("+MACHUPANH+" integer PRIMARY KEY autoincrement , "+
//        ID_DIEM_VONGDI+" integer null, "+
//        URL_FILE_ANH+" text null, "+
//        URL_FILE_ANH_THUBNAIL+" text null , "+
//        THUTU_LOAI_ANH+" integer null , "+
//        LOAI_ANH+" integer null )";
//
//
//@Override
//public void onCreate(SQLiteDatabase database){
////        database.execSQL(DATABASE_CREATE_NHANVIEN);
//
//        database.execSQL(SONGS);
//        String pathdb=database.getPath();
//        Log.d("path ",pathdb);
//        }
//
//@Override
//public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
//        // TODO Auto-generated method stub
//        Log.w(SQLite_Helper.class.getName(),
//        "Upgrading database from version "+oldVersion+" to "
//        +newVersion+", which will destroy all old data");
//        onCreate(db);
//        }
//        }
