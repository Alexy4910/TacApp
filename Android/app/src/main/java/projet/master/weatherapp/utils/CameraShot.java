package projet.master.weatherapp.utils;

import java.io.Serializable;
import java.util.Date;

/**
 * Lib created by Alexy on 15/02/2019
 */

public class CameraShot implements Serializable {
    private static final long serialVersionUID = 1L;
    public String description = "";
    public byte[] picture = null;
    public Date date = null;

    public CameraShot(Date date, byte[] picture){
        this.date = date;
        this.picture = picture;
    }
}
