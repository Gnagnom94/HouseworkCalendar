package namenotfoundunica.houseworkcalendar.other;

import java.util.ArrayList;
import java.util.Arrays;

import namenotfoundunica.houseworkcalendar.activity.SchermataIniziale;

public class Pagamento {
    private String nome;
    private float totale;
    private int id;
    public boolean statoUtenti[]; //array parallelo a pagantio che contiene lo stato di ogni singolo utente in modo che si sappia chi ha pagato e chi no

    public Pagamento(String nome, float totale,int id,ArrayList<Utente> utentiGruppo)
    {
        this.setNome(nome);
        this.setTotale(totale);
        this.setId(id);
        this.statoUtenti=new boolean[utentiGruppo.size()];
        Arrays.fill(statoUtenti,false);

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Utente> getUtentiGruppo() {
        return SchermataIniziale.utenti;
    }

    public void setUtentiGruppo(ArrayList<Utente> utentiGruppo) {
        SchermataIniziale.utenti = utentiGruppo;
    }
    public int getNumeroPaganti()
    {
        return SchermataIniziale.utenti.size();
    }

    public void setStatoUtenti(int posizione)
    {
        statoUtenti[posizione]=true;
    }


}
