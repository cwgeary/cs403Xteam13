package com.team13.finalproject;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/*
 * Copyright AllSeen Alliance. All rights reserved.
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

/**
 * Attempt to use AllJoyn sample code and modify to work with our application.
 * Unfortunately the API doesn't have the best documentation and it's fairly complex.
 * It was probably way too ambitious to try and understand all of this in one project.
 *
 * -Alyssa
 */
public class WiForum extends Application implements Observable{
    private List<Observer> mObservers = new ArrayList<Observer>();
    public static final String APPLICATION_QUIT_EVENT = "APPLICATION_QUIT_EVENT";
    public static final String USE_JOIN_CHANNEL_EVENT = "USE_JOIN_CHANNEL_EVENT";
    public static final String HOST_INIT_CHANNEL_EVENT = "HOST_INIT_CHANNEL_EVENT";
    public static final String USE_LEAVE_CHANNEL_EVENT = "USE_LEAVE_CHANNEL_EVENT";
    public static final String HOST_START_CHANNEL_EVENT = "HOST_START_CHANNEL_EVENT";
    public static final String HOST_STOP_CHANNEL_EVENT = "HOST_STOP_CHANNEL_EVENT";
    public static final String OUTBOUND_CHANGED_EVENT = "OUTBOUND_CHANGED_EVENT";
    public static final String HISTORY_CHANGED_EVENT = "HISTORY_CHANGED_EVENT";
    public static final String USE_CHANNEL_STATE_CHANGED_EVENT = "USE_CHANNEL_STATE_CHANGED_EVENT";
    public static final String HOST_CHANNEL_STATE_CHANGED_EVENT = "HOST_CHANNEL_STATE_CHANGED_EVENT";
    public static String PACKAGE_NAME;
    private List<String> mChannels = new ArrayList<String>();
    private String mHostChannelName;
    private AllJoynService.HostChannelState mHostChannelState = AllJoynService.HostChannelState.IDLE;
    private AllJoynService.UseChannelState mUseChannelState = AllJoynService.UseChannelState.IDLE;
    private String mUseChannelName;
    private List<String> mOutbound = new ArrayList<String>();
    private List<String> mHistory = new ArrayList<String>();
    final int HISTORY_MAX = 20;
    ComponentName mRunningService = null;

    public void onCreate() {
        Intent intent = new Intent(this, AllJoynService.class);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        mRunningService = startService(intent);
    }

    public synchronized void addObserver(Observer obs) {
        //Log.i(TAG, "addObserver(" + obs + ")");
        if (mObservers.indexOf(obs) < 0) {
            mObservers.add(obs);
        }
    }

    public synchronized void useJoinChannel() {
        clearHistory();
        notifyObservers(USE_CHANNEL_STATE_CHANGED_EVENT);
        notifyObservers(USE_JOIN_CHANNEL_EVENT);
    }


    public synchronized void deleteObserver(Observer obs) {
        //Log.i(TAG, "deleteObserver(" + obs + ")");
        mObservers.remove(obs);
    }

    public synchronized void useSetChannelName(String name) {
        mUseChannelName = name;
        notifyObservers(USE_CHANNEL_STATE_CHANGED_EVENT);
    }



    public synchronized void removeFoundChannel(String channel) {
       // Log.i(TAG, "removeFoundChannel(" + channel + ")");

        for (Iterator<String> i = mChannels.iterator(); i.hasNext();) {
            String string = i.next();
            if (string.equals(channel)) {
                //Log.i(TAG, "removeFoundChannel(): removed " + channel);
                i.remove();
            }
        }
    }

    public synchronized AllJoynService.HostChannelState hostGetChannelState() {
        return mHostChannelState;
    }

    public synchronized void hostSetChannelName(String name) {
        mHostChannelName = name;
        //notifyObservers(HOST_CHANNEL_STATE_CHANGED_EVENT);
    }

    public synchronized String useGetChannelName() {
        return mUseChannelName;
    }

    public synchronized void hostSetChannelState(AllJoynService.HostChannelState state) {
        mHostChannelState = state;
        //notifyObservers(HOST_CHANNEL_STATE_CHANGED_EVENT);
    }

    public synchronized String hostGetChannelName() {
        return mHostChannelName;
    }

    public synchronized void addFoundChannel(String channel) {
        //Log.i(TAG, "addFoundChannel(" + channel + ")");
        removeFoundChannel(channel);
        mChannels.add(channel);
        //Log.i(TAG, "addFoundChannel(): added " + channel);
    }

    public synchronized void hostStartChannel() {
        notifyObservers(HOST_CHANNEL_STATE_CHANGED_EVENT);
        notifyObservers(HOST_START_CHANNEL_EVENT);
    }

    public synchronized void useSetChannelState(AllJoynService.UseChannelState state) {
        mUseChannelState = state;
        notifyObservers(USE_CHANNEL_STATE_CHANGED_EVENT);
    }

    private void notifyObservers(Object arg) {
        //Log.i(TAG, "notifyObservers(" + arg + ")");
        for (Observer obs : mObservers) {
            //Log.i(TAG, "notify observer = " + obs);
            obs.update(this, arg);
        }
    }

    public synchronized String getOutboundItem() {
        if (mOutbound.isEmpty()) {
            return null;
        } else {
            return mOutbound.remove(0);
        }
    }

    public synchronized void newRemoteUserMessage(String nickname, String message) {
        addInboundItem(nickname, message);
    }

    private void addInboundItem(String nickname, String message) {
        addHistoryItem(nickname, message);
    }


    private void addHistoryItem(String nickname, String message) {
        if (mHistory.size() == HISTORY_MAX) {
            mHistory.remove(0);
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        mHistory.add("[" + dateFormat.format(date) + "] (" + nickname + ") " + message);
        notifyObservers(HISTORY_CHANGED_EVENT);
    }

    private void clearHistory() {
        mHistory.clear();
        notifyObservers(HISTORY_CHANGED_EVENT);
    }
}
