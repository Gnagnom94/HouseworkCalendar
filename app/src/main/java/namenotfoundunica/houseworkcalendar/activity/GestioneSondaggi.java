package namenotfoundunica.houseworkcalendar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Sondaggio;
import namenotfoundunica.houseworkcalendar.other.Utente;

public class GestioneSondaggi extends AppCompatActivity
{

    public static ArrayList<Sondaggio> lstSondaggio;
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
            lstSondaggio.add(new Sondaggio("titolo1", "descrizione1", "wait", 0, risposte));
            lstSondaggio.add(new Sondaggio("titolo2", "descrizione2", "wait", 1, risposte));
            lstSondaggio.add(new Sondaggio("titolo3", "descrizione3", "wait", 2, risposte));
            flagCreation = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        ListView listView = (ListView) findViewById(R.id.listViewSondaggi);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                showInfoSondaggio(position);
            }
        });

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
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            convertView = getLayoutInflater().inflate(R.layout.custom_survey_layout,null);
            TextView textView_name = (TextView) convertView.findViewById(R.id.domanda_sondaggio);
            final ImageView button = convertView.findViewById(R.id.vota_sondaggio);
            final Sondaggio sondaggio = lstSondaggio.get(position);
            button.setId(sondaggio.getId());
            textView_name.setText(sondaggio.getTitolo());
            boolean flagVotoCompleto=true;

            for(int i=0;i<sondaggio.utentiGruppo.size();i++)
                if(sondaggio.statoUtenti[i]==-1)
                    flagVotoCompleto=false;

            if(flagVotoCompleto==true)
            {
                button.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lstSondaggio.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }

            else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GestioneSondaggi.this, VisualizzaSondaggio.class);
                        intent.putExtra("indice", position);
                        startActivity(intent);
                    }
                });
            }
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

    private void showInfoSondaggio(int posizione)
    {

        Sondaggio sondaggio=lstSondaggio.get(posizione);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        TextView tvMessage = new TextView(this);
        TextView tvMessage2 = new TextView(this);
        TextView contenuto=new TextView(this);
        int i=0;
        String tmp="";
        for(String r:sondaggio.getRisposte())
        {
            int j=0;
            int contatore=0;
            tmp=tmp+r+": ";
            for(Utente utente:sondaggio.utentiGruppo)
            {
                if(sondaggio.statoUtenti[j]==i)
                    contatore++;
                j++;
            }
            i++;
            tmp=tmp+Integer.toString((contatore*100)/sondaggio.utentiGruppo.size())+"% \n";
        }


        tvMessage.setText(sondaggio.getTitolo());
        tvMessage2.setText(sondaggio.getDescrizione());
        contenuto.setText(tmp);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(tvMessage2);
        layout.addView(contenuto);
        layout.setPadding(50, 50, 100, 0);

        builder.setView(layout);


        builder.setNegativeButton("Ok",null);
        builder.create().show();
    }
}
