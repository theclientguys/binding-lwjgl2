package the.client.guys.binding.lwjgl2;

import org.lwjgl.input.Mouse;
import the.client.guys.binding.InteractionVisitor;
import the.client.guys.binding.button.MouseButton;

/**
 * @author Foundry
 */
public class LWJGLButton implements MouseButton<LWJGLKey, LWJGLButton> {

    private final int buttonIndex;

    private final String buttonName;

    private LWJGLButton(final int buttonIndex, final String buttonName) {
        this.buttonName = buttonName;
        this.buttonIndex = buttonIndex;
    }

    public static LWJGLButton fromName(final String buttonName) {
        final int buttonIndex = Mouse.getButtonIndex(buttonName);
        if (buttonIndex != -1) {
            return new LWJGLButton(buttonIndex, buttonName);
        } else {
            throw new IllegalArgumentException("Button name '" + buttonName + "' does not map to a button index");
        }
    }

    public static LWJGLButton fromIndex(final int buttonIndex) {
        final String buttonName = Mouse.getButtonName(buttonIndex);
        return new LWJGLButton(buttonIndex, buttonName == null ? Integer.toString(buttonIndex) : buttonName);
    }

    public int getButtonIndex() {
        return this.buttonIndex;
    }

    @Override
    public String getButtonName() {
        return this.buttonName;
    }


    @Override
    public <Visitor extends InteractionVisitor<? super LWJGLKey, ? super LWJGLButton>> Visitor visit(final Visitor visitor) {
        visitor.visitMouseButton(this);
        return visitor;
    }
}
