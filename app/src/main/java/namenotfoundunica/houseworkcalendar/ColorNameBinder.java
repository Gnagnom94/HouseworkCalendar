package namenotfoundunica.houseworkcalendar;

import android.graphics.Color;

public class ColorNameBinder {

    private String nomeEvento;
    private String coloreEvento;

    public ColorNameBinder(String nomeEvento, String coloreEvento) {
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

    public int getColoreEventoToInt(){
        return Color.parseColor(coloreEvento);
    }

    public void setColoreEventoFromInt(int color){this.coloreEvento = "#" + Integer.toHexString(color);}
}
