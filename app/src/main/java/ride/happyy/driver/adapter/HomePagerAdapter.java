package ride.happyy.driver.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ride.happyy.driver.fragments.AccountsFragment;
import ride.happyy.driver.fragments.EarningsFragment;
import ride.happyy.driver.fragments.HomeFragment;
import ride.happyy.driver.fragments.LeaderBordFragmentNew;
import ride.happyy.driver.fragments.RatingsFragment;


public class HomePagerAdapter extends FragmentPagerAdapter {

    private HomeFragment homeFragment;
    private EarningsFragment earningsFragment;
   // private LeaderBordFragment leaderBordFragment;
    private LeaderBordFragmentNew leaderBordFragmentNew;
    private RatingsFragment ratingsFragment;
    private AccountsFragment accountsFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                return homeFragment;
            case 1:
                if (earningsFragment == null) {
                    earningsFragment = new EarningsFragment();
                }
                return earningsFragment;

            case 2:
                if (leaderBordFragmentNew == null) {
                    leaderBordFragmentNew = new LeaderBordFragmentNew();
                }
                return leaderBordFragmentNew;
            case 3:
                if (ratingsFragment == null) {
                    ratingsFragment = new RatingsFragment();
                }
                return ratingsFragment;
            case 4:
                if (accountsFragment == null) {
                    accountsFragment = new AccountsFragment();
                }
                return accountsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
