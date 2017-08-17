package id.co.imastudio.bakingapp3;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by idn on 8/17/2017.
 */


class SimpleIdlingResource implements IdlingResource {

    private AtomicBoolean msIdleNow = new AtomicBoolean(true);
    @Nullable
    private volatile ResourceCallback mCallback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return msIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdleState(boolean idleState){
        msIdleNow.set(isIdleNow());
        if (isIdleNow() && mCallback != null){
            mCallback.onTransitionToIdle();
        }
    }
}
