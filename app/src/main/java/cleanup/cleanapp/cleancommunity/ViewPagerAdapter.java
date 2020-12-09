/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

package cleanup.cleanapp.cleancommunity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int totalTabs;

    @SuppressWarnings("deprecation")
    public ViewPagerAdapter(FragmentManager fm, int totalTabs)
    {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Gps_Fragment gps_fragment = new Gps_Fragment();
        Settings_Fragment settings_fragment = new Settings_Fragment();

        if (position == 1) {
            return settings_fragment;
        }
        return gps_fragment;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }


}
