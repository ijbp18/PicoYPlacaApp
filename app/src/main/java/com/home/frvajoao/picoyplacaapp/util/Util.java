package com.home.frvajoao.picoyplacaapp.util;


import com.home.frvajoao.picoyplacaapp.model.BitacoraContract;
import com.home.frvajoao.picoyplacaapp.model.Horario;
import com.home.frvajoao.picoyplacaapp.model.HorarioContract;
import com.home.frvajoao.picoyplacaapp.model.MultaContract;

public class Util {

    public static final String CREAR_TABLA_HORARIO = "CREATE TABLE "+ HorarioContract.HorarioEntry.TABLE_NAME +"("+HorarioContract.HorarioEntry.COLUMN_ID+" INTEGER, " +
                HorarioContract.HorarioEntry.COLUMN_DESCRIPCION+" TEXT, "+HorarioContract.HorarioEntry.COLUMN_PLACA_INI+" TEXT, "+HorarioContract.HorarioEntry.COLUMN_PLACA_FIN+" TEXT)";

    public static final String CREAR_TABLA_BITACORA = "CREATE TABLE "+ BitacoraContract.BitacoraEntry.TABLE_NAME +"("+BitacoraContract.BitacoraEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BitacoraContract.BitacoraEntry.COLUMN_FECHA_REGISTRO+" TEXT, "+BitacoraContract.BitacoraEntry.COLUMN_PLACA+" TEXT, "+BitacoraContract.BitacoraEntry.COLUMN_FECHA_CONSULTA+" TEXT, " +
            BitacoraContract.BitacoraEntry.COLUMN_INFRACCION+" BIT)";

    public static final String CREAR_TABLA_MULTA = "CREATE TABLE "+ MultaContract.MultaEntry.TABLE_NAME +"("+MultaContract.MultaEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MultaContract.MultaEntry.COLUMN_FECHA_REGISTRO+" TEXT, "+MultaContract.MultaEntry.COLUMN_PLACA+" TEXT)";



    public static String INSERT_TABLA_HORARIO(Horario horario) {

       return "INSERT INTO "+ HorarioContract.HorarioEntry.TABLE_NAME  +
                " VALUES("+horario.getId()+", '" +horario.getDiaSemana()+"', "+horario.getPlacaIni()+", "+horario.getPlacaFin()+")";

    }


}
