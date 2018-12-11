package model;

import sun.awt.image.ByteArrayImageSource;

import java.time.LocalDate;
import java.util.Date;

public class User {
     private String userName;
     private String password;
     private LocalDate birthday;
     private String fName;
     private String lName;
     private String address;
     private String email;
     private ByteArrayImageSource image;

     public User(String user,String pass,LocalDate d,String fn,String ln,String add, String e,ByteArrayImageSource pic){
          this.userName=user;
          this.password=pass;
          this.birthday=d;
          this.fName=fn;
          this.lName=ln;
          this.address=add;
          this.email=e;
          this.image=pic;

     }

     public String getUserName() {
          return userName;
     }

     public void setUserName(String userName) {
          this.userName = userName;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public LocalDate getBirthday() {
          return birthday;
     }

     public void setBirthday(LocalDate birthday) {
          this.birthday = birthday;
     }

     public String getfName() {
          return fName;
     }

     public void setfName(String fName) {
          this.fName = fName;
     }

     public String getlName() {
          return lName;
     }

     public void setlName(String lName) {
          this.lName = lName;
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

     public ByteArrayImageSource getImage() {
          return image;
     }

     public void setImage(ByteArrayImageSource image) {
          this.image = image;
     }
}


