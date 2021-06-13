package com.ayanix.panther.impl.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2021.
 */
public class FillerGUIItem extends BukkitGUIItem
{

	/**
	 * @param item ItemStack representing item.
	 */
	public FillerGUIItem(ItemStack item)
	{
		super(item);
	}

	@Override
	public void run(Player player, ClickType type)
	{
		// do nothing
	}

}
