package andfans.com.demolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import andfans.com.demolist.Data.User;

/**
 *
 * Created by 兆鹏 on 2017/4/3.
 */
public class TestIPCBundle extends Activity {
    private TextView tvGet;
    private EditText tvSend;
    private Button btSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundle_test_layout);
        tvGet = (TextView) findViewById(R.id.id_activity_Bundle_tv01);
        tvSend = (EditText) findViewById(R.id.id_activity_Bundle_tv02);
        btSend = (Button) findViewById(R.id.id_activity_Bundle_bt01);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("user");
        User user = (User) bundle.get("user");
        tvGet.setText(user.toString());
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str[] = tvSend.getText().toString().split(",|，");
                /*
                虽然在多个进程中单例模式是失效的(也就是说多个进程有多个User对象)，
                但是在每一个进程中都会有唯一一个User对象，所以程序只有第一次返回的结果是用户输入的结果，
                除了第一次，返回的都是第一次我们输入的消息
                 */
                User user = new User(str[0],str[1]);
                Bundle bundle = new Bundle();
                bundle.putParcelable("response",user);
                Intent intent = new Intent();
                intent.putExtra("response",bundle);
                setResult(1,intent);
                tvSend.setText("");
                finish();
            }
        });
    }
}
