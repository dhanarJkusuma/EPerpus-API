package id.veintechnology.apps.library.id.veintechnology.apps.common;

import java.util.List;

public class PaginationData {
    private List<?> contents;
    private int page;
    private int size;
    private int totalElements;

    public PaginationData() {
    }

    public PaginationData(List<?> contents, int page, int size, int totalElements) {
        this.contents = contents;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
    }

    protected PaginationData(Builder builder){
        setContent(builder.contents);
        setPage(builder.page);
        setSize(builder.size);
        setTotalElements(builder.totalElements);
    }

    public List<?> getContent() {
        return contents;
    }

    public void setContent(List<?> contents) {
        this.contents = contents;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public static class Builder{

        List<?> contents;
        int page;
        int size;
        int totalElements;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder contents(List<?> val){
            this.contents = val;
            return this;
        }

        public Builder page(int val){
            this.page = val;
            return this;
        }

        public Builder size(int val){
            this.size = val;
            return this;
        }

        public Builder totalElements(int val){
            this.totalElements = val;
            return this;
        }

        public PaginationData build(){
            return new PaginationData(this);
        }
    }
}
