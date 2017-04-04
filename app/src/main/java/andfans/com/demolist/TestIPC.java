package andfans.com.demolist;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import andfans.com.demolist.Data.User;
import andfans.com.demolist.IPC.TestIPCMessenger;

/**
 * IPC测试Demo
 * Created by 兆鹏 on 2017/4/3.
 */
public class TestIPC extends Activity {
    private static Context context;
    private TextView textView;
    private Button btFile,btMessenger;
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Test/file.txt";
    public static class MessengerService extends Service{
        private Handler MessengerHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 11:
                        Bundle bundle = msg.getData();
                        /*
                        我们应用中存在两种类加载器它们分别是BootClassLoader和PathClassLoader.
                        BootClassLoader用来加载系统类,PathClassLoader用来加载我们在应用中自己写的类.
                        所以当类加载器为BootClassLoader时我们要加载自己写的类就会出现ClassNotFound异常.
                        下面这句话就是将加载器指定为PathClassLoader
                        */
                        bundle.setClassLoader(User.class.getClassLoader());
                        User user = (User) bundle.get("user");
                        Log.e("Messenger",user.toString());
                        Messenger messenger = msg.replyTo;
                        Message reply = Message.obtain(null,2);
                        Bundle replyData = new Bundle();
                        replyData.putParcelable("reply",new User("11你好","我已经收到你的消息"));
                        reply.setData(replyData);
                        try {
                            messenger.send(reply);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:super.handleMessage(msg);
                }
            }
        };
        private final Messenger messenger = new Messenger(MessengerHandler);
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return messenger.getBinder();
        }
    }
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
        btMessenger = (Button) findViewById(R.id.id_activity_ipc_messenger);
        btFile = (Button) findViewById(R.id.id_activity_ipc_file);
        Button btBundle = (Button) findViewById(R.id.id_activity_ipc_bundle);
        btMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TestIPCMessenger.class);
                startActivity(intent);
            }
        });
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
