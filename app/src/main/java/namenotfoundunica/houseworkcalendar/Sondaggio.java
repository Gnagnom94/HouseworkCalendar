package namenotfoundunica.houseworkcalendar;

public class Sondaggio
{

    private String titolo;          //Domanda principale del sondaggio
    private String tipo_sondaggio;  //Tipologia del sondaggio (maggioranza, unanimità)
    private String descrizione;     //Breve descrizione del sondaggio
    private String stato;           //Stati possibili del sondaggio (in attesa dei voti, completato con successo, completato senza successo)
    private int id;                 //Id relativo al sondaggio

    //Aggiungere un array di classe Risposte per definire le possibili risposte allo specifico sondaggio

    public Sondaggio(String titolo, String tipo_sondaggio, String descrizione, String stato, int id)
    {
        this.titolo = titolo;
        this.tipo_sondaggio = tipo_sondaggio;
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

    public String getTipo_sondaggio() {
        return tipo_sondaggio;
    }

    public void setTipo_sondaggio(String tipo_sondaggio) {
        this.tipo_sondaggio = tipo_sondaggio;
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
}
