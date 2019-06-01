package com.ayanix.panther.impl.bukkit.enchantment.compat;

import com.ayanix.panther.enchantment.bukkit.PantherEnchantment;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public class v1_13_BukkitPantherEnchantment extends Enchantment
{

	private final PantherEnchantment enchantment;

	public v1_13_BukkitPantherEnchantment(PantherEnchantment enchantment, JavaPlugin plugin, int id)
	{
		super(new NamespacedKey(plugin, String.valueOf(id)));

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
	public boolean isTreasure()
	{
		return false;
	}

	@Override
	public boolean isCursed()
	{
		return false;
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
