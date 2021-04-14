package com.ylg.myretrofittest;

import java.util.List;

import androidx.annotation.NonNull;

public class Translation {

    private String type;
    private int errorCode;
    private int elapsedTime;
    private List<List<TranslateResultBean>> translateResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<List<TranslateResultBean>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(List<List<TranslateResultBean>> translateResult) {
        this.translateResult = translateResult;
    }

   /* @NonNull
    @Override
    public String toString() {
        return "{type = " + type + " , errorCode = " + errorCode
                + " , elapsedTime= " + elapsedTime + " ,[translateResult= " + translateResult + "] }";
    }*/

    public static class TranslateResultBean {

        public String src;
        public String tgt;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTgt() {
            return tgt;
        }

        public void setTgt(String tgt) {
            this.tgt = tgt;
        }

        @NonNull
        @Override
        public String toString() {
            return "{src= " + src + " ,tgt= " + tgt + "}";
        }
    }

}




