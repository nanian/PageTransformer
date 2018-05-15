package brokermanager.dhffcw.com.zhongliganying;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.stx.xhb.xbanner.transformers.BasePageTransformer;

/**
 * author：${pgb} on 2018/5/15 11:46
 * Remarks：
 */
public class ScaleTransformer extends BasePageTransformer {
    private float mMinScale = 0.5f;

    public ScaleTransformer() {
    }

    public ScaleTransformer(float minScale) {
        setMinScale(minScale);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewCompat.setAlpha(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(mMinScale + (1 - mMinScale) * (1 + position));
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(mMinScale + (1 - mMinScale) * (1 - position));
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }

}
