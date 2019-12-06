package ar.edu.uade.scrumgame.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GameContentModel implements Parcelable, Cloneable {
    private String type;
    private Boolean isCorrect;
    private String data;

    public static final Parcelable.Creator<GameContentModel> CREATOR
            = new Parcelable.Creator<GameContentModel>() {
        public GameContentModel createFromParcel(Parcel in) {
            return new GameContentModel(in);
        }

        public GameContentModel[] newArray(int size) {
            return new GameContentModel[size];
        }
    };

    private GameContentModel(Parcel in) {
        this.type = in.readString();
        this.isCorrect = in.readByte() != 0;
        this.data = in.readString();
    }


    public GameContentModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeByte((byte) (this.isCorrect ? 1 : 0));
        dest.writeString(this.data);
    }
}
