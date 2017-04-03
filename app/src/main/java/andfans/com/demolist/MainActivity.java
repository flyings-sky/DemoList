package andfans.com.demolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends BaseActivity{
    private static final String TAG = ".MainActivity";
    @Override
    ClassAndName[] getDatas() {
        ClassAndName[] datas = {
                new ClassAndName(TestMenuActivity.class,"菜单测试Demo"),
                new ClassAndName(TestRecycleActivity.class,"activity生命周期测试"),
                new ClassAndName(TestAsyncTask.class,"AsyncTask测试"),
                new ClassAndName(TestIPC.class,"Android中的IPC机制")
        };

        return datas;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG,"onNewIntent");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.e(TAG,"onRestoreInstanceState");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
//        int pid = Process.myPid();
//        Log.e(TAG,pid+"");
//        Toast.makeText(this,""+pid,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

}
