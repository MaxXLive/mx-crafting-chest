package de.ermackov.craftingchest.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import de.ermackov.craftingchest.ChestInfoPayload;
import de.ermackov.craftingchest.PendingChestInfo;

public class CraftingChestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(ChestInfoPayload.TYPE, (payload, context) -> PendingChestInfo.set(payload));
	}
}
