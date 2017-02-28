package andfans.com.demolist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 测试证明程序执行System.exit(0)之后，onStop,onPause,onDestroy全部都不会执行
 * Created by 兆鹏 on 2017/2/26.
 */
public class TestRecycleActivity extends AppCompatActivity {
    private Button bt1,bt2,bt3;
    private static final String TAG = ".TestRecycleActivity";
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_test);
        Log.e(TAG,"onCreate");
//        int pid = Process.myPid();
//        Log.e(TAG,pid+"");
//        ActivityManager.RunningAppProcessInfo info = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses().get(0);
        context = this;
        bt1 = (Button) findViewById(R.id.id_activity_recycle_bt01);
        bt2 = (Button) findViewById(R.id.id_activity_recycle_bt02);
        bt3 = (Button) findViewById(R.id.id_activity_recycle_bt03);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
//                PackageManager p = new MockPackageManager();
//                p.queryIntentActivities(i,PackageManager.MATCH_DEFAULT_ONLY);
//                p.resolveActivity(i,PackageManager.MATCH_DEFAULT_ONLY);
                i.setAction("aaaa");
                startActivity(i);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,TestRecycleActivity.class));
            }
        });
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG,"onNewIntent");
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
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }
}
