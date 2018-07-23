package namenotfoundunica.houseworkcalendar.other;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import namenotfoundunica.houseworkcalendar.activity.SchermataIniziale;

public class Sondaggio implements Comparable,Serializable
{

    private String titolo;                  //Domanda principale del sondaggio
    private String descrizione;             //Breve descrizione del sondaggio
    private String stato;                   //Stati possibili del sondaggio (in attesa dei voti, completato con successo)
    private int id;                         //Id relativo al sondaggio
    public List<String> risposte;           //Array delle risposte disponibili per il sondaggio

    public int[] statoUtenti;               //array parallelo a pagantio che contiene lo stato di ogni singolo utente in modo che si sappia chi ha pagato e chi no

    //Aggiungere un array di classe Risposte per definire le possibili risposte allo specifico sondaggio
    public Sondaggio(String titolo, String descrizione, String stato, int id, List<String> risposte)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = stato;
        this.id = id;
        this.risposte = risposte;
        this.statoUtenti=new int[SchermataIniziale.utenti.size()];
        Arrays.fill(statoUtenti,-1);
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getRisposte()
    {
        return risposte;
    }

    public void setRisposte(List<String> risposte)
    {
        this.risposte = risposte;
    }

    public ArrayList<Utente> getUtentiGruppo(){ return SchermataIniziale.utenti; }

    @Override
    public int compareTo(@NonNull Object sondaggio)
    {
        Sondaggio other = (Sondaggio) sondaggio;

        if(stato.compareTo("wait")==0 && other.stato.compareTo("wait")!=0)
        {
            return -1;
        }
        if(statoUtenti[SchermataIniziale.utenteLoggato.getId()]!=-1)
        {
            if(other.stato.compareTo("wait")==0)
            {
                return 1;
            }
            if(other.stato.compareTo("done")==0)
            {
                return -1;
            }
        }
        if(stato.compareTo("done")==0 && other.stato.compareTo("done")!=0)
        {
            return 1;
        }
        return 0;
    }
}
