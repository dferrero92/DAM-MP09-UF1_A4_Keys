package com.company;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class KeyGenKeyGeneration {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
// write your code here

        String text = "PROBANDO";

        byte[] data = text.getBytes(); // Convierte la variable "text" en un Array bytes

        SecretKey skey; // Declarem una clau (skey) de tipus SecretKey

        skey = keygenKeyGeneration(128);

        byte[] encrypteddata = encryptData(skey, data); // Creamos otra array de bytes y se iguala al método encryptData que hemos creado y se le pasa como parametros la skey y el array de Bytes (data)

        System.out.println("Dades Encriptades: " + new String(encrypteddata)); // Se encriptan los datos.

        System.out.println("Text en clar:" + skey.getEncoded()); // texto en claro

        byte[] decryptdata = desencryptData(skey, encrypteddata); // desencriptamos

        System.out.println("Text Desencriptat:" + new String(decryptdata));
    }

    public static byte[] encryptData(SecretKey skey, byte[] data) {
        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant les dades " + ex);

        }
        return encryptedData;

    }

    public static byte[] desencryptData(SecretKey sKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] desencryptedData = null;

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, sKey);

        desencryptedData = cipher.doFinal(data);

        return desencryptedData;
    }


    public static SecretKey keygenKeyGeneration(int keySize) {    //Generamos una skey()

        SecretKey sKey = null;

        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();
                System.out.println("Clau: " + sKey.hashCode());


            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;

    }


    public static SecretKey passwordKeyGeneration(String text, int keySize) {
        SecretKey sKey = null;

        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {
                byte[] data = text.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize / 8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sKey;

    }



}