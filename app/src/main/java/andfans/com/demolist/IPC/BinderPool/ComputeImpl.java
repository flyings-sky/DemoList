package andfans.com.demolist.IPC.BinderPool;

import android.os.RemoteException;

/**
 * Created by 兆鹏 on 2017/4/10.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
