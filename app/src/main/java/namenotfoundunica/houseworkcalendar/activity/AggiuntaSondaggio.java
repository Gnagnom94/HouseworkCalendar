package namenotfoundunica.houseworkcalendar.activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Sondaggio;

import static java.lang.StrictMath.abs;

public class AggiuntaSondaggio extends AppCompatActivity
{
    private List<String> risposte = new ArrayList<String>();
    private LinearLayout linearLayout;
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aggiunta_sondaggio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

        Button buttonAddQ = findViewById(R.id.buttonAddQ);

        selectedIndex = 3;
        buttonAddQ.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final View child = getLayoutInflater().inflate(R.layout.custom_addq_layout, null);
                child.setId(selectedIndex);

                EditText editText = child.findViewById(R.id.AnswerAgg);
                editText.setHint("Inserisci Risposta ");

                final ImageButton meno = child.findViewById(R.id.removeQ);
                meno.setId(selectedIndex);

                selectedIndex++;

                meno.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        linearLayout.removeView(child);
                        selectedIndex--;
                    }
                });
                linearLayout.addView(child);

            }
        });

        Button buttonSendSurvey = findViewById(R.id.buttonSendSurvey);
        buttonSendSurvey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                for (int i = 2; i < 4; i++) {
                    EditText editText = (EditText) linearLayout.getChildAt(i);
                    risposte.add(editText.getText().toString());
                }

                for (int i = 4; i <= selectedIndex; i++) {
                    ConstraintLayout constraintLayout = (ConstraintLayout) linearLayout.getChildAt(i);
                    EditText editText = constraintLayout.findViewById(R.id.AnswerAgg);
                    risposte.add(editText.getText().toString());
                }

                EditText editTextTitolo = (EditText) linearLayout.getChildAt(0);
                EditText editTextDescrizione = (EditText) linearLayout.getChildAt(1);



                GestioneSondaggi.lstSondaggio.add(new Sondaggio(editTextTitolo.getText().toString(),
                        editTextDescrizione.getText().toString(),
                        "wait",
                        GestioneSondaggi.lstSondaggio.size(),
                        risposte));

                Intent openPageHome = new Intent(AggiuntaSondaggio.this, GestioneSondaggi.class);
                startActivity(openPageHome);

            }
        });


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
    public boolean onOptionsItemSelected(MenuItem item) {


            Intent openPageHome = new Intent(AggiuntaSondaggio.this, GestioneSondaggi.class);
            startActivity(openPageHome);


        return super.onOptionsItemSelected(item);
    }
}
