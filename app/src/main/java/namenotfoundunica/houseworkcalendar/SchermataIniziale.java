package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class SchermataIniziale extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final Settimana settimana = new Settimana();
        settimana.add(new Evento("Lavatrice 10",
                new GregorianCalendar(2018,5,11,10, 00),
                new GregorianCalendar(2018,5,11,11, 00), true,
                new Utente("Matteo", "Atzeni", "matteo.atzeni@outlook.com", "atzeni"), "Veranda", ""));
        settimana.add(new Evento("Pavimento Bagno 11.30",
                new GregorianCalendar(2018,5,11,11, 30),
                new GregorianCalendar(2018,5,11,12, 30), true,
                new Utente("Alessandro", "Caddeo", "alessandro.caddeo@outlook.com", "caddeo"), "Cucina", ""));
        settimana.add(new Evento("Piatti 11",
                new GregorianCalendar(2018,5,11,11, 00),
                new GregorianCalendar(2018,5,11,12, 00), true,
                new Utente("Marco", "Pittau", "marcopittau@live.it", "pittau"), "Cucina", ""));

        settimana.add(new Evento("Pavimento Bagno 13",
                new GregorianCalendar(2018,5,11,13, 00),
                new GregorianCalendar(2018,5,11,14, 00), true,
                new Utente("Alessandro", "Caddeo", "alessandro.caddeo@outlook.com", "caddeo"), "Cucina", ""));
        settimana.sort();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_iniziale);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent openPage = new Intent(SchermataIniziale.this, AggiuntaEvento.class);
                startActivity(openPage);
            }
        });

        final LinearLayout linearLayout = findViewById(R.id.ll);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                linearLayout.removeAllViews();
                int i = 0;
                for (Evento evento:settimana) {
                    if(
                        (evento.getInizio().get(Calendar.YEAR) <= year &&
                        evento.getInizio().get(Calendar.MONTH)<= month &&
                        evento.getInizio().get(Calendar.DAY_OF_MONTH) <= dayOfMonth)
                            &&
                        (evento.getFine().get(Calendar.YEAR) >= year &&
                        evento.getFine().get(Calendar.MONTH) >= month &&
                        evento.getFine().get(Calendar.DAY_OF_MONTH) >= dayOfMonth)
                        )
                    {
                        Button btn = new Button(linearLayout.getContext());
                        String tmp;
                        if(evento.getInizio().get(Calendar.MINUTE) < 10)
                        {
                            tmp = evento.getInizio().get(Calendar.MINUTE) + "0";
                        }
                        else
                        {
                            tmp ="" + evento.getInizio().get(Calendar.MINUTE);
                        }
                        btn.setText(evento.getInizio().get(Calendar.HOUR_OF_DAY) + ":" + tmp + " " + evento.getUtente().getNome() + " deve fare: " + evento.getNome());
                        //btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 50, 50));
                        btn.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        btn.setId(i++);
                        linearLayout.addView(btn);
                    }
                }
            }
        });

        //descrizione navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

                            }

                            mDrawerLayout.closeDrawers();//chiudo la nav
                            return true;
                        }
                    });
        }

//        //quando seleziono il numero del giorno nel calendario stampa nella textview il giorno, l'anno e il mese di tale giorno
//        final TextView t = (TextView) findViewById(R.id.elementoLista);
//        CalendarView c= findViewById(R.id.calendarView);
//        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
//        {
//            public void onSelectedDayChange(CalendarView c, int year, int month, int dayOfMonth)
//            {
//                //qua andrò a leggere tutti gli eventid della giornata selezionata
//                t.setText(""+ year+" "+month+" "+dayOfMonth);
//            }
//
//        });



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



}
