// IBinderPool.aidl
package andfans.com.demolist.IPC.BinderPool;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
