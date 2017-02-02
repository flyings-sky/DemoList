package andfans.com.demolist;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 * Created by 兆鹏 on 2017/2/2.
 */
public abstract class BaseActivity extends ListActivity {
    abstract ClassAndName [] getDatas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<ClassAndName> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getDatas());
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ClassAndName classAndName = (ClassAndName) l.getItemAtPosition(position);
        startActivity(new Intent(this,classAndName.getaClass()));
    }
}
