package com.team13.finalproject;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;

/**
 * Created by Alyssa on 5/4/2015.
 */
@BusInterface(name = "com.team13.finalproject")
public interface WiForumInterface {
    @BusSignal
    public void Chat(String str) throws BusException;
}
