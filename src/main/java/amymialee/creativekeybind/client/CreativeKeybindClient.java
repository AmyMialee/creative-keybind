package amymialee.creativekeybind.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class CreativeKeybindClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.creativekeybind.bind",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.creativekeybind.bind"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                try {
                    assert client.player != null;
                    if (client.player.hasPermissionLevel(2)) {
                        GameMode gameMode2;
                        assert client.interactionManager != null;
                        if (client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE) {
                            gameMode2 = GameMode.SURVIVAL;
                        } else {
                            gameMode2 = GameMode.CREATIVE;
                        }
                        client.interactionManager.setGameMode(gameMode2);
                    } else {
                        client.player.sendMessage(Text.translatable("creativekeybind.denied").formatted(Formatting.RED), true);
                    }
                } catch (Exception ignored) {}
            }
        });
    }
}