package namenotfoundunica.houseworkcalendar.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import namenotfoundunica.houseworkcalendar.activity.AggiuntaEvento;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(YEAR);
        int month = c.get(MONTH);
        int day = c.get(DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        // Do something with the date chosen by the user
        AggiuntaEvento c = (AggiuntaEvento) getActivity();
        GregorianCalendar newDate=new GregorianCalendar(year,month,day,0,0);
        Calendar compare;
        compare=Calendar.getInstance();

        if((newDate.get(DAY_OF_MONTH)<compare.get(DAY_OF_MONTH))&&(newDate.get(MONTH)<= compare.get(MONTH))&&(newDate.get(YEAR)<=compare.get(YEAR)))
        {
            c.makeToast("Non credo che tu possa viaggiare nel tempo");
            c.dataEventoText.setBackgroundColor(Color.parseColor("#FF3333"));
        }
        else
        {
            c.dataEventoText.setBackgroundColor(Color.parseColor("#5A595B"));
            c.dataInizio = newDate;
            c.dataFine = newDate;
            c.dataEventoText.setText(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
        }
    }
}