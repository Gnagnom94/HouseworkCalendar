package namenotfoundunica.houseworkcalendar;

import java.util.Calendar;

public class Evento {
    private String nome;
    private Calendar inizio;
    private Calendar fine;
    private Boolean flagRipetizione;
    private Utente utente;
    private String categoria;
    private String note;
    private String color;

    public Evento(String nome, Calendar inizio, Calendar fine, Boolean flagRipetizione, Utente utente, String categoria, String note, String color) {
        this.setNome(nome);
        this.setInizio(inizio);
        this.setFine(fine);
        this.setFlagRipetizione(flagRipetizione);
        this.setUtente(utente);
        this.setCategoria(categoria);
        this.setNote(note);
        this.setColor(color);
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
