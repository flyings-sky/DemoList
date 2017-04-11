package andfans.com.demolist.IPC;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import andfans.com.demolist.Book;
import andfans.com.demolist.Data.User;
import andfans.com.demolist.R;

/**
 *
 * Created by 兆鹏 on 2017/4/10.
 */

public class ProviderActivity extends Activity {
    private static final String TAG = "ProviderActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_test_layout);
        Uri bookUri = Uri.parse("content://andfans.com.demolist.IPC.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","程序设计的艺术");
        getContentResolver().insert(bookUri,values);
        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{
                "_id","name"},null,null,null);
        while (bookCursor.moveToNext()){
            Book book = new Book(bookCursor.getInt(0),bookCursor.getString(1));
            Log.e(TAG,"query book"+book.toString());
        }
        bookCursor.close();
        Uri userUri = Uri.parse("content://andfans.com.demolist.IPC.provider/user");
        Cursor userCursor = getContentResolver().query(userUri,new String[]{"_id","name","sex"},null,null,null);
        while (userCursor.moveToNext()){
            User user = new User(userCursor.getInt(0),userCursor.getString(1),userCursor.getInt(2) == 1);
            Log.e(TAG,"query user:"+user.toString());
        }
        userCursor.close();
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
    }
}
