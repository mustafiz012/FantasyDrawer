package com.github.musta.fantasydrawer.app;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.musta.fantasydrawer.SideBar;
import com.github.musta.fantasydrawer.SimpleFantasyListener;
import com.github.musta.fantasydrawer.Transformer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(indicator);

//        setTransformer();
        // setListener();
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

    public void onClick(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            String title = textView.getText().toString();
            if (title.startsWith("星期")) {
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
            } else {
                startActivity(UniversalActivity.newIntent(this, title));
            }
        } else if (view.getId() == R.id.userInfo) {
            startActivity(UniversalActivity.newIntent(this, "个人中心"));
        }
    }

    private void setListener() {
        final TextView tipView = findViewById(R.id.tipView);
        SideBar leftSideBar = findViewById(R.id.leftSideBar);
        leftSideBar.setFantasyListener(new SimpleFantasyListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                tipView.setVisibility(View.VISIBLE);
                if (view instanceof TextView) {
                    tipView.setText(String.format("%s at %d", ((TextView) view).getText().toString(), index));
                } else if (view != null && view.getId() == R.id.userInfo) {
                    tipView.setText(String.format("个人中心 at %d", index));
                } else {
                    tipView.setText(null);
                }
                return false;

            }

            @Override
            public boolean onSelect(View view, int index) {
                tipView.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, String.format("%d selected", index), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onCancel() {
                tipView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setTransformer() {
        final float spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        SideBar rightSideBar = findViewById(R.id.rightSideBar);
        rightSideBar.setTransformer(new Transformer() {
            private View lastHoverView;

            @Override
            public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
                boolean hovered = itemView.isPressed();
                if (hovered && lastHoverView != itemView) {
                    animateIn(itemView);
                    animateOut(lastHoverView);
                    lastHoverView = itemView;
                }
            }

            private void animateOut(View view) {
                if (view == null) {
                    return;
                }
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -spacing, 0);
                translationX.setDuration(200);
                translationX.start();
            }

            private void animateIn(View view) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -spacing);
                translationX.setDuration(200);
                translationX.start();
            }
        });
    }
}
