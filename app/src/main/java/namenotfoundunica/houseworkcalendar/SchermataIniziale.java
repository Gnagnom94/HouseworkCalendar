package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


public class SchermataIniziale extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    public ArrayList<Evento> tmp = new ArrayList<>();
    public static ArrayList<Utente> UtentiGruppo;
    public static ArrayList<ColorNameBinder> colorNameBinder = new ArrayList<>();


    public static ArrayList<Utente> utenti = new ArrayList<Utente>();
    public static Calendario calendario = new Calendario();
    public static Calendario settimana = new Calendario();

    private static boolean flagCreation = false;
    public boolean flagSelectUnselectAll = false;
    public static Utente utenteLoggato=null;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_iniziale);

        if (!flagCreation) {
            Utente matteo = new Utente("Matteo", "Atzeni", "matteo.atzeni@outlook.com", "atzeni");
            Utente alessandro = new Utente("Alessandro", "Caddeo", "alessandro.caddeo@outlook.com", "caddeo");
            Utente pitta = new Utente("Marco", "Pittau", "marco.pittau@outlook.com", "piattau");
            utenti.add(matteo);
            utenti.add(alessandro);
            utenti.add(pitta);

            UtentiGruppo = new ArrayList<>(utenti);


            colorNameBinder.add(new ColorNameBinder("Lavatrice", "#FF0000"));
            colorNameBinder.add(new ColorNameBinder("Bucato", "#0025FF"));
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
                Calendar calendarSelected = new GregorianCalendar(year, month, dayOfMonth);
                tmp.clear();
                int i = 0;
                for (Evento evento : calendario) {
                    i++;
                    if (
                            (evento.getInizio().get(Calendar.YEAR) >= year &&
                                    evento.getInizio().get(Calendar.MONTH) >= month &&
                                    evento.getInizio().get(Calendar.DAY_OF_MONTH) >= dayOfMonth)
                                    &&
                                    (evento.getInizio().get(Calendar.YEAR) <= year &&
                                            evento.getInizio().get(Calendar.MONTH) <= month &&
                                            evento.getInizio().get(Calendar.DAY_OF_MONTH) <= dayOfMonth)
                            ) {
                        //Inizializzazione NUOVO layout

                        tmp.add(evento);
                    }
                }
                final ListView listView = findViewById(R.id.listView);

                final CustomAdapter customAdapter = new CustomAdapter(SchermataIniziale.this, R.layout.customlayout, tmp);
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
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        // Calls getSelectedIds method from customAdapter Class
                        SparseBooleanArray selected = customAdapter.getSelectedIds();
                        switch (item.getItemId()) {
                            case R.id.delete:
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
                        // TODO Auto-generated method stub
                        customAdapter.removeSelection();
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        // TODO Auto-generated method stub
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
            }
        });


        //descrizione navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
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
                                    openPage = new Intent(SchermataIniziale.this, GestioneGruppo.class);
                                    // passo all'attivazione dell'activity Pagina.java
                                    startActivity(openPage);
                                    return true;

                                case R.id.nav_pagamenti:
                                    openPage = new Intent(SchermataIniziale.this, GestionePagamenti.class);
                                    // passo all'attivazione dell'activity Pagina.java
                                    startActivity(openPage);
                                    return true;

                                case R.id.nav_sondaggio:
                                    openPage = new Intent(SchermataIniziale.this, GestioneSondaggi.class);
                                    // passo all'attivazione dell'activity Pagina.java
                                    startActivity(openPage);
                                    return true;
                                case R.id.nav_settimana_tipo:
                                    openPage = new Intent(SchermataIniziale.this, SettimanaTipo.class);
                                    startActivity(openPage);
                                    return true;

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

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int todayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int todayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int todayMonth = calendar.get(Calendar.MONTH);
        int todayYear = calendar.get(Calendar.YEAR);

        Log.d("todayOfMonth", "" + todayOfMonth);
        Log.d("todayOfWeek", "" + todayOfWeek);

        int firstDayOfWeek = (todayOfMonth - (todayOfWeek - 2));
        int lastDayOfWeek = ((todayOfMonth - (todayOfWeek - 2)) + 6);

        Log.d("firstDayOfWeek", "" + firstDayOfWeek);
        Log.d("lastDayOfWeek", "" + lastDayOfWeek);

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

        Calendar tmpCal;
        Calendar tmpCal2;
        for (int k = 1; k <= 7; k++) {
            tmpCal = (Calendar) calendar.clone();
            tmpCal2 = (Calendar) tmpCal.clone();
            tmpCal2.roll(Calendar.HOUR_OF_DAY, 1);

            for (int j = 0; j < 10; j++) {
                int rUtenti = random.nextInt(((UtentiGruppo.size() - 1) - 0) + 1);
                int rNomeColoreEvento = random.nextInt(((colorNameBinder.size() - 1) - 0) + 1);

                settimana.add(new Evento(colorNameBinder.get(rNomeColoreEvento),
                        tmpCal,
                        tmpCal2,
                        true,
                        SchermataIniziale.UtentiGruppo.get(rUtenti), "", "", true)
                );

                tmpCal.roll(Calendar.HOUR_OF_DAY, 1);
                tmpCal2.roll(Calendar.HOUR_OF_DAY, 1);
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

}
