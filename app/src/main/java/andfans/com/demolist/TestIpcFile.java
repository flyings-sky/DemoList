package andfans.com.demolist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import andfans.com.demolist.Data.User;

/**
 *
 * Created by 兆鹏 on 2017/4/3.
 */
public class TestIpcFile extends Activity{
    private static TextView tvGet;
    static User user;
    static class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            File file = new File(TestIPC.FILE_PATH);
            ObjectInputStream objectInputStream = null;
            Log.e("File","begin");
            if(file.exists()){
                try {
                    objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    user = (User) objectInputStream.readObject();
                    tvGet.setText(user.toString());
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }else {
                Log.e("File","NO find");
            }
            Log.e("File","end");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_test_layout);
        tvGet = (TextView) findViewById(R.id.id_activity_file_tv01);
        new MyThread().start();

    }
}
