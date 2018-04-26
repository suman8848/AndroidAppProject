package com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sumankhatiwada on 4/25/18.
 */

public class CommentReq {

    @SerializedName("comments")
    @Expose
    private CommentObject comments;

    public CommentObject getComments() {
        return comments;
    }

    public void setComments(CommentObject comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "CommentReq{" +
                "comments=" + comments +
                '}';
    }
}
