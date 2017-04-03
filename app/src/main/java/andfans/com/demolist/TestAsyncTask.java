package andfans.com.demolist;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 兆鹏 on 2017/3/10.
 */
public class TestAsyncTask extends AppCompatActivity{
    @Bind(R.id.id_activity_asynctask_ed)
    EditText editText;
    @Bind(R.id.id_activity_asynctask_bt)
    Button bt;

    @OnClick(R.id.id_activity_asynctask_bt)
    public void runs(){
        new MyTask().execute(editText.getText().toString().trim());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask_layout);
        ButterKnife.bind(this);

    }

    /**
     * 第一个参数用于传入到doInBackground方法中
     * 第二个参数是指的耗时操作在后台执行的进度
     * 第三个指的后台操作完成后，返回的结果
     */
    class MyTask extends AsyncTask<String,Void,String>{
        //在doInBackground()方法前执行，可以更新UI
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            editText.setText("");
        }
        //执行耗时操作，在这里可以调用publishProgress()方法来更新进度的操作
        @Override
        protected String doInBackground(String... params) {
            double result,opNum1,opNum2;
            char op = ' ';
            String para = params[0];
            int index = 0;
            for(int i = 0;i < para.length();i++){
                if(!Character.isDigit(para.charAt(i))){
                    index = i;
                    op = para.charAt(i);
                    break;
                }
            }
            opNum1 = Double.parseDouble(para.substring(0,index));
            opNum2 = Double.parseDouble(para.substring(index+1));

            switch (op){
                case '+':result = opNum1 + opNum2;break;
                case '-':result = opNum1 - opNum2;break;
                case '*':result = opNum1 * opNum2;break;
                case '/':result = opNum1 / opNum2;break;
                default:return "error";
            }
            return String.valueOf(result);
        }



        //在UI线程中执行，在doInBackground调用publishProgress(Progress...)后执行
        //用于在前台显示后台进程的完成情况
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        //在耗时操作完成后，触发这个方法，在UI线程中执行，更新UI，告诉用户后台操作已经完成
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            editText.setText(s);
        }
    }


}
