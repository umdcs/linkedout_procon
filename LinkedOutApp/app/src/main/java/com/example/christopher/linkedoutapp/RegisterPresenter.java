package com.example.christopher.linkedoutapp;

/**
 * Created by imcdo on 3/15/2017.
 */

public class RegisterPresenter {

    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;

    public RegisterPresenter(ModelViewPresenterComponents.View mpgView){
        mView = mpgView;
        mModel = new MPGCalc(this);
    }


    void clickRegister(){
        
    }

}
