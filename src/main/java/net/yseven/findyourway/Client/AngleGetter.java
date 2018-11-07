package net.yseven.findyourway.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.yseven.findyourway.CommonProxy;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class AngleGetter implements IItemPropertyGetter {
    @SideOnly(Side.CLIENT)
    private double prevAngle = 0.0D;
    @SideOnly(Side.CLIENT)
    private double prevWobble = 0.0D;
    @SideOnly(Side.CLIENT)
    private long prevWorldTime = 0L;
    @SideOnly(Side.CLIENT)
    private BlockPos blockPos;

    @Override
    @ParametersAreNonnullByDefault
    @SideOnly(Side.CLIENT)
    public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {

        boolean isLiving = entityIn != null;
        Entity errorChicken = new EntityChicken(Minecraft.getMinecraft().world);

        Entity entity;
        if(isLiving) {
            entity = entityIn;
        } else if(stack.getItemFrame() != null) {
            entity = stack.getItemFrame();
        } else {
            entity = errorChicken;
        }

        if (worldIn == null) worldIn = entity.world;

        double angle;
        setBlockPos(stack);

        if(blockPos != null) {
            if (blockPos.getY() == 0) return 0.0F;
            double entityAngle;
            if(isLiving) {
                entityAngle = entity.rotationYaw;
            } else if(stack.getItemFrame() != null) {
                entityAngle = getFrameAngle((EntityItemFrame) entity);
            } else {
                return 0.0F;
            }

            entityAngle /= 360.0D;
            entityAngle = MathHelper.positiveModulo(entityAngle, 1.0D);
            double posAngle = getAngle(blockPos, entity);
            posAngle /= Math.PI * 2D;
            angle = 0.5D - (entityAngle - 0.25D - posAngle);
        } else {
            if(!ClientProxy.hasAngleErrrored()) {
                System.out.println("Compass angle is random due to an unexpected error");
                ClientProxy.AngleHasErrored();
            }
            angle = 0.0D;
        }

        if(isLiving) {
            angle = wobble(worldIn, angle);
        }


        return MathHelper.positiveModulo((float) angle, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    private double wobble(World world, double angle) {
        long worldTime = world.getTotalWorldTime();
        if (worldTime != prevWorldTime) {
            prevWorldTime = worldTime;
            double angleDifference = angle - prevAngle;
            angleDifference = MathHelper.positiveModulo(angleDifference + 0.5D, 1.0D) - 0.5D;
            prevWobble += angleDifference * 0.1D;
            prevWobble *= 0.8D;
            prevAngle = MathHelper.positiveModulo(prevAngle + prevWobble, 1.0D);
        }
        return prevAngle;
    }

    @SideOnly(Side.CLIENT)
    private void setBlockPos(ItemStack stack) {
        for (int i = 0; i < CommonProxy.compassList.size(); i++) {
            if (stack.getItem().getUnlocalizedName().equals(CommonProxy.compassList.get(i).getUnlocalizedName())) {
                blockPos = CommonProxy.compassList.get(i).getStructurePos();
            } else {
                if (!ClientProxy.hasAngleErrrored()) {
                    System.out.println("unable to get blockPos from compassList in AngleGetter class");
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private double getAngle(BlockPos pos, Entity ent) {
        return MathHelper.atan2(pos.getZ() - ent.posZ, pos.getX() - ent.posX);
    }

    @SideOnly(Side.CLIENT)
    private double getFrameAngle(EntityItemFrame entity) {
        return (double) MathHelper.wrapDegrees(180 + (entity.facingDirection.getHorizontalIndex() * 90));
    }
}
