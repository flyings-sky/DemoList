package andfans.com.demolist.IPC;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import andfans.com.demolist.Book;
import andfans.com.demolist.BookManager;
import andfans.com.demolist.R;
import andfans.com.demolist.Services.AIDLService;
import andfans.com.demolist.TestIPC;

/**
 *
 * Created by 兆鹏 on 2017/4/5.
 */
public class TestIPCAidl extends Activity{
    private static final String TAG = "TestIPCAidl";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private BookManager mBookManager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.e(TAG,"receive new book:"+msg.obj);
                    break;
                default:super.handleMessage(msg);break;
            }
        }
    };
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BookManager bookManager = BookManager.Stub.asInterface(service);
            try {
                mBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                Log.e(TAG,list.toString());
                Book newBook = new Book(3,"Android");
                bookManager.addBook(newBook);
                List<Book> newList = bookManager.getBookList();
                Log.e(TAG,newList.toString());
                bookManager.registerListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
        }
    };

    private IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_test_layout);
        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if(mBookManager != null&&mBookManager.asBinder().isBinderAlive()){
            try {
                mBookManager.unregisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
        super.onDestroy();
    }
}
