package pl.lifelesspixels.lpchestgui.data;

import java.util.function.Function;

public class ChestGUIClickAction {

    private final Function<ChestGUIClickActionContext, ChestGUIClickActionResult> leftClickCallback;
    private final Function<ChestGUIClickActionContext, ChestGUIClickActionResult> rightClickCallback;
    private final Function<ChestGUIClickActionContext, ChestGUIClickActionResult> shiftLeftClickCallback;
    private final Function<ChestGUIClickActionContext, ChestGUIClickActionResult> shiftRightClickCallback;

    ChestGUIClickAction(Function<ChestGUIClickActionContext, ChestGUIClickActionResult> leftClick,
                               Function<ChestGUIClickActionContext, ChestGUIClickActionResult> rightClick,
                               Function<ChestGUIClickActionContext, ChestGUIClickActionResult> shiftLeftClick,
                               Function<ChestGUIClickActionContext, ChestGUIClickActionResult> shiftRightClick) {
        this.leftClickCallback = leftClick;
        this.rightClickCallback = rightClick;
        this.shiftLeftClickCallback = shiftLeftClick;
        this.shiftRightClickCallback = shiftRightClick;
    }

    public ChestGUIClickActionResult runCallback(ChestGUIClickActionContext context, ClickType clickType) {
        return switch (clickType) {
            case Left -> runLeftClickCallback(context);
            case Right -> runRightClickCallback(context);
            case ShiftLeft -> runShiftLeftClickCallback(context);
            case ShiftRight -> runShiftRightClickCallback(context);
        };
    }

    private ChestGUIClickActionResult runLeftClickCallback(ChestGUIClickActionContext context) {
        if(leftClickCallback != null)
            return leftClickCallback.apply(context);
        return ChestGUIClickActionResult.RemainOpen;
    }

    private ChestGUIClickActionResult runRightClickCallback(ChestGUIClickActionContext context) {
        if(rightClickCallback != null)
            return rightClickCallback.apply(context);
        return ChestGUIClickActionResult.RemainOpen;
    }

    private ChestGUIClickActionResult runShiftLeftClickCallback(ChestGUIClickActionContext context) {
        if(shiftLeftClickCallback != null)
            return shiftLeftClickCallback.apply(context);
        return ChestGUIClickActionResult.RemainOpen;
    }

    private ChestGUIClickActionResult runShiftRightClickCallback(ChestGUIClickActionContext context) {
        if(shiftRightClickCallback != null)
            return shiftRightClickCallback.apply(context);
        return ChestGUIClickActionResult.RemainOpen;
    }

}
