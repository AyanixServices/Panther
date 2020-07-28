package com.ayanix.panther.impl.bukkit.utils.block.compat;

import com.ayanix.panther.utils.bukkit.blocks.BukkitBlockUtilsCompat;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class v1_13_BukkitBlockUtilsCompat extends BukkitBlockUtilsCompat
{

	@Override
	public boolean isFullyGrown(Block block)
	{
		if (block.getState().getData() instanceof Ageable)
		{
			Ageable ageable = (Ageable) block;

			return ageable.getAge() == ageable.getMaximumAge();
		}

		return true;
	}

}