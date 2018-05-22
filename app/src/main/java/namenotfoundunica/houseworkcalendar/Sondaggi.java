package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Sondaggi extends AppCompatActivity {


    public static List<Sondaggio> lstSondaggio;
    private static boolean flagCreation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondaggi);

        if (!flagCreation)
        {
            lstSondaggio = new ArrayList<>();
            lstSondaggio.add(new Sondaggio("titolo1" , "descrizione1", "wait", 1));
            lstSondaggio.add(new Sondaggio("titolo2" , "descrizione2", "wait", 2));
            lstSondaggio.add(new Sondaggio("titolo3", "descrizione3", "wait", 3));
            flagCreation = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        ListView listView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_sondaggi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Sondaggi.this, AggiuntaSondaggio.class);
                startActivity(intent);

            }
        });


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
            final Button button = (Button) convertView.findViewById(R.id.vota_sondaggio);
            Sondaggio sondaggio = lstSondaggio.get(position);
            button.setId(sondaggio.getId());
            textView_name.setText(sondaggio.getTitolo());


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(Sondaggi.this,""+button.getId(), Toast.LENGTH_SHORT).show();
                }
            });
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
