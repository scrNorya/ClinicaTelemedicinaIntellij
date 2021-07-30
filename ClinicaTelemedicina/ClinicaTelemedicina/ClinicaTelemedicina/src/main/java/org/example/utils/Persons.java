package org.example.utils;

import org.example.LoginController;

import java.io.File;
import java.net.URISyntaxException;

public enum Persons {
    Medico,
    Recepcionista;

    public String getPath() throws URISyntaxException {
        if(Verifications.isRunningFromJar()) {
            File ref = new File(Persons.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return ref.getParentFile().toURI().getPath() + "Dados/" + this + ".json";
        } else {
            return LoginController.class.getResource(this + ".json").getPath();
        }
    }
}
