package epsi.dettes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class listDettes extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dettes);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void retour(View view) {
        Intent intent = new Intent(listDettes.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_dettes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Storage helper;
        ArrayList<String> dataList;
        List<Dette> detteList;
        DetteAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            helper = new Storage(this.getActivity());

            View rootView = inflater.inflate(R.layout.fragment_list_dettes, container, false);

            ListView lst = (ListView) rootView.findViewById(R.id.listDettes);
            final Activity act = this.getActivity();


            detteList = helper.getAll();
            dataList = new ArrayList<String>();

            for (Dette t: detteList){
                dataList.add(t.title);
            }

            adapter = new DetteAdapter(this.getActivity(), detteList);
            lst.setAdapter(adapter);

            lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Dette dette = (Dette) parent.getItemAtPosition(position);
                    Log.v("dette", "suppr dette: " + dette.title + " - " + dette.name);

                    helper.deleteDette(dette);
                    reloadData();
                    return true;
                }
            });

            return rootView;
        }

        public void reloadData() {

            detteList.clear();
            detteList.addAll(helper.getAll());
            adapter.notifyDataSetChanged();

        }
    }

}
