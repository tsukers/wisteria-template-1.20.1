package net.tsukers.wisteria;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class WisteriaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(Wisteria.POINTS_EARNED, (client, handler, buf, responseSender) -> {
            int totalPointsEarned = buf.readInt();
            client.execute(() -> {
                client.player.sendMessage(Text.literal("Total dirt blocks broken: " + totalPointsEarned));
            });
        });
    }
}
