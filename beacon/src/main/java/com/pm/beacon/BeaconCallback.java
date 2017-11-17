package com.pm.beacon;

import org.altbeacon.beacon.*;

import java.util.Collection;

public interface BeaconCallback {
        void onBeaconsReceived(Collection<org.altbeacon.beacon.Beacon> collection);

}