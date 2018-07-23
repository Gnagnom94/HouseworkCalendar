package namenotfoundunica.houseworkcalendar.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import namenotfoundunica.houseworkcalendar.other.Calendario;
import namenotfoundunica.houseworkcalendar.other.ColorNameBinder;
import namenotfoundunica.houseworkcalendar.other.Evento;
import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Pagamento;
import namenotfoundunica.houseworkcalendar.other.Utente;
import namenotfoundunica.houseworkcalendar.fragments.DatePickerFragment;
import namenotfoundunica.houseworkcalendar.fragments.TimePickerFragment;

public class AggiuntaEvento extends AppCompatActivity
{
    private AutoCompleteTextView nomeAttivita;
    private ImageButton colorPickerButton;
    private ColorNameBinder colorNameScelto;

    private Button buttonDataPicker;
    private Button buttonInizioTimePicker;
    private Button buttonFineTimePicker;

    public TextView dataEventoText; // uso dataInizio per avere la data dell'evento
    public TextView timeInizioEvento;
    public TextView timeFineEvento;
    public Calendar dataInizio;
    public Calendar dataFine;
    DateFormat dfTime = new SimpleDateFormat("HH:mm");
    DateFormat dfData = new SimpleDateFormat("dd/MM/yyyy");

    private Spinner categoriaSpinner;
    private String sceltaSpinnerCategoria;

    private Switch ripetizione;

    private Spinner utenteSpinner;
    private Utente utenteSceltoSpinner;
    private Switch groupActivityFlag;

    private TextView noteAttivita;

    private Button bottoneConferma;

    public boolean flagTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_evento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        //inizializzo le variabili
        colorNameScelto=new ColorNameBinder("","");
        dataInizio = Calendar.getInstance();
        dataFine = Calendar.getInstance();
        utenteSceltoSpinner=SchermataIniziale.utenti.get(0);//seleziono il primo elemento dell'array
        utenteSpinner=findViewById(R.id.spinnerUtente);
        utenteSpinner.setEnabled(false);
        groupActivityFlag=findViewById(R.id.switchGruppo);
        bottoneConferma=findViewById(R.id.ConfermaButton);
        dataEventoText = findViewById(R.id.TextData1);
        timeInizioEvento = findViewById(R.id.TimeInizio);
        timeFineEvento = findViewById(R.id.TimeFine);
        noteAttivita=findViewById(R.id.NoteInput);

        buttonDataPicker=findViewById(R.id.buttonDataPicker);
        buttonInizioTimePicker=findViewById(R.id.buttonInizioTimePicker);
        buttonFineTimePicker=findViewById(R.id.buttonFineTimePicker);

        colorPickerButton=findViewById(R.id.ColorPicker);
        ripetizione=findViewById(R.id.switch1);

        nomeAttivita=findViewById(R.id.textNomeInput);
        categoriaSpinner=findViewById(R.id.spinnerCategoria);

        if(getIntent().getStringExtra("Chiamante").equals("SettimanaTipo")){
            ripetizione.setChecked(true);
            ripetizione.setClickable(false);
            ripetizione.setEnabled(false);
            groupActivityFlag.setChecked(true);
            groupActivityFlag.setClickable(false);
            groupActivityFlag.setEnabled(false);

        }

        if(getIntent().getIntExtra("Evento",-1)>=0)
        {
            Evento tmp = SchermataIniziale.calendario.get(getIntent().getIntExtra("Evento",-1));
            colorNameScelto=tmp.getColorNameBinder();
            dataInizio=tmp.getInizio();
            dataFine=tmp.getFine();
            utenteSceltoSpinner=tmp.getUtente();
            groupActivityFlag.setChecked(tmp.isGroupFlag());

            utenteSpinner.setEnabled(tmp.isGroupFlag());
            ripetizione.setChecked(tmp.getFlagRipetizione());
            nomeAttivita.setText(tmp.getNote());
            sceltaSpinnerCategoria=tmp.getCategoria();
            nomeAttivita.setText(tmp.getColorNameBinder().getNomeEvento());
            colorPickerButton.setBackgroundColor(tmp.getColorNameBinder().getColoreEventoToInt());

        }

        dataEventoText.setText(dfData.format(dataInizio.getTime()));
        //Log.d("datainizio", ""+ dataInizio.get(Calendar.HOUR_OF_DAY) + ":" + dataInizio.get(Calendar.MINUTE));

        timeInizioEvento.setText(dfTime.format(dataInizio.getTime()));
        timeFineEvento.setText(dfTime.format(dataFine.getTime()));

