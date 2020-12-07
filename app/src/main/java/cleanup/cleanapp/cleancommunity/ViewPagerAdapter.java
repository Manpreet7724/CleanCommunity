package cleanup.cleanapp.cleancommunity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;

    public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Gps_Fragment gps_fragment = new Gps_Fragment();
        Settings_Fragment settings_fragment = new Settings_Fragment();
        switch (position) {
            case 1:
                return settings_fragment;
            default:
                return gps_fragment;
            }
        }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
