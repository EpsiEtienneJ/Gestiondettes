package epsi.dettes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class addDette extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dette);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void retour(View view) {
        Intent intent = new Intent(addDette.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_dette, menu);
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
        List<Dette> todoList;
        DetteAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            helper = new Storage(this.getActivity());

            View rootView = inflater.inflate(R.layout.fragment_add_dette, container, false);
            Button btn = (Button) rootView.findViewById(R.id.addDetteButton);
            final EditText titleDette = (EditText) rootView.findViewById(R.id.titleDette);
            final EditText nameDette = (EditText) rootView.findViewById(R.id.nameDette);
            final EditText numeroDette = (EditText) rootView.findViewById(R.id.numeroDette);
            final EditText montantDette = (EditText) rootView.findViewById(R.id.montantDette);


            btn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String title = titleDette.getText().toString();
                    String name = nameDette.getText().toString();
                    String numero = numeroDette.getText().toString();
                    String montant = montantDette.getText().toString();
                    helper.addDette(title, name, numero, montant);

                    final String msg = "Salut, je te dois " + montant + "€ pour " + title;

                    SmsManager.getDefault().sendTextMessage(numero, null, msg, null, null);
                    
                    Intent redirect = new Intent(v.getContext(),listDettes.class);
                    startActivity(redirect);
                }
            });

            return rootView;
        }
    }

}
