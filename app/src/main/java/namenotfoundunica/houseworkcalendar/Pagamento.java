package namenotfoundunica.houseworkcalendar;

import java.util.ArrayList;

public class Pagamento {
    private String nome;
    private float totale;
    private int id;
    private ArrayList<Utente> paganti;

    public Pagamento(String nome, float totale,int id,ArrayList<Utente> paganti)
    {
        this.setNome(nome);
        this.setTotale(totale);
        this.setId(id);
        this.paganti=new ArrayList<>(paganti);
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

    public ArrayList<Utente> getPaganti() {
        return paganti;
    }

    public void setPaganti(ArrayList<Utente> paganti) {
        this.paganti = this.paganti;
    }
    public int getNumeroPaganti()
    {
        return paganti.size();
    }
}
