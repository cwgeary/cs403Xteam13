package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Crime {

    public static final int MAX_PHOTOS = 4;

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_PHOTOS = "photos";
    private static final String JSON_NUM_PHOTOS = "numPhotos";
    private static final String JSON_I_PHOTO = "iPhoto";
    
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    Photo[] photos = new Photo[MAX_PHOTOS];

    private int i_photo = -1;
    private int num_photos = 0;
    
    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        num_photos = json.getInt(JSON_NUM_PHOTOS);
        i_photo = json.getInt(JSON_I_PHOTO);

        JSONArray jsonPhotos = json.getJSONArray(JSON_PHOTOS);

        for( int i = 0; i < jsonPhotos.length(); i++ ) {
            photos[i] = new Photo(jsonPhotos.getJSONObject(i));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_NUM_PHOTOS, num_photos);
        json.put(JSON_I_PHOTO, i_photo);

        JSONArray jsonPhotos = new JSONArray();

        for( int i = 0; i < num_photos; i++ ) {
            jsonPhotos.put(i, photos[i].toJSON());
        }

        json.put(JSON_PHOTOS, jsonPhotos);

        return json;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

	public Photo getPhoto(int i) {
        if( i < num_photos ) {
            return photos[i];
        }
        else {
            return null;
        }
	}

    public int getNumPhotos() {
        return num_photos;
    }

	public int setPhoto(Photo photo) {
        i_photo = ( i_photo + 1 ) % MAX_PHOTOS;
		photos[i_photo] = photo;
        if( num_photos < i_photo + 1 ) {
            num_photos = i_photo + 1;
        }

        return i_photo;
	}

    public void deletePhotos() {
        for( int i = 0; i < num_photos; i++ ) {
            photos[i] = null;
        }
        num_photos = 0;
        i_photo = -1;
    }
    
}
