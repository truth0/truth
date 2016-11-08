package com.google.common.truth;

import android.view.View;

import javax.annotation.Nullable;

/**
 * @author Kevin Leigh Crain
 */
public class ViewSubject extends Subject<ViewSubject, View> {

    public ViewSubject(FailureStrategy failureStrategy, @Nullable View actual) {
        super(failureStrategy, actual);
    }

    public final void isVisible() {
        if(actual().getVisibility() != View.VISIBLE) fail("is not visible");
    }

    public final void isNotVisible() {
        if(actual().getVisibility() == View.VISIBLE || actual().getVisibility() == View.GONE) fail("is visible or gone");
    }

    public static SubjectFactory<ViewSubject, View> views() {
        return FACTORY;
    }

    private static final SubjectFactory<ViewSubject, View> FACTORY = new SubjectFactory<ViewSubject, View>() {
        @Override
        public ViewSubject getSubject(FailureStrategy fs, View that) {
            return new ViewSubject(fs, that);
        }
    };
}
