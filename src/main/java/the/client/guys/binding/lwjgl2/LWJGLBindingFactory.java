package the.client.guys.binding.lwjgl2;

import the.client.guys.binding.Binding;
import the.client.guys.binding.BindingFactory;
import the.client.guys.binding.Interaction;
import the.client.guys.binding.PartitioningBindingFactory;
import the.client.guys.binding.lwjgl2.internal.LWJGLBinding;

import java.util.Set;

/**
 * @author Foundry
 */
public enum LWJGLBindingFactory implements BindingFactory<LWJGLKey, LWJGLButton> {
    INSTANCE;

    private final BindingFactory<LWJGLKey, LWJGLButton> innerBindingFactory;

    LWJGLBindingFactory() {
        this.innerBindingFactory = new PartitioningBindingFactory<>(LWJGLBinding::new);
    }

    @Override
    public Binding<LWJGLKey, LWJGLButton> makeBinding(final Runnable action, final Set<Interaction<LWJGLKey, LWJGLButton>> interactions) {
        return innerBindingFactory.makeBinding(action, interactions);
    }
}
