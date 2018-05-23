package namenotfoundunica.houseworkcalendar;

import java.util.List;

public class Sondaggio
{

    private String titolo;                  //Domanda principale del sondaggio
    private String descrizione;             //Breve descrizione del sondaggio
    private String stato;                   //Stati possibili del sondaggio (in attesa dei voti, completato con successo, completato senza successo)
    private int id;                         //Id relativo al sondaggio
    private List<String> risposte;          //Array delle risposte disponibili per il sondaggio

    //Aggiungere un array di classe Risposte per definire le possibili risposte allo specifico sondaggio
    public Sondaggio(String titolo, String descrizione, String stato, int id)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = stato;
        this.id = id;
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
}
