package com.data;

import java.util.List;

/**
 * Created by karthik on 1/28/15.
 */
public class CaseDataModel {

    private List<Integer> pageIdList;
    private List<String> figCaptionList;
    private List<String> pageURLList;

    public List<Integer> getPageIdList() {
        return pageIdList;
    }

    public void setPageIdList(List<Integer> pageIdList) {
        this.pageIdList = pageIdList;
    }

    public List<String> getFigCaptionList() {
        return figCaptionList;
    }

    public void setFigCaptionList(List<String> figCaptionList) {
        this.figCaptionList = figCaptionList;
    }

    public List<String> getPageURLList() {
        return pageURLList;
    }

    public void setPageURLList(List<String> pageURLList) {
        this.pageURLList = pageURLList;
    }

}
