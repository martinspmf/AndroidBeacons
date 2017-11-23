package com.mobinteg.androidbeacons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pm.beacon.BeaconCallback;
import com.pm.beacon.MyApplicationName;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private MyApplicationName miBeaconService;
    private MainActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        searchBeacons();
    }

    private synchronized void searchBeacons() {
        if (miBeaconService == null) {
            miBeaconService = new MyApplicationName();
            miBeaconService.setScanPeriod(1500);
            miBeaconService.init(mContext, new BeaconCallback() {
                @Override
                public void onBeaconsReceived(final Collection<Beacon> collection) {

                    if (!collection.isEmpty()) {
                        for (Beacon b : collection) {
                                System.out.println("mib: ---------------------------------");
                                System.out.println("mib: distance  : " + b.getDistance());
                                System.out.println("mib: id2       : " + b.getId2());
                                System.out.println("mib: id3       : " + b.getId3());
                        }
                    }
                }
            });
        }
    }
}
