package com.kf.regreen.regreenproject.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.kf.regreen.regreenproject.R;

import java.lang.reflect.Field;

public class CustomBottomNavigationView extends BottomNavigationView {

    //Value position for badge
    final SparseArray<Integer> badgePositionValue = new SparseArray<Integer>();

    private Drawable mDrawableBadge;

    //see guideline https://material.io/guidelines/components/bottom-navigation.html#bottom-navigation-specs
    private final int PX_MAX_ITEM_WIDTH = convertDpToPx(169); //check  if 169 is better in despite of guideline, getwidth() return 169

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_navigation_bottom_bar);
        mDrawableBadge = a.getDrawable(R.styleable.custom_navigation_bottom_bar_badge);
        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Adding badges to each Item
        BottomNavigationMenuView menuView = getBottomMenuView();
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);

//            int width = convertDpToPx(16);
//            int heigth = width;

            //Inflate TextView badge
            TextView textView = (TextView) View.inflate(getContext(), R.layout.tab_navbar_badge_count, null);
            textView.setVisibility(GONE);
//            textView.setMinimumWidth(width);
//            int padding = convertDpToPx(3);
//            textView.setPadding(padding, 0, padding, 0);
            //Build Layout Param of badge
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParam.gravity = Gravity.LEFT;
//            int screenWitdh = getDeviceWidth();
//            screenWitdh = screenWitdh > PX_MAX_ITEM_WIDTH * menuView.getChildCount() ? PX_MAX_ITEM_WIDTH * menuView.getChildCount() : screenWitdh;
//            int marginTop = convertDpToPx(10);
//            int marginRight = (int) (((screenWitdh / menuView.getChildCount()) * 0.5) - marginTop * 1.8);
            layoutParam.setMargins(5, 10, 0, 0);
            //Add it to the item
            item.addView(textView, layoutParam);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        disableShiftingMode();
        calculateBadgesPosition();
    }

    @SuppressLint("RestrictedApi")
    public void disableShiftingMode() {
        //This disables the shifting mode (which makes tab grow on select)
        BottomNavigationMenuView menuView = getBottomMenuView();
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    // allow to get the BottomMenuView
    private BottomNavigationMenuView getBottomMenuView() {
        Object menuView = null;
        try {
            Field field = BottomNavigationView.class.getDeclaredField("mMenuView");
            field.setAccessible(true);
            menuView = field.get(this);
        } catch (NoSuchFieldException e) {
            //   Crashlytics.logException(e);
        } catch (IllegalAccessException e) {
            // Crashlytics.logException(e);
        }

        return (BottomNavigationMenuView) menuView;
    }

    /**
     * This method place dynamically badge on the bottom bar
     * Since the  MenuItemCompat.getActionView() /  MenuItemCompat.setActionView() is not well supported
     * see more : http://techqa.info/programming/question/42087027/badge-on-bottomnavigationview
     * TODO each new Version of BottomNavigationView Check if this ActionView behaviour is fixed
     */
    private void calculateBadgesPosition() {
        //Manage display of badges
        BottomNavigationMenuView menuView = getBottomMenuView();
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            //noinspection RestrictedApi
            Integer value = badgePositionValue.get(i);
            TextView badge = (TextView) item.findViewById(R.id.bottom_bar_badge_id);
            if (badge != null) {
                setBadge(badge, value);
                if (mDrawableBadge != null) {
                    Drawable drawable = mDrawableBadge.getConstantState() != null ? mDrawableBadge.getConstantState().newDrawable() : null;
                    badge.setBackground(drawable);
                }
            }
        }
    }

    private void setBadge(TextView badge, Integer count) {
        //set Value
        if (count != null && count > 0) {
            badge.setText(String.valueOf(count));
            badge.setVisibility(VISIBLE);
        } else {
            badge.setVisibility(GONE);
        }
    }

    public void setBadgePositionValue(int position, int count) {
        badgePositionValue.put(position, count);
        //refresh badges
        calculateBadgesPosition();
    }

    //this is used to programmatically select a tab.
    public boolean setSelected(int index) {
        boolean success = false;
        final BottomNavigationMenuView menu = getBottomMenuView();
        if (menu.getChildCount() > index) {
            final View item = menu.getChildAt(index);
            if (item != null && item instanceof BottomNavigationItemView) {
                success = item.performClick();
            }
        }
        return success;
    }

    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    private int getDeviceWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }
}