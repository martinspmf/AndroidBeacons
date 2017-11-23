package com.pm.beacon;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

public class MyApplicationName extends Application {
    private static final String TAG = ".MyApplicationName";
    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context context;
    public static long scanPeriod = 1500;
    public static long betweenScanPeriod = 1500;
    public static boolean showClosestOnly = false;
    public static double maxDistance = 0;
    public static BeaconCallback callback;
    private Intent intent;

    public void init(Context context, BeaconCallback callback){
        this.context = context;
        this.callback = callback;
        intent = new Intent(context, RangingService.class);
        context.startService(intent);
    }

    public boolean isOn(){
        if(intent==null){
            return false;
        }else{
            return true;
        }
    }

    public void stop() {
        if(intent!=null && context!=null) {
            context.stopService(new Intent(context, RangingService.class));
            intent = null;
        }
    }


    public static void showBeacons(Collection<Beacon> collection) {
        for (org.altbeacon.beacon.Beacon b : collection) {
            System.out.println("mib: --------BACKGROUND-------------");
            System.out.println("mib: distance  : " + b.getDistance());
            //System.out.println("mib: id1       : " + b.getId1());
            System.out.println("mib: id2       : " + b.getId2());
            System.out.println("mib: id3       : " + b.getId3());
            //System.out.println("mib: rssi      : " + b.getRssi());
            //System.out.println("mib: txPower   : " + b.getTxPower());
        }
    }

    public void setScanPeriod(long value) {
        scanPeriod = value;
    }

    public void setBetweenScanPeriod(long value) {
        scanPeriod = value;
    }
}
