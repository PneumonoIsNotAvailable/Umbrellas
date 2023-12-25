package net.pneumono.umbrellas;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.Configs;

public class UmbrellasModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Configs.hasConfigs(Umbrellas.MOD_ID) ? parent -> new ConfigOptionsScreen(parent, Umbrellas.MOD_ID) : null;
    }
}
