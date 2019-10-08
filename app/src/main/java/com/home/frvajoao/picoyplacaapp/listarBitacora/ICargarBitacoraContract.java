package com.home.frvajoao.picoyplacaapp.listarBitacora;


import android.content.Context;

import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.DiaSemana;

import java.util.List;

public interface ICargarBitacoraContract {

    interface View{

        void showBitacoraList(List<Bitacora> resultado);
    }

    interface Presenter{


        void buscarBitacoraList(Context context);
    }

    interface InteractorBase{



        void buscarBitacora(Context context);
    }

    interface CompleteListener {

        void showMessageFailed(String message);

        void saveSuccessfully();


        void getBitacoraList(List<Bitacora> resultado);
    }
}
