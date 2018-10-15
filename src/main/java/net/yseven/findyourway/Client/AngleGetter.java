package net.yseven.findyourway.Client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.yseven.findyourway.item.ItemCompassBase;
import net.yseven.findyourway.item.ModItems;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class AngleGetter implements IItemPropertyGetter {
    @SideOnly(Side.CLIENT)
    private double prevAngle = 0.0D;
    @SideOnly(Side.CLIENT)
    private double prevWobble = 0.0D;
    @SideOnly(Side.CLIENT)
    private long prevWorldTime = 0L;
    @SideOnly(Side.CLIENT)
    private BlockPos blockPos;

    @SideOnly(Side.CLIENT)
    AngleGetter(ItemCompassBase compassBase) {
        this.blockPos = compassBase.getStructurePos();
    }

    @Override
    @ParametersAreNonnullByDefault
    @SideOnly(Side.CLIENT)
    public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {

        if (entityIn == null && !stack.isOnItemFrame()) return 0.0F;
        final boolean entityExists = entityIn != null;

        final Entity entity = (Entity) (entityExists ? entityIn : stack.getItemFrame());

        if (worldIn == null) worldIn = entity.world;

        double rotation = entityExists ? (double) entity.rotationYaw : getFrameAngle((EntityItemFrame) entity);
        rotation %= 360.0D;

        double adjusted = Math.PI - ((rotation - 90.0D) * 0.01745329238474369D - getAngle(worldIn, entity, stack));

        if (entityExists) adjusted = wobble(worldIn, adjusted);

        final float f = (float) (adjusted / (Math.PI * 2D));
        return MathHelper.positiveModulo(f,    1.0F);
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
    private double getAngle(World world, Entity entity, ItemStack stack) {
        return Math.atan2((double) blockPos.getZ() - entity.posZ, (double) blockPos.getX() - entity.posX);
    }

    @SideOnly(Side.CLIENT)
    private double getFrameAngle(EntityItemFrame entity) {
        return (double) MathHelper.wrapDegrees(180 + (entity.facingDirection.getHorizontalIndex() * 90));
    }
}
