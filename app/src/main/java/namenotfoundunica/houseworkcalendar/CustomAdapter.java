package namenotfoundunica.houseworkcalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomAdapter extends BaseAdapter {

    private int buttonColorInt;
    private int buttonForegroundColorCompliment;


    Context c;
    public ArrayList<Evento> giorno;
    LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Evento> giorno) {
        this.c = c;
        this.giorno = giorno;
    }

    @Override
    public int getCount() {
        return giorno.size();
    }

    @Override
    public Object getItem(int position) {
        return giorno.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.customlayout,parent,false);
        }

        TextView textNomeUtente = convertView.findViewById(R.id.textNomeUtente);
        final TextView textNomeEvento = convertView.findViewById(R.id.textNomeEvento);
        TextView textOraInizio = convertView.findViewById(R.id.textOraInizio);
        TextView textOraFine = convertView.findViewById(R.id.textOraFine);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        String oreInizio;
        //if per rendere sempre di due cifre le ore
        if(giorno.get(position).getInizio().get(Calendar.HOUR_OF_DAY) < 10)
        {
            oreInizio = "0" + giorno.get(position).getInizio().get(Calendar.HOUR_OF_DAY);
        }
        else
        {
            oreInizio = "" + giorno.get(position).getInizio().get(Calendar.HOUR_OF_DAY);
        }
        String minutiInizio;
        //if per rendere sempre di due cifre i minuti
        if(giorno.get(position).getInizio().get(Calendar.MINUTE) < 10)
        {
            minutiInizio = giorno.get(position).getInizio().get(Calendar.MINUTE) + "0";
        }
        else
        {
            minutiInizio ="" + giorno.get(position).getInizio().get(Calendar.MINUTE);
        }
        String oreFine;
        //if per rendere sempre di due cifre le ore
        if(giorno.get(position).getFine().get(Calendar.HOUR_OF_DAY) < 10)
        {
            oreFine = "0" + giorno.get(position).getFine().get(Calendar.HOUR_OF_DAY);
        }
        else
        {
            oreFine = "" + giorno.get(position).getFine().get(Calendar.HOUR_OF_DAY);
        }
        String minutiFine;
        //if per rendere sempre di due cifre i minuti
        if(giorno.get(position).getFine().get(Calendar.MINUTE) < 10)
        {
            minutiFine = giorno.get(position).getFine().get(Calendar.MINUTE) + "0";
        }
        else
        {
            minutiFine ="" + giorno.get(position).getFine().get(Calendar.MINUTE);
        }

        textNomeUtente.setText(giorno.get(position).getUtente().getNome());
        textNomeEvento.setText(giorno.get(position).getColorNameBinder().getNomeEvento() + " * " + giorno.get(position).getInizio().get(Calendar.DAY_OF_MONTH));
        textOraInizio.setText(oreInizio + ":" + minutiInizio);
        textOraFine.setText(oreFine + ":" + minutiFine);

        buttonColorInt = giorno.get(position).getColorNameBinder().getColoreEventoToInt();
        buttonForegroundColorCompliment = ColorUtils.getContrastColor(buttonColorInt);

        imageView.setBackground(CustomDrawable.getTintedDrawable(imageView.getContext(), R.drawable.roundedbutton, buttonColorInt));

        Calendar calendar = Calendar.getInstance();

        if(giorno.get(position).getFine().get(Calendar.YEAR) <=  calendar.get(Calendar.YEAR)&&
                giorno.get(position).getFine().get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) &&
                giorno.get(position).getFine().get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH))
        {
            imageView.setColorFilter(buttonForegroundColorCompliment);
//            imageView.setForeground(CustomDrawable.getTintedDrawable(imageView.getContext(), R.drawable.ic_done_black_24dp, buttonForegroundColorCompliment));
            Log.d("primoif", "1 " + buttonForegroundColorCompliment);
        }
        else
        {
            if (giorno.get(position).isCompletedFlag())
            {
                imageView.setColorFilter(buttonForegroundColorCompliment);
//                imageView.setForeground(CustomDrawable.getTintedDrawable(imageView.getContext(), R.drawable.ic_done_black_24dp, buttonForegroundColorCompliment));
                Log.d("secondoif", "2 " + buttonForegroundColorCompliment);
            }
            else
            {
                imageView.setColorFilter(buttonColorInt);
//                imageView.setForeground(CustomDrawable.getTintedDrawable(imageView.getContext(), R.drawable.ic_done_black_24dp, buttonColorInt));
                Log.d("terzoif", "3 " + buttonColorInt);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(giorno.get(position).isCompletedFlag()) {
                    SchermataIniziale.calendario.get(SchermataIniziale.calendario.indexOf(giorno.get(position))).setCompletedFlag(false);
                }else{
                    SchermataIniziale.calendario.get(SchermataIniziale.calendario.indexOf(giorno.get(position))).setCompletedFlag(true);
                }
                //Notifica alla listview che i dati sono stati modificati e quindi ordina alla stessa di aggiornarsi.
                notifyDataSetChanged();
            }
        });

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,textNomeEvento.getText(),Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }
}
