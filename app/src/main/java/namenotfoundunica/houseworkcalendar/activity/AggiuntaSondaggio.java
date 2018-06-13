package namenotfoundunica.houseworkcalendar.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Sondaggio;

public class AggiuntaSondaggio extends AppCompatActivity
{
    final List<String> risposte = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_sondaggio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();


        ListView lista_risposte = (ListView) findViewById(R.id.ListAnswer);

        risposte.add("Titolo");
        risposte.add("Descrizione");
        risposte.add("Risposta 1");
        risposte.add("Risposta 2");


        CustomAdapter custom = new CustomAdapter(AggiuntaSondaggio.this, R.layout.custom_addq_layout, risposte);
        lista_risposte.setAdapter(custom);

        /*send_survey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                risposte.add(risposta1.getText().toString());
                risposte.add(risposta2.getText().toString());
                Sondaggio survey = new Sondaggio(titolo_sondaggio.getText().toString(),
                                                descrizione_sondaggio.getText().toString(),
                                                "wait", GestioneSondaggi.lstSondaggio.size()+1, risposte);

                GestioneSondaggi.lstSondaggio.add(survey);
                Intent backToSurvey = new Intent(AggiuntaSondaggio.this, GestioneSondaggi.class);
                startActivity(backToSurvey);

            }
        });*/


    }
    public class CustomAdapter extends ArrayAdapter
    {
        private Context mContext;
        private List<String> risposte;
        private LayoutInflater inflater;
        private int count = 0;

        public CustomAdapter(@NonNull Context mContext, int resource, List<String> risposte)
        {
            super(mContext, resource, risposte);
            this.risposte = risposte;
            inflater = LayoutInflater.from(mContext);
        }

        public int getCount()
        {
            return risposte.size();
        }
        public long getItemId(int position)
        {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            if (inflater == null)
            {
                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            if(position <= 3) {
                switch (position) {
                    case 0:
                        convertView = inflater.inflate(R.layout.custom_firsttw_layout, parent, false);
                        EditText editTextTitolo = convertView.findViewById(R.id.editText);
                        editTextTitolo.setHint("Inserisci Titolo");
                        return convertView;

                    case 1:
                        convertView = inflater.inflate(R.layout.custom_firsttw_layout, parent, false);
                        EditText editTextDescrizione = convertView.findViewById(R.id.editText);
                        editTextDescrizione.setHint("Inserisci Descrizione");
                        return convertView;

                    case 2:
                        convertView = inflater.inflate(R.layout.custom_secondtw_layout, parent, false);
                        EditText editTextRisposta1 = convertView.findViewById(R.id.editText2);
                        editTextRisposta1.setHint("Inserisci Risposta 1");
                        return convertView;

                    case 3:
                        convertView = inflater.inflate(R.layout.custom_addq_layout, parent, false);

                        EditText editTextRisposta2 = convertView.findViewById(R.id.AnswerAgg);
                        editTextRisposta2.setHint("Inserisci Risposta 2");

                        final ImageButton piu = (ImageButton) convertView.findViewById(R.id.addQ);
                        final ImageButton meno = (ImageButton) convertView.findViewById(R.id.removeQ);

                        piu.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                risposte.add("prova");
                                piu.setVisibility(View.GONE);
                                meno.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });
                        meno.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                risposte.remove(position);
                                piu.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        });
                        return convertView;
                }
            }
            if(position > 3)
            {
                if (convertView == null)
                {
                    convertView = inflater.inflate(R.layout.custom_addq_layout, parent, false);
                }

                EditText editTextRispostaI = convertView.findViewById(R.id.AnswerAgg);
                editTextRispostaI.setHint("Inserisci Risposta " + position);

                final ImageButton piu = (ImageButton) convertView.findViewById(R.id.addQ);
                final ImageButton meno = (ImageButton) convertView.findViewById(R.id.removeQ);

                piu.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        risposte.add("prova");
                        piu.setVisibility(View.GONE);
                        meno.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });
                meno.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
//                        risposte.remove(position);
                        piu.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                });


                return convertView;
            }
            return null;
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
    public boolean onOptionsItemSelected(MenuItem item) {


            Intent openPageHome = new Intent(AggiuntaSondaggio.this, GestioneSondaggi.class);
            startActivity(openPageHome);


        return super.onOptionsItemSelected(item);
    }
}
