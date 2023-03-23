package pl.lifelesspixels.lpchestgui.data;

import java.util.function.Function;

public class GUIActionBuilder {

    private Function<GUIActionContext, GUIActionResult> leftCallback;
    private Function<GUIActionContext, GUIActionResult> rightCallback;
    private Function<GUIActionContext, GUIActionResult> shiftLeftCallback;
    private Function<GUIActionContext, GUIActionResult> shiftRightCallback;

    public GUIActionBuilder withLeftClickCallback(Function<GUIActionContext, GUIActionResult> callback) {
        leftCallback = callback;
        return this;
    }

    public GUIActionBuilder withRightClickCallback(Function<GUIActionContext, GUIActionResult> callback) {
        rightCallback = callback;
        return this;
    }

    public GUIActionBuilder withShiftLeftClickCallback(Function<GUIActionContext, GUIActionResult> callback) {
        shiftLeftCallback = callback;
        return this;
    }

    public GUIActionBuilder withShiftRightClickCallback(Function<GUIActionContext, GUIActionResult> callback) {
        shiftRightCallback = callback;
        return this;
    }

    public GUIAction build() {
        return new GUIAction(leftCallback, rightCallback, shiftLeftCallback, shiftRightCallback);
    }

}
