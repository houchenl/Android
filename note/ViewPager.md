
# ViewPager

`ViewPager`用来左右滑动显示全屏页面，页面内容由`PagerAdapter`及其子类提供。`ViewPager`一般与`Fragment`配合使用，这样方便管理每一页的生命周期。当使用`Fragment`显示每个页面时，`PagerAdapter`有两个已实现的子类`FragmentPagerAdapter`和`FragmentStatePagerAdapter`。  

## 基本使用 Slide between fragments using ViewPager

#### Create the views

## 缓存
`ViewPager`默认情况下，预加载当前页和左右两页，共三页。使用Fragment实现时，这三页的`onCreate`、`onCreateView`、`onStart`，`onResume`都执行，表示虽然界面只看到一页，但代码中相当于三页同时显示。  
当滑动页面到第n页时，第n-1页，第n+1页的上述四个方法都会执行。而第n-2页滑出缓存范围时，它的`onPause`、`onStop`、`onDestroy`会执行，直接销毁该fragment。  

