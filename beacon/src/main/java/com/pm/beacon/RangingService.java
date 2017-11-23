package com.pm.beacon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

import static com.pm.beacon.MyApplicationName.betweenScanPeriod;
import static com.pm.beacon.MyApplicationName.scanPeriod;

public class RangingService extends Service implements BeaconConsumer {

    IBinder mBinder;

    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager;
    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {

/*
        final org.altbeacon.beacon.Region region = new org.altbeacon.beacon.Region("myBeacons", Identifier.parse("ebefd083-70a2-47c8-9837-e7b5634df577"), null, null);
        regionBootstrap = new RegionBootstrap((BootstrapNotifier) this, region);

*/


        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        beaconManager.setForegroundScanPeriod(scanPeriod);
        beaconManager.setForegroundBetweenScanPeriod(betweenScanPeriod);

        beaconManager.bind(this);


    }

    @Override
    public void onDestroy() {
        beaconManager.unbind(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> collection, Region region) {

                if (MyApplicationName.callback != null){
                    MyApplicationName.callback.onBeaconsReceived(collection);
                }else{
                    MyApplicationName.showBeacons(collection);
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

}




















   /* public static final String TAG = "BeaconsEverywhere";
    private BeaconManager beaconManager;
    //static LinearLayout notificationHolder;
    ViewGroup layout;
    static int a = 0;
    org.altbeacon.beacon.MiBeacon oneBeacon = null;
    org.altbeacon.beacon.MiBeacon lastBeacon = null;
    public static Context context;

    *//**
 * indicates how to behave if the service is killed
 *//*
    int mStartMode;

    *//**
 * interface for clients that bind
 *//*
    IBinder mBinder;

    *//**
 * indicates whether onRebind should be used
 *//*
    boolean mAllowRebind;
    private RegionBootstrap regionBootstrap;

    *//**
 * Called when the service is being created.
 *//*
    @Override
    public void onCreate() {

        final org.altbeacon.beacon.Region region = new org.altbeacon.beacon.Region("myBeacons", Identifier.parse("ebefd083-70a2-47c8-9837-e7b5634df577"), null, null);
        regionBootstrap = new RegionBootstrap(this, region);

        if (MiBeacon.context != null) {

            beaconManager = BeaconManager.getInstanceForApplication(this);

            beaconManager.getBeaconParsers().add(new BeaconParser()
                    .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

            beaconManager.setBackgroundScanPeriod(MiBeacon.pulseInterval);

            beaconManager.bind(this);
        }


    }

    *//**
 * The service is starting, due to a call to startService()
 *//*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
    }

    *//**
 * A client is binding to the service with bindService()
 *//*
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    *//**
 * Called when all clients have unbound with unbindService()
 *//*
    @Override
    public boolean onUnbind(Intent intent) {

        //beaconManager.unbind(this);
        return mAllowRebind;
    }

    *//**
 * Called when a client is binding to the service with bindService()
 *//*
    @Override
    public void onRebind(Intent intent) {

    }

    *//**
 * Called when The service is no longer used and is being destroyed
 *//*
    @Override
    public void onDestroy() {

    }


    @Override
    public void didEnterRegion(org.altbeacon.beacon.Region region) {
        try {
            Log.d(TAG, "didEnterRegion");
            beaconManager.addRangeNotifier(rangeNotifier);
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didExitRegion(org.altbeacon.beacon.Region region) {

        try {
            Log.d(TAG, "didExitRegion");
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void didDetermineStateForRegion(int i, org.altbeacon.beacon.Region region) {


    }

    *//**
 * Range notifier
 *//*

    public RangeNotifier rangeNotifier = new RangeNotifier() {
        @Override
        public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.MiBeacon> beacons, org.altbeacon.beacon.Region region) {

            for (org.altbeacon.beacon.MiBeacon b : beacons) {
                System.out.println("mib: ---------------------------------");
                System.out.println("mib: distance  : " + b.getDistance());
                System.out.println("mib: id1       : " + b.getId1());
                System.out.println("mib: id2       : " + b.getId2());
                System.out.println("mib: id3       : " + b.getId3());
                System.out.println("mib: rssi      : " + b.getRssi());
                System.out.println("mib: txPower   : " + b.getTxPower());
            }

            if (MiBeacon.showClosestOnly) {
                for (org.altbeacon.beacon.MiBeacon currentBeacon : beacons) {
                    if (beacons.size() == 1 || oneBeacon == null) {
                        oneBeacon = currentBeacon;
                        if ((MiBeacon.maxDistance == 0 || oneBeacon.getDistance() < MiBeacon.maxDistance) && lastBeacon == null) {
                            lastBeacon = oneBeacon;
                        }
                    } else if (currentBeacon.getDistance() < lastBeacon.getDistance()) {
                        if (!(oneBeacon.getId2() == lastBeacon.getId2()
                                && oneBeacon.getId3() == lastBeacon.getId3())) {
                            oneBeacon = currentBeacon;
                            lastBeacon = oneBeacon;
                        }
                    }
                }

                Log.d(TAG, "distance: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());

                if (MiBeacon.maxDistance == 0 || oneBeacon.getDistance() < MiBeacon.maxDistance) {
                    if (lastBeacon != null
                            && !((oneBeacon.getId2().toString().equals(lastBeacon.getId2().toString())
                            || !(oneBeacon.getId3().toString().equals(lastBeacon.getId3().toString()))))) {


                    }
                }
                lastBeacon = oneBeacon;
            } else {
                for (org.altbeacon.beacon.MiBeacon currentBeacon : beacons) {
                    if (a == 0) {
                        oneBeacon = currentBeacon;
                    }
                }
                //a=1;
            }

        }
    };

    @Override
    public void onBeaconServiceConnect() {

    }
}*/