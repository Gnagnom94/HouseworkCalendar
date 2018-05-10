package Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import namenotfoundunica.houseworkcalendar.AggiuntaEvento;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        // Do something with the date chosen by the user
        AggiuntaEvento c = (AggiuntaEvento) getActivity();
        c.dataInizio=new GregorianCalendar(year,month,day,0,0);
        c.dataFine=new GregorianCalendar(year,month,day,0,0);
        c.dataEventoText.setText(Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year));
    }
}