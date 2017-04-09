package andfans.com.demolist.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import andfans.com.demolist.Data.User;

/**
 * Created by 兆鹏 on 2017/4/7.
 */

public class MessengerService extends Service {
    private Handler messengerHandler = new Handler(){
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
                default:super.handleMessage(msg);break;
            }

        }
    };
    private Messenger messenger = new Messenger(messengerHandler);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
