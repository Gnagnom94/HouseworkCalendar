package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Calendar;
import java.util.GregorianCalendar;

import Fragments.DatePickerFragment;
import Fragments.TimePickerFragment;

public class AggiuntaEvento extends AppCompatActivity
{
    public Calendar dataInizio;
    public Calendar dataFine;
    public boolean flagTime;
    public TextView dataEventoText;
    public TextView timeInizioEvento;
    public TextView timeFineEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_evento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        //inizializzo le variabili calendar con la data di oggi
        dataInizio=new GregorianCalendar(1800,10,3);
        dataFine = new GregorianCalendar(1800,10,3);

        dataEventoText = findViewById(R.id.TextData1);
        timeInizioEvento = findViewById(R.id.TimeInizio);
        timeFineEvento = findViewById(R.id.TimeFine);
        AggiuntaEventi();
        popolaSpinnerUtenti();
        popolaSpinnerCategoria();
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void popolaSpinnerUtenti()
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerUtente);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Utenti, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    private void popolaSpinnerCategoria()
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategoria);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Categorie, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent openPageHome = new Intent(this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void AggiuntaEventi()
    {

        timeInizioEvento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new TimePickerFragment();
                flagTime=true;
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        timeFineEvento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new TimePickerFragment();
                flagTime=false;
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
    }

}
