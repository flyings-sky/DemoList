package andfans.com.demolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import andfans.com.demolist.Data.User;

/**
 * IPC测试Demo
 * Created by 兆鹏 on 2017/4/3.
 */
public class TestIPC extends Activity {
    private Context context;
    private TextView textView;
    Map<Integer,String> data = new ArrayMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_test_layout);
        context = this;
        data.put(1,"aaaaa");
        data.put(1,"bbbbbbbbb");
        Log.e("Bundle",data.get(1));
        textView = (TextView) findViewById(R.id.id_activity_ipc_response);
        Button btBundle = (Button) findViewById(R.id.id_activity_ipc_bundle);
        btBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = User.getUser("11","小明");
                Intent intent = new Intent(context,TestIPCBundle.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user",user);
                intent.putExtra("user",bundle);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0&&resultCode == 1){
            Bundle bundle = data.getBundleExtra("response");
            User user = (User) bundle.get("response");
            textView.setText("收到回复消息:"+user.toString());
        }
    }
}
