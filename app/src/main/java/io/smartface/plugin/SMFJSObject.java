package io.smartface.plugin;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

public class SMFJSObject implements Parcelable {

    public static final Creator<SMFJSObject> CREATOR = new Creator<SMFJSObject>() {
        @Override
        public SMFJSObject createFromParcel(Parcel in) {
            return new SMFJSObject(in);
        }

        @Override
        public SMFJSObject[] newArray(int size) {
            return new SMFJSObject[size];
        }
    };

    protected SMFJSObject(Parcel in) {
    }

    public SMFJSObject() {
    }

    public SMFJSObject(int val) {
    }

    public SMFJSObject(double val) {
    }

    public SMFJSObject(String val) {
    }

    public SMFJSObject(JSONObject val) {
    }

    public SMFJSObject(JSONArray val) {
    }

    public SMFJSObject(boolean val) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public SMFType getType() {
        return SMFType.Null;
    }

    public boolean hasProperty(String propertyName) {
        return false;
    }

    public SMFJSObject getProperty(String propertyName) throws Exception {
        return null;
    }

    public void setProperty(String propertyName, SMFJSObject value) throws Exception {
    }

    public boolean deleteProperty(String propertyName) throws Exception {
        return false;
    }

    public SMFJSObject getPropertyAtIndex(int propertyIndex) throws Exception {
        return null;
    }

    public void setPropertyAtIndex(int propertyIndex, SMFJSObject value) throws Exception {
    }

    public boolean isFunction() {
        return false;
    }

    public boolean isUndefined() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isObjectOfClass(SMFJSObject aClass) {
        return false;
    }

    public boolean isEqual(SMFJSObject value) throws Exception {
        return false;
    }

    public boolean isStrictEqual(SMFJSObject value) {
        return false;
    }

    public SMFJSObject callAsFunction(
            SMFJSObject thisObject,
            SMFJSObject[] arguments) throws Exception {
        return null;
    }

    public String[] copyPropertyNames() {
        return null;
    }

    int getPropertyNameArrayLength() {
        return 0;
    }

    public boolean toBoolean() {
        return false;
    }

    public double toDouble() {
        return 0.0;
    }

    public int toInt() {
        return 0;
    }

    public String toString() {
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
    }

    public enum SMFType {
        Null
    }
}
