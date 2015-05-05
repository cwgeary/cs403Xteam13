package com.team13.finalproject;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;

/**
 * Created by Alyssa on 5/4/2015.
 */

/**
 * Attempt to use AllJoyn sample code and modify to work with our application.
 * Unfortunately the API doesn't have the best documentation and it's fairly complex.
 * It was probably way too ambitious to try and understand all of this in one project.
 *
 * -Alyssa
 */


@BusInterface(name = "com.team13.finalproject")
public interface WiForumInterface {
    @BusSignal
    public void Chat(String str) throws BusException;
}
