package andfans.com.demolist.IPC;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import andfans.com.demolist.Data.User;
import andfans.com.demolist.R;
import andfans.com.demolist.Services.MessengerService;

/**
 *
 * Created by 兆鹏 on 2017/4/3.
 */
public class TestIPCMessenger extends Activity {
    private TextView tvGet;
    private Messenger mService;
    //为了收到Service的回复，客户端需要创建一个接收消息的Messenger和Handler
    private Handler MessengerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Bundle bundle = msg.getData();
                    bundle.setClassLoader(User.class.getClassLoader());
                    User user = (User) bundle.get("reply");
                    Log.e("Messenger",user.toString());
                    break;
                default:super.handleMessage(msg);break;
            }
        }
    };
    private Messenger mGetMessenger = new Messenger(MessengerHandler);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger_test_layout);
        tvGet = (TextView) findViewById(R.id.id_activity_messenger_tv01);
        init();
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //根据得到的IBinder对象创建Messenger，然后使用Messenger进行通信
            mService = new Messenger(service);
            Message message = Message.obtain();
            message.what = 11;
            message.replyTo = mGetMessenger;
            Bundle bundle = new Bundle();
            bundle.putParcelable("user",new User(11,"22",true));
            message.setData(bundle);
            message.arg1 = 555;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private void init() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
//        Intent intent2 = new Intent("andfans.com.andfans_csdn.ser");
//        intent2.setClassName("andfans.com.andfans_csdn","andfans.com.andfans_csdn.MainActivity$MyService");
//        bindService(intent2,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
