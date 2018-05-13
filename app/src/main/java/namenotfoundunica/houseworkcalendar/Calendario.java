package namenotfoundunica.houseworkcalendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Calendario extends ArrayList<Evento> {

    public void sort() {
        Collections.sort(this, new Comparator<Evento>() {
            @Override
            public int compare(Evento ev1, Evento ev2)
            {
                return  ev1.getInizio().compareTo(ev2.getInizio());
            }
        });
    }
}
