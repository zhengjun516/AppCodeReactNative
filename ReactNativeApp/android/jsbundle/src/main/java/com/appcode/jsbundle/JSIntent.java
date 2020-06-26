package com.appcode.jsbundle;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class JSIntent implements Parcelable {

	public static final String KEY_JS_INTENT = "JSIntent";

	public String packageName;
	public String mainComponentName;

	public Bundle bundle;

	public JSIntent(String packageName, String mainComponentName) {
		this.packageName = packageName;
		this.mainComponentName = mainComponentName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getMainComponentName() {
		return mainComponentName;
	}

	public void setMainComponentName(String mainComponentName) {
		this.mainComponentName = mainComponentName;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	protected JSIntent(Parcel in) {
		packageName = in.readString();
		mainComponentName = in.readString();
		bundle = in.readBundle();
	}

	public static final Creator<JSIntent> CREATOR = new Creator<JSIntent>() {
		@Override
		public JSIntent createFromParcel(Parcel in) {
			return new JSIntent(in);
		}

		@Override
		public JSIntent[] newArray(int size) {
			return new JSIntent[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(packageName);
		dest.writeString(mainComponentName);
		dest.writeBundle(bundle);
	}
}
