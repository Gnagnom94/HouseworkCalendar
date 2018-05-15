package namenotfoundunica.houseworkcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class SettimanaTipo extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager mPager;
    public MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(myPagerAdapter);

        //Link al pagerView
//        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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

    public static class MyFragment extends Fragment{
        public static final String ARG_PAGE = "arg_page";

        public MyFragment(){

        }
        public static MyFragment newInstance(int pageNumber){
            MyFragment myFragment = new MyFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_PAGE, pageNumber);
            myFragment.setArguments(arguments);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            Bundle arguments = getArguments();
            int pageNumber = arguments.getInt(ARG_PAGE);
            TextView myText = new TextView(getActivity());
            myText.setText("Hello I am the text inside this fragment " + pageNumber);
            myText.setGravity(Gravity.CENTER);
            return myText;
        }
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter{

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        SettimanaTipo.MyFragment myFragment = new SettimanaTipo.MyFragment().newInstance(position);
        return myFragment;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String[] giorni= {"L", "M", "M", "G", "V", "S", "D"};
        return giorni[position];
    }
}