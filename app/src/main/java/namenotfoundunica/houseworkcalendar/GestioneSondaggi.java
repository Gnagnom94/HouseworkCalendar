package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GestioneSondaggi extends AppCompatActivity
{

    public static List<Sondaggio> lstSondaggio;
    private static boolean flagCreation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondaggi);

        if (!flagCreation)
        {
            lstSondaggio = new ArrayList<>();
            List<String> risposte = new ArrayList<String>();
            risposte.add("si");
            risposte.add("no");
            lstSondaggio.add(new Sondaggio("titolo1", "descrizione1", "wait", 1, risposte));
            lstSondaggio.add(new Sondaggio("titolo2", "descrizione2", "wait", 2, risposte));
            lstSondaggio.add(new Sondaggio("titolo3", "descrizione3", "wait", 3, risposte));
            flagCreation = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        ListView listView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        final FloatingActionButton fab = findViewById(R.id.fab_sondaggi);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GestioneSondaggi.this, AggiuntaSondaggio.class);
                startActivity(intent);

            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = getLayoutInflater().inflate(R.layout.custom_survey_layout,null);
            TextView textView_name = (TextView) convertView.findViewById(R.id.domanda_sondaggio);
            final Button button = (Button) convertView.findViewById(R.id.vota_sondaggio);
            Sondaggio sondaggio = lstSondaggio.get(position);
            button.setId(sondaggio.getId());
            textView_name.setText(sondaggio.getTitolo());


            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(GestioneSondaggi.this, VisualizzaSondaggio.class);
                    intent.putExtra("indice",button.getId());
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
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
            Intent openPageHome = new Intent(GestioneSondaggi.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
