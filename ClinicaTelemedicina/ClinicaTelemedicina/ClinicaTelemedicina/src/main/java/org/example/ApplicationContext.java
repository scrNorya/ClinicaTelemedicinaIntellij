package org.example;

import org.example.model.Medico;
import org.example.model.Recepcionista;

public class ApplicationContext {

    private Medico medicoLogado;
    private Recepcionista recepcionistaLogado;
    private static ApplicationContext instance;


    private ApplicationContext(){

    }

    public static synchronized ApplicationContext getInstance(){
        if(instance==null) {
            instance = new ApplicationContext();
        }

        return instance;

    }


    public Medico getMedicoLogado() {
        return medicoLogado;
    }

    public void setMedicoLogado(Medico medicoLogado) {
        this.medicoLogado = medicoLogado;
    }

    public Recepcionista getRecepcionistaLogado() {
        return recepcionistaLogado;
    }

    public void setRecepcionistaLogado(Recepcionista recepcionistaLogado) {
        this.recepcionistaLogado = recepcionistaLogado;
    }
}
