package andfans.com.demolist.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Socket进行IPC通讯服务端Demo
 * Created by 兆鹏 on 2017/4/10.
 */

public class TCPServerService extends Service {
    public static final String TAG = "SocketServer";
    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{
      "你好啊，哈哈", "请问你叫什么名字呀？","今天北京天气不错，Shy","你知道吗?我可是可以和多个人同时聊天的哦"
            ,"给你讲个笑话吧:据说爱笑的人运气都不会太差"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable{
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            }catch (IOException e){
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestoryed){
                try {
                    //接受客户端请求
                    final Socket client = serverSocket.accept();
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                response(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void response(Socket client) throws IOException{
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                client.getOutputStream())),true);
        out.println("欢迎来到聊天室");
        while (!mIsServiceDestoryed){
            String str = in.readLine();
            if(str == null){
                //客户端断开连接
                break;
            }
            Log.e(TAG,"MSG from client:"+str);
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            Log.e(TAG,"send:"+msg);
        }
        Log.e(TAG,"client quit");
        in.close();
        out.close();
        client.close();
    }
}
