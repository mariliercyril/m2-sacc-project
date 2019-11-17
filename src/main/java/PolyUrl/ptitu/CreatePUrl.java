package PolyUrl.ptitu;

public class CreatePUrl {

    public CreatePUrl() {
    }

    public static String idToShortURL(int n) {
        // Map to store 62 possible characters
        char[] map = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder shorturl = new StringBuilder();

        // Convert given integer id to a base 62 number
        while (n > 0) {
            // use above map to store actual character
            // in short url
            shorturl.append(map[n % 62]);
            n = n / 62;
        }

        // Reverse shortURL to complete base conversion
        while (shorturl.length() < 4) {
            shorturl.append('$');
        }
        return shorturl.reverse().toString();
    }
}
