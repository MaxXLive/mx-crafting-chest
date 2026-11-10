package de.ermackov.craftingchest;

import net.minecraft.network.chat.Component;

/**
 * Implemented by {@link net.minecraft.world.inventory.CraftingMenu} through a mixin.
 */
public interface CraftingChestMenu {
	/** Index of the first chest slot; the vanilla crafting menu uses slots 0-45. */
	int CHEST_SLOT_START = 46;
	int PANEL_WIDTH = 176;

	int mxcraftingchest$getChestRows();

	Component mxcraftingchest$getChestTitle();

	boolean mxcraftingchest$isPanelVisible();

	void mxcraftingchest$setPanelVisible(boolean visible);

	default boolean mxcraftingchest$hasChest() {
		return this.mxcraftingchest$getChestRows() > 0;
	}

	default int mxcraftingchest$getPanelHeight() {
		return this.mxcraftingchest$getChestRows() * 18 + 24;
	}
}
