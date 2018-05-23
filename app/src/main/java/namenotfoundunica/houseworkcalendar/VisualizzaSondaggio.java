package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class VisualizzaSondaggio extends AppCompatActivity {

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

        titolo_sondaggio.setText(visualizza.getTitolo());
        descrizione_sondaggio.setText(visualizza.getDescrizione());

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


        Intent openPageHome = new Intent(VisualizzaSondaggio.this, GestioneSondaggi.class);
        startActivity(openPageHome);


        return super.onOptionsItemSelected(item);
    }
}