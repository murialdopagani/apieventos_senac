package com.eventos.senac.apieventos_senac.model.valueobjects;

public record Cpf(String cpf) {

    public Cpf() {
        this("");
    }

    public Cpf(String cpf, Long id) {
           this.cpf = cpf;
    }

    public Cpf(String cpf) {
        if(cpf == null || !validadorDeCpf(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }

    public static boolean validadorDeCpf(String cpf) {
        if (cpf == null)
            return false;

        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11)
            return false;
        if (cpf.chars().distinct().count() == 1)
            return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int d1 = sum % 11;
        d1 = d1 < 2 ? 0 : 11 - d1;

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
