package cn.jack.module_fragment_03.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @创建者 Jack
 * @创建时间 2021/3/12 10:01
 * @描述
 */
public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragments;

    public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.mFragments = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }

}

