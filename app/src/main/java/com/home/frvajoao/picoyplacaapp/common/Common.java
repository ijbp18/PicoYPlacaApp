package com.home.frvajoao.picoyplacaapp.common;

import com.home.frvajoao.picoyplacaapp.model.Horario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Common {

    public static final String DB_NAME = "PicoYPlacaDB";
    public static final int HORA_PICO_INICIO_MANAMA = 700 ;
    public static final int HORA_PICO_FIN_MANAMA = 930 ;
    public static final int HORA_PICO_INICIO_TARDE = 1600;
    public static final int HORA_PICO_FIN_TARDE = 1930;


    public static String getDayOfWeek(int value) {

        String day = "";
        switch(value){
            case Calendar.SUNDAY:
                day="Domingo";
                break;
            case Calendar.MONDAY:
                day="Lunes";
                break;
            case Calendar.TUESDAY:
                day="Martes";
                break;
            case Calendar.WEDNESDAY:
                day="Miércoles";
                break;
            case Calendar.THURSDAY:
                day="Jueves";
                break;
            case Calendar.FRIDAY:
                day="Viernes";
                break;
            case Calendar.SATURDAY:
                day="Sábado";
                break;
        }
        return day;
    }

    public static String getMonthString(int value) {

        String month = "";
        switch(value){
            case 1:
                month="enero";
                break;
            case 2:
                month="febrero";
                break;
            case 3:
                month="marzo";
                break;
            case 4:
                month="abril";
                break;
            case 5:
                month="mayo";
                break;
            case 6:
                month="junio";
                break;
            case 7:
                month="julio";
                break;
            case 8:
                month="agosto";
                break;
            case 9:
                month="septiembre";
                break;
            case 10:
                month="octubre";
                break;
            case 11:
                month="noviembre";
                break;
            case 12:
                month="diciembre";
                break;
        }
        return month;
    }

    public static List<Horario> getHorarioDefault() {

        //Creamos un array por default para cargar el horario establecido

        List<Horario> horarioList = new ArrayList<>();
        horarioList.add(new Horario(1, "Domingo", "-1", "-1"));
        horarioList.add(new Horario(2, "Lunes", "1", "2"));
        horarioList.add(new Horario(3, "Martes", "3", "4"));
        horarioList.add(new Horario(4, "Miércoles", "5", "6"));
        horarioList.add(new Horario(5, "Jueves", "7", "8"));
        horarioList.add(new Horario(6, "Viernes", "9", "0"));
        horarioList.add(new Horario(7, "Sábado", "-1", "-1"));

        return horarioList;
    }

    public static boolean validarRestriccionHora(int horaActual) {
        System.out.println("*** validarRestriccionHora horaSel--> "+horaActual);

        if(horaActual >= HORA_PICO_INICIO_MANAMA && horaActual <= HORA_PICO_FIN_MANAMA ) {
            System.out.println("*** horaSel: "+horaActual+" esta entre "+HORA_PICO_INICIO_MANAMA+" Y "+HORA_PICO_FIN_MANAMA);
            return true;
        }else if(horaActual >= HORA_PICO_INICIO_TARDE && horaActual <= HORA_PICO_FIN_TARDE) {
            System.out.println("*** horaSel: "+horaActual+" esta entre "+HORA_PICO_INICIO_TARDE+" Y "+HORA_PICO_FIN_TARDE);
            return true;
        }else{
            System.out.println("*** horaSel: "+horaActual+" esta libre de horario");
            return false;
        }

    }

    public static String getFechaActual(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getAMPM(int hourOfDay) {

        String form;
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
            form = "PM";
        } else {
            if (hourOfDay == 0) {
                hourOfDay = 12;
            }
            form = "AM";
        }

        return form;
    }

    public static String getMinuteFormat(int minute) {

        String form = "";
        if (minute < 10) form = "0"+minute;
        else
            form = String.valueOf(minute);


        return form;
    }

    public static String getDayFormat(int day) {

        String form = "";
        if (day < 10) form = "0"+day;
        else
            form = String.valueOf(day);


        return form;
    }

    public static String getUltimoNumeroPlaca(String placaSel) {

        String numberOnly = placaSel.replaceAll("[^0-9]", "");
        String ultimoNumero = numberOnly.substring(numberOnly.length()-1);

        return ultimoNumero;
    }

    public static boolean validarCaracteresText(String cadena) {

        return ((!cadena.equals(""))
                && (cadena != null)
                && (cadena.matches("[A-Z]*$")));
    }
}
