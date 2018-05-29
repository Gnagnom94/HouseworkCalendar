package namenotfoundunica.houseworkcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import static namenotfoundunica.houseworkcalendar.SettimanaTipo.getDataPage;

public class ListViewFragment extends Fragment {
    public static final String ARG_PAGE = "arg_page";
    public boolean flagSelectUnselectAll = false;

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

        final ListView listView = (ListView) rootView.findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), R.layout.customlayout, getDataPage(pageNumber));
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

        return rootView;
    }
}
