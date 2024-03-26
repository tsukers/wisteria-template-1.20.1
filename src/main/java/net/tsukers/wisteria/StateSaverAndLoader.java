package net.tsukers.wisteria;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class StateSaverAndLoader extends PersistentState {
    public Integer totalPointsEarned = 0;
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("totalPointsEarned", totalPointsEarned);
        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.totalPointsEarned = tag.getInt("totalPointsEarned");
        return state;
    }

    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::createFromNbt,
            null
    );

    // Hello

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, Wisteria.MOD_ID);

        state.markDirty();

        return state;
    }


}
