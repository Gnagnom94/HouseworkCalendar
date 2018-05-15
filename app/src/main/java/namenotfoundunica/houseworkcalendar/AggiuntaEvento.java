package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
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

    private ColorNameBinder colorNameScelto;
    private Button bottoneConferma;
    private ImageButton colorPickerButton;
    private AutoCompleteTextView nomeAttivita;
    private Switch ripetizione;
    private Spinner utenteSpinner;
    private Spinner categoriaSpinner;
    private Utente utenteSceltoSpinner;
    private String sceltaSpinnerCategoria;
    private TextView noteAttivita;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_evento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        //inizializzo le variabili
        dataInizio=Calendar.getInstance();
        dataFine = Calendar.getInstance();
        utenteSceltoSpinner=SchermataIniziale.utenti.get(0);//seleziono il primo elemento dell'array


        bottoneConferma=findViewById(R.id.ConfermaButton);
        dataEventoText = findViewById(R.id.TextData1);
        timeInizioEvento = findViewById(R.id.TimeInizio);
        timeFineEvento = findViewById(R.id.TimeFine);
        noteAttivita=findViewById(R.id.NoteInput);

        colorPickerButton=findViewById(R.id.ColorPicker);
        ripetizione=findViewById(R.id.switch1);
        utenteSpinner=findViewById(R.id.spinnerUtente);
        nomeAttivita=findViewById(R.id.textNomeInput);
        categoriaSpinner=findViewById(R.id.spinnerCategoria);
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
        for(Utente u:SchermataIniziale.utenti)
        {
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
            public void afterTextChanged(Editable s) {

            }
        });



        dataEventoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

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

                Evento nuovoEvento = new Evento(colorNameScelto, dataInizio, dataFine, ripetizione.getSplitTrack(), utenteSceltoSpinner, sceltaSpinnerCategoria, noteAttivita.getText().toString());
                SchermataIniziale.calendario.add(nuovoEvento);
                Context context = getApplicationContext();
                CharSequence text = "Attività aggiunta";
                SchermataIniziale.calendario.sort();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent openPageHome;
                String callingActivity = getIntent().getStringExtra("Chiamante");

                switch (callingActivity) {
                    case "SchermataIniziale":
                        openPageHome = new Intent(getBaseContext(), SchermataIniziale.class);
                        startActivity(openPageHome);
                        break;
                    case "SettimanaTipo":
                        openPageHome = new Intent(getBaseContext(), SettimanaTipo.class);
                        startActivity(openPageHome);
                        break;
                }
            }
        });

        colorPickerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
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
                            public void onColorChanged(int selectedColor) {
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
    }

}
