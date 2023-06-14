package com.example.dogbreeds.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class KendaliLogin {
    private SharedPreferences SP;
    private SharedPreferences.Editor SPE;
    private Context ctx;

    public String keySP_username = "SPBreeds_username";
    public String keySP_nama_lengkap = "SPBreeds_nama";
    public String keySP_email = "SPBreeds_email";

    public KendaliLogin(Context ctx){this.ctx = ctx;}

    public void setPref(String key, String value){
        SP = PreferenceManager.getDefaultSharedPreferences(ctx);
        SPE = SP.edit();
        SPE.putString(key, value);
        SPE.apply();
    }

    public String getPref(String key)
    {
        SP = PreferenceManager.getDefaultSharedPreferences(ctx);
        return SP.getString(key, null);
    }

    public Boolean isLogin(String key)
    {
        if(getPref(key) != null)
        {
            return true;
        }
        else{
            return false;
        }
    }

}
