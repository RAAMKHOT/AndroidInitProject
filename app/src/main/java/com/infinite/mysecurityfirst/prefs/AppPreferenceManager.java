package com.infinite.mysecurityfirst.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Ramagouda Khot on 27-01-2022.
 */

public class AppPreferenceManager {


    private static SharedPreferences sharedPreferences = null;
    private static byte[] sKey;

    public static SharedPreferences getSharedPreference(final Context context) {
        if (context != null) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                try {
                    final String key = generateAesKeyName(context);
                    String value = sharedPreferences.getString(key, null);
                    if (value == null) {
                        value = generateAesKeyValue();
                        sharedPreferences.edit().putString(key, value).commit();
                    }
                    sKey = decode(value);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return sharedPreferences;
    }

    public static String getString(String key, Context context) {
        //String mKey = encrypt(key);
        return getSharedPreference(context).getString(key, "");

    }

    public static boolean getBoolean(String key, Context context) {
        //String mKey = encrypt(key);
        /*Log.v("value", "value--getBoolean----->" + mKey);
        final String encryptedValue = getSharedPreference(context).getString(mKey, "");
        if (encryptedValue == null || encryptedValue.equals("")) {
            return false;
        }
        try {
            return Boolean.parseBoolean(decrypt(encryptedValue));
        } catch (NumberFormatException e) {
            throw new ClassCastException(e.getMessage());
        }*/
        return getSharedPreference(context).getBoolean(key, false);

    }

    public static void putBoolean(String key, boolean value, Context context) {
        //String mKey = encrypt(key);
        // String mValue = encrypt(Boolean.toString(value));
        Log.d("", "--------------------------" + Boolean.toString(value));
        //getSharedPreference(context).edit().putString(mKey, encrypt(mValue)).commit();
        getSharedPreference(context).edit().putBoolean(key, value).commit();
    }

    public static void putString(String key, String value, Context context) {
        //String mKey = encrypt(key);
        //String mValue = encrypt(value);
        //Log.v("value", "value--putString--mKey--->" + key + "<----mValue-------->" + value);
        getSharedPreference(context).edit().putString(key, value).commit();
        //Log.v("value", "value--getString--mKey--->" + getString(AppPreferenceConstants.KEY_TRUCKUPDATE_URL, context) + "<----mValue-------->" + value);
    }

    public static void putInt(String key, int value, Context context) {
        String mKey = encrypt(key);
        String mValue = encrypt(Integer.toString(value));
        getSharedPreference(context).edit().putString(mKey, mValue).apply();
    }

    public static int getInt(String key, Context context) {
        String mKey = encrypt(key);
        Log.v("value", "value--getInt----->" + mKey);
        final String encryptedValue = getSharedPreference(context).getString(mKey, "");
        if (encryptedValue == null || encryptedValue.equals("")) {
            return 0;
        }
        try {
            System.out.println("Integer.parseInt(decrypt(encryptedValue)) == " + Integer.parseInt(decrypt(encryptedValue)));
            return Integer.parseInt(decrypt(encryptedValue));
        } catch (NumberFormatException e) {
            throw new ClassCastException(e.getMessage());
        }

    }

    public static void clearSharedPref(Context context) {
        getSharedPreference(context).edit().clear().commit();
    }

    public static void Logout(Context context) {
        clearSharedPref(context);
       /* Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.TWITTER_CONSUMER_KEY, Config.TWITTER_CONSUMER_SECRET);
        twitter.setOAuthAccessToken(null);*/
        CookieManager.getInstance().removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();

    }

    private static String encode(byte[] input) {
        return Base64.encodeToString(input, Base64.NO_PADDING | Base64.NO_WRAP);
    }

    private static byte[] decode(String input) {
        return Base64.decode(input, Base64.NO_PADDING | Base64.NO_WRAP);
    }

    private static String encrypt(String cleartext) {
        if (cleartext == null || cleartext.length() == 0) {
            return cleartext;
        }
        try {
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(AppPreferenceManager.sKey, "AES"));
            return encode(cipher.doFinal(cleartext.getBytes("UTF-8")));
        } catch (Exception e) {
            Log.e(AppPreferenceManager.class.getName(), "encrypt", e);
            return null;
        }
    }

    private static String decrypt(String ciphertext) {
        if (ciphertext == null || ciphertext.length() == 0) {
            return ciphertext;
        }
        try {
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(AppPreferenceManager.sKey, "AES"));
            return new String(cipher.doFinal(AppPreferenceManager.decode(ciphertext)), "UTF-8");
        } catch (Exception e) {
            Log.e(AppPreferenceManager.class.getName(), "decrypt", e);
            return null;
        }
    }

    private static String generateAesKeyValue() throws NoSuchAlgorithmException {
        // Do *not* seed secureRandom! Automatically seeded from system entropy
        final SecureRandom random = new SecureRandom();

        // Use the largest AES key length which is supported by the OS
        final KeyGenerator generator = KeyGenerator.getInstance("AES");
        try {
            generator.init(256, random);
        } catch (Exception e) {
            try {
                generator.init(192, random);
            } catch (Exception e1) {
                generator.init(128, random);
            }
        }
        return encode(generator.generateKey().getEncoded());
    }


    private static String generateAesKeyName(Context context) throws InvalidKeySpecException,
            NoSuchAlgorithmException {
        final char[] password = context.getPackageName().toCharArray();
        final byte[] salt = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID).getBytes();

        // Number of PBKDF2 hardening rounds to use, larger values increase
        // computation time, you should select a value that causes
        // computation to take >100ms
        final int iterations = 1000;

        // Generate a 256-bit key
        final int keyLength = 256;

        final KeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        return encode(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(spec).getEncoded());
    }

}
