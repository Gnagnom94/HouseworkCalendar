package namenotfoundunica.houseworkcalendar.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Sondaggio;
import namenotfoundunica.houseworkcalendar.other.Utente;


public class VisualizzaSondaggio extends AppCompatActivity
{
    private Sondaggio sondaggio;
    private RadioGroup radioGrp;
    private static int value;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_sondaggio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visualizza_sondaggio);
        setSupportActionBar(toolbar);
        setupActionBar();

        Bundle extras = getIntent().getExtras();

        value = extras.getInt("indice",-1);
        sondaggio = GestioneSondaggi.lstSondaggio.get(value);
        TextView titoloSondaggio = (TextView) findViewById(R.id.survey_q);
        TextView descrizioneSondaggio = (TextView) findViewById(R.id.survey_d);
        radioGrp = (RadioGroup) findViewById(R.id.radioGroup);
        final Button conferma= findViewById(R.id.survey_confirm);

        titoloSondaggio.setText(sondaggio.getTitolo());
        if(sondaggio.getDescrizione().compareTo("")!=0)
        {
            descrizioneSondaggio.setText(sondaggio.getDescrizione());
        }
        else
        {
            descrizioneSondaggio.setText("Nessuna descrizione specificata");
        }

        int i=0;
        for(String s:sondaggio.getRisposte())
        {

            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(s);
            radioButton.setId(i);
            radioGrp.addView(radioButton);
            if(i == 0)
                radioButton.setChecked(true);
            i++;
        }
        if(sondaggio.getStato().compareTo("done")==0)
        {
            conferma.setVisibility(View.GONE);
        }
        if(sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()]== -1)
        {
            conferma.setOnClickListener(new Conferma());
        }
        else
        {
            for(i = 0; i < sondaggio.risposte.size(); i++)
        {
            if(i == sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()])
            {
                RadioButton radiob = (RadioButton) radioGrp.getChildAt(i);
                radiob.setChecked(true);


            }
            radioGrp.getChildAt(i).setEnabled(false);
        }
            conferma.setText("Modifica risposta");
            conferma.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                  for(int j = 0; j < sondaggio.risposte.size(); j++)
                  {
                      radioGrp.getChildAt(j).setEnabled(true);
                  }
                  conferma.setText("Conferma");
                  conferma.setOnClickListener(new Conferma());

                }
            });

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

        Intent openPageHome = new Intent(this, GestioneSondaggi.class);
        openPageHome.putExtra("indice",-1);
        startActivity(openPageHome);
        return super.onOptionsItemSelected(item);
    }

    private class Conferma implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(VisualizzaSondaggio.this, "Voto acquisito", Toast.LENGTH_SHORT).show();
            sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()] = radioGrp.getCheckedRadioButtonId();
            sondaggio.setStato("answer");
            Intent openPage = new Intent(VisualizzaSondaggio.this, GestioneSondaggi.class);
            openPage.putExtra("Sondaggio",sondaggio);
            startActivity(openPage);
        }

    }
}