package com.home.frvajoao.picoyplacaapp.consultarPlaca;


import android.content.Context;

import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.DiaSemana;

public interface IConsultarPlacaContract {

    interface View{

        void onErrorInputType(String message);

        void onSuccessfullyInputText();

        void validateFechaPP(boolean isDayPP);

        void displayNumeroReincidencia(int totalReincidencia);

        void showMessageDialog(String title, StringBuilder message, boolean isSancion);

        void displaySaveSuccessFullySancion();

        void displaySaveSuccessFullyBitacora();

        void displayPlacaNotInHorarioPP();
    }

    interface Presenter{

        //valida posibles datos erroneos de entrada
        boolean isValidData(String placa);

        //Enviamos los datos al Interactor
        void attemptSignUp(String placa);

        void validarFechaPP(String placalSel, DiaSemana diaSel, String fechaConsulta, Context context);

        void validarHoraPP(int timeSel, String placalSel);

        void saveRegBitacora( Bitacora regBitacora);
    }

    interface InteractorBase{

        void realizarRegistroPlaca(String placa);

        void saveRegPlacaSuccessfully();

        void validarFechaPicoYPlaca(String id, String ultimoNumeroPlaca, Context context);

        void saveRegistroSancion(String placaSel);

        void guardarRegConsultaBitacora(Bitacora conInfraccion);
    }

    interface CompleteListener {

        void showMessageFailed(String message);
        void saveSuccessfully();

        void validateSuccessfully(boolean isDayPP);

        void mostrarNumeroReincidencia(int totalReincidencia);

        void saveSuccessfullySancion(String placalSel);

        void saveSuccessfullyBitacora();
    }
}
