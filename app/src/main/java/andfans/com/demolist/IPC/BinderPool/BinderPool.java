package andfans.com.demolist.IPC.BinderPool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.net.PortUnreachableException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 兆鹏 on 2017/4/10.
 */

public class BinderPool {
    private static final String TAG = "BinderPoolActivity";
    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;

    private Context mContext;
    private IBinderPool iBinderPool;
    private static volatile BinderPool sInstance;
    //同步工具类
    private CountDownLatch countDownLatch;

    private BinderPool(Context context){
        mContext = context.getApplicationContext();
        //连接服务
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context){
        if(sInstance == null){
            synchronized (BinderPool.class){
                if(sInstance == null){
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService(){
        countDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext,BinderPoolService.class);
        mContext.bindService(service,connection,Context.BIND_AUTO_CREATE);
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                iBinderPool.asBinder().linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    //死亡代理
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            iBinderPool.asBinder().unlinkToDeath(deathRecipient,0);
            iBinderPool = null;
            connectBinderPoolService();
        }
    };
    public static class BinderPoolImpl extends IBinderPool.Stub{

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                default:break;
            }
            return binder;
        }
    }

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;
        Log.e(TAG,"queryBinder:binderPool ="+(iBinderPool == null));
        if (iBinderPool != null){
            try {
                binder = iBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }
}
