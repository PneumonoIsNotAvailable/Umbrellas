package net.pneumono.umbrellas.content;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?} else {
/*import net.minecraft.client.renderer.RenderType;
*///?}

//? if >=1.21.9 {
import net.minecraft.util.Unit;
//?}

//? if <1.21.6 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
*///?}

public abstract class UmbrellaModel extends /*? if >=1.21.9 {*/Model<Unit>/*?} else {*//*Model*//*?}*/ {
    public static final String HANDLE = "handle";
    public static final String CANOPY = "canopy";
    //? if <1.21.6
    //private final ModelPart root;

    public UmbrellaModel(ModelPart root) {
        //? if >=1.21.6 {
        super(root, RenderTypes::entitySolid);
        //?} else {
        /*super(RenderTypes::entitySolid);
        this.root = root;
        *///?}
    }

    //? if <1.21 {
    /*@Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        this.root.render(poseStack, vertexConsumer, i, j, f, g, h, k);
    }
    *///?} else if <1.21.6 {
    /*@Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        this.root.render(poseStack, vertexConsumer, i, j, k);
    }
    *///?}

    //? if <1.21.6 {
    /*public ModelPart root() {
        return this.root;
    }
    *///?}

    public static class Handle extends UmbrellaModel {
        public Handle(ModelPart root) {
            super(root);
        }

        public static LayerDefinition createLayer() {
            MeshDefinition meshDefinition = new MeshDefinition();
            PartDefinition partDefinition = meshDefinition.getRoot();

            partDefinition.addOrReplaceChild(HANDLE, CubeListBuilder.create()
                    .texOffs(0, 62)
                    .addBox(8.0F, -21.0F, -9.0F, 1.0F, 29.0F, 1.0F),
                    PartPose.offset(-1.0F, -8.0F, 1.0F)
            );

            return LayerDefinition.create(meshDefinition, 128, 128);
        }
    }

    public static class Canopy extends UmbrellaModel {
        public Canopy(ModelPart root) {
            super(root);
        }

        public static LayerDefinition createLayer() {
            MeshDefinition meshDefinition = new MeshDefinition();
            PartDefinition partDefinition = meshDefinition.getRoot();

            partDefinition.addOrReplaceChild(CANOPY, CubeListBuilder.create()
                    .texOffs(0, 46).addBox(1.0F, -21.0F, -16.0F, 15.0F, 1.0F, 15.0F)
                    .texOffs(0, 24).addBox(-1.0F, -20.0F, -18.0F, 19.0F, 3.0F, 19.0F)
                    .texOffs(0, 0).addBox(-2.0F, -17.0F, -19.0F, 21.0F, 3.0F, 21.0F),
                    PartPose.offset(-1.0F, -8.0F, 1.0F)
            );

            return LayerDefinition.create(meshDefinition, 128, 128);
        }
    }
}
