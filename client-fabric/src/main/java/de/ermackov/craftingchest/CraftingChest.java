package de.ermackov.craftingchest;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class CraftingChest implements ModInitializer {
	public static final String MOD_ID = "mxcraftingchest";

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.clientboundPlay().register(ChestInfoPayload.TYPE, ChestInfoPayload.CODEC);
	}
}
