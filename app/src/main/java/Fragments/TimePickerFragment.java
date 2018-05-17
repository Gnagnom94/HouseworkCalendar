package Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import namenotfoundunica.houseworkcalendar.AggiuntaEvento;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener
{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        // Do something with the time chosen by the user
        AggiuntaEvento c = (AggiuntaEvento) getActivity();
        String ora;
        String min;
        if(hourOfDay<=9)
            ora="0"+Integer.toString(hourOfDay);
        else
            ora=Integer.toString(hourOfDay);
        if(minute<=9)
            min="0"+Integer.toString(minute);
        else
            min=Integer.toString(minute);
        if(c.flagTime==true)
        {

            c.dataFine.set(Calendar.MINUTE,minute);
            c.dataFine.set(Calendar.HOUR,hourOfDay);
            c.timeInizioEvento.setText(ora+ ":" +min);
            c.timeFineEvento.setText(ora+ ":" +min);
            if(c.dataFine.compareTo(c.dataInizio)<=0)
            {
                c.dataFine.set(Calendar.MINUTE,minute);
                c.dataFine.set(Calendar.HOUR,hourOfDay);
                c.timeFineEvento.setText(ora+ ":" +min);
            }
        }
        else
        {

            c.dataFine.set(Calendar.MINUTE, minute);
            c.dataFine.set(Calendar.HOUR, hourOfDay);
            if(c.dataFine.compareTo(c.dataInizio)>0)
            {
                c.dataInizio.set(Calendar.MINUTE,minute);
                c.dataInizio.set(Calendar.HOUR,hourOfDay);
                c.timeInizioEvento.setText(ora+ ":" +min);
            }
            c.timeFineEvento.setText(ora+ ":" +min);
        }
    }
}