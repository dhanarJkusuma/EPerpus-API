package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto;

public class TransactionMemberDto {

    private String publicId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String company;

    public TransactionMemberDto() {
    }

    protected TransactionMemberDto(Builder builder){
        setPublicId(builder.publicId);
        setName(builder.name);
        setPhone(builder.phone);
        setAddress(builder.address);
        setEmail(builder.email);
        setCompany(builder.company);
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public static class Builder{
        String publicId;
        String name;
        String phone;
        String address;
        String email;
        String company;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder publicId(String val){
            this.publicId = val;
            return this;
        }

        public Builder name(String val){
            this.name = val;
            return this;
        }

        public Builder phone(String val){
            this.phone = val;
            return this;
        }

        public Builder address(String val){
            this.address = val;
            return this;
        }

        public Builder email(String val){
            this.email = val;
            return this;
        }

        public Builder company(String val){
            this.company = val;
            return this;
        }

        public TransactionMemberDto build(){
            return new TransactionMemberDto(this);
        }

    }
}
