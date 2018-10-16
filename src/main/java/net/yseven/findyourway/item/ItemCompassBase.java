package net.yseven.findyourway.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.yseven.findyourway.Client.ClientProxy;
import net.yseven.findyourway.CommonProxy;
import net.yseven.findyourway.FindYourWay;

import javax.annotation.Nonnull;

public class ItemCompassBase extends Item {
    private final String structureType;
    private BlockPos structurePos;
    private World structureWorld;
    public final String assetTag;

    public ItemCompassBase(String name, String structureName) {
        setUnlocalizedName(FindYourWay.modId + "." + name);
        setRegistryName(name);
        structureType = structureName;
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
        assetTag = name + "_angle";
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
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (world.isRemote) {
            ClientProxy.resetStructurePos(this);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
