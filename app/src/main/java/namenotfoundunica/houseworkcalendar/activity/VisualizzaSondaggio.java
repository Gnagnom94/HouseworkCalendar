package namenotfoundunica.houseworkcalendar.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Sondaggio;

public class VisualizzaSondaggio extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_sondaggio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visualizza_sondaggio);
        setSupportActionBar(toolbar);
        setupActionBar();

        Bundle extras = getIntent().getExtras();

        int value = extras.getInt("indice",-1);
        Sondaggio visualizza = GestioneSondaggi.lstSondaggio.get(value-1);
        TextView titolo_sondaggio = (TextView) findViewById(R.id.survey_q);
        TextView descrizione_sondaggio = (TextView) findViewById(R.id.survey_d);

        ListView elenco_risposte = (ListView) findViewById(R.id.listViewSurvey);

        CustomAdapter customAdapter = new CustomAdapter();
        customAdapter.setRisposte(visualizza.risposte);
        elenco_risposte.setAdapter(customAdapter);


        titolo_sondaggio.setText(visualizza.getTitolo());
        descrizione_sondaggio.setText(visualizza.getDescrizione());


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
        Intent openPageHome = new Intent(VisualizzaSondaggio.this, GestioneSondaggi.class);
        startActivity(openPageHome);
        return super.onOptionsItemSelected(item);
    }

    class CustomAdapter extends BaseAdapter
    {
        private List<String>risposte;

        public List<String> getRisposte()
        {
            return risposte;
        }

        public void setRisposte(List<String> risposte) {
            this.risposte = risposte;
        }

        @Override
        public int getCount()
        {
            return risposte.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = getLayoutInflater().inflate(R.layout.custom_answer_layout,null);

            Button risposta = (Button) convertView.findViewById(R.id.answer_button);

            risposta.setText(risposte.get(position));





            return convertView;
        }
    }
}