package com.jkb.core.data.search.detail.subject;

import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.search.detail.SearchData;

import java.util.List;

/**
 * 搜索的专题数据
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchSubjectData extends SearchData {

    //data
    private int id;
    private String dtype;
    private String title;
    private List<String> img;
    private String doc;
    private boolean tag;
    private int count_of_favorite;

    public SearchSubjectData(SearchEntity.DataBean dataBean) {
        handleSubjectData(dataBean);
    }

    @Override
    public SearchData getSearchData() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理专题数据
     */
    private void handleSubjectData(SearchEntity.DataBean dataBean) {
        SearchEntity.DataBean.SubjectBean subject = dataBean.getSubject();
        if (subject == null) {
            isDataEffective = false;
            return;
        }
        SearchEntity.DataBean.SubjectBean.ContentBean content = subject.getContent();
        if (content == null) {
            isDataEffective = false;
            return;
        }
        setDtype(subject.getDtype());
        setId(subject.getId());
        setTitle(subject.getTitle());
        setDoc(content.getDoc());
        setImg(content.getImg());
        setTag(subject.getTag()!=0);
        setCount_of_favorite(subject.getCount_of_favorite());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public boolean getTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public int getCount_of_favorite() {
        return count_of_favorite;
    }

    public void setCount_of_favorite(int count_of_favorite) {
        this.count_of_favorite = count_of_favorite;
    }
}
