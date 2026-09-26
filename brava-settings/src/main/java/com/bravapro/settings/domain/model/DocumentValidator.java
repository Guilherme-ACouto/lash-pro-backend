package com.bravapro.settings.domain.model;

/** Validação de CPF/CNPJ pelos dígitos verificadores (o front valida igual, pra resposta imediata). */
public final class DocumentValidator {

    private DocumentValidator() {}

    public static String digitsOnly(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }

    public static boolean isValid(DocumentType type, String digits) {
        if (type == null || digits == null) {
            return false;
        }
        return type == DocumentType.CPF ? isValidCpf(digits) : isValidCnpj(digits);
    }

    static boolean isValidCpf(String cpf) {
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
            return false;
        }
        return checkDigit(cpf, 9, 10) == cpf.charAt(9) - '0' && checkDigit(cpf, 10, 11) == cpf.charAt(10) - '0';
    }

    private static int checkDigit(String cpf, int length, int startWeight) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * (startWeight - i);
        }
        int rest = (sum * 10) % 11;
        return rest == 10 ? 0 : rest;
    }

    static boolean isValidCnpj(String cnpj) {
        if (cnpj.length() != 14 || cnpj.chars().distinct().count() == 1) {
            return false;
        }
        int[] firstWeights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] secondWeights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        return cnpjDigit(cnpj, firstWeights) == cnpj.charAt(12) - '0'
                && cnpjDigit(cnpj, secondWeights) == cnpj.charAt(13) - '0';
    }

    private static int cnpjDigit(String cnpj, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (cnpj.charAt(i) - '0') * weights[i];
        }
        int rest = sum % 11;
        return rest < 2 ? 0 : 11 - rest;
    }
}
