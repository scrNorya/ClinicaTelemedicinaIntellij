package org.example;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.example.model.Recepcionista;
import org.example.model.Medico;

public class EmailController {
    Session session;
    public EmailController() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.ssl.protocols","TLSv1.2");
        session = Session.getDefaultInstance(props);
    }

    public void sendRecepcionistaConfirmation(Recepcionista recepcionista) {
        String email = "disclinicadisc@gmail.com";
        String senha = "Aps21dahora";
        String destinationEmail = recepcionista.getEmail();
        String subject = "Confirmação de cadastro";
        String msg = "Olá "+ recepcionista.getNome()+ "! Seu cadastro foi confirmado! Sua senha é: "+ recepcionista.getSenha();
        MimeMessage mail = new MimeMessage(this.session);

        try {
            InternetAddress destinyEmail =  new InternetAddress(destinationEmail);
            destinyEmail.validate();

            mail.setFrom(new InternetAddress(email));
            mail.addRecipient(Message.RecipientType.TO,destinyEmail);
            mail.setSubject(subject);
            mail.setText(msg);

            Transport transport = session.getTransport("smtp");
            transport.connect(email,senha);
            transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void sendMedicoConfirmation(Medico medico) {
        String email = "disclinicadisc@gmail.com";
        String senha = "Aps21dahora";
        String destinationEmail = medico.getEmail();
        String subject = "Confirmação de cadastro";
        String msg = "Olá "+ medico.getNome()+ "! Seu cadastro foi confirmado! Sua senha é: "+ medico.getSenha();
        MimeMessage mail = new MimeMessage(this.session);

        try {
            InternetAddress destinyEmail =  new InternetAddress(destinationEmail);
            destinyEmail.validate();

            mail.setFrom(new InternetAddress(email));
            mail.addRecipient(Message.RecipientType.TO,destinyEmail);
            mail.setSubject(subject);
            mail.setText(msg);

            Transport transport = session.getTransport("smtp");
            transport.connect(email,senha);
            transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
