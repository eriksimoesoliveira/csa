package com.eriks.android;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
//        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
//        return new IOSApplication(UIController.INSTANCE, config);
        return null;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}