package com.brandon3055.draconicevolution.common.items.tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.common.core.helper.ItemNBTHelper;
import com.brandon3055.draconicevolution.common.items.ModItems;
import com.brandon3055.draconicevolution.common.lib.References;
import com.brandon3055.draconicevolution.common.lib.Strings;

public class WyvernPickaxe extends ItemPickaxe {
	public IIcon itemIcon0;
	public IIcon itemIcon1;

	public WyvernPickaxe() {
		super(ModItems.DRACONIUM_T1);
		this.setUnlocalizedName(Strings.wyvernPickaxeName);
		this.setCreativeTab(DraconicEvolution.getCreativeTab(1));
		GameRegistry.registerItem(this, Strings.wyvernPickaxeName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister iconRegister)
	{
		this.itemIcon0 = iconRegister.registerIcon(References.RESOURCESPREFIX + "wyvern_pick");
		this.itemIcon1 = iconRegister.registerIcon(References.RESOURCESPREFIX + "wyvern_pick_active");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (ItemNBTHelper.getShort(stack, "size", (short)0) > 0)
			return itemIcon1;
		else
			return itemIcon0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack)
	{
		if (ItemNBTHelper.getShort(stack, "size", (short)0) > 0)
			return itemIcon1;
		else
			return itemIcon0;
	}

	@Override
	public boolean onBlockStartBreak(final ItemStack stack, final int x, final int y, final int z, final EntityPlayer player)
	{
		World world = player.worldObj;
		Block block = world.getBlock(x, y, z);
		Material mat = block.getMaterial();
		if (!ToolHandler.isRightMaterial(mat, ToolHandler.materialsPick)) {
			return false;
		}
		int fortune = EnchantmentHelper.getFortuneModifier(player);
		boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
		ToolHandler.disSquare(x, y, z, player, world, silk, fortune, ToolHandler.materialsPick, stack);
		return false;
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player)
	{
		return ToolHandler.changeMode(stack, player, false, 1);
	}

	public static int getMode(final ItemStack tool)
	{
		return tool.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean extraInformation)
	{
		int size = (ItemNBTHelper.getShort(stack, "size", (short)0) * 2) + 1;

		if ((!Keyboard.isKeyDown(42)) && (!Keyboard.isKeyDown(54)))
			list.add(EnumChatFormatting.DARK_GREEN + "Hold shift for information");
		else {
			list.add(EnumChatFormatting.GREEN + "Mining Mode: " + EnumChatFormatting.BLUE + size + "x" + size);
			list.add(EnumChatFormatting.GREEN + "Shift Right-click to change mode");
			list.add("");
			list.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + "Weary of plain tools you begin to understand");
			list.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + "ways to use Draconic energy to upgrade");
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

	public static void registerRecipe()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(ModItems.wyvernPickaxe), " C ", "CPC", " C ", 'C', ModItems.infusedCompound, 'P', Items.diamond_pickaxe);
	}
}

