package namenotfoundunica.houseworkcalendar;

import java.util.Calendar;
import java.util.Date;

public class Evento {
    private String nome;
    private Calendar inizio;
    private Calendar fine;
    private Boolean flagRipetizione;
    private Utente utente;
    private String categoria;
    private String note;

    Evento(String nome, Calendar inizio, Calendar fine, Boolean flagRipetizione, Utente utente, String categoria, String note)
    {
        this.nome = nome;
        this.inizio = inizio;
        this.fine = fine;
        this.flagRipetizione = flagRipetizione;
        this.utente = utente;
        this.categoria = categoria;
        this.note = note;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getInizio() {
        return inizio;
    }

    public void setInizio(Calendar inizio) {
        this.inizio = inizio;
    }

    public Calendar getFine() {
        return fine;
    }

    public void setFine(Calendar fine) {
        this.fine = fine;
    }

    public Boolean getFlagRipetizione() {
        return flagRipetizione;
    }

    public void setFlagRipetizione(Boolean flagRipetizione) {
        this.flagRipetizione = flagRipetizione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
