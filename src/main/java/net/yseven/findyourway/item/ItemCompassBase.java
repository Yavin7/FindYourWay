package net.yseven.findyourway.item;

import io.netty.buffer.ByteBuf;
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
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.yseven.findyourway.Client.ClientProxy;
import net.yseven.findyourway.CommonProxy;
import net.yseven.findyourway.FindYourWay;

import javax.annotation.Nonnull;

public class ItemCompassBase extends Item {
    private String structureType;
    private BlockPos structurePos;
    private World structureWorld;
    private final int ItemCompassID;

    public ItemCompassBase(String name, String structureName) {
        setUnlocalizedName(FindYourWay.modId + "." + name);
        setRegistryName(name);
        structureType = structureName;
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
        CommonProxy.compassList.add(this);
        ItemCompassID = CommonProxy.compassList.indexOf(this);
    }

    public String toString() {
        return getUnlocalizedName();
    }

    //getters

    public String getStructureType() {
        return structureType;
    }
    public BlockPos getStructurePos() {
        return structurePos;
    }
    public World getStructureWorld() {
        return structureWorld;
    }
    public int getCompassID() {
        return ItemCompassID;
    }

    //setters

    public void setStructureType(String type) {
        structureType= type;
    }
    public void setStructurePos(BlockPos pos) {
        structurePos = pos;
    }
    public void setStructureWorld(World world) {
        structureWorld = world;
    }

    //netcode implementation

    public void toBytes(ByteBuf buf) {
        if(CommonProxy.compassList.contains(this)) {
            buf.writeInt(ItemCompassID);
        }
    }

    public static ItemCompassBase fromBytes(ByteBuf buf) {
        int ID = buf.readInt();
        for(int i = 0; i <= CommonProxy.compassList.size(); i++) {
            if(ID == CommonProxy.compassList.get(i).getCompassID()) return CommonProxy.compassList.get(i);
        }
        return null;
    }

    //Client Code (mostly registration)
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
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
