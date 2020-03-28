package ar.edu.uade.scrumgame.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class InfoGameModel implements Parcelable, Cloneable {
    private String code;
    private String type;
    private String title;
    private List<GameContentModel> content;

    public InfoGameModel() {
    }

    public static final Parcelable.Creator<InfoGameModel> CREATOR
            = new Parcelable.Creator<InfoGameModel>() {
        public InfoGameModel createFromParcel(Parcel in) {
            return new InfoGameModel(in);
        }

        public InfoGameModel[] newArray(int size) {
            return new InfoGameModel[size];
        }
    };

    private InfoGameModel(Parcel in) {
        this.code = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        in.readList(this.content, GameContentModel.class.getClassLoader());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GameContentModel> getContent() {
        return content;
    }

    public void setContent(List<GameContentModel> content) {
        this.content = content;
    }

    public int getLevelCode() {
        return Integer.parseInt(code.split("\\.")[0]);
    }

    public int getSubLevelCode() {
        return Integer.parseInt(code.split("\\.")[1]);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeList(this.content);
    }
}
