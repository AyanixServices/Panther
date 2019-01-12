package com.ayanix.panther.impl.bukkit.enchantment.compat;

import com.ayanix.panther.enchantment.bukkit.PantherEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2019.
 */
public class v1_8_BukkitPantherEnchantment extends Enchantment
{

	private final PantherEnchantment enchantment;

	public v1_8_BukkitPantherEnchantment(PantherEnchantment enchantment, int id)
	{
		super(id);

		this.enchantment = enchantment;
	}

	@Override
	public String getName()
	{
		return enchantment.getName();
	}

	@Override
	public int getMaxLevel()
	{
		return enchantment.getMaxLevel();
	}

	@Override
	public int getStartLevel()
	{
		return enchantment.getStartLevel();
	}

	@Override
	public EnchantmentTarget getItemTarget()
	{
		return enchantment.getItemTarget();
	}

	@Override
	public boolean conflictsWith(Enchantment enchantment)
	{
		return this.enchantment.conflictsWith(enchantment);
	}

	@Override
	public boolean canEnchantItem(ItemStack itemStack)
	{
		return enchantment.canEnchantItem(itemStack);
	}

}
