package com.home.frvajoao.picoyplacaapp.listarBitacora;

import android.content.Context;

import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.DiaSemana;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class CargarBitacoraPresenter implements ICargarBitacoraContract.Presenter, ICargarBitacoraContract.CompleteListener {

    ICargarBitacoraContract.View view;
    ICargarBitacoraContract.InteractorBase interactor;

    public CargarBitacoraPresenter(ICargarBitacoraContract.View signupView) {
        this.view = signupView;
        this.interactor = new CargarBitacoraInteractor(this);
    }


    @Override
    public void buscarBitacoraList(Context context) {

        interactor.buscarBitacora(context);

    }

    @Override
    public void showMessageFailed(String message) {

    }

    @Override
    public void saveSuccessfully() {

    }

    @Override
    public void getBitacoraList(List<Bitacora> resultado) {
        view.showBitacoraList(resultado);
    }
}
