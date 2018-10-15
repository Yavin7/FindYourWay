package net.yseven.findyourway.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.yseven.findyourway.Client.ClientProxy;
import net.yseven.findyourway.CommonProxy;
import net.yseven.findyourway.FindYourWay;

import javax.annotation.Nonnull;

public class ItemCompassBase extends Item {
    private final String structureType;
    private BlockPos structurePos;
    private World structureWorld;
    public final String assetTag;

    ItemCompassBase(String name, String structureType, CreativeTabs tab) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.structureType = structureType;
        this.setCreativeTab(tab);
        this.setMaxStackSize(1);
        this.assetTag = name + "_angle";
        CommonProxy.compassList.add(this);
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructurePos(BlockPos pos) {
        structurePos = pos;
    }

    public BlockPos getStructurePos() {
        return structurePos;
    }

    public void setStructureWorld(World world) {
        structureWorld = world;
    }

    public World getStructureWorld() {
        return structureWorld;
    }

    public void registerItemModel() {
        FindYourWay.proxy.registerItemRenderer(this, 0, this.getUnlocalizedName());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (world.isRemote) {
            ClientProxy.resetStructurePos(this);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
