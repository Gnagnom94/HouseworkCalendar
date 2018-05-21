package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class SettimanaTipo extends AppCompatActivity{

    public TabLayout tabLayout;
    public ViewPager mPager;
    public MyPagerAdapter myPagerAdapter;

    public static Calendario settimana = new Calendario();
    public static ArrayList<Utente> utenti = new ArrayList<>();
    public static ArrayList<ColorNameBinder> colorNameBinders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settimana.clear();
        Utente matteo = new Utente("Matteo", "Atzeni", "matteo.atzeni@outlook.com", "atzeni");
        Utente alessandro = new Utente("Alessandro", "Caddeo", "Alessandro.Caddeo@outlook.com", "caddeo");
        Utente pitta = new Utente("Marco", "Pittau", "Marco.pittau@outlook.com", "piattau");
        utenti.add(matteo);
        utenti.add(alessandro);
        utenti.add(pitta);

        colorNameBinders.add(new ColorNameBinder("Lavatrice", "#FF0000"));
        colorNameBinders.add(new ColorNameBinder("Bucato", "#0025FF"));
        colorNameBinders.add(new ColorNameBinder("Pavimento", "#FFE500"));
        colorNameBinders.add(new ColorNameBinder("Bagno", "#0FFF00"));
        colorNameBinders.add(new ColorNameBinder("Stoviglie", "#00FFF8"));


        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int todayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int todayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int todayMonth = calendar.get(Calendar.MONTH);
        int todayYear = calendar.get(Calendar.YEAR);

//        Log.d("todayOfMonth", "" + todayOfMonth);
//        Log.d("todayOfWeek", "" + todayOfWeek);

        int firstDayOfWeek = (todayOfMonth - (todayOfWeek - 2));
        int lastDayOfWeek = ((todayOfMonth - (todayOfWeek - 2)) + 6);

//        Log.d("firstDayOfWeek", "" + firstDayOfWeek);
//        Log.d("lastDayOfWeek", "" + lastDayOfWeek);

        for (int k = firstDayOfWeek; k <= lastDayOfWeek; k++) {
            for (int j = 9; j < 20; j++) {
                int rUtenti = random.nextInt(((utenti.size() - 1) - 0) + 1);
                int rNomeColoreEvento = random.nextInt(((colorNameBinders.size() - 1) - 0) + 1);

                settimana.add(new Evento(colorNameBinders.get(rNomeColoreEvento),
                        new GregorianCalendar(todayYear, todayMonth, k, j, 00),
                        new GregorianCalendar(todayYear, todayMonth, k, j + 1, 00), true,
                        utenti.get(rUtenti), "", "")
                );
            }
        }

        settimana.sort();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settimana_tipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPage = new Intent(SettimanaTipo.this, AggiuntaEvento.class);
                openPage.putExtra("Chiamante", "SettimanaTipo");
                startActivity(openPage);
            }
        });
        /*INIZIO NUOVA PARTE*/
        mPager= (ViewPager) findViewById(R.id.pager);
        this.addPages();

        //TABLAYOUT
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /*Override del comportamento del tap sulla tab (non è necessario ma lo lasciamo nel caso servisse modificare ulteriormente il risultato).
            Bastererbbe chiamare semplicemente tabLayout.setupWithViewPager(mPager) per creare il collegamento tra tab e pager */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pageNumebr = tab.getPosition();
                //Log.d("pageNumber", ""+ pageNumebr);
                mPager.setCurrentItem(pageNumebr);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //tabLayout.setOnTabSelectedListener(this);
        /*FINE NUOVA PARTE*/


        /*INIZIO VECCHIA PARTE*/
        /*tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        this.addPages();

        //Link al pagerView
//        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/
        /*FINE VECCHIA PARTE*/
    }

    private void addPages()
    {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());
        pagerAdapter.addFragment(new ListViewFragment());
        pagerAdapter.addFragment(new ListViewFragment());
        pagerAdapter.addFragment(new ListViewFragment());
        pagerAdapter.addFragment(new ListViewFragment());
        pagerAdapter.addFragment(new ListViewFragment());
        pagerAdapter.addFragment(new ListViewFragment());
        pagerAdapter.addFragment(new ListViewFragment());

        //SET ADAPTER TO VP
        mPager.setAdapter(pagerAdapter);
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent openPageHome = new Intent(SettimanaTipo.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //da sistemare non si capisce ancora cosa voglia
    public static ArrayList<Evento> getDataPage(int pageNumber) {
        ArrayList<Evento> tmp = new ArrayList<>();
        for (Evento evento:settimana) {
            evento.getInizio().setFirstDayOfWeek(Calendar.MONDAY);
//            Log.d("getDataPage", "" + (evento.getInizio().get(Calendar.DAY_OF_WEEK)));
//            Log.d("getDataPagePageNumber", "" + pageNumber);
            if((evento.getInizio().get(Calendar.DAY_OF_WEEK) - 2) == (pageNumber)){
                tmp.add(evento);
            }else if(evento.getInizio().get(Calendar.DAY_OF_WEEK) == 1 && pageNumber == 6){
                tmp.add(evento);
            }
        }
        return tmp;
    }
}


