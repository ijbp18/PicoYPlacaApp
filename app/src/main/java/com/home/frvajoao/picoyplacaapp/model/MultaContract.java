package com.home.frvajoao.picoyplacaapp.model;

import android.provider.BaseColumns;

public class MultaContract {

    private MultaContract() {}

    public static abstract class MultaEntry implements BaseColumns {
        public static final String TABLE_NAME = "regMulta";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FECHA_REGISTRO = "fechaRegistro";
        public static final String COLUMN_PLACA = "placa";
    }
}
