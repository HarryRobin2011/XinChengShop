package com.banksoft.XinChengShop.widget.viewpagerindicator;

public interface IconPagerAdapter {
    /**
     * Get ic_launcher representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();
}
