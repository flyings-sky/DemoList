package andfans.com.demolist;

import android.os.Bundle;

public class MainActivity extends BaseActivity{
    @Override
    ClassAndName[] getDatas() {
        ClassAndName[] datas = {
                new ClassAndName(TestMenuActivity.class,"菜单测试Demo")
        };

        return datas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
