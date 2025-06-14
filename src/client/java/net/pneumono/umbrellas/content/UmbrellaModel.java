package net.pneumono.umbrellas.content;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;

public class UmbrellaModel extends Model {
    private static final String CANOPY = "canopy";
    private static final String HANDLE = "handle";

    public UmbrellaModel(ModelPart root) {
        super(root, RenderLayer::getEntitySolid);
    }

    // Made with BlockBench
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(HANDLE, ModelPartBuilder.create().uv(0, 62).cuboid(8.0F, -21.0F, -9.0F, 1.0F, 29.0F, 1.0F), ModelTransform.origin(-1.0F, -8.0F, 1.0F));
        modelPartData.addChild(CANOPY, ModelPartBuilder.create().uv(0, 46).cuboid(1.0F, -21.0F, -16.0F, 15.0F, 1.0F, 15.0F)
                .uv(0, 24).cuboid(-1.0F, -20.0F, -18.0F, 19.0F, 3.0F, 19.0F)
                .uv(0, 0).cuboid(-2.0F, -17.0F, -19.0F, 21.0F, 3.0F, 21.0F), ModelTransform.origin(-1.0F, -8.0F, 1.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
