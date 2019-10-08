package com.home.frvajoao.picoyplacaapp.model;

import android.provider.BaseColumns;

public class BitacoraContract {

    private BitacoraContract() {}

    public static abstract class BitacoraEntry implements BaseColumns {
        public static final String TABLE_NAME = "regBitacora";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FECHA_REGISTRO = "fechaRegistro";
        public static final String COLUMN_PLACA = "placa";
        public static final String COLUMN_FECHA_CONSULTA = "fechaConsulta";
        public static final String COLUMN_INFRACCION= "infraccion";
    }
}
