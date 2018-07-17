package the.client.guys.binding.lwjgl2.internal;

import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import the.client.guys.binding.internal.AbstractBinding;
import the.client.guys.binding.lwjgl2.LWJGLButton;
import the.client.guys.binding.lwjgl2.LWJGLKey;

import java.util.Set;

/**
 * @author Foundry
 */
public final class LWJGLBinding extends AbstractBinding<LWJGLKey, LWJGLButton> {

    private final Runnable action;

    private final Int2BooleanMap keyStates;

    private final Int2BooleanMap buttonStates;

    private final int targetNumKeysDown;

    private final int targetNumButtonsDown;

    private int currentNumKeysDown;

    private int currentNumButtonsDown;

    public LWJGLBinding(final Runnable action, final Set<LWJGLKey> keys, final Set<LWJGLButton> buttons) {
        super(keys, buttons);
        this.action = action;

        this.keyStates = new Int2BooleanOpenHashMap(keys.size());
        for (final LWJGLKey key : keys) {
            this.keyStates.put(key.getKeyIndex(), false);
        }
        this.targetNumKeysDown = keys.size();

        this.buttonStates = new Int2BooleanOpenHashMap(buttons.size());
        for (final LWJGLButton button : buttons) {
            this.buttonStates.put(button.getButtonIndex(), false);
        }
        this.targetNumButtonsDown = buttons.size();
    }

    private static boolean doesMapContainKeyAndValueEqualTo(final Int2BooleanMap map, final int key, final boolean requiredValue) {
        if (map.containsKey(key)) {
            return map.get(key) == requiredValue;
        }
        return false;
    }

    private boolean isKeyRequiredAndStateEqualTo(final int keyCode, final boolean requiredState) {
        return doesMapContainKeyAndValueEqualTo(this.keyStates, keyCode, requiredState);
    }

    private boolean isButtonRequiredAndStateEqualTo(final int buttonCode, final boolean requiredState) {
        return doesMapContainKeyAndValueEqualTo(this.buttonStates, buttonCode, requiredState);
    }

    private boolean shouldDoAction() {
        return this.currentNumKeysDown == this.targetNumKeysDown
                && this.currentNumButtonsDown == this.targetNumButtonsDown;
    }

    @Override
    public void onKeyPress(final LWJGLKey key) {
        final int keyCode = key.getKeyIndex();
        if (isKeyRequiredAndStateEqualTo(keyCode, false)) {
            this.keyStates.put(keyCode, true);
            this.currentNumKeysDown += 1;
            if (shouldDoAction()) this.action.run();
        }
    }

    @Override
    public void onKeyRelease(final LWJGLKey key) {
        final int keyCode = key.getKeyIndex();
        if (isKeyRequiredAndStateEqualTo(keyCode, true)) {
            this.keyStates.put(keyCode, false);
            this.currentNumKeysDown -= 1;
        }
    }

    @Override
    public void onButtonPress(final LWJGLButton button) {
        final int buttonCode = button.getButtonIndex();
        if (isButtonRequiredAndStateEqualTo(buttonCode, false)) {
            this.buttonStates.put(buttonCode, true);
            this.currentNumButtonsDown += 1;
            if (shouldDoAction()) this.action.run();
        }
    }

    @Override
    public void onButtonRelease(final LWJGLButton button) {
        final int buttonCode = button.getButtonIndex();
        if (isButtonRequiredAndStateEqualTo(buttonCode, true)) {
            this.buttonStates.put(buttonCode, false);
            this.currentNumButtonsDown -= 1;
        }
    }
}
