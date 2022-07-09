package com.eyezah.cosmetics.mixin;

import com.eyezah.cosmetics.Cosmetica;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mixin(Options.class)
public class OptionsMixin {
	@Shadow
	@Final
	@Mutable
	public KeyMapping[] keyMappings;

	@Shadow @Final public KeyMapping keyAdvancements;

	@Inject(at = @At("RETURN"), method = "<init>")
	private void afterInit(Minecraft minecraft, File file, CallbackInfo ci) {
		List<KeyMapping> moddedKeyMappings = new ArrayList<>();
		Cosmetica.registerKeyMappings(moddedKeyMappings);

		KeyMapping[] newKeyMappings = new KeyMapping[this.keyMappings.length + moddedKeyMappings.size()];
		System.arraycopy(keyMappings, 0, newKeyMappings, 0, keyMappings.length);
		System.arraycopy(moddedKeyMappings.toArray(new KeyMapping[0]), 0, newKeyMappings, keyMappings.length, moddedKeyMappings.size());
		keyMappings = newKeyMappings;
	}
}
