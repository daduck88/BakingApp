
package com.bakingapp.android.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.bakingapp.android.App;
import com.bakingapp.android.provider.ingredient.IngredientContentValues;
import com.bakingapp.android.provider.ingredient.IngredientCursor;
import com.bakingapp.android.provider.step.StepContentValues;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable{

    @SerializedName("quantity")
    @Expose
    private Float quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public Ingredient(IngredientCursor cursor){
        quantity = cursor.getQuantity();
        measure = cursor.getMeasure();
        ingredient = cursor.getIngredient();
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.quantity = (Float) in.readValue(Float.class.getClassLoader());
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Uri insertUri(Integer idRecipe) {
        IngredientContentValues values = new IngredientContentValues();
        values.putQuantity(quantity);
        values.putMeasure(measure);
        values.putIngredient(ingredient);
        values.putIdRecipe(idRecipe);
        return values.insert(App.getContext());
    }
}
