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

public class Login  extends AppCompatActivity
{
    private TextView emailTextView ;
    private TextView pswTextView ;
    private Button confermaButton;
    private Button registratiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        emailTextView=findViewById(R.id.emailtext);
        pswTextView=findViewById(R.id.pswText);
        confermaButton=findViewById(R.id.loginButton);
        registratiButton=findViewById(R.id.registratiButton);

        confermaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=emailTextView.getText().toString();
                String psw=pswTextView.getText().toString();
                boolean connesso=false;
                boolean visualizza_toast = true;
                for(Utente utente:SchermataIniziale.utenti)
                {
                    if(email.compareTo("") == 0)
                    {
                        emailTextView.setError("Inserisci l'email!");
                        visualizza_toast = false;
                    }
                    if(psw.compareTo("") == 0)
                    {
                        pswTextView.setError("Inserisci la password!");
                        visualizza_toast = false;
                    }
                    if((email.compareToIgnoreCase(utente.getEmail())==0)&&(psw.compareTo(utente.getPassword())==0))
                    {
                        SchermataIniziale.utenteLoggato=utente;
                        connesso=true;
                        makeToast(utente.getNome()+" Hai eseguito l'accesso");

                        Intent openPageHome = new Intent(Login.this, SchermataIniziale.class);
                        startActivity(openPageHome);

                    }
                }
                if(!connesso && visualizza_toast == true)
                    makeToast("Email o password errati ");
            }
        });

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openPageHome = new Intent(Login.this, Registrazione.class);
                startActivity(openPageHome);
            }
        });

    }
    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public void makeToast(String message)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent openPageHome = new Intent(Login.this, SchermataIniziale.class);
            startActivity(openPageHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
