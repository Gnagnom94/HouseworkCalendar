package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AggiuntaSondaggio extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_sondaggio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        final TextView titolo_sondaggio = (TextView) findViewById(R.id.titolo_sondaggio);
        final TextView descrizione_sondaggio = (TextView) findViewById(R.id.descrizione_sondaggio);
        Button send_survey = (Button) findViewById(R.id.send_survey);

        send_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Sondaggio survey = new Sondaggio(titolo_sondaggio.getText().toString(),
                                                descrizione_sondaggio.getText().toString(),
                                                "wait",Sondaggi.lstSondaggio.size()+1);
                Sondaggi.lstSondaggio.add(survey);
                Intent backToSurvey = new Intent(AggiuntaSondaggio.this, Sondaggi.class);
                startActivity(backToSurvey);

            }
        });


    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


            Intent openPageHome = new Intent(AggiuntaSondaggio.this, Sondaggi.class);
            startActivity(openPageHome);


        return super.onOptionsItemSelected(item);
    }
}
