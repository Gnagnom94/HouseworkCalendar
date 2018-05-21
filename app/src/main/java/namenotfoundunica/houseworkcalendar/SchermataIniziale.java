package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public class SchermataIniziale extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    public ArrayList<Evento> tmp = new ArrayList<>();

    public static ArrayList<ColorNameBinder> colorNameBinder = new ArrayList<>();

    public static ArrayList<Utente> utenti = new ArrayList<Utente>();
    public static Calendario calendario = new Calendario();

    private static boolean flagCreation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(!flagCreation) {
            calendario.clear();
            Utente matteo = new Utente("Matteo", "Atzeni", "matteo.atzeni@outlook.com", "atzeni");
            Utente alessandro = new Utente("Alessandro", "Caddeo", "Alessandro.Caddeo@outlook.com", "caddeo");
            Utente pitta = new Utente("Marco", "Pittau", "Marco.pittau@outlook.com", "piattau");
            utenti.add(matteo);
            utenti.add(alessandro);
            utenti.add(pitta);

            colorNameBinder.add(new ColorNameBinder("Lavatrice", "#FF0000"));
            colorNameBinder.add(new ColorNameBinder("Bucato", "#0025FF"));
            colorNameBinder.add(new ColorNameBinder("Pavimento", "#FFE500"));
            colorNameBinder.add(new ColorNameBinder("Bagno", "#0FFF00"));
            colorNameBinder.add(new ColorNameBinder("Stoviglie", "#00FFF8"));


            Random random = new Random();

            for (int i = 0; i <= 12; i++) {
                for (int k = 1; k <= 30; k++) {
                    for (int j = 9; j < 20; j++) {
                        int rUtenti = random.nextInt(((utenti.size() - 1) - 0) + 1);
                        int rNomeColoreEvento = random.nextInt(((colorNameBinder.size() - 1) - 0) + 1);

                        calendario.add(new Evento(colorNameBinder.get(rNomeColoreEvento),
                                new GregorianCalendar(2018, i, k, j, 00),
                                new GregorianCalendar(2018, i, k, j + 1, 00), true,
                                utenti.get(rUtenti), "", "")
                        );
                    }
                }
            }
            calendario.sort();
            flagCreation = true;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_iniziale);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent openPage = new Intent(SchermataIniziale.this, AggiuntaEvento.class);
                openPage.putExtra("Chiamante", "SchermataIniziale");
                startActivity(openPage);
            }
        });

        //final LinearLayout linearLayout = findViewById(R.id.ll);
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tmp.clear();
                int i = 0;
                for (Evento evento: calendario){
                    i++;
                    if(
                        (evento.getInizio().get(Calendar.YEAR) <= year &&
                        evento.getInizio().get(Calendar.MONTH) <= month &&
                        evento.getInizio().get(Calendar.DAY_OF_MONTH) <= dayOfMonth)
                            &&
                        (evento.getFine().get(Calendar.YEAR) >= year &&
                        evento.getFine().get(Calendar.MONTH) >= month &&
                        evento.getFine().get(Calendar.DAY_OF_MONTH) >= dayOfMonth)
                        )
                    {
                        //Inizializzazione NUOVO layout

                        tmp.add(evento);
                    }
                }
                ListView listView = findViewById(R.id.listView);

                CustomAdapter customAdapter = new CustomAdapter();
                listView.setAdapter(customAdapter);
            }
        });


        //descrizione navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        Intent openPage;
                        switch (menuItem.getItemId())
                        {
                            case R.id.nav_calendario:
                                mDrawerLayout.closeDrawers();//chiudo la nav
                                return true;

                            case R.id.nav_gestione_gruppo:
                                openPage = new Intent(SchermataIniziale.this, GestioneGruppo.class);
                                // passo all'attivazione dell'activity Pagina.java
                                startActivity(openPage);
                                return true;

                            case R.id.nav_pagamenti:
                                openPage = new Intent(SchermataIniziale.this, Pagamenti.class);
                                // passo all'attivazione dell'activity Pagina.java
                                startActivity(openPage);
                                return true;

                            case R.id.nav_sondaggio:
                                openPage = new Intent(SchermataIniziale.this, Sondaggi.class);
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

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("settimana", calendario);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calendario = (Calendario) savedInstanceState.getSerializable("settimana");
    }*/

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

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tmp.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);

            TextView textNomeUtente = convertView.findViewById(R.id.textNomeUtente);
            TextView textNomeEvento = convertView.findViewById(R.id.textNomeEvento);
            TextView textOraInizio = convertView.findViewById(R.id.textOraInizio);
            TextView textOraFine = convertView.findViewById(R.id.textOraFine);
            Button buttonColor = convertView.findViewById(R.id.buttonColor);

            String oreInizio;
            //if per rendere sempre di due cifre le ore
            if(tmp.get(position).getInizio().get(Calendar.HOUR_OF_DAY) < 10)
            {
                oreInizio = "0" + tmp.get(position).getInizio().get(Calendar.HOUR_OF_DAY);
            }
            else
            {
                oreInizio = "" + tmp.get(position).getInizio().get(Calendar.HOUR_OF_DAY);
            }
            String minutiInizio;
            //if per rendere sempre di due cifre i minuti
            if(tmp.get(position).getInizio().get(Calendar.MINUTE) < 10)
            {
                minutiInizio = tmp.get(position).getInizio().get(Calendar.MINUTE) + "0";
            }
            else
            {
                minutiInizio ="" + tmp.get(position).getInizio().get(Calendar.MINUTE);
            }
            String oreFine;
            //if per rendere sempre di due cifre le ore
            if(tmp.get(position).getFine().get(Calendar.HOUR_OF_DAY) < 10)
            {
                oreFine = "0" + tmp.get(position).getFine().get(Calendar.HOUR_OF_DAY);
            }
            else
            {
                oreFine = "" + tmp.get(position).getFine().get(Calendar.HOUR_OF_DAY);
            }
            String minutiFine;
            //if per rendere sempre di due cifre i minuti
            if(tmp.get(position).getFine().get(Calendar.MINUTE) < 10)
            {
                minutiFine = tmp.get(position).getFine().get(Calendar.MINUTE) + "0";
            }
            else
            {
                minutiFine ="" + tmp.get(position).getFine().get(Calendar.MINUTE);
            }

            textNomeUtente.setText(tmp.get(position).getUtente().getNome());
            textNomeEvento.setText(tmp.get(position).getColorNameBinder().getNomeEvento());
            textOraInizio.setText(oreInizio + ":" + minutiInizio);
            textOraFine.setText(oreFine + ":" + minutiFine);
            buttonColor.setBackground(GetTintedDrawable(R.drawable.roundedbutton, tmp.get(position).getColorNameBinder().getColoreEventoToInt()));
            return convertView;
        }

        public Drawable GetTintedDrawable(int res, int color)
        {
            Drawable drawable =  getDrawable(res);

            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

            return drawable;
        }
    }
}
