package org.example.utils;

import java.util.InputMismatchException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class FieldsValidator {

	public FieldsValidator() {

	}

	public boolean isValidEmail(String email) throws Exception {
		if (email != null) {
			try {
				InternetAddress destinyEmail = new InternetAddress(email);
				destinyEmail.validate();
				
				return true;
				
			} catch (AddressException e) {
				throw new Exception("Email inv�lido!");
			}
		}
		
		return false;

	}

	public boolean isValidName(String name) throws Exception {
		if (name != null) {

			if (name.length() > 1) {
				return true;
			}
			
		}
		throw new Exception("Nome inv�lido!");
	}
	
	public boolean isEnderecoValid (String endereco) throws Exception {
		if(!endereco.isEmpty() && endereco != null) {
			return true;
		}
		throw new Exception("Endere�o inv�lido!");
	}
	
	public boolean isTelefoneValid (String telefone) throws Exception {
		if(!telefone.isEmpty() && telefone != null) {
			return true;
		}
		throw new Exception("Telefone inv�lido!");
	}

	public boolean isCpf(String CPF) throws Exception {
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			throw new Exception("CPF inv�lido!");

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {

			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				throw new Exception("CPF inv�lido!");
		} catch (InputMismatchException erro) {
			throw new Exception("CPF inv�lido!");
		}
	}
	
	
}
