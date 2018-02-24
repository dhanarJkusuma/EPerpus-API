package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto;


public class TransactionBookDto{

    private String code;
    private String title;
    private String author;
    private String editor;
    private String publisher;
    private int year;
    private int quantity;

    public TransactionBookDto() {
    }

    protected TransactionBookDto(Builder builder){
        setCode(builder.code);
        setTitle(builder.title);
        setAuthor(builder.author);
        setEditor(builder.editor);
        setPublisher(builder.publisher);
        setYear(builder.year);
        setQuantity(builder.quantity);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class Builder{

        String code;
        String title;
        String author;
        String editor;
        String publisher;
        int year;
        int quantity;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder code(String val){
            this.code = val;
            return this;
        }

        public Builder title(String val){
            this.title = val;
            return this;
        }

        public Builder author(String val){
            this.author = val;
            return this;
        }

        public Builder editor(String val){
            this.editor = val;
            return this;
        }

        public Builder publisher(String val){
            this.publisher = val;
            return this;
        }

        public Builder year(int val){
            this.year = val;
            return this;
        }

        public Builder quantity(int val){
            this.quantity = val;
            return this;
        }

        public TransactionBookDto build(){
            return new TransactionBookDto(this);
        }
    }
}
