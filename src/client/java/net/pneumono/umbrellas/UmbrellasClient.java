package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.pneumono.umbrellas.content.UmbrellasClientRegistry;

public class UmbrellasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		UmbrellasClientRegistry.registerClientContent();
	}
}