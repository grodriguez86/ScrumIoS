package ar.edu.uade.scrumgame.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductBacklogItemModel implements Parcelable, Cloneable {
    private String name;
    private String estimatedStoryPoints;
    private Boolean isPriority;

    public static final Creator<ProductBacklogItemModel> CREATOR
            = new Creator<ProductBacklogItemModel>() {
        public ProductBacklogItemModel createFromParcel(Parcel in) {
            return new ProductBacklogItemModel(in);
        }

        public ProductBacklogItemModel[] newArray(int size) {
            return new ProductBacklogItemModel[size];
        }
    };

    private ProductBacklogItemModel(Parcel in) {
        this.name = in.readString();
        this.estimatedStoryPoints = in.readString();
        this.isPriority = in.readByte() != 0;
    }


    public ProductBacklogItemModel(String name, String estimatedStoryPoints, Boolean isPriority) {
        this.name = name;
        this.estimatedStoryPoints = estimatedStoryPoints;
        this.isPriority = isPriority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstimatedStoryPoints() {
        return estimatedStoryPoints;
    }

    public void setEstimatedStoryPoints(String estimatedStoryPoints) {
        this.estimatedStoryPoints = estimatedStoryPoints;
    }

    public Boolean getPriority() {
        return isPriority;
    }

    public void setPriority(Boolean priority) {
        isPriority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.estimatedStoryPoints);
        dest.writeByte((byte) (this.isPriority ? 1 : 0));
    }
}
