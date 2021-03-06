package namenotfoundunica.houseworkcalendar.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import namenotfoundunica.houseworkcalendar.other.Calendario;
import namenotfoundunica.houseworkcalendar.other.ColorNameBinder;
import namenotfoundunica.houseworkcalendar.adapters.CustomAdapter;
import namenotfoundunica.houseworkcalendar.other.Evento;
import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Pagamento;
import namenotfoundunica.houseworkcalendar.other.Utente;


public class SchermataIniziale extends AppCompatActivity
{

    public static ArrayList<Pagamento> pagamenti;
    private DrawerLayout mDrawerLayout;
    public ArrayList<Evento> tmp = new ArrayList<>();

    public static ArrayList<ColorNameBinder> colorNameBinder = new ArrayList<>();

    public CustomAdapter customAdapter;

    public static ArrayList<Utente> utenti = new ArrayList<Utente>();
    public static Calendario calendario = new Calendario();
    public static Calendario settimana = new Calendario();

    private static boolean flagCreation = false;
    public boolean flagSelectUnselectAll = false;
    public static Utente utenteLoggato=null;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_iniziale);

        if (!flagCreation) {
//          Utente matteo = new Utente("Matteo", "Atzeni", "matteo.atzeni@outlook.com", "atzeni");
//            Utente alessandro = new Utente("Alessandro", "Caddeo", "alessandro.caddeo@outlook.com", "caddeo");
//            Utente pitta = new Utente("Marco", "Pittau", "marco.pittau@outlook.com", "pittau");
//            utenti.add(matteo);
//            utenti.add(alessandro);
//            utenti.add(pitta);
            utenti.add(new Utente("a", "a", "a", "a"));
            utenti.add(new Utente("b", "b", "b", "b"));
            utenti.add(new Utente("c", "c", "c", "c"));

            colorNameBinder.add(new ColorNameBinder("Lavatrice", "#FF0000"));
            colorNameBinder.add(new ColorNameBinder("Pavimento", "#FFE500"));
            colorNameBinder.add(new ColorNameBinder("Bagno", "#0FFF00"));
            colorNameBinder.add(new ColorNameBinder("Stoviglie", "#00FFF8"));

            generazioneAnno();

            generazioneSettimanaTipo();

            fusioneCalendarioSettimanaTipo();

            flagCreation = true;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPage = new Intent(SchermataIniziale.this, AggiuntaEvento.class);
                openPage.putExtra("Chiamante", "SchermataIniziale");
                startActivity(openPage);
            }
        });


        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendarStart = new GregorianCalendar(year, month, dayOfMonth, 00, 00, 00);
                Calendar calendarEnd = new GregorianCalendar(year, month, dayOfMonth, 23, 59, 59);
                tmp.clear();
                int i = 0;
                for (Evento evento : calendario) {
                    i++;
                    if ((evento.getInizio().getTimeInMillis() >= calendarStart.getTimeInMillis())
                    && (evento.getInizio().getTimeInMillis() <= calendarEnd.getTimeInMillis())
                            ) {
                        //Inizializzazione NUOVO layout

                        tmp.add(evento);
                    }
                }
                final ListView listView = findViewById(R.id.listView);

                customAdapter = new CustomAdapter(SchermataIniziale.this, R.layout.customlayout, tmp);
                listView.setAdapter(customAdapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                // Capture ListView item click
                listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode,
                                                          int position, long id, boolean checked) {
                        // Capture total checked items
                        final int checkedCount = listView.getCheckedItemCount();
                        // Set the CAB title according to total checked items
                        mode.setTitle(checkedCount + " Selezionato");
                        // Calls toggleSelection method from customAdapter Class
                        customAdapter.toggleSelection(position);
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                        // Calls getSelectedIds method from customAdapter Class
                        final SparseBooleanArray selected = customAdapter.getSelectedIds();
                        switch (item.getItemId()) {
                            case R.id.delete:
                                if(selected.size() > 1){
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(SchermataIniziale.this);
                                    builder.setTitle("Conferma Eliminazione");
                                    builder.setNegativeButton("Annulla", null);
                                    builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Captures all selected ids with a loop
                                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                                if (selected.valueAt(i)) {
                                                    Evento selecteditem = customAdapter.getItem(selected.keyAt(i));
                                                    // Remove selected items following the ids
                                                    customAdapter.remove(selecteditem);
                                                }
                                            }
                                            // Close CAB
                                            mode.finish();
                                        }
                                    });
                                    final AlertDialog dialog = builder.create();

                                    dialog.show();
                                }else{
                                    // Captures all selected ids with a loop
                                    for (int i = (selected.size() - 1); i >= 0; i--) {
                                        if (selected.valueAt(i)) {
                                            Evento selecteditem = customAdapter.getItem(selected.keyAt(i));
                                            // Remove selected items following the ids
                                            customAdapter.remove(selecteditem);
                                        }
                                    }
                                    // Close CAB
                                    mode.finish();
                                }
                                return true;
                            case R.id.selectAll:

                                if(!flagSelectUnselectAll) {
                                    flagSelectUnselectAll = true;
                                    item.setIcon(R.drawable.ic_close_black_24dp);
                                    for (int i = (customAdapter.getCount() - 1); i >= 0; i--) {
                                        if (!selected.get(i)) {
                                            listView.setItemChecked(i, true);
                                        }
                                    }
                                }else{
                                    flagSelectUnselectAll = false;
                                    for (int i = (customAdapter.getCount() - 1); i >= 0; i--) {
                                        if (selected.get(i)) {
                                            listView.setItemChecked(i, false);
                                        }
                                    }
                                }
                                final int checkedCount = listView.getCheckedItemCount();
                                mode.setTitle(checkedCount + " Selezionato");
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.contextual_choices, menu);
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        customAdapter.removeSelection();
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
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
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showInfoEvento(customAdapter.getItem(position));
                        customAdapter.notifyDataSetChanged();
                    }
                });
            }
        });


        //descrizione navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        if (navigationView != null) {
            final TextView textViewNomeUtente = headerView.findViewById(R.id.name);
            final TextView textViewEmailUtente = headerView.findViewById(R.id.mail);

            if( textViewNomeUtente != null && textViewEmailUtente != null && utenteLoggato != null) {
                textViewNomeUtente.setText(utenteLoggato.getNome());
                textViewEmailUtente.setText(utenteLoggato.getEmail());
            }

            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            Intent openPage;

                                switch (menuItem.getItemId()) {

                                    case R.id.nav_Login:
                                        openPage = new Intent(SchermataIniziale.this, Login.class);
                                        // passo all'attivazione dell'activity Pagina.java
                                        startActivity(openPage);
                                        return true;

                                    case R.id.nav_calendario:
                                        mDrawerLayout.closeDrawers();//chiudo la nav
                                        return true;

                                    case R.id.nav_gestione_gruppo:
                                        if(utenteLoggato!=null) {
                                            openPage = new Intent(SchermataIniziale.this, GestioneGruppo.class);
                                            // passo all'attivazione dell'activity Pagina.java
                                            startActivity(openPage);
                                            return true;
                                        }
                                        else
                                        {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Prima di accedere a questo devi aver fatto il login", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        break;


                                    case R.id.nav_pagamenti:
                                        if(utenteLoggato!=null) {
                                            openPage = new Intent(SchermataIniziale.this, GestionePagamenti.class);
                                            // passo all'attivazione dell'activity Pagina.java
                                            startActivity(openPage);
                                            return true;
                                        }
                                        else
                                        {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Prima di accedere a questo devi aver fatto il login", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        break;

                                    case R.id.nav_sondaggio:
                                        if(utenteLoggato!=null) {
                                            openPage = new Intent(SchermataIniziale.this, GestioneSondaggi.class);
                                            openPage.putExtra("indice",-1);
                                            // passo all'attivazione dell'activity Pagina.java
                                            startActivity(openPage);
                                            return true;
                                        }
                                        else
                                        {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Prima di accedere a questo devi aver fatto il login", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        break;
                                    case R.id.nav_settimana_tipo:
                                        openPage = new Intent(SchermataIniziale.this, SettimanaTipo.class);
                                        startActivity(openPage);
                                        return true;
                                    case R.id.nav_logout:
                                        if(utenteLoggato!=null) {
                                            utenteLoggato=null;
                                            textViewNomeUtente.setText("Non sei Loggato");
                                            textViewEmailUtente.setText("");
                                            Toast toast = Toast.makeText(getApplicationContext(), "Logout eseguito", Toast.LENGTH_SHORT);
                                            toast.show();
                                            mDrawerLayout.closeDrawers();//chiudo la nav
                                            return true;
                                        }
                                        else
                                        {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Prima di accedere a questo devi aver fatto il login", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        break;
                                    case R.id.nav_registrazione:
                                    {
                                        openPage = new Intent(SchermataIniziale.this, Registrazione.class);
                                        startActivity(openPage);
                                    }

                            }

                            mDrawerLayout.closeDrawers();//chiudo la nav
                            return true;
                        }
                    });
        }
    }

    //Generazione casuale di eventi in un anno
    private void generazioneAnno() {
        for (int i = 0; i <= 12; i++) {
            for (int k = 1; k <= 30; k++) {
                for (int j = 9; j < 20; j++) {
                    int rUtenti = random.nextInt(((utenti.size() - 1) - 0) + 1);
                    int rNomeColoreEvento = random.nextInt(((colorNameBinder.size() - 1) - 0) + 1);

                    calendario.add(new Evento(colorNameBinder.get(rNomeColoreEvento),
                            new GregorianCalendar(2018, i, k, j, 00),
                            new GregorianCalendar(2018, i, k, j + 1, 00), true,
                            utenti.get(rUtenti), "", "", false)
                    );
                }
            }
        }
        calendario.sort();
    }

    //generazione di due settimane tipo consecutive partendo dalla settimana corrente
    private void generazioneSettimanaTipo(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);

        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1:
                calendar.roll(Calendar.DAY_OF_YEAR, -6);//domenica
                break;
            case 2: //lunedì
                break;
            case 3:
                calendar.roll(Calendar.DAY_OF_YEAR, -1);//martedì
                break;
            case 4:
                calendar.roll(Calendar.DAY_OF_YEAR, -2);//mercoledì
                break;
            case 5:
                calendar.roll(Calendar.DAY_OF_YEAR, -3);//giovedì
                break;
            case 6:
                calendar.roll(Calendar.DAY_OF_YEAR, -4);//venerdì
                break;
            case 7:
                calendar.roll(Calendar.DAY_OF_YEAR, -5);//sabato
                break;
        }

        for (int k = 1; k <= 7; k++) {
            for (int j = 0; j < 10; j++) {
                int rUtenti = random.nextInt(((utenti.size() - 1) - 0) + 1);
                int rNomeColoreEvento = random.nextInt(((colorNameBinder.size() - 1) - 0) + 1);

                settimana.add(new Evento(colorNameBinder.get(rNomeColoreEvento),
                        new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY) + j, calendar.get(Calendar.MINUTE)),
                        new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY) + j + 1, calendar.get(Calendar.MINUTE)),
                        true,
                        SchermataIniziale.utenti.get(rUtenti), "", "", true)
                );

            }

            calendar.roll(Calendar.DAY_OF_YEAR, 1);
        }
        for (int k = 1; k <= 7; k++) {
            for (int j = 0; j < 10; j++) {
                int rUtenti = random.nextInt(((utenti.size() - 1) - 0) + 1);
                int rNomeColoreEvento = random.nextInt(((colorNameBinder.size() - 1) - 0) + 1);

                settimana.add(new Evento(colorNameBinder.get(rNomeColoreEvento),
                        new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY) + j, calendar.get(Calendar.MINUTE)),
                        new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY) + j + 1, calendar.get(Calendar.MINUTE)),
                        true,
                        SchermataIniziale.utenti.get(rUtenti), "", "", true)
                );

            }
            calendar.roll(Calendar.DAY_OF_YEAR, 1);
        }
        settimana.sort();
    }

    //fusione delle due variabili calendario e settimana
    private void fusioneCalendarioSettimanaTipo() {
        calendario.addAll(settimana);
        calendario.sort();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schermata_iniziale, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Intent openPageSetting = new Intent(SchermataIniziale.this,SettingsActivity.class);
                // passo all'attivazione dell'activity Pagina.java
                startActivity(openPageSetting);
        }
        return super.onOptionsItemSelected(item);
    }


    public static int dpiToPx(int dpi, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dpi * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void showInfoEvento(final Evento selected) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customlayout_info_evento, null);
        TextView tipo = alertLayout.findViewById(R.id.titoloAttivita);
        TextView oraInizio = alertLayout.findViewById(R.id.oraInizio);
        TextView oraFine = alertLayout.findViewById(R.id.oraFine);
        TextView data = alertLayout.findViewById(R.id.Data);
        TextView utente = alertLayout.findViewById(R.id.utente);
        CheckBox ripetizione = alertLayout.findViewById(R.id.checkBoxRicorrenza);
        CheckBox attivitaGruppo = alertLayout.findViewById(R.id.checkBoxGruppo);
        TextView textViewLabelNote = alertLayout.findViewById(R.id.textViewLabelNote);
        TextView note = alertLayout.findViewById(R.id.noteEvento);
        Button buttonFatto = alertLayout.findViewById(R.id.buttonFatto);
        Button buttonModifica = alertLayout.findViewById(R.id.buttonModifica);
        Button buttonChiudi = alertLayout.findViewById(R.id.buttonChiudi);


        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        DateFormat dfData = new SimpleDateFormat("dd/MM/yyyy");


        tipo.setText(selected.getColorNameBinder().getNomeEvento());
        oraInizio.setText(dfTime.format(selected.getInizio().getTime()));
        oraFine.setText(dfTime.format(selected.getFine().getTime()));
        data.setText(dfData.format(selected.getInizio().getTime()));
        utente.setText(selected.getUtente().getNome().toString());
        ripetizione.setClickable(false);
        if(selected.getFlagRipetizione()==true)
            ripetizione.setChecked(true);
        else
            ripetizione.setChecked(false);

        attivitaGruppo.setClickable(false);
        if(selected.isGroupFlag()==true)
            attivitaGruppo.setChecked(true);
        else
            attivitaGruppo.setClickable(false);

        if(selected.getNote() != ""){
            note.setText(selected.getNote());
        }else{
            textViewLabelNote.setVisibility(View.GONE);
            note.setGravity(Gravity.CENTER_HORIZONTAL);
            note.setText("Nessuna nota");
        }


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info evento");
        builder.setView(alertLayout);

        final AlertDialog dialog = builder.create();

        //alert.setPositiveButton("Ok",null);
        buttonChiudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Calendar calendar = Calendar.getInstance();
        if(!(selected.getFine().getTimeInMillis() <=  calendar.getTimeInMillis()))
        {
            if(selected.isCompletedFlag())
            {
                buttonFatto.setText("Segna come non Concluso");
                buttonFatto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendario.get(calendario.indexOf(selected)).setCompletedFlag(false);
                        customAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                    /*alert.setNegativeButton("Segna non Concluso", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            calendario.get(calendario.indexOf(selected)).setCompletedFlag(false);
                            customAdapter.notifyDataSetChanged();
                        }
                    });*/
            }
            else
            {
                buttonFatto.setText("Segna come Concluso");
                buttonFatto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendario.get(calendario.indexOf(selected)).setCompletedFlag(true);
                        customAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                    /*alert.setNegativeButton("Segna Concluso", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            calendario.get(calendario.indexOf(selected)).setCompletedFlag(true);
                            customAdapter.notifyDataSetChanged();
                        }
                    });*/
            }
        }
        else
        {
            buttonFatto.setVisibility(View.GONE);
        }
        buttonModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openPage = new Intent(SchermataIniziale.this, AggiuntaEvento.class);
                openPage.putExtra("Chiamante", "SchermataIniziale");
                openPage.putExtra("Evento",calendario.indexOf(selected));

                startActivity(openPage);
            }
        });
            /*alert.setNeutralButton("Modifica", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent openPage = new Intent(SchermataIniziale.this, AggiuntaEvento.class);
                    openPage.putExtra("Chiamante", "SchermataIniziale");
                    openPage.putExtra("Evento",calendario.indexOf(selected));

                    startActivity(openPage);
                }
            });*/

        dialog.show();
    }
}
