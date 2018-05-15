package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Sondaggi extends AppCompatActivity {


    List<Sondaggio> lstSondaggio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondaggi);


        lstSondaggio = new ArrayList<>();
        lstSondaggio.add(new Sondaggio("titolo1","maggioranza","descrizione1","wait"));
        lstSondaggio.add(new Sondaggio("titolo2","unanimità","descrizione2","wait"));
        lstSondaggio.add(new Sondaggio("titolo3","maggioranza","descrizione2","wait"));

        ListView listView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return lstSondaggio.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_survey_layout,null);

            TextView textView_name = (TextView) convertView.findViewById(R.id.domanda_sondaggio);

            Sondaggio sondaggio = lstSondaggio.get(position);
            textView_name.setText(sondaggio.getTitolo());
            return convertView;
        }
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent openPageHome = new Intent(Sondaggi.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
