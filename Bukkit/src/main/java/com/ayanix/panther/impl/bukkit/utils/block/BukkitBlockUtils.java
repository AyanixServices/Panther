package com.ayanix.panther.impl.bukkit.utils.block;

import com.ayanix.panther.impl.bukkit.compat.BukkitVersion;
import com.ayanix.panther.impl.bukkit.utils.block.compat.v1_13_BukkitBlockUtilsCompat;
import com.ayanix.panther.utils.bukkit.blocks.BukkitBlockUtilsCompat;
import com.ayanix.panther.utils.bukkit.blocks.IBukkitBlockUtils;
import org.bukkit.block.Block;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class BukkitBlockUtils implements IBukkitBlockUtils
{

	private static BukkitBlockUtils       instance;
	private final  BukkitBlockUtilsCompat internal;

	public BukkitBlockUtils()
	{
		if (BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_13))
		{
			internal = new v1_13_BukkitBlockUtilsCompat();
		} else
		{
			internal = new v1_8_BukkitBlockUtilsInternal();
		}
	}

	/**
	 * Grab the static version of BukkitBlockUtils.
	 *
	 * @return BukkitLocationUtils.
	 */
	public static BukkitBlockUtils get()
	{
		if (instance == null)
		{
			instance = new BukkitBlockUtils();
		}

		return instance;
	}

	@Override
	public boolean isFullyGrown(Block block)
	{
		return internal.isFullyGrown(block);
	}

}
