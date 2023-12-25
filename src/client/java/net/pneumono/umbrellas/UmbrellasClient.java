package net.pneumono.umbrellas;

import net.fabricmc.api.ClientModInitializer;
import net.pneumono.umbrellas.content.ModClientContent;

public class UmbrellasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModClientContent.registerColorProviders();
	}
}