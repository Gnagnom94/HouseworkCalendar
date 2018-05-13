package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public class SchermataIniziale extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    Utente matteo = new Utente("Matteo", "Atzeni", "matteo.atzeni@outlook.com", "atzeni");
    Utente alessandro = new Utente("Alessandro", "Caddeo", "Alessandro.Caddeo@outlook.com", "caddeo");
    Utente pitta = new Utente("Marco", "Pittau", "Marco.pittau@outlook.com", "piattau");
    ArrayList<Utente> utenti = new ArrayList<Utente>();
    utenti.add(matteo);
    utenti.add(alessandro);
    utenti.add(pitta);

    Random random = new Random();
    final Settimana settimana = new Settimana();
        for(int i = 0; i <= 12; i++) {
            for (int k = 1; k <= 30; k++) {
                for (int j = 9; j < 20; j++) {
                    int r = random.nextInt((2 - 0) + 1);
                    settimana.add(new Evento("Lavatrice " + (k),
                            new GregorianCalendar(2018, i, k, j, 00),
                            new GregorianCalendar(2018, i, k, j + 1, 00), true,
                                    utenti.get(r), "Veranda", "")
                            );
                }
            }
        }
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
                linearLayout.removeAllViews();
                int i = 0;
                for (Evento evento:settimana) {
                    i++;
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
                        //Inizializzazione NUOVO layout
                        String minuti;
                        //if per rendere sempre di due cifre i minuti altrimenti se i minuti sono inferiori a 10 sono di una cifra
                        if(evento.getInizio().get(Calendar.MINUTE) < 10)
                        {
                            minuti = evento.getInizio().get(Calendar.MINUTE) + "0";
                        }
                        else
                        {
                            minuti ="" + evento.getInizio().get(Calendar.MINUTE);
                        }

                        ConstraintLayout constraintLayout = new ConstraintLayout(linearLayout.getContext());

                        //constraintLayout
                        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.MATCH_PARENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        constraintLayoutParams.orientation = 0;
                        constraintLayout.setLayoutParams(constraintLayoutParams);
                        int consId = View.generateViewId();
                        constraintLayout.setId(consId);

                        linearLayout.addView(constraintLayout);

                        //current ConstraintLayout element
                        constraintLayout = findViewById(consId);

                        //guideline
                        Guideline guideline = new Guideline(constraintLayout.getContext());
                        ConstraintLayout.LayoutParams guidelineParams =  new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        guidelineParams.orientation = 0 ;
                        guideline.setLayoutParams(guidelineParams);

                        guideline.setId(View.generateViewId());
                        guideline.setGuidelineBegin(0);
                        guideline.setGuidelinePercent(0.77f);

                        constraintLayout.addView(guideline);

                        //textNomeEvento
                        TextView textNomeEvento = new TextView(constraintLayout.getContext());
                        ConstraintLayout.LayoutParams textNomeEventoParams = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        textNomeEventoParams.setMargins(0,dpiToPx(8, linearLayout.getContext()),0, dpiToPx(8, linearLayout.getContext()));
                        textNomeEventoParams.setMarginStart(dpiToPx(8, linearLayout.getContext()));
                        textNomeEventoParams.setMarginEnd(dpiToPx(8,linearLayout.getContext()));
                        textNomeEventoParams.bottomToBottom = constraintLayout.getId();
                        textNomeEventoParams.endToStart = guideline.getId();
                        textNomeEventoParams.horizontalBias = 0.19f;
                        textNomeEventoParams.startToStart = constraintLayout.getId();
                        textNomeEventoParams.topToTop = constraintLayout.getId();
                        textNomeEvento.setLayoutParams(textNomeEventoParams);

                        textNomeEvento.setId(View.generateViewId());
                        textNomeEvento.setTextSize(15);
                        textNomeEvento.setText(evento.getUtente().getNome() + " deve fare: " + evento.getNome());

                        constraintLayout.addView(textNomeEvento);

                        //textOraEvento
                        TextView textOraEvento = new TextView(constraintLayout.getContext());
                        ConstraintLayout.LayoutParams textOraEventoParams = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        textOraEventoParams.setMargins(0,dpiToPx(8, linearLayout.getContext()),0, dpiToPx(8, linearLayout.getContext()));
                        textOraEventoParams.setMarginStart(dpiToPx(8, linearLayout.getContext()));
                        textOraEventoParams.setMarginEnd(dpiToPx(8,linearLayout.getContext()));
                        textOraEventoParams.bottomToBottom = constraintLayout.getId();
                        textOraEventoParams.endToEnd = constraintLayout.getId();
                        textOraEventoParams.horizontalBias = 0;
                        textOraEventoParams.startToStart = guideline.getId();
                        textOraEventoParams.topToTop = constraintLayout.getId();
                        textNomeEvento.setLayoutParams(textOraEventoParams);

                        textOraEvento.setId(View.generateViewId());
                        textOraEvento.setTextSize(15);
                        textOraEvento.setText(evento.getInizio().get(Calendar.HOUR_OF_DAY) + ":" + minuti + " " + evento.getUtente().getNome() + " deve fare: " + evento.getNome());

                        constraintLayout.addView(textOraEvento);
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


    public static int dpiToPx(int dpi, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dpi * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
