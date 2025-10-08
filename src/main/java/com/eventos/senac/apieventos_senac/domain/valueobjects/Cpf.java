package com.eventos.senac.apieventos_senac.domain.valueobjects;

public record Cpf(String cpf) {

    public Cpf {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");

        if (!validarCpf(cpfLimpo)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        cpf = cpfLimpo;
    }

    public String getNumero() {
        return cpf;
    }
    

    public static boolean validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Calcula primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int d1 = sum % 11;
        d1 = d1 < 2 ? 0 : 11 - d1;

        // Calcula segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int d2 = sum % 11;
        d2 = d2 < 2 ? 0 : 11 - d2;

        return d1 == (cpf.charAt(9) - '0') && d2 == (cpf.charAt(10) - '0');
    }

    @Override
    public String toString() {
        return cpf;
    }
}
