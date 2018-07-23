package namenotfoundunica.houseworkcalendar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Utente;

public class GestioneGruppo extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_gestione_gruppo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                cercaUtenti();
            }
        });
        ListView listView = (ListView) findViewById(R.id.listViewGruppo);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return SchermataIniziale.utenti.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            convertView = getLayoutInflater().inflate(R.layout.custom_utentelist, null);
            final Utente utenteThis = SchermataIniziale.utenti.get(position);

            TextView textView_name = (TextView) convertView.findViewById(R.id.nomeUtente);
            final Button button = (Button) convertView.findViewById(R.id.cancellaUtente);
            textView_name.setText(utenteThis.getNome());
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(GestioneGruppo.this);
                    builder.setTitle("Cancella " + utenteThis.getNome());
                    builder.setMessage("Vuoi davero togliere "+utenteThis.getNome()+" dal gruppo?");
                    DialogInterface.OnClickListener onPositiveClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), utenteThis.getNome()+" eliminato dal gruppo", Toast.LENGTH_SHORT);
                            SchermataIniziale.utenti.remove(position);
                            toast.show();
                            notifyDataSetChanged();
                        }
                    };
                    builder.setPositiveButton("Si",onPositiveClickListener);
                    builder.setNegativeButton("No",null);
                    builder.show();
                }
            });

            return convertView;
        }
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
            Intent openPageHome = new Intent(GestioneGruppo.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cercaUtenti()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        final EditText etInput    = new EditText(this);

        tvMessage.setText("Inserisci email dell'utente da aggiungere al gruppo:");
        etInput.setSingleLine();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(etInput);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);

        DialogInterface.OnClickListener onPositiveClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                String tmp= etInput.getText().toString();
                boolean flagtrovato=false;
                for(Utente i:SchermataIniziale.utenti)
                {
                    if (i.getEmail().compareTo(tmp)==0)
                    {
                        SchermataIniziale.utenti.add(i);
                        Toast toast = Toast.makeText(getApplicationContext(), i.getNome() + " aggiunto al gruppo", Toast.LENGTH_SHORT);
                        toast.show();
                        flagtrovato=true;
                        finish();
                        startActivity(getIntent());
                    }
                }
                if(flagtrovato!=true)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Utente non trovato", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        };
        builder.setPositiveButton("Aggiungi",onPositiveClickListener);
        builder.setNegativeButton("Annulla",null);
        builder.create().show();
    }
}


