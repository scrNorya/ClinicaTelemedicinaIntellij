package org.example.utils;

import java.util.InputMismatchException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validations {

	public static boolean isValidEmail(String email) throws Exception {
		if (email != null) {
			try {
				InternetAddress destinyEmail = new InternetAddress(email);
				destinyEmail.validate();

				return true;

			} catch (AddressException e) {
				throw new Exception("Email invalido");
			}
		}

		return false;

	}

	public static boolean isValidName(String name) throws Exception {
		if (name != null) {

			if (name.length() > 1) {
				return true;
			}

		}
		throw new Exception("Nome invalido");
	}

	public static boolean isValidPassword(String senha) throws Exception {
		if(senha != null) {
			if(senha.length() >= 1) {
				return true;
			}
		}
		throw new Exception("Senha Invalida");
	}

	public static boolean isEnderecoValid (String endereco) throws Exception {
		if(!endereco.isEmpty() && endereco != null) {
			return true;
		}
		throw new Exception("Endereco invalido");
	}

	public static boolean isTelefoneValid (String telefone) throws Exception {
		if(!telefone.isEmpty() && telefone != null) {
			return true;
		}
		throw new Exception("Telefone invalido");
	}

	public static boolean isCpf(String CPF) throws Exception {
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			throw new Exception("CPF invalido");

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
				throw new Exception("CPF invalido");
		} catch (InputMismatchException erro) {
			throw new Exception("CPF invalido");
		}
	}

	public static boolean isCRMValid (String CRM) throws Exception {
		if(CRM != null && CRM.length() == 5) {
			return true;
		}
		throw new Exception("CRM invalido");
	}

}
