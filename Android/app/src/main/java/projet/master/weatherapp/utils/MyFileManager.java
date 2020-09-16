package projet.master.weatherapp.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Lib created by Alexy on 15/02/2019
 */

public class MyFileManager {


    public static void saveObject(Context mContext, String directory, String nameFile, Object objectToSave) throws Exception {
        File mydir = mContext.getDir(directory, Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, nameFile);
        ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream(fileWithinMyDir));
        os2.writeObject(objectToSave);
        os2.close();
    }

    public static Object getObject(Context mContext, String directory, String nameFile) throws Exception {
        File mydir = mContext.getDir(directory, Context.MODE_PRIVATE);
        File fileWithinMyDir = new File(mydir, nameFile);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileWithinMyDir));
        Object mObject = in.readObject();
        in.close();
        return mObject;
    }

    public static String[] list(Context mContext, String directory) {
        return mContext.getDir(directory, Context.MODE_PRIVATE).list();
    }

    public static void moveFile(Context mContext, String oldDirectory, String oldNameFile, String newDirectory, String newNameFile) throws Exception {
        File myOldDir = mContext.getDir(oldDirectory, Context.MODE_PRIVATE);
        File myNewDir = mContext.getDir(newDirectory, Context.MODE_PRIVATE);
        new File(myOldDir, oldNameFile).renameTo(new File(myNewDir, newNameFile));
    }

    public static void deleteFile(Context mContext, String directory, String nameFile) {
        File myDir = mContext.getDir(directory, Context.MODE_PRIVATE);
        new File(myDir, nameFile).delete();
    }

    public static boolean fileExist(Context mContext, String directory, String nameFile) {
        try {
            File myDir = mContext.getDir(directory, Context.MODE_PRIVATE);
            return new File(myDir, nameFile).exists();
        } catch (Exception ex) {
            return false;
        }
    }

    public static String returnNewFileName(Context mContext, String directory) {
        String[] fichiers, temp;
        String path = "";
        int i = 0, numero = 0, numeroTemp = 0;
        File myDir = mContext.getDir(directory, Context.MODE_PRIVATE);

        //Vérification des fichiers existants
        fichiers = myDir.list();
        if (fichiers.length > 0) {
            //Récup du plus grand numéro de fichier
            for (i = 0; i < fichiers.length; i++) {
                try {
                    numeroTemp = Integer.parseInt(fichiers[i]);
                    if (numeroTemp > numero) numero = numeroTemp;
                } catch (Exception ex) {
                }
            }

            //On a le plus grand numéro dans numero
            do {
                numero++;
            } while ((new File(myDir, String.valueOf(numero))).exists());
            path = String.valueOf(numero);
        } else path = "1";
        return path;
    }

    public static String getPathFirstFileFromDir(Context mContext, String directory) {
        String path = "";
        int numeroTemp = 0;
        int numero = 1000000; //Biggest possible
        try {
            File myDir = mContext.getDir(directory, Context.MODE_PRIVATE);
            String[] fichiers = myDir.list();
            if (fichiers.length > 0) {
                //Get smallest num of file
                for (int i = 0; i < fichiers.length; i++) {
                    try {
                        numeroTemp = Integer.parseInt(fichiers[i]);
                        if (numeroTemp < numero) numero = numeroTemp;
                    } catch (Exception ex) {
                        //What to do here ?
                    }
                }
                return String.valueOf(numero);
            } else return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
