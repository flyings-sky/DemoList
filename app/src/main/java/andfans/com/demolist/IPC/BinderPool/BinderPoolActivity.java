package andfans.com.demolist.IPC.BinderPool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import andfans.com.demolist.R;

/**
 *
 * Created by 兆鹏 on 2017/4/10.
 */

public class BinderPoolActivity extends Activity {
    private static final String TAG = "BinderPoolActivity";
    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binderpool_test_layout);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork(){
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.e(TAG,"visit ISecurityCenter");
        String msg = "hello world-安卓";
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.e(TAG,"doWork:password ="+password);
            Log.e(TAG,"doWork:content ="+mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.e(TAG,"doWork:3+5 ="+mCompute.add(3,5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
