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
        final Sondaggio sondaggio = GestioneSondaggi.lstSondaggio.get(value);
        TextView titolo_sondaggio = (TextView) findViewById(R.id.survey_q);
        TextView descrizione_sondaggio = (TextView) findViewById(R.id.survey_d);
        final RadioGroup radioGrp = (RadioGroup) findViewById(R.id.radioGroup);
        Button conferma= findViewById(R.id.survey_confirm);

        titolo_sondaggio.setText(sondaggio.getTitolo());
        if(sondaggio.getDescrizione().compareTo("")!=0)
        {
            descrizione_sondaggio.setText(sondaggio.getDescrizione());
        }
       else
        {
            descrizione_sondaggio.setText("Nessuna descrizione specificata");
        }

        int i=0;
//        if(sondaggio.statoUtenti[]!=-1)
        if(sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()]== -1)
        {
            for(String s:sondaggio.getRisposte())
            {

                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(s);
                radioButton.setId(i);
                radioGrp.addView(radioButton);
                if(i==0)
                    radioButton.setChecked(true);
                i++;
            }
        }
        else
        {
            Toast.makeText(this, "Hai votato la risposta: "+ sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()], Toast.LENGTH_SHORT).show();

        }



        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()] = radioGrp.getCheckedRadioButtonId();
                Intent openPage = new Intent(VisualizzaSondaggio.this, GestioneSondaggi.class);
                startActivity(openPage);



            }
        });
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
        startActivity(openPageHome);
        return super.onOptionsItemSelected(item);
    }
}