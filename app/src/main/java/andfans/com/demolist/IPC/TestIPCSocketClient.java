package andfans.com.demolist.IPC;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import andfans.com.demolist.R;
import andfans.com.demolist.Services.TCPServerService;

/**
 * IPC之Socket客户端Demo
 * Created by 兆鹏 on 2017/4/10.
 */

public class TestIPCSocketClient extends Activity {
    private static final String TAG = "SocketClient";
    private static final int MESSAGE_RECEIVE_NEW_MESSAGE = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    private Button btSend;
    private EditText editText;
    private PrintWriter printWriter;
    private Socket mClientSocket;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MESSAGE:
                    String receive = (String) msg.obj;
                    Log.e(TAG,"receive:"+receive);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    Log.e(TAG,"connected!!!");
                    break;
                default:break;
            }
        }
    };

    @Override
    @SuppressLint("SimpleDateFormat")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_test_layout);
        editText = (EditText) findViewById(R.id.id_activity_socket_ed01);
        btSend = (Button) findViewById(R.id.id_activity_socket_bt01);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = editText.getText().toString();
                if(!TextUtils.isEmpty(msg)&&printWriter != null){
                    printWriter.println(msg);
                    editText.setText("");
                    String time = new SimpleDateFormat("(HH:mm:ss)").format(new Date(System.currentTimeMillis()));
                    final String showedMsg = "self"+time+":"+msg+"\n";
                    Log.e(TAG,showedMsg);
                }
            }
        });
        Intent intent = new Intent(this, TCPServerService.class);
        startService(intent);
        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }
    @SuppressLint("SimpleDateFormat")
    private void connectTCPServer(){
        Socket socket = null;
        while(socket == null){
            try{
                //连接服务器
                socket = new Socket("localhost",8688);
                mClientSocket = socket;
                //创建从客户端到服务器传递数据的流
                printWriter = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream())),true);
                handler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.e(TAG,"connect server success");
            }catch (IOException e){
                e.printStackTrace();
                SystemClock.sleep(1000);
                Log.e(TAG,"connect failed");
            }
        }
        //接受服务端消息
        try {
            //用于客户端从服务器端接收数据的流
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            while(!TestIPCSocketClient.this.isFinishing()){
                String msg = br.readLine();
                Log.e(TAG,"receive:"+msg);
                if(msg != null){
                    String time = new SimpleDateFormat("(HH:mm:ss)").format(new Date(System.currentTimeMillis()));
                    final String showedMsg = "server"+time+":"+msg+"\n";
                    handler.obtainMessage(MESSAGE_RECEIVE_NEW_MESSAGE,showedMsg).sendToTarget();
                }
            }
            Log.e(TAG,"quit....");
            printWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
