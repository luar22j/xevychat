package net.xevy;

import java.io.BufferedReader;

public class Validadores {

    /**
     * Valida el email del usuario
     * 
     * @param email
     * @return boolean. true si es correcto
     */
    public static boolean validador(String email) {

        return email.matches("[A-Za-z0-9]*@+[A-Za-z0-9].+[A-Za-z]");
    }

    /**
     * Valida la password
     * que contenga un digito
     * que contenga una minuscula
     * que contenga una mayuscula
     * algun simbolo especial
     * no espacio libres
     * 8 o mas digitos
     * 
     * @param password
     * @return boolean
     */
    public static boolean validadordepassword(String password) {

        return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}");
    }

    /**
     * comprueba que es un digito antes de transformarlo sino entra en bucle hasta
     * que conteste bien
     * 
     * @param input el lector en bufferreader
     * @return integer
     */
    public static Integer integercorrecto(BufferedReader input) {
        String hola = "a";
        while (!hola.matches("[0-9]*")) {
            try {
                hola = input.readLine();
            } catch (Exception e) {
                e.getCause();
            }
        }
        return Integer.parseInt(hola);
    }
}
