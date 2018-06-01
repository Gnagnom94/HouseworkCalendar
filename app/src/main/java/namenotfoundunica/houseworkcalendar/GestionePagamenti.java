package namenotfoundunica.houseworkcalendar;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class GestionePagamenti extends AppCompatActivity {

    public static ArrayList<Pagamento> pagamenti;
    private boolean flagCreazione=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_pagamenti);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        if(!flagCreazione)
        {
            pagamenti=new ArrayList<>();
            pagamenti.add(new Pagamento("Netflix",14.00f,0,SchermataIniziale.UtentiGruppo));
            pagamenti.add(new Pagamento("Luce enel",122.21f,1,SchermataIniziale.UtentiGruppo));
            pagamenti.add(new Pagamento("Gas",60f,2,SchermataIniziale.UtentiGruppo));
            flagCreazione=true;
        }

        ListView listView = (ListView) findViewById(R.id.listViewPagamenti);
        GestionePagamenti.CustomAdapter customAdapter = new GestionePagamenti.CustomAdapter();
        listView.setAdapter(customAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_Pagamento);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent intent = new Intent(GestionePagamenti.this, AggiuntaSondaggio.class);
                //startActivity(intent);

            }
        });
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return pagamenti.size();
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
            convertView = getLayoutInflater().inflate(R.layout.custom_pagamento_layout,null);
            TextView textView_name = (TextView) convertView.findViewById(R.id.nomePagamento);
            final ProgressBar progressBar = convertView.findViewById(R.id.progressBarPagamento);
            progressBar.setMax(99);
            progressBar.setProgress(0);


            final ImageButton button = convertView.findViewById(R.id.confermaPagamento);
            TextView prezzo = convertView.findViewById(R.id.prezzoText);

            final Pagamento pagamento = pagamenti.get(position);
            button.setId(pagamento.getId());
            float prezzoParziale =(pagamento.getTotale())/(pagamento.getNumeroPaganti());
            prezzo.setText(String.format("%.2f",prezzoParziale)+" €");
            textView_name.setText(pagamento.getNome());


            button.setOnClickListener(new View.OnClickListener()
            {
                @TargetApi(Build.VERSION_CODES.N)
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v)
                {
                    int pro=progressBar.getProgress()+(100/pagamento.getNumeroPaganti());
                    progressBar.setProgress(pro,true);

                    if(progressBar.getProgress()>=progressBar.getMax())//se la barra ha raggiunto il massimo
                        button.setImageResource(R.drawable.ic_done_black_24dp);
                   // button.setEnabled(false);
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
            Intent openPageHome = new Intent(GestionePagamenti.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
