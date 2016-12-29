package com.ys.yoosir.zzshow.mvp.model.entity.netease;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author yoosir
 * Created by Administrator on 2016/11/16.
 */

public class NewsPhotoDetail implements Parcelable {

    public NewsPhotoDetail(){}

    private String title;
    private List<PictureItem> pictureItemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PictureItem> getPictureItemList() {
        return pictureItemList;
    }

    public void setPictureItemList(List<PictureItem> pictureItemList) {
        this.pictureItemList = pictureItemList;
    }

    protected NewsPhotoDetail(Parcel in) {
        title = in.readString();
        pictureItemList = new ArrayList<>();
        in.readList(pictureItemList,PictureItem.class.getClassLoader());
    }

    public static final Creator<NewsPhotoDetail> CREATOR = new Creator<NewsPhotoDetail>() {
        @Override
        public NewsPhotoDetail createFromParcel(Parcel in) {
            return new NewsPhotoDetail(in);
        }

        @Override
        public NewsPhotoDetail[] newArray(int size) {
            return new NewsPhotoDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeList(pictureItemList);
    }

    public static class PictureItem implements Parcelable{

        private String description;
        private String imgPath;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public PictureItem() {
        }

        protected PictureItem(Parcel in) {
            description = in.readString();
            imgPath = in.readString();
        }

        public static final Creator<PictureItem> CREATOR = new Creator<PictureItem>() {
            @Override
            public PictureItem createFromParcel(Parcel in) {
                return new PictureItem(in);
            }

            @Override
            public PictureItem[] newArray(int size) {
                return new PictureItem[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(description);
            dest.writeString(imgPath);
        }
    }
}
