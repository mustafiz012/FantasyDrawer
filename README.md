# FantasySlide

[![DOWNLOAD](https://api.bintray.com/packages/mzule/maven/fantasy-slide/images/download.svg)](https://bintray.com/mzule/maven/fantasy-slide/_latestVersion)
[![API](https://img.shields.io/badge/API-15%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=15)
<a href="http://www.methodscount.com/?lib=com.github.mzule.fantasyslide%3Alibrary%3A1.0.4"><img src="https://img.shields.io/badge/Methods and size-core: 142 | deps: 15054 | 24 KB-e91e63.svg"/></a>
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FantasySlide-green.svg?style=flat)](http://android-arsenal.com/details/1/4309)

一个 DrawerLayout The extension has a handsome animation and innovative interaction. A gesture completes the slide out of the sidebar and selects the menu. Welcome to download the demo experience.

<https://raw.githubusercontent.com/mzule/FantasySlide/master/demo.apk>

## effect

![](https://raw.githubusercontent.com/mzule/FantasySlide/master/sample.gif)


## Instructions

### Add dependency

``` groovy
implementation 'com.github.mzule.fantasyslide:library:1.0.5'
```

### transfer

The calling method is basically DrawerLayout Consistent. This project supports around (start left end right) Sidebar is defined at the same time

``` xml
<com.github.mzule.fantasyslide.FantasyDrawerLayout xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"
        android:src="@drawable/fake" />

    <com.github.mzule.fantasyslide.SideBar
        android:id="@+id/leftSideBar"
        android:layout_width="200dp"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@color/colorPrimary"
        app:maxTranslationX="66dp">
        
        <!-- Here is a subview of SideBar -->
        
    </com.github.mzule.fantasyslide.SideBar>
    <!-- If necessary, you can add the right sidebar -->
</com.github.mzule.fantasyslide.FantasyDrawerLayout>

```
1. The use of the outermost FantasyDrawerLayout is identical to the official DrawerLayout.
2. SideBar is used to wrap every menu item, and SideBar can essentially be used as a vertical LinearLayout.
3. The color change on the renderings indicates that the menu is in the hover state. By default, the hover state sets the pressed state of the view to true. It can be rewritten by Listener, which will be explained in detail below.
4. Detailed reference <https://github.com/mustafiz012/FantasySlide/blob/master/app/src/main/res/layout/activity_main.xml>



## Advanced

### maxTranslationX

The maximum displacement of the menu item animation can be set by setting maxTranslationX. Only valid when using the default Transformer.

``` xml
<com.github.mzule.fantasyslide.SideBar
	...
    app:maxTranslationX="88dp">
```
In general, the left sidebar maxTranslationX is a positive number and the right sidebar maxTranslationX is a negative number.


### Listener

Support for setting the Listener to monitor the state of the sidebar menu.

``` java
SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
leftSideBar.setFantasyListener(new SimpleFantasyListener() {
    @Override
    public boolean onHover(@Nullable View view) {
    	return false;
    }

    @Override
    public boolean onSelect(View view) {
        return false;
    }

    @Override
    public void onCancel() {
    }
});
```

1. Hover refers to the highlighted state in the above effect diagram. At this time, the finger is still on the screen. The default hover processing logic is to set the pressed state of the view to true. Rewriting the onHover(View) method returns true to override the default. logic.
2. Select refers to the finger off the screen when the hover state triggers the select state. The default processing logic is to call the view's onClick event. Overriding the onSelect(View) method returns true to override the default logic.
3. Cancel means that when the finger leaves the screen, if there is no view to trigger the select state, it is cancel, there is no default processing logic.

### Transformer

The project designed a Transformer interface for the caller to customize the animation of the menu items. The method is similar to PageTransformer of ViewPager.

``` java
class DefaultTransformer implements Transformer {
    private float maxTranslationX;

    DefaultTransformer(float maxTranslationX) {
        this.maxTranslationX = maxTranslationX;
    }

    @Override
    public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
        float translationX;
        int centerY = itemView.getTop() + itemView.getHeight() / 2;
        float distance = Math.abs(touchY - centerY);
        float scale = distance / sideBar.getHeight() * 3; // 距离中心点距离与 sideBar 的 1/3 对比
        if (isLeft) {
            translationX = Math.max(0, maxTranslationX - scale * maxTranslationX);
        } else {
            translationX = Math.min(0, maxTranslationX - scale * maxTranslationX);
        }
        itemView.setTranslationX(translationX * slideOffset);
    }
}
```

## thank

Animation effect reference dribbble. <https://dribbble.com/shots/2269140-Force-Touch-Slide-Menu> Thank you very much.

In addition, the right side of the MainActivity in the demo implements a menu animation similar to the original. For details, please refer to the relevant code.

## license

Apache License  2.0

## contact me

Any related questions can be contacted by me in the following ways.

Issue
1. Mail "mustafiz".concat("012").concat("@").concat("outlook.com")
