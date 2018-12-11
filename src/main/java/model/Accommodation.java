package model;

public class Accommodation extends Addition {
    private String userName;
    private int vacId;
    private String placeName;
    private String address;
    private int grade;

    public Accommodation(String user,int id,String name, String address,int grade){
        this.userName=user;
        this.vacId=id;
        this.placeName=name;
        this.address=address;
        this.grade=grade;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVacId() {
        return vacId;
    }

    public void setVacId(int vacId) {
        this.vacId = vacId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
