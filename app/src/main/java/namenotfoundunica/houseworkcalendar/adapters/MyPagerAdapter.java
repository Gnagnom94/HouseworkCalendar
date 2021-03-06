package namenotfoundunica.houseworkcalendar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import namenotfoundunica.houseworkcalendar.fragments.ListViewFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<Fragment> fragments = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        ListViewFragment listViewFragment = ListViewFragment.newInstance(position);
        return listViewFragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //ADD PAGE
    public void addFragment(Fragment f)
    {
        fragments.add(f);
    }

    //set title

    @Override
    public CharSequence getPageTitle(int position) {
        String[] title = {"L", "M", "M", "G", "V", "S", "D"};
        return title[position];
    }
}
