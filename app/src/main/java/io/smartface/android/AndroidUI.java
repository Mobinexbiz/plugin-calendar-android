package io.smartface.android;


/**
 * This provides access to the JavaScript engine.This class has interfaces that give plugin views
 * access to some of the existing Smartface functionality.
 * <p/>
 * For example, if the plugin class is a UI plugin called MyView, JavaScript developer can create
 * instance var v = new MyView(); After this, the developer can write v.left or v.visible like
 * other Smartface UI objects.
 * <p/>
 * This structure with geometry will be triggered and it must be implemented in plugin UI view.
 */
public class AndroidUI {

    public AndroidUI() {
    }

    public interface WithGeometry {
        void setPosition__N(int left, int top, int width, int height, int right, int bottom);

        void setVisible__N(boolean visible, boolean enabled, boolean showOnTop);

        void setElevation__N(float elevation);

        void resetZ__N(float alpha);
    }
}
