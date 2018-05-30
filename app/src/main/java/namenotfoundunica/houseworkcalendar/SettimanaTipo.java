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
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class SettimanaTipo extends AppCompatActivity{

    public TabLayout tabLayout;
    public ViewPager mPager;
    private TextView textDataGiornoSelezionato;
    private Button right;
    private  Button left;
    private MyPagerAdapter pagerAdapter;
    private static Calendar dataSelected;                                                           //Dichiarazione dataSelected
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        dataSelected = Calendar.getInstance();                                                      //Imposto dataSelected al giorno corrente
        right=findViewById(R.id.buttonRight);
        left=findViewById(R.id.buttonLeft);
        /*INIZIO NUOVA PARTE*/
        mPager= (ViewPager) findViewById(R.id.pager);
        this.addPages();
        textDataGiornoSelezionato=findViewById(R.id.showDataDaySelected);

        switch (dataSelected.get(Calendar.DAY_OF_WEEK))                                             //Porto dataSelected al Lunedì della settimana corrente
        {
            case 1: dataSelected.roll(Calendar.DAY_OF_MONTH,-6);//domenica
                break;
            case 2: //lunedì
                break;
            case 3: dataSelected.roll(Calendar.DAY_OF_MONTH,-1);//martedì
                break;
            case 4: dataSelected.roll(Calendar.DAY_OF_MONTH,-2);//mercoledì
                break;
            case 5: dataSelected.roll(Calendar.DAY_OF_MONTH,-3);//giovedì
                break;
            case 6: dataSelected.roll(Calendar.DAY_OF_MONTH,-4);//venerdì
                break;
            case 7: dataSelected.roll(Calendar.DAY_OF_MONTH,-5);//sabato
                break;
        }
        textDataGiornoSelezionato.setText(dataSelected.get(Calendar.DAY_OF_MONTH)+"/"+(dataSelected.get(Calendar.MONTH)+1)+"/"+dataSelected.get(Calendar.YEAR)); //Aggiorno il testo nella TextView
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
                //prendo la data dal primo evente della giornata per sapere il giorno selezionato
                mPager.setCurrentItem(pageNumebr);
                dataPageSelected(pageNumebr);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dataSelected.roll(Calendar.WEEK_OF_YEAR,1);                                 //se premi la freccia destra porta la data di dataSelected ad una settimana avanti
                mPager.setCurrentItem(0);                                                           //Imposto il Pager al primo giorno della settimana (Lunedì)
                dataPageSelected(0);                                                    //Aggiorno il giorno selezionato al Lunedì della settimana successiva (Cambiata una riga Prima)
                refreshFragments();                                                                 //Aggiorno la variabile fragments del pagerAdapter per generare le nuove pagine della settimana di destinazione
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSelected.roll(Calendar.WEEK_OF_YEAR,-1);
                mPager.setCurrentItem(0);
                dataPageSelected(0);
                refreshFragments();
            }
        });
    }

    private void refreshFragments(){
        pagerAdapter.fragments.clear();
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

    private void addPages()
    {
        pagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());
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

    // metodo che dato il numero di pagina, pageNumber:(numero di giorno della settimana 0-Lunedì/6-Domenica) ritorna un ArrayList<Evento> che contiene solo eventi di quel dato giorno della settimana.
    public static ArrayList<Evento> getDataPage(int pageNumber)
    {
        ArrayList<Evento> eventiDelGiorno = new ArrayList<>();
        int firstWeekdayInYear=dataSelected.get(Calendar.DAY_OF_YEAR);                              //Imposto in firstWeekdayInYear il giorno dell'anno della data selezionatata in dataSelected

        switch (dataSelected.get(Calendar.DAY_OF_WEEK))                                             //Scorro firstWeekdayInYear per adeguarlo al giorno selezionato della settimana selezionata in dataSelected
        {
            case 1: firstWeekdayInYear-=6;//domenica
                break;
            case 2: //lunedì
                break;
            case 3: firstWeekdayInYear-=1;//martedì
                break;
            case 4: firstWeekdayInYear-=2;//mercoledì
                break;
            case 5: firstWeekdayInYear-=3;//giovedì
                break;
            case 6: firstWeekdayInYear-=4;//venerdì
                break;
            case 7: firstWeekdayInYear-=5;//sabato
                break;
        }

        for (Evento evento:SchermataIniziale.calendario)
        {
            evento.getInizio().setFirstDayOfWeek(Calendar.MONDAY);
            if(evento.getFlagRipetizione() && evento.isGroupFlag())
            {
                if((evento.getInizio().get(Calendar.DAY_OF_YEAR)>=firstWeekdayInYear)&&(evento.getInizio().get(Calendar.DAY_OF_YEAR)<firstWeekdayInYear+7))
                    if ((evento.getInizio().get(Calendar.DAY_OF_WEEK) - 2) == (pageNumber))
                        eventiDelGiorno.add(evento);
                    else
                        if (evento.getInizio().get(Calendar.DAY_OF_WEEK) == 1 && pageNumber == 6)
                            eventiDelGiorno.add(evento);
            }
        }
        return eventiDelGiorno;
    }

    //cerco il primo evento per ogni pagina in modo da restitture la data del giorno selezionato
    public void dataPageSelected(int pageNumber)
    {
        int dayPrew=dataSelected.get(Calendar.DAY_OF_WEEK);
        switch (dayPrew)
        {
            case 1: dayPrew=6;//domenica
                break;
            case 2: dayPrew=0;//lunedì
                break;
            case 3: dayPrew=1;//martedì
                break;
            case 4: dayPrew=2;//mercoledì
                break;
            case 5: dayPrew=3;//giovedì
                break;
            case 6: dayPrew=4;//venerdì
                break;
            case 7: dayPrew=5;//sabato
                break;
        }
        if(pageNumber>dayPrew)
            dataSelected.roll(Calendar.DAY_OF_MONTH,pageNumber-dayPrew);
        else
            dataSelected.roll(Calendar.DAY_OF_MONTH,-(dayPrew-pageNumber));
        textDataGiornoSelezionato.setText(dataSelected.get(Calendar.DAY_OF_MONTH)+"/"+(dataSelected.get(Calendar.MONTH)+1)+"/"+dataSelected.get(Calendar.YEAR));

    }

}


