package namenotfoundunica.houseworkcalendar;

import java.util.Calendar;

public class Evento {
    private ColorNameBinder colorNameBinder;
    private Calendar inizio;
    private Calendar fine;
    private Boolean flagRipetizione;
    private Utente utente;
    private String categoria;
    private String note;
    private boolean completedFlag;

    public Evento(ColorNameBinder colorNameBinder, Calendar inizio, Calendar fine, Boolean flagRipetizione, Utente utente, String categoria, String note) {
        this.colorNameBinder = colorNameBinder;
        this.inizio = inizio;
        this.fine = fine;
        this.flagRipetizione = flagRipetizione;
        this.utente = utente;
        this.categoria = categoria;
        this.note = note;
        this.completedFlag = false;
    }

    public ColorNameBinder getColorNameBinder() {
        return colorNameBinder;
    }

    public void setColorNameBinder(ColorNameBinder colorNameBinder) {
        this.colorNameBinder = colorNameBinder;
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

    public boolean isCompletedFlag() {
        return completedFlag;
    }
    public void setCompletedFlag(boolean completedFlag) {
        this.completedFlag = completedFlag;
    }
}
