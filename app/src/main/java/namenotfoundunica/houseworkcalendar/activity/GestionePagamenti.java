package namenotfoundunica.houseworkcalendar.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import namenotfoundunica.houseworkcalendar.other.Pagamento;
import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Utente;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                showInfoPagamento(position);
            }
        });
        GestionePagamenti.CustomAdapter customAdapter = new GestionePagamenti.CustomAdapter();
        listView.setAdapter(customAdapter);

        final FloatingActionButton fab = findViewById(R.id.fab_Pagamento);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                aggiungiPagamento();
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
                    int i=0;
                    for(Utente utente:pagamento.utentiGruppo)
                    {
                        if(utente.equals(SchermataIniziale.utenteLoggato))
                        {
                            pagamento.statoUtenti[i]=true;
                        }
                        i++;
                    }
                    if(progressBar.getProgress()>=progressBar.getMax())//se la barra ha raggiunto il massimo
                    {
                        button.setImageResource(R.drawable.ic_done_black_24dp);
                        button.setVisibility(View.VISIBLE);
                    }
                    else
                        button.setVisibility(View.INVISIBLE);
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
    private void aggiungiPagamento()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        final EditText etInput    = new EditText(this);
        final EditText prInput  =new EditText(this);

        tvMessage.setText("Inserisci tipo e cifra totale del pagamento");
        etInput.setSingleLine();
        etInput.setHint("Tipo:");
        prInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        prInput.setHint("Prezzo totale:");
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(etInput);
        layout.addView(prInput);
        layout.setPadding(50, 50, 100, 0);

        builder.setView(layout);

        DialogInterface.OnClickListener onPositiveClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                 String tmpTipo= etInput.getText().toString();
                 float tmpPrezzo=Float.valueOf(prInput.getText().toString());
                pagamenti.add(new Pagamento(tmpTipo,tmpPrezzo,pagamenti.size(),SchermataIniziale.UtentiGruppo));
            }
        };
        builder.setPositiveButton("Aggiungi",onPositiveClickListener);
        builder.setNegativeButton("Annulla",null);
        builder.create().show();
    }
    private void showInfoPagamento(int posizione)
    {

        Pagamento pagamento=pagamenti.get(posizione);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        TextView tvMessage = new TextView(this);
        TextView pagati = new TextView(this);
        TextView nonPagati = new TextView(this);

        tvMessage.setText(pagamento.getNome());
        String tmpPagato,tmpNonPagato;
        tmpPagato="Pagato: ";
        tmpNonPagato="Mancano ancora: ";
        int i=0;
        for(Utente utente:pagamento.utentiGruppo)
        {
            if(pagamento.statoUtenti[i]==true)
                tmpPagato=tmpPagato+utente.getNome()+", ";
            else
                tmpNonPagato=tmpNonPagato+utente.getNome()+", ";
            i++;

        }
        pagati.setText(tmpPagato);
        nonPagati.setText(tmpNonPagato);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(pagati);
        layout.addView(nonPagati);
        layout.setPadding(50, 50, 100, 0);

        builder.setView(layout);


        builder.setNegativeButton("Ok",null);
        builder.create().show();
    }
}