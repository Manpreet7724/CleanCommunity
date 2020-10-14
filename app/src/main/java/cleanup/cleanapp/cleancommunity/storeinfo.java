package cleanup.cleanapp.cleancommunity;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class storeinfo
{
    static ArrayList<account> accounts = new ArrayList<>();


    public static int adddata(String name, String email, String phone, String pass, String conPass)
    {
        final String TAG = "singUp";
        if(!accounts.isEmpty())
        {
            if (conPass.equals(pass))
            {
                boolean foundAt = email.contains("@");
                if (foundAt)
                {

                    for (int i = 0; i <= accounts.size() + 1; i++)
                    {
                        account hold = accounts.get(i);
                        if (hold.getEmail().equals(email))
                        {
                            return -1;
                        }
                        if (i == accounts.size())
                        {
                            accounts.add(new account(name, pass, phone, email));
                            return 1;
                        }
                    }


                }
            }
        }
        else
            {
            accounts.add(new account(name, pass, phone, email));
                return 1;
            }

        return -1;
    }
    public static int  templogin(String email, String pass)
    {
        final String TAG = "login";
        Log.d(TAG, "0");
        if(!accounts.isEmpty())
        {
            for (int i = 0; i <= accounts.size(); i++)
            {
                Log.d(TAG, "1");
                account hold = accounts.get(i);
                if (hold.getEmail().equals(email))
                {
                    Log.d(TAG, "2");
                    if (hold.getEmail().equals(email))
                    {
                        Log.d(TAG, "3");
                        if (hold.getPass().equals(pass))
                        {
                            Log.d(TAG, "pass");
                            return  1;
                        }
                    }
                }
            }
        }
        return -1;
    }


}
