package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.CalendarView;

import android.widget.TextView;



public class SchermataIniziale extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
                                    //rimani in questa pagina
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

        //quando seleziono il numero del giorno nel calendario stampa nella textview il giorno, l'anno e il mese di tale giorno
        final TextView t = (TextView) findViewById(R.id.elementoLista);
        CalendarView c= findViewById(R.id.calendarView);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            public void onSelectedDayChange(CalendarView c, int year, int month, int dayOfMonth)
            {
                //qua andrò a leggere tutti gli eventid della giornata selezionata
                t.setText(""+ year+" "+month+" "+dayOfMonth);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
