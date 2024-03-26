package net.tsukers.wisteria;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wisteria implements ModInitializer {
	public static final String MOD_ID = "wisteria";
	public static final Logger LOGGER = LoggerFactory.getLogger("wisteria");

	public static final Identifier POINTS_EARNED = new Identifier(MOD_ID, "points_earned");
	private Integer totalPointsEarned = 0;



	@Override
	public void onInitialize() {
		LOGGER.info("Hello from tsukers :D");

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			if (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT) {
				StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
				// Increment the amount of dirt blocks that have been broken
				serverState.totalPointsEarned += 1;

				// Send a packet to the client
				MinecraftServer server = world.getServer();

				PacketByteBuf data = PacketByteBufs.create();
				data.writeInt(serverState.totalPointsEarned);

				ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
				server.execute(() -> {
					ServerPlayNetworking.send(playerEntity, POINTS_EARNED, data);
				});
			}
		});
	}
}