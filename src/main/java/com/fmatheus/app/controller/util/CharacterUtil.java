package com.fmatheus.app.controller.util;


import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterUtil extends CapitalizeUtil {

    private CharacterUtil() {
        super();
    }

    /**
     * Converte o primeiro caracter de cada palavra em maiusculo.
     *
     * @param value String
     * @return String
     * @author Fernando Matheus
     */
    public static String convertFirstUppercaseCharacter(String value) {
        return Objects.nonNull(value) ? capitalizeFully(removeDuplicateSpace(value)).trim() : null;
    }

    /**
     * Converter todos caracteres de uma string em maiusculo.
     *
     * @param value String
     * @return String
     * @author Fernando Matheus
     */
    public static String convertAllUppercaseCharacters(String value) {
        return Objects.nonNull(value) ? value.toUpperCase().trim() : null;
    }

    /**
     * Converter todos caracteres de uma string em minusculo.
     *
     * @param value String
     * @return String
     * @author Fernando Matheus
     */
    public static String convertAllLowercaseCharacters(String value) {
        return Objects.nonNull(value) ? value.toLowerCase().trim() : null;
    }

    /**
     * Remove todos os espacos de uma string.
     *
     * @param value String
     * @return String
     * @author Fernando Matheus
     */
    public static String removeAllSpaces(String value) {
        return Objects.nonNull(value) ? value.replace(" ", "") : null;
    }

    /**
     * Remove duplicidade de espaco em uma string.
     *
     * @param value String
     * @return String
     * @author Fernando Matheus
     */
    public static String removeDuplicateSpace(String value) {
        return Objects.nonNull(value) ? value.replaceAll("\\s+", " ").trim() : null;
    }


    public static String removeSpecialCharacters(String value) {
        return Objects.nonNull(value) ? value.replaceAll("[^a-zA-Z0-9]", "") : null;
    }

    /**
     * Cria uma mascara para uma string.
     *
     * @param valor   Valor a ser convertido
     * @param mascara Mascara
     * @return String
     * @author Fernando Matheus
     */
    public static String formatMask(String valor, String mascara) {

        StringBuilder dado = new StringBuilder();
        for (var i = 0; i < valor.length(); i++) {
            var c = valor.charAt(i);
            if (Character.isDigit(c)) {
                dado.append(c);
            }
        }
        int indMascara = mascara.length();
        int indCampo = dado.length();

        while (indCampo > 0 && indMascara > 0) {
            if (mascara.charAt(--indMascara) == '#') {
                indCampo--;
            }
        }

        StringBuilder saida = new StringBuilder();
        for (; indMascara < mascara.length(); indMascara++) {
            saida.append((mascara.charAt(indMascara) == '#') ? dado.charAt(indCampo++) : mascara.charAt(indMascara));
        }
        return saida.toString();
    }

    /**
     * Retorna o primeiro caracter de uma string.
     *
     * @param value Valor a ser convertido
     * @return String
     */
    public static String returnFirstWord(String value) {
        String pattern = "^([a-zA-ZÈ-Úè-ú]+)\\s";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(value);
        if (m.find()) {
            return m.group(0);
        }
        return null;
    }

    /**
     * Cria uma mascara para CPF.
     *
     * @param value Valor a ser convertido
     * @return String
     * @author Fernando Matheus
     */
    public static String formatCPF(String value) {
        return Objects.nonNull(value) ? new CPFFormatter().format(value) : null;
    }

    /**
     * Cria uma mascara para CNPJ.
     *
     * @param value Valor a ser convertido
     * @return String
     * @author Fernando Matheus
     */
    public static String formatCNPJ(String value) {
        return Objects.nonNull(value) ? new CNPJFormatter().format(value) : null;
    }

}
