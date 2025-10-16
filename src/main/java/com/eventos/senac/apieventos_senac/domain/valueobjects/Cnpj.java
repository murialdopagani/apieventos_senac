package com.eventos.senac.apieventos_senac.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;


//@Data
public class CNPJ {

    private final String cnpj;

    public CNPJ() {
        this.cnpj = "";
    }

    @JsonValue
    public String getNumero() {
        return cnpj;
    }

    public CNPJ (String cnpj){
        if (cnpj == null) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo");
        }

        String cnpjLimpo = cnpj.replaceAll("\\D", "");

        if (!validarCnpj(cnpjLimpo)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.cnpj = cnpjLimpo;
    }

    public boolean validarCnpj(String cnpj) {
        try {
            if (cnpj == null || cnpj.length() != 14) {
                return false;
            }

            // Verifica se todos os dígitos são iguais
            if (cnpj.chars().distinct().count() == 1) {
                return false;
            }

            // Calcula primeiro dígito verificador
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += (cnpj.charAt(i) - '0') * peso1[i];
            }
            int d1 = sum % 11;
            d1 = d1 < 2 ? 0 : 11 - d1;

            // Calcula segundo dígito verificador
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += (cnpj.charAt(i) - '0') * peso2[i];
            }
            int d2 = sum % 11;
            d2 = d2 < 2 ? 0 : 11 - d2;

            return d1 == (cnpj.charAt(12) - '0') && d2 == (cnpj.charAt(13) - '0');
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    public String toString() {
        return cnpj;
    }


    public String getCnpj() {
        return cnpj;
    }
}
