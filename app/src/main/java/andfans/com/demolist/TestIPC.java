package andfans.com.demolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import andfans.com.demolist.Data.User;

/**
 * IPC测试Demo
 * Created by 兆鹏 on 2017/4/3.
 */
public class TestIPC extends Activity {
    private static Context context;
    private TextView textView;
    private Button btFile;
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Test/file.txt";
    static class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            User user = new User("File","fileValue");
            File file = new File(TestIPC.FILE_PATH);
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(user);
            }catch (IOException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(context,TestIpcFile.class);
            context.startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_test_layout);
        context = this;
        textView = (TextView) findViewById(R.id.id_activity_ipc_response);
        btFile = (Button) findViewById(R.id.id_activity_ipc_file);
        Button btBundle = (Button) findViewById(R.id.id_activity_ipc_bundle);
        btFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new MyThread();
                t.start();
            }
        });
        btBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("11","小明");
                Intent intent = new Intent(context,TestIPCBundle.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user",user);
                intent.putExtra("user",bundle);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0&&resultCode == 1){
            Bundle bundle = data.getBundleExtra("response");
            User user = (User) bundle.get("response");
            textView.setText("收到回复消息:"+user.toString());
        }
    }
}
