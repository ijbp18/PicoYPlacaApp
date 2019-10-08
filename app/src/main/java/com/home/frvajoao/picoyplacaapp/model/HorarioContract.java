package com.home.frvajoao.picoyplacaapp.model;

import android.provider.BaseColumns;

public class HorarioContract {

    private HorarioContract() {}

    public static abstract class HorarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "horario";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_PLACA_INI = "placaIni";
        public static final String COLUMN_PLACA_FIN = "placaFin";
    }
}
