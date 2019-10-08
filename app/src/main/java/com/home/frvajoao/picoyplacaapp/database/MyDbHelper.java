package com.home.frvajoao.picoyplacaapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.model.BitacoraContract;
import com.home.frvajoao.picoyplacaapp.model.Horario;
import com.home.frvajoao.picoyplacaapp.model.HorarioContract;
import com.home.frvajoao.picoyplacaapp.model.MultaContract;
import com.home.frvajoao.picoyplacaapp.util.Util;

import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Util.CREAR_TABLA_HORARIO);

        List<Horario> horarios =Common.getHorarioDefault();

        for (Horario horarioSel: horarios ) {
            System.out.println("***Horario a GUARDAR: "+ horarioSel.getId()+" - "+horarioSel.getDiaSemana()+" - "+horarioSel.getPlacaIni()+" - "+horarioSel.getPlacaFin());
            db.execSQL(Util.INSERT_TABLA_HORARIO(horarioSel));
        }

        db.execSQL(Util.CREAR_TABLA_BITACORA);
        db.execSQL(Util.CREAR_TABLA_MULTA);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ HorarioContract.HorarioEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ BitacoraContract.BitacoraEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MultaContract.MultaEntry.TABLE_NAME);
        onCreate(db);
    }
}
