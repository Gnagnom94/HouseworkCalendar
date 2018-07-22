package namenotfoundunica.houseworkcalendar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import namenotfoundunica.houseworkcalendar.R;
import namenotfoundunica.houseworkcalendar.other.Utente;

public class Registrazione extends AppCompatActivity {

    private TextView nomeTextView ;
    private TextView cognomeTextView ;
    private TextView emailTextView ;
    private TextView pswTextView ;
    private TextView confermaPswTextView ;
    private Button conferma;

    private Boolean flagCompletamento = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        nomeTextView=findViewById(R.id.inputNome);
        cognomeTextView=findViewById(R.id.inputCognome);
        emailTextView=findViewById(R.id.inputEmail);
        pswTextView=findViewById(R.id.inputPassword_1);
        confermaPswTextView=findViewById(R.id.inputPassword_2);
        conferma=findViewById(R.id.button);

        conferma.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(nomeTextView.getText().toString().isEmpty()){
                    nomeTextView.setError("Inserisci Nome");
                    flagCompletamento = false;
                }
                if(cognomeTextView.getText().toString().isEmpty()){
                    cognomeTextView.setError("Inserisci Cognome");
                    flagCompletamento = false;
                }
                if(emailTextView.getText().toString().isEmpty()){
                    emailTextView.setError("Inserisci email");
                    flagCompletamento = false;
                }
                if(pswTextView.getText().toString().isEmpty()){
                    pswTextView.setError("Inserisci Password");
                    flagCompletamento = false;
                }
                if(confermaPswTextView.getText().toString().isEmpty()){
                    confermaPswTextView.setError("Conferma Password");
                    flagCompletamento = false;
                }

                if(flagCompletamento) {
                    if (pswTextView.getText().toString().compareTo(confermaPswTextView.getText().toString()) == 0) {
                        String newEmail = emailTextView.getText().toString();
                        boolean flagEmail = false;
                        for (Utente i : SchermataIniziale.utenti) {
                            if (i.getEmail().compareTo(newEmail) == 0)
                                flagEmail = true;
                        }

                        if (flagEmail == false) {
                            SchermataIniziale.utenti.add(new Utente(nomeTextView.getText().toString(), cognomeTextView.getText().toString(), newEmail, pswTextView.getText().toString()));
                            makeToast("Registrazione effettuata, effetuare il login");
                            Intent openPage = new Intent(Registrazione.this, SchermataIniziale.class);
                            startActivity(openPage);
                        } else {
                            makeToast("Utente gia presente nel database");
                        }
                    } else {
                        makeToast("Le due password non sono uguali");
                    }
                }
                flagCompletamento = true;
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent openPageHome = new Intent(Registrazione.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeToast(String message)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}

