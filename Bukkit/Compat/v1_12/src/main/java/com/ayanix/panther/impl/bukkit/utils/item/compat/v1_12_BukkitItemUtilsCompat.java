package com.ayanix.panther.impl.bukkit.utils.item.compat;

import com.ayanix.panther.utils.bukkit.item.BukkitItemUtilsCompat;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class v1_12_BukkitItemUtilsCompat extends BukkitItemUtilsCompat
{

	@Override
	public boolean isUnbreakable(ItemMeta itemMeta)
	{
		return itemMeta.isUnbreakable();
	}

	@Override
	public void setUnbreakable(ItemMeta itemMeta, boolean unbreakable, boolean hidden)
	{
		itemMeta.setUnbreakable(unbreakable);

		if (hidden)
		{
			itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		}
	}

}