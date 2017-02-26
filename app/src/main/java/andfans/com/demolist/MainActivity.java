package andfans.com.demolist;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity{

    private static final String TAG = ".MainActivity";
    @Override
    ClassAndName[] getDatas() {
        ClassAndName[] datas = {
                new ClassAndName(TestMenuActivity.class,"菜单测试Demo"),
                new ClassAndName(TestRecycleActivity.class,"activity生命周期测试")
        };

        return datas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
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
