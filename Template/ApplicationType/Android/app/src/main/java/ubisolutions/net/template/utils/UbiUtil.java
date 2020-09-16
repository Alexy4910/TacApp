package ubisolutions.net.template.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Build;
import android.provider.Settings;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;

/**
 * Lib created by Alexy on 15/02/2019
 */

public class UbiUtil {

    public static String fillWithZero(String string, int totalSize) {
        while (string.length() < totalSize) {
            string = "0" + string;
        }
        return string;
    }

    public static byte[] bitmapToByte(Bitmap picture){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public static int neededRotation(String path)
    {
        try
        {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            { return 270; }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            { return 180; }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            { return 90; }
            return 0;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    //méthode qui permet de savoir la distance entre 2 points GPS avec une précision de +- 0.03 mètre
    private double distVincenty(double lat1, double lon1, double lat2, double lon2) {
        double a = 6378137, b = 6356752.3142, f = 1 / 298.257223563;  // WGS-84 ellipsiod
        double L = Math.toRadians(lon2 - lon1);
        double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
        double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
        double cosSigma;
        double sigma;
        double sinAlpha;
        double cosSqAlpha;
        double cos2SigmaM;
        double sinSigma;
        double lambda = L, lambdaP, iterLimit = 100;
        do {
            double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if (sinSigma == 0) {
                return 0;  // co-incident points
            }
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)) cos2SigmaM = 0;  // equatorial line: cosSqAlpha=0 (§6)
            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);
        if (iterLimit == 0) return 0;  // formula failed to converge

        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
        double s = b * A * (sigma - deltaSigma);
        double d = (double) Math.round(s * 100) / 100; // 4.248 --> 4.25
        return d;
    }

    public static String getDeviceIdentifier(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                return Build.MANUFACTURER.replaceAll("[^\\w\\s]", "").replace(' ', '_') + "_" + Build.getSerial();
            } catch (SecurityException se) {
            }
        }
        if (Build.SERIAL.equals(Build.UNKNOWN)) {
            return Build.MANUFACTURER.replaceAll("[^\\w\\s]", "").replace(' ', '_') + "_" + Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            return Build.MANUFACTURER.replaceAll("[^\\w\\s]", "").replace(' ', '_') + "_" + Build.SERIAL;
        }
    }

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String convertAsciiToCaracter(String tagID) {
        String result = "";

        try {
            StringBuilder sb = new StringBuilder(tagID.length() / 2);
            for (int i = 0; i < tagID.length(); i += 2) {
                String hex = "" + tagID.charAt(i) + tagID.charAt(i + 1);
                int ival = Integer.parseInt(hex, 16);
                sb.append((char) ival);
            }

            return sb.toString().replaceAll("\\p{C}", "");
        } catch (Exception ex) {
            return tagID;
        }
    }

    public static String convertCaracterToAscii(String tagID) {
        String result = "";

        try {
            StringBuilder sb = new StringBuilder(tagID.length() * 2);
            char[] ch = tagID.toCharArray();
            for (char c : ch) {
                // Step-2 Use %H to format character to Hex
                String hexCode = String.format("%2H", c);
                sb.append(hexCode);
            }

            while (sb.length() < 24) {
                sb.insert(0, "00");
            }

            return sb.toString();
        } catch (Exception ex) {
            return tagID;
        }
    }
}
