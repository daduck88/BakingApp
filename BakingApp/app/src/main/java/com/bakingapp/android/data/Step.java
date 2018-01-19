
package com.bakingapp.android.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import com.bakingapp.android.App;
import com.bakingapp.android.provider.recipe.RecipeContentValues;
import com.bakingapp.android.provider.step.StepContentValues;
import com.bakingapp.android.provider.step.StepCursor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;
    private int position;
    private boolean notFirst;
    private boolean notLast;

    public Step(StepCursor stepCursor) {
        id = stepCursor.getPosition();
        shortDescription = stepCursor.getShortdescription();
        description = stepCursor.getDescription();
        videoURL = stepCursor.getVideourl();
        thumbnailURL = stepCursor.getThumbnailurl();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isNotFirst() {
        return notFirst;
    }

    public void setNotFirst(boolean notFirst) {
        this.notFirst = notFirst;
    }

    public boolean isNotLast() {
        return notLast;
    }

    public void setNotLast(boolean notLast) {
        this.notLast = notLast;
    }

    public boolean showVideo(){
        return (videoURL != null && !videoURL.isEmpty());
    }

    public int videoVisibility(){
        if(TextUtils.isEmpty(videoURL)){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    public int imageVisibility(){
        if(TextUtils.isEmpty(thumbnailURL) || thumbnailURL.endsWith(".mp4")){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public Uri insertUri(int idRecipe) {
        StepContentValues values = new StepContentValues();
        values.putPosition(id);
        values.putShortdescription(shortDescription);
        values.putDescription(description);
        values.putVideourl(videoURL);
        values.putThumbnailurl(thumbnailURL);
        values.putIdRecipe(idRecipe);
        return values.insert(App.getContext());
    }
}
