package the.client.guys.binding.lwjgl2;

import org.lwjgl.input.Keyboard;
import the.client.guys.binding.InteractionVisitor;
import the.client.guys.binding.key.KeyboardKey;

import java.util.Objects;

/**
 * @author Foundry
 */
public final class LWJGLKey implements KeyboardKey<LWJGLKey, LWJGLButton> {

    private final int keyIndex;

    private final String keyName;

    private LWJGLKey(final int keyIndex, final String keyName) {
        this.keyName = Keyboard.getKeyName(keyIndex);
        this.keyIndex = keyIndex;
    }

    public static LWJGLKey fromName(final String keyName) {
        final int keyIndex = Keyboard.getKeyIndex(keyName);
        if (keyIndex != -1) {
            return new LWJGLKey(keyIndex, keyName);
        } else {
            throw new IllegalArgumentException("Key name '" + keyName + "' does not map to a key index");
        }
    }

    public static LWJGLKey fromIndex(final int keyIndex) {
        final String keyName = Keyboard.getKeyName(keyIndex);
        return new LWJGLKey(keyIndex, keyName == null ? Integer.toString(keyIndex) : keyName);
    }

    @Override
    public String getKeyName() {
        return this.keyName;
    }

    public int getKeyIndex() {
        return this.keyIndex;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LWJGLKey lwjglKey = (LWJGLKey) o;
        return this.keyIndex == lwjglKey.keyIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyIndex);
    }

    @Override
    public <Visitor extends InteractionVisitor<? super LWJGLKey, ? super LWJGLButton>> Visitor visit(final Visitor visitor) {
        visitor.visitKeyboardKey(this);
        return visitor;
    }
}
