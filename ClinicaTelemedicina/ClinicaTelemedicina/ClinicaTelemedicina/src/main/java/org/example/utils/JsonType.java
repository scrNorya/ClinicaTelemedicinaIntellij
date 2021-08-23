package org.example.utils;

import org.example.LoginController;

import java.io.File;
import java.net.URISyntaxException;

public enum JsonType {
    Medico,
    Recepcionista,
    Paciente,
    Consulta;

    public String getPath() throws URISyntaxException {
        if(Verifications.isRunningFromJar()) {
            File ref = new File(JsonType.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return ref.getParentFile().toURI().getPath() + "Dados/" + this + ".json";
        } else {
            return LoginController.class.getResource(this + ".json").getPath();
        }
    }
}
