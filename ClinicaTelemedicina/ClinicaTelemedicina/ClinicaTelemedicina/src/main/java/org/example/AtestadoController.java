package org.example;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.model.Medico;
import org.example.model.Paciente;
import org.example.utils.JsonType;
import org.example.utils.JsonUtils;
import org.example.utils.ValidationUtils;
import org.example.utils.ViewUtils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;


public class AtestadoController {
    @FXML private TextField cpfPaciente;
    @FXML private TextField cidText;
    @FXML private TextField duracaoText;
    @FXML private TextArea descricaoTextArea;

    private Medico medicoLogado;
    private Paciente pacienteSelecionado;
    private LocalDate date;
    private LocalDate finalDate;

    private EmailController emailController = new EmailController();

    public void sendPDFAtestado() throws Exception {
        try{
            medicoLogado = ApplicationContext.getInstance().getMedicoLogado();
            pacienteSelecionado = (Paciente) JsonUtils.findByCPF(cpfPaciente.getText(), JsonType.Paciente);
            if(ValidationUtils.isValidDuracao(this.duracaoText.getText())&&ValidationUtils.isValidCid(this.cidText.getText())){
                date = LocalDate.now();
                finalDate = date.plusDays(Long.parseLong(this.duracaoText.getText()));
                this.writePDFAtestado();

                if(ViewUtils.generateConfirmationDialog("Deseja enviar o atestado gerado?")){
                    if(emailController.sendAtestado(this.pacienteSelecionado)){
                        ViewUtils.generateAlert("Atestado enviado com sucesso!");
                    }else{
                        ViewUtils.generateAlert("Falha ao enviar atestado!");
                    }
                }
            }

        }catch(Exception e){
            ViewUtils.generateAlert(e.getMessage());
        }

    }

    public void writePDFAtestado() throws IOException {

        Document documentPDF = new Document();

        PdfWriter.getInstance(documentPDF, new FileOutputStream("AtestadoMedico.pdf"));

        documentPDF.open();
        this.generateTitle(documentPDF);
        this.generateBody(documentPDF);
        this.generateSignature(documentPDF);
        documentPDF.close();

    }

    private void generateSignature(Document documentPDF) {
        Paragraph signature = new Paragraph();
        signature.setAlignment(Element.ALIGN_JUSTIFIED);
        signature.add(new Chunk(this.medicoLogado.getNome()+"\n", new Font(Font.TIMES_ROMAN, 12)));
        documentPDF.add(new Paragraph(" "));
        signature.add(new Chunk(this.medicoLogado.getCrm()+"\n", new Font(Font.TIMES_ROMAN, 12)));
        documentPDF.add(new Paragraph(" "));
        signature.add(new Chunk(date.toString()+"\n", new Font(Font.TIMES_ROMAN, 12)));
        documentPDF.add(signature);

    }

    private void generateBody(Document documentPDF) {
        Paragraph body = new Paragraph();
        body.setAlignment(Element.ALIGN_JUSTIFIED);
        body.add(new Chunk("Atesto para os devidos fins que "+ this.pacienteSelecionado.getNome()+", CPF nº "+ this.pacienteSelecionado.getCpf()+", estará ausente do trabalho por motivo de doença do dia "+ this.dataFormatter(this.date
                .toString())+" ao dia"+ this.dataFormatter(this.finalDate.toString()) , new Font(Font.TIMES_ROMAN, 12)));
        documentPDF.add(body);
        documentPDF.add(new Paragraph(" "));
    }

    public void generateTitle(Document documentPDF){
        Paragraph titulo = new Paragraph();
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.add(new Chunk("Atestado Médico", new Font(Font.HELVETICA, 24)));
        documentPDF.add(titulo);
        documentPDF.add(new Paragraph(" "));

    }

    public void goBack() throws IOException {
        App.setRoot("Agenda");
        ApplicationContext.getInstance().getMedicoLogado();
    }

    private String dataFormatter(String data) {
        String valueData = data.split("\n")[1];
        String[] dataArray = valueData.split("/");
        return dataArray[2] + "-" + dataArray[1] + "-" + dataArray[0];
    }

}
