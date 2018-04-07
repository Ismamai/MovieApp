package com.iblesa.movieapp.util;

import android.util.Log;

import com.iblesa.movieapp.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Parser {
    public static String getStringProperty(String propertyName, JSONObject jsonObject, boolean compulsory) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return jsonObject.getString(propertyName);
        } else if (compulsory) {

            String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
            Log.e(Constants.TAG, errorMessage);
            throw new JSONException(errorMessage);
        }
        return null;
    }

    public static int getCompulsoryIntProperty(String propertyName, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return jsonObject.getInt(propertyName);
        }

        String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
        Log.e(Constants.TAG, errorMessage);
        throw new JSONException(errorMessage);
    }

    public static float getCompulsoryFloatProperty(String propertyName, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return (float) jsonObject.getDouble(propertyName);
        }

        String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
        Log.e(Constants.TAG, errorMessage);
        throw new JSONException(errorMessage);
    }

    public static boolean getCompulsoryBooleanProperty(String propertyName, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(propertyName)) {
            return jsonObject.getBoolean(propertyName);
        }

        String errorMessage = "JSONObject does not have compulsory property, propertyName = " + propertyName + ", jsonObject = " + jsonObject.toString();
        Log.e(Constants.TAG, errorMessage);
        throw new JSONException(errorMessage);
    }

}
