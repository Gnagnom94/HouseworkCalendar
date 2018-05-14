package namenotfoundunica.houseworkcalendar;

import java.util.Calendar;

public class Evento {
    private NomeColore nomeColore;
    private Calendar inizio;
    private Calendar fine;
    private Boolean flagRipetizione;
    private Utente utente;
    private String categoria;
    private String note;

    public Evento(NomeColore nomeColore, Calendar inizio, Calendar fine, Boolean flagRipetizione, Utente utente, String categoria, String note) {
        this.nomeColore = nomeColore;
        this.inizio = inizio;
        this.fine = fine;
        this.flagRipetizione = flagRipetizione;
        this.utente = utente;
        this.categoria = categoria;
        this.note = note;
    }

    public NomeColore getNomeColore() {
        return nomeColore;
    }

    public void setNomeColore(NomeColore nomeColore) {
        this.nomeColore = nomeColore;
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
