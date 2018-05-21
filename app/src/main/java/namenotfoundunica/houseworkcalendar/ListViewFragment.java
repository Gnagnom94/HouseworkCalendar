package namenotfoundunica.houseworkcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import static namenotfoundunica.houseworkcalendar.SettimanaTipo.getDataPage;

public class ListViewFragment extends Fragment {
    public static final String ARG_PAGE = "arg_page";

    public ListViewFragment(){

    }

    public static ListViewFragment newInstance(int pageNumber){
        ListViewFragment listViewFragment = new ListViewFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_PAGE, pageNumber);
        listViewFragment.setArguments(arguments);
        return listViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle arguments = getArguments();
        int pageNumber = arguments.getInt(ARG_PAGE);

        View rootView = inflater.inflate(R.layout.list_view_fragment_layout,container,false);

        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        CustomAdapter adapter=new CustomAdapter(this.getActivity(), getDataPage(pageNumber));
        lv.setAdapter(adapter);

        return rootView;
    }
}
