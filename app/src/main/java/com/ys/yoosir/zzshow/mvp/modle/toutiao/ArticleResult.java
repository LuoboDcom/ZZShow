package com.ys.yoosir.zzshow.mvp.modle.toutiao;

/**
 * toutiao
 * Created by Yoosir on 2016/10/20 0020.
 */
public class ArticleResult<T> {

    private boolean     has_more;
    private String      message;
    private T           data;
    private ArticleNext next;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ArticleNext getNext() {
        return next;
    }

    public void setNext(ArticleNext next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "ArticleResult{" +
                "has_more=" + has_more +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
