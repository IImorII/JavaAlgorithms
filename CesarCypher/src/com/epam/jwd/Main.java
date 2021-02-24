package com.epam.jwd;

public class Main {

    public static void main(String[] args) {
        String rusAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        Cesar cesar = new Cesar(rusAlphabet);
        Gramota gramota = new Gramota();
        RouteTransportation rt = new RouteTransportation();
    }
}

class Cesar {

    public String alphabet = null;

    public Cesar(String alphabet) {
        this.alphabet = alphabet.toLowerCase();
    }

    public String encrypt(String text, int key) {
        text = text.toLowerCase();
        char[] charText = text.toCharArray();
        char[] charAlphabet = alphabet.toCharArray();
        String encryptAlphabet = alphabet;
        char[] charEncryptAlphabet = encryptAlphabet.toCharArray();
        for (int i = 0; i < charEncryptAlphabet.length; i++) {
            charEncryptAlphabet[i] = charAlphabet[(i + key) % alphabet.length()];
        }
        for (int i = 0; i < text.length(); i++) {
            if (alphabet.indexOf(charText[i]) == -1) continue;
            charText[i] = charEncryptAlphabet[alphabet.indexOf(charText[i])];
        }
        return String.valueOf(charText);
    }

    public void bruteforceDecrypt(String text) {
        text = text.toLowerCase();
        char[] charText = text.toCharArray();
        char[] charAlphabet = alphabet.toCharArray();
        String encryptAlphabet = alphabet;
        char[] charEncryptAlphabet = encryptAlphabet.toCharArray();
        for (int j = 0; j < alphabet.length(); j++) {
            for (int i = 0; i < alphabet.length(); i++) {
                charEncryptAlphabet[i] = charAlphabet[(i + 1) % alphabet.length()];
            }
            for (int i = 0; i < text.length(); i++) {
                if (alphabet.indexOf(charText[i]) == -1) continue;
                charText[i] = charEncryptAlphabet[alphabet.indexOf(charText[i])];
            }
            System.out.println(String.valueOf(charText) + " key: " + (32 - j));
        }
    }
}

class Gramota {
    private String tabGramota1 = "бвгджзклмн";
    private String tabGramota2 = "щшчцхфтсрп";

    public String crypt(String text) {
        text = text.toLowerCase();
        char[] charText = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            if (tabGramota1.indexOf(charText[i]) != -1) {
                charText[i] = tabGramota2.charAt(tabGramota1.indexOf(charText[i]));
            }
            else if (tabGramota2.indexOf(charText[i]) != -1){
                charText[i] = tabGramota1.charAt(tabGramota2.indexOf(charText[i]));
            }
            else continue;
        }
        return String.valueOf(charText);
    }
}

class RouteTransportation {
    public String encrypt(String text, int key) {
        text = text.toLowerCase();
        int w = key;
        int h = (int)Math.ceil((double)text.length()/key);
        char[][] table = new char[h][w];
        int ind = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (ind >= text.length()) { break; }
                table[i][(i % 2 == 0) ? j : (w - j - 1)] = text.charAt(ind++);
            }
        }
        String result = "";
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result += table[j][i];
            }
        }
        return result;
    }
    public String decrypth(String text, int key) {
        text = text.toLowerCase();
        int w = key;
        int h = (int)Math.ceil((double)text.length()/key);
        Character[][] table = new Character[h][w];
        int ind = 0;
        String result = "";
        if (text.length() % w == 0) {
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (ind >= text.length()) {
                        break;
                    }
                    table[j][i] = text.charAt(ind++);
                }
            }
        } else {
            if (h % 2 == 0) {
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        if (ind >= text.length()) {
                            break;
                        }
                        if (j == h - 1 && i == 0) {table[j][i] = Character.MIN_VALUE; continue;}
                        table[j][i] = text.charAt(ind++);
                    }
                }
            } else {
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        if (ind >= text.length()) {
                            break;
                        }
                        if (j == h - 1 && i == w - 1) {table[j][i] = Character.MIN_VALUE; continue;}
                        table[j][i] = text.charAt(ind++);
                    }
                }
            }
        }
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                result += table[i][(i % 2 == 0) ? j : (w - j - 1)];
            }
        }
        return result;
    }
}

class Vigenere{
    public static String crypt(String plaintext, String key, boolean encrypt, String alphabet) {
        plaintext = plaintext.toLowerCase();
        char[] specChar = new char[plaintext.length()];
        char[] StringArray = plaintext.toCharArray();

        for (int i = 0; i < StringArray.length; i++) {
            if (StringArray[i] == ' ' || StringArray[i] == ',' || StringArray[i] == '.' || StringArray[i] == '-' ||
                    StringArray[i] == '–'  || StringArray[i] == ';' || StringArray[i] == ':') { specChar[i] = StringArray[i]; }
            else { specChar[i] = '#'; }
        }
        plaintext = plaintext.replaceAll("[\\s\\,\\.\\-\\–\\:\\;]", "");
        int alphabetSize = alphabet.length();
        int textSize = plaintext.length();
        int keySize = key.length();
        StringBuilder encryptedText = new StringBuilder(textSize);

        for (int i = 0; i < textSize; i++) {
            char plainChar = plaintext.charAt(i); // get the current character to be shifted
            char keyChar = key.charAt(i % keySize); // use key again if the end is reached
            int plainPos = alphabet.indexOf(plainChar); // plain character's position in alphabet string
            if (plainPos == -1) { // if character not in alphabet just append unshifted one to the result text
                encryptedText.append(plainChar);
            }
            else { // if character is in alphabet shift it and append the new character to the result text
                final int keyPos = alphabet.indexOf(keyChar); // key character's position in alphabet string
                if (encrypt) { // encrypt the input text
                    encryptedText.append(alphabet.charAt((plainPos+keyPos) % alphabetSize));
                }
                else { // decrypt the input text
                    int shiftedPos = plainPos - keyPos;
                    if (shiftedPos < 0) { // negative numbers cannot be handled with modulo
                        shiftedPos += alphabetSize;
                    }
                    encryptedText.append(alphabet.charAt(shiftedPos));
                }
            }
        }
        for (int i = 0; i < specChar.length; i++) {
            if (specChar[i] == ' ' || specChar[i] == ',' || specChar[i] == '.' || specChar[i] == '-' ||
                    specChar[i] == '–' || specChar[i] == ';' || specChar[i] == ':') { encryptedText.insert(i, specChar[i]); }
        }
        return encryptedText.toString();
    }
}
