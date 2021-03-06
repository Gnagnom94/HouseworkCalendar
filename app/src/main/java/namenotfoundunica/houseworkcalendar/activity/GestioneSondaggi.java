package namenotfoundunica.houseworkcalendar.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Evento;
import namenotfoundunica.houseworkcalendar.other.Sondaggio;
import namenotfoundunica.houseworkcalendar.other.Utente;

public class GestioneSondaggi extends AppCompatActivity
{

    public static ArrayList<Sondaggio> lstSondaggio;
    private static boolean flagCreation = false;
    public boolean flagSelectUnselectAll = false;
    public CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondaggi);

        if (!flagCreation)
        {
            lstSondaggio = new ArrayList<>();
            List<String> risposte = new ArrayList<String>();
            String titolo = "Cosa mangiamo per cena?";
            String descrizione = "Sto andando a fare la spesa, scegliamo cosa mangiare così compro il necessario per cucinarlo";
            risposte.add("Fettine");
            risposte.add("Pasta al sugo");
            risposte.add("Toast");
            lstSondaggio.add(new Sondaggio(titolo, descrizione, "wait", 0, risposte));
//            lstSondaggio.add(new Sondaggio("titolo2", "descrizione2", "wait", 1, risposte));
//            lstSondaggio.add(new Sondaggio("titolo3", "descrizione3", "done", 2, risposte));
            flagCreation = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Boolean completato = true;
        /*Controllo icona*/
        for(Sondaggio s:lstSondaggio)
        {
            completato = true;
            for(int i=0; i < s.statoUtenti.length; i++)
            {
                if(s.statoUtenti[i] == -1)
                {
                    completato = false;
                }
            }
            if(completato)
            {
                s.setStato("done");
            }
        }
        for(Sondaggio s:lstSondaggio)
        {
            if(s.statoUtenti[SchermataIniziale.utenteLoggato.getId()]==-1)
            {
                s.setStato("wait");
            }
        }


        lstSondaggio.sort(new Comparator<Sondaggio>()
        {
            @Override
            public int compare(Sondaggio o1, Sondaggio o2)
            {
                return o1.compareTo(o2);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.listViewSondaggi);
        customAdapter = new CustomAdapter(GestioneSondaggi.this, R.layout.custom_survey_layout, lstSondaggio);
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
                mode.setTitle(checkedCount + " selezionato");
                // Calls toggleSelection method from customAdapter Class
                customAdapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                // Calls getSelectedIds method from customAdapter Class
                final SparseBooleanArray selected = customAdapter.getSelectedIds();
                switch (item.getItemId()) {
                    case R.id.delete:
                        if(selected.size() > 1){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(GestioneSondaggi.this);
                            builder.setTitle("Conferma Eliminazione");
                            builder.setNegativeButton("Annulla", null);
                            builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Captures all selected ids with a loop
                                    for (int i = (selected.size() - 1); i >= 0; i--) {
                                        if (selected.valueAt(i)) {
                                            Sondaggio selecteditem = customAdapter.getItem(selected.keyAt(i));
                                            // Remove selected items following the ids
                                            customAdapter.remove(selecteditem);
                                        }
                                    }
                                    // Close CAB
                                    mode.finish();
                                }
                            });
                            final AlertDialog dialog = builder.create();

                            dialog.show();
                        }else{
                            // Captures all selected ids with a loop
                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    Sondaggio selecteditem = customAdapter.getItem(selected.keyAt(i));
                                    // Remove selected items following the ids
                                    customAdapter.remove(selecteditem);
                                }
                            }
                            // Close CAB
                            mode.finish();
                        }
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
//                showInfoSondaggio(position);
                showInfoSondaggio2(lstSondaggio.get(position));
            }
        });

        final FloatingActionButton fab = findViewById(R.id.fab_sondaggi);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GestioneSondaggi.this, AggiuntaSondaggio.class);
                startActivity(intent);

            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING)
                {
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        Intent i = getIntent();
        Sondaggio sondaggio = (Sondaggio) i.getSerializableExtra("Sondaggio");
        if(sondaggio != null)
        {
            showInfoSondaggio2(sondaggio);
        }


    }

    class CustomAdapter extends ArrayAdapter<Sondaggio>
    {
        Context context;
        ArrayList<Sondaggio> sondaggio;
        LayoutInflater inflater;
        private SparseBooleanArray mSelectedItemsIds;

        public CustomAdapter(Context context, int resourceId, ArrayList<Sondaggio> sondaggio) {
            super(context, resourceId, sondaggio);
            mSelectedItemsIds = new SparseBooleanArray();
            this.context = context;
            this.sondaggio = sondaggio;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
            return lstSondaggio.size();
        }

        @Override
        public Sondaggio getItem(int position)
        {
            return sondaggio.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            convertView = getLayoutInflater().inflate(R.layout.custom_survey_layout,null);
            TextView textView_name = (TextView) convertView.findViewById(R.id.domanda_sondaggio);
            //final ImageView button = convertView.findViewById(R.id.vota_sondaggio);
            final ImageView statoImmagineSondaggio = convertView.findViewById(R.id.immagineStato);

            if(lstSondaggio.get(position).statoUtenti[SchermataIniziale.utenteLoggato.getId()]!=-1)
            {
                statoImmagineSondaggio.setImageResource(R.drawable.icon_answer_sent);
            }
            if(lstSondaggio.get(position).getStato().compareTo("wait") == 0)
            {
                statoImmagineSondaggio.setImageResource(R.drawable.icon_pending_survey);
            }
            else if(lstSondaggio.get(position).getStato().compareTo("done") == 0)
            {
                statoImmagineSondaggio.setImageResource(R.drawable.icon_done_survey);
            }

            final Button button = convertView.findViewById(R.id.vota_sondaggio);
            final Sondaggio sondaggio = lstSondaggio.get(position);
            button.setId(sondaggio.getId());
            if(sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()] == -1)
            {
                button.setText("VOTA");
            }
            else
            {
                button.setText("VISIONA");
            }
            if(sondaggio.getStato().compareTo("done")==0)
            {
                button.setText("VISIONA");
            }
            textView_name.setText(sondaggio.getTitolo());
            boolean flagVotoCompleto=true;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GestioneSondaggi.this, VisualizzaSondaggio.class);
                        intent.putExtra("indice", position);
                        startActivity(intent);
                    }
                });
//            }
            return convertView;
        }

        @Override
        public void remove(Sondaggio object) {
            sondaggio.remove(object);
            lstSondaggio.remove(object);
            notifyDataSetChanged();
        }

        public void toggleSelection(int position) {
            selectView(position, !mSelectedItemsIds.get(position));
        }

        public void removeSelection() {
            mSelectedItemsIds = new SparseBooleanArray();
            notifyDataSetChanged();
        }

        public void selectView(int position, boolean value) {
            if (value)
                mSelectedItemsIds.put(position, value);
            else
                mSelectedItemsIds.delete(position);
            notifyDataSetChanged();
        }

        public int getSelectedCount() {
            return mSelectedItemsIds.size();
        }

        public SparseBooleanArray getSelectedIds() {
            return mSelectedItemsIds;
        }
    }


    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
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
            Intent openPageHome = new Intent(GestioneSondaggi.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showInfoSondaggio2(Sondaggio sondaggio)
    {


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customlayout_info_sondaggio, null);
        LinearLayout linearLayout = alertLayout.findViewById(R.id.linearLayout);

        TextView textViewTitolo = alertLayout.findViewById(R.id.textViewTitolo);
        TextView textViewDescrizione = alertLayout.findViewById(R.id.textViewDescrizione);
        TextView textViewRispostaScelta = alertLayout.findViewById(R.id.textViewRispostaScelta);

        textViewTitolo.setText(sondaggio.getTitolo());
        textViewDescrizione.setText(sondaggio.getDescrizione());
        if(sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()] != -1) {
            textViewRispostaScelta.setText("La tua Risposta: " + sondaggio.getRisposte().get(sondaggio.statoUtenti[SchermataIniziale.utenteLoggato.getId()]));
        }

        int i = 0;
        for(String risposta:sondaggio.getRisposte()){
            final View child = getLayoutInflater().inflate(R.layout.custom_answer_layout, null);

            TextView textViewRisposta = child.findViewById(R.id.textViewRisposta);
            TextView textViewCounter = child.findViewById(R.id.textViewCounter);

            textViewRisposta.setText(risposta);


            ProgressBar progressBar = child.findViewById(R.id.progressBar);

            int j = 0;
            int contatore = 0;
            for(Utente utente:sondaggio.getUtentiGruppo())
            {
                if(sondaggio.statoUtenti[j]==i)
                    contatore++;
                j++;
            }
            progressBar.setProgress((contatore*100)/sondaggio.getUtentiGruppo().size());

            textViewCounter.setText("" + contatore + (contatore > 1 || contatore == 0 ? " Voti" : " Voto"));

            linearLayout.addView(child);
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("OK", null);
        builder.setView(alertLayout);

        final AlertDialog dialog = builder.create();

        dialog.show();
    }
}
