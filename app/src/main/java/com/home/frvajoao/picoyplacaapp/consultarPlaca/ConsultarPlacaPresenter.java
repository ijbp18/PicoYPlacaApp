package com.home.frvajoao.picoyplacaapp.consultarPlaca;

import android.content.Context;

import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.DiaSemana;

import java.util.Calendar;
import java.util.regex.Pattern;

public class ConsultarPlacaPresenter implements IConsultarPlacaContract.Presenter, IConsultarPlacaContract.CompleteListener {

    IConsultarPlacaContract.View view;
    IConsultarPlacaContract.InteractorBase interactor;

    public ConsultarPlacaPresenter(IConsultarPlacaContract.View signupView) {
        this.view = signupView;
        this.interactor = new ConsultarPlacaInteractor(this);
    }

    @Override
    public boolean isValidData(String placa) {

        String textPlaca = "";
        if(!placa.equals("") && placa.length() > 3)
            textPlaca = placa.substring(0, 3);

        if (placa.isEmpty()) {
            view.onErrorInputType("Ingrese la placa a verificar");
        } else if (placa.length() <= 4) {
            view.onErrorInputType("Es necesario un mínimo de 5 caracteres");
        } else if (!Pattern.matches(".*[0-9].*", placa)) {
            view.onErrorInputType("Es necesario que ingrese valores numéricos");
        } else if (!Pattern.matches("[a-zA-Z]+", textPlaca)) {
            view.onErrorInputType("Es necesario que los 3 primeros caracteres sean letras");
        } else {

            view.onSuccessfullyInputText();



        }

        return false;
    }

    @Override
    public void attemptSignUp(String placa) {

    }

    @Override
    public void validarFechaPP(String placaSel, DiaSemana diaSel, String fechaConsulta, Context context) {

        if (diaSel == null) {
            final Calendar c = Calendar.getInstance();
            diaSel = new DiaSemana(String.valueOf(c.get(Calendar.DAY_OF_WEEK)), Common.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK)));

        }

        interactor.validarFechaPicoYPlaca(diaSel.getId(), Common.getUltimoNumeroPlaca(placaSel), context);


    }



    @Override
    public void validarHoraPP(int timeSel, String placaSel) {

        if (Common.validarRestriccionHora(timeSel)) {
            interactor.saveRegistroSancion(placaSel);

        } else {

            view.displayPlacaNotInHorarioPP();
            view.showMessageDialog( "¡ATENCIÓN A LA HORA! ", new StringBuilder().append("El vehículo con la placa ").append(placaSel).append(", se encuentra en su día de pico y placa, se recomienda estar atento y NO conducir el vehículo en el horario prohibido."), false);

        }

    }

    @Override
    public void saveRegBitacora(Bitacora regBitacora) {

        interactor.guardarRegConsultaBitacora(regBitacora);

    }


    @Override
    public void showMessageFailed(String message) {
        view.onErrorInputType(message);

    }

    @Override
    public void saveSuccessfully() {

    }

    @Override
    public void validateSuccessfully(boolean isDayPP) {
        view.validateFechaPP(isDayPP);
    }

    @Override
    public void mostrarNumeroReincidencia(int totalReincidencia) {

        view.displayNumeroReincidencia(totalReincidencia);

    }

    @Override
    public void saveSuccessfullySancion(String placaSel) {
        view.showMessageDialog( "¡CUIDADO! REQUIERE SANCIÓN...", new StringBuilder().append("El vehículo con la placa ").append(placaSel).append(", se encuentra en su día de pico y placa y dentro del horario prohibido."), true);
        view.displaySaveSuccessFullySancion();
//        interactor.guardarRegConsultaBitacora(true);

    }

    @Override
    public void saveSuccessfullyBitacora() {
        view.displaySaveSuccessFullyBitacora();
    }
}
