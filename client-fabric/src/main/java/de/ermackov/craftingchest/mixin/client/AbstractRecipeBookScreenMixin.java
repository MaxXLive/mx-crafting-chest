package de.ermackov.craftingchest.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.ermackov.craftingchest.CraftingChestMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractRecipeBookScreen.class)
public abstract class AbstractRecipeBookScreenMixin<T extends RecipeBookMenu> extends AbstractContainerScreen<T> {
	@Shadow
	@Final
	private RecipeBookComponent<?> recipeBookComponent;

	private AbstractRecipeBookScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	/**
	 * The chest panel and the recipe book both live left of the crafting grid, so only one of them is shown.
	 * While the panel is shown, the crafting screen moves right so that both together are centered.
	 */
	@ModifyExpressionValue(
		method = {"init", "lambda$initButton$0"},
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;updateScreenPosition(II)I")
	)
	private int mxcraftingchest$makeRoomForChestPanel(int leftPos) {
		if (this.menu instanceof CraftingChestMenu chestMenu && chestMenu.mxcraftingchest$hasChest()) {
			boolean panelVisible = !this.recipeBookComponent.isVisible();
			chestMenu.mxcraftingchest$setPanelVisible(panelVisible);
			if (panelVisible) {
				return leftPos + CraftingChestMenu.PANEL_WIDTH / 2;
			}
		}
		return leftPos;
	}

	@Inject(method = "hasClickedOutside", at = @At("RETURN"), cancellable = true)
	private void mxcraftingchest$clickInsideChestPanel(double mx, double my, int xo, int yo, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValueZ() && this.menu instanceof CraftingChestMenu chestMenu && chestMenu.mxcraftingchest$hasChest() && chestMenu.mxcraftingchest$isPanelVisible()) {
			int panelLeft = this.leftPos - CraftingChestMenu.PANEL_WIDTH;
			if (mx >= panelLeft && mx < this.leftPos && my >= this.topPos && my < this.topPos + chestMenu.mxcraftingchest$getPanelHeight()) {
				cir.setReturnValue(false);
			}
		}
	}
}