        AggiuntaEventi();
        popolaSpinnerUtenti();
        popolaSpinnerCategoria();
        popolaAutocompleteNome();


    }


    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void popolaAutocompleteNome()
    {
        ArrayList<String> listaNomi= new  ArrayList<String>();
        for(ColorNameBinder u:SchermataIniziale.colorNameBinder)
        {
            listaNomi.add(u.getNomeEvento());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaNomi);
        nomeAttivita.setAdapter(adapter);
    }
    private void popolaSpinnerUtenti()
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerUtente);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<String> listaUtenti= new  ArrayList<String>();
        if(SchermataIniziale.utenteLoggato!=null)
            listaUtenti.add(SchermataIniziale.utenteLoggato.getNome());

        for(Utente u:SchermataIniziale.utenti)
        {
            if(u!=SchermataIniziale.utenteLoggato)
                listaUtenti.add(u.getNome());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listaUtenti);
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
        Intent openPageHome;
        if (id == android.R.id.home)
        {
            String callingActivity = getIntent().getStringExtra("Chiamante");

            switch (callingActivity) {
                case "SchermataIniziale":
                    openPageHome = new Intent(this, SchermataIniziale.class);
                    startActivity(openPageHome);
                    break;
                case "SettimanaTipo":
                    openPageHome = new Intent(this, SettimanaTipo.class);
                    startActivity(openPageHome);
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AggiuntaEventi()
    {
        nomeAttivita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeAttivita.showDropDown();
            }
        });
        nomeAttivita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=nomeAttivita.getText().toString();
                for(ColorNameBinder i:SchermataIniziale.colorNameBinder)
                {
                    if(str.compareToIgnoreCase(i.getNomeEvento())==0)
                    {
                        colorNameScelto=i;
                        colorPickerButton.setBackgroundColor(i.getColoreEventoToInt());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                colorNameScelto.setNomeEvento(s.toString());
            }
        });



        buttonDataPicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

       buttonInizioTimePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new TimePickerFragment();
                flagTime=true;
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        buttonFineTimePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new TimePickerFragment();
                flagTime=false;
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        utenteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id)
            {
                utenteSceltoSpinner=SchermataIniziale.utenti.get(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
            });

        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
                sceltaSpinnerCategoria = (String) adapter.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        bottoneConferma.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(getIntent().getIntExtra("Evento",-1)>=0)//controllo se è un vecchio evento modifcato
                    SchermataIniziale.calendario.remove(getIntent().getIntExtra("Evento", -1));//rimuovo il vecchio evento



                //controllo che sia stato inserito un nome
                if((nomeAttivita.getText().toString().compareTo("")!=0)&&(colorNameScelto.getColoreEvento().compareTo("")!=0))
                {
                    if(ripetizione.isChecked())
                    {
                        //se la ripetizione è settata aggiungo il nuovo tipo di attività nell'array colorNameBinder
                        boolean trovato=false;
                        //cerca all'interno dell'array se non è presente
                        for(ColorNameBinder i:SchermataIniziale.colorNameBinder)
                            if(colorNameScelto.equals(i))
                                trovato=true;

                        if(trovato!=true)
                            SchermataIniziale.colorNameBinder.add(colorNameScelto);
                    }

                    if(!(ripetizione.isChecked() && groupActivityFlag.isChecked())) {

                        Evento nuovoEvento = new Evento(colorNameScelto, dataInizio, dataFine, ripetizione.isChecked(), utenteSceltoSpinner, sceltaSpinnerCategoria, noteAttivita.getText().toString(), groupActivityFlag.isChecked());
                        SchermataIniziale.calendario.add(nuovoEvento);

                    }
                    else
                        SchermataIniziale.calendario.addAll(generazioneSettimanaTipo());

                    SchermataIniziale.calendario.sort();

                    makeToast("Attività creata");

                    Intent openPageHome;
                    String callingActivity = getIntent().getStringExtra("Chiamante");

                    switch (callingActivity) {
                        case "SchermataIniziale":
                            openPageHome = new Intent(getBaseContext(), SchermataIniziale.class);
                            finish();
                            startActivity(openPageHome);
                            break;
                        case "SettimanaTipo":
                            openPageHome = new Intent(getBaseContext(), SettimanaTipo.class);
                            finish();
                            startActivity(openPageHome);
                            break;
                    }
                }
                else
                {
                    if(nomeAttivita.getText().toString().compareTo("")==0)
                    {
                        nomeAttivita.setError("Inserisci il nome dell'attività!");
                    }
                    else
                    {
                        makeToast("Scegli un colore per l'attività!");
                    }

                }

            }
        });

        colorPickerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Context context = AggiuntaEvento.this;

                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("")
                        .initialColor(0xffffffff)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener()
                        {
                            @Override
                            public void onColorChanged(int selectedColor)
                            {
                                // Handle on color change
                                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                            }
                        })


                        .setOnColorSelectedListener(new OnColorSelectedListener()
                        {
                            @Override
                            public void onColorSelected(int selectedColor){}
                        })

                        .setPositiveButton("ok", new ColorPickerClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors)
                            {
                                colorPickerButton.setBackgroundColor(selectedColor);
                                colorNameScelto.setColoreEventoFromInt(selectedColor);
                            }
                        })

                        .setNegativeButton("cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })

                        .showColorEdit(true)
                        .build()
                        .show();
            }
        });
        groupActivityFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked==true)
                    utenteSpinner.setEnabled(true);
                else
                    utenteSpinner.setEnabled(false);
            }
        });
    }

    private Calendario generazioneSettimanaTipo(){
        Random random = new Random();
        Calendario settimana = new Calendario();


            for (int j = 1; j < (Calendar.getInstance().getWeeksInWeekYear() - Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)); j++) {
                int rUtenti = random.nextInt(((SchermataIniziale.utenti.size() - 1) - 0) + 1);

                settimana.add(new Evento(colorNameScelto,
                        new GregorianCalendar(dataInizio.get(Calendar.YEAR), dataInizio.get(Calendar.MONTH), dataInizio.get(Calendar.DAY_OF_MONTH), dataInizio.get(Calendar.HOUR_OF_DAY) , dataInizio.get(Calendar.MINUTE)),
                        new GregorianCalendar(dataFine.get(Calendar.YEAR), dataFine.get(Calendar.MONTH), dataFine.get(Calendar.DAY_OF_MONTH), dataFine.get(Calendar.HOUR_OF_DAY), dataFine.get(Calendar.MINUTE)),
                        true,
                        SchermataIniziale.utenti.get(rUtenti), "", "", true)
                );

                dataInizio.roll(Calendar.WEEK_OF_YEAR, 1);
                dataFine.roll(Calendar.WEEK_OF_YEAR, 1);
            }



        settimana.sort();

        return settimana;
    }

    public void makeToast(String message)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
