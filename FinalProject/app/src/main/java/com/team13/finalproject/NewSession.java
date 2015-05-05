package com.team13.finalproject;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.ProxyBusObject;

/**
 * Attempt to use AllJoyn sample code and modify to work with our application.
 * Unfortunately the API doesn't have the best documentation and it's fairly complex.
 * It was probably way too ambitious to try and understand all of this in one project.
 *
 * -Alyssa
 */


public class NewSession {
    static {
        System.loadLibrary("alljoyn_java");
    }

        private BusHandler busHandler;
}


class BusHandler extends Handler {
        /*
          * Name used as the well-known name and the advertised name of the service this client is
          * interested in.  This name must be a unique name both to the bus and to the network as a
          * whole.
          *
          * The name uses reverse URL style of naming, and matches the name used by the service.
          */
    private static final String SERVICE_NAME = "com.team13.finalProject";
    private static final short CONTACT_PORT = 42;

    private BusAttachment mBus;
    private ProxyBusObject mProxyObj;

    private int mSessionId;
    private boolean mIsInASession;
    private boolean mIsConnected;
    private boolean mIsStoppingDiscovery;

    /* These are the messages sent to the BusHandler from the UI. */
    public static final int CONNECT = 1;
    public static final int JOIN_SESSION = 2;
    public static final int DISCONNECT = 3;
    public static final int PING = 4;

    public BusHandler(Looper looper) {
        super(looper);

        mIsInASession = false;
        mIsConnected = false;
        mIsStoppingDiscovery = false;
    }

    @Override
    public void handleMessage(Message message) {

    }
}
