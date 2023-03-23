package pl.lifelesspixels.lpchestgui.data;

import java.util.function.Function;

public class GUIAction {

    private final Function<GUIActionContext, GUIActionResult> leftClickCallback;
    private final Function<GUIActionContext, GUIActionResult> rightClickCallback;
    private final Function<GUIActionContext, GUIActionResult> shiftLeftClickCallback;
    private final Function<GUIActionContext, GUIActionResult> shiftRightClickCallback;

    public GUIAction(Function<GUIActionContext, GUIActionResult> leftClick,
                     Function<GUIActionContext, GUIActionResult> rightClick,
                     Function<GUIActionContext, GUIActionResult> shiftLeftClick,
                     Function<GUIActionContext, GUIActionResult> shiftRightClick) {
        this.leftClickCallback = leftClick;
        this.rightClickCallback = rightClick;
        this.shiftLeftClickCallback = shiftLeftClick;
        this.shiftRightClickCallback = shiftRightClick;
    }

    public GUIActionResult runCallback(GUIActionContext context, ClickType clickType) {
        return switch (clickType) {
            case Left -> runLeftClickCallback(context);
            case Right -> runRightClickCallback(context);
            case ShiftLeft -> runShiftLeftClickCallback(context);
            case ShiftRight -> runShiftRightClickCallback(context);
        };
    }

    private GUIActionResult runLeftClickCallback(GUIActionContext context) {
        if(leftClickCallback != null)
            return leftClickCallback.apply(context);
        return GUIActionResult.RemainOpen;
    }

    private GUIActionResult runRightClickCallback(GUIActionContext context) {
        if(rightClickCallback != null)
            return rightClickCallback.apply(context);
        return GUIActionResult.RemainOpen;
    }

    private GUIActionResult runShiftLeftClickCallback(GUIActionContext context) {
        if(shiftLeftClickCallback != null)
            return shiftLeftClickCallback.apply(context);
        return GUIActionResult.RemainOpen;
    }

    private GUIActionResult runShiftRightClickCallback(GUIActionContext context) {
        if(shiftRightClickCallback != null)
            return shiftRightClickCallback.apply(context);
        return GUIActionResult.RemainOpen;
    }

}
