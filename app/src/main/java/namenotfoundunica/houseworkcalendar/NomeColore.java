package namenotfoundunica.houseworkcalendar;

public class NomeColore {

    private String nomeEvento;
    private String coloreEvento;

    public NomeColore(String nomeEvento, String coloreEvento) {
        this.setNomeEvento(nomeEvento);
        this.setColoreEvento(coloreEvento);
    }


    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getColoreEvento() {
        return coloreEvento;
    }

    public void setColoreEvento(String coloreEvento) {
        this.coloreEvento = coloreEvento;
    }
}
