package cleanup.cleanapp.cleancommunity;

public class account
{
    String name;
    String pass;
    String phoneNum;
    String email;
    public account(String newName, String newPass,String newPhone, String newEmail)
    {
        this.name = newName;
        this.pass = newPass;
        this.phoneNum = newPhone;
        this.email = newEmail;
    }
    public String getName()
    {
        return name;
    }
    public String getPass()
    {
        return pass;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }
    public String getEmail()
    {
        return email;
    }

    public void setName(String newName)
    {
        this.name = newName;
    }
    public void setPass(String newPass)
    {
        this.name = newPass;
    }
    public void setPhoneNum(String newPhoneNum)
    {
        this.phoneNum =  newPhoneNum;
    }
    public void setEmail(String newEmail)
    {
        this.email =  newEmail;
    }
}
