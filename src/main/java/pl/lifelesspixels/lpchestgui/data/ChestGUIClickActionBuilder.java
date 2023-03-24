package pl.lifelesspixels.lpchestgui.data;

import java.util.function.Function;

public class ChestGUIClickActionBuilder {

    private Function<ChestGUIClickActionContext, ChestGUIClickActionResult> leftCallback;
    private Function<ChestGUIClickActionContext, ChestGUIClickActionResult> rightCallback;
    private Function<ChestGUIClickActionContext, ChestGUIClickActionResult> shiftLeftCallback;
    private Function<ChestGUIClickActionContext, ChestGUIClickActionResult> shiftRightCallback;

    public ChestGUIClickActionBuilder withLeftClickCallback(Function<ChestGUIClickActionContext, ChestGUIClickActionResult> callback) {
        leftCallback = callback;
        return this;
    }

    public ChestGUIClickActionBuilder withRightClickCallback(Function<ChestGUIClickActionContext, ChestGUIClickActionResult> callback) {
        rightCallback = callback;
        return this;
    }

    public ChestGUIClickActionBuilder withShiftLeftClickCallback(Function<ChestGUIClickActionContext, ChestGUIClickActionResult> callback) {
        shiftLeftCallback = callback;
        return this;
    }

    public ChestGUIClickActionBuilder withShiftRightClickCallback(Function<ChestGUIClickActionContext, ChestGUIClickActionResult> callback) {
        shiftRightCallback = callback;
        return this;
    }

    public ChestGUIClickAction build() {
        return new ChestGUIClickAction(leftCallback, rightCallback, shiftLeftCallback, shiftRightCallback);
    }

}
