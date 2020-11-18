package cleanup.cleanapp.cleancommunity;

public class UserData {
    public String name, email, phone, password;

    public UserData(){

    }

    public UserData(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
