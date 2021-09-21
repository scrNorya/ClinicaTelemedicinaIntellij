package org.example;

import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.example.model.Funcionario;
import org.example.model.Paciente;
import org.example.utils.JsonType;

public class EmailController {
    Session session;
    String email;
    String senha;
    public EmailController() {
        this.email = "disclinicadisc@gmail.com";
        this.senha  = "Aps21dahora";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.ssl.protocols","TLSv1.2");
        session = Session.getDefaultInstance(props);

    }

    public void sendConfirmation(Funcionario funcionario, JsonType personType) {

        String destinationEmail = funcionario.getEmail();
        String subject = "Confirmação de cadastro";
        String msg = "Olá " + funcionario.getNome() + "!\nSeu cadastro de CPF " + funcionario.getCpf()
                + " e perfil " + personType +" foi confirmado!\nSua senha é: "+
                funcionario.getSenha();
        MimeMessage mail = new MimeMessage(this.session);

        try {
            InternetAddress destinyEmail =  new InternetAddress(destinationEmail);
            destinyEmail.validate();

            mail.setFrom(new InternetAddress(this.email));
            mail.addRecipient(Message.RecipientType.TO,destinyEmail);
            mail.setSubject(subject);
            mail.setText(msg);


            Transport transport = session.getTransport("smtp");
            transport.connect(this.email,this.senha);
            transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public boolean sendAtestado(Paciente paciente) {
        try{
            String destinationEmail = paciente.getEmail();
            String subject = "Atestado Médico";
            String msg = "Olá "+ paciente.getNome() + ", segue em anexo o atestado médico de sua consulta.";
            InternetAddress destinyEmail = new InternetAddress(destinationEmail);
            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(email));
            mail.addRecipient(Message.RecipientType.TO,destinyEmail);
            mail.setSubject(subject);
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText(msg);
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile("AtestadoMedico.pdf");
            emailContent.addBodyPart(textBody);
            emailContent.addBodyPart(pdfAttachment);
            mail.setContent(emailContent);
            Transport transport = session.getTransport("smtp");
            transport.connect(email,senha);
            transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transport.close();
            return true;
        }catch (Exception e) {
            return false;
        }

    }
}
