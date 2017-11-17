package com.pm.beacon;

import android.content.Context;
import android.content.Intent;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

public class MiBeacon {

    public static Context context;
    public static long pulseInterval = 5000;
    public static boolean showClosestOnly = false;
    public static double maxDistance = 0;
    public static BeaconCallback callback;
    private Intent intent;

    public Beacon closest(Collection<Beacon> collection) {
        Beacon best = null;
        for (Beacon beacon : collection) {
            if(best!=null){
                if(best.getDistance()>beacon.getDistance()){
                    best = beacon;
                }
            }else{
                best = beacon;
            }
        }

        return best;
    }
}
