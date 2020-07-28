package com.ayanix.panther.impl.bukkit.utils.block;

import com.ayanix.panther.utils.bukkit.blocks.BukkitBlockUtilsCompat;
import org.bukkit.CropState;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class v1_8_BukkitBlockUtilsInternal extends BukkitBlockUtilsCompat
{

	@Override
	public boolean isFullyGrown(Block block)
	{
		if (block.getState().getData() instanceof Crops)
		{
			return ((Crops) block.getState().getData()).getState() == CropState.RIPE;
		} else if (block.getState().getData() instanceof CocoaPlant)
		{
			return ((CocoaPlant) block.getState().getData()).getSize() == CocoaPlant.CocoaPlantSize.LARGE;
		} else if (block.getState().getData() instanceof NetherWarts)
		{
			return ((NetherWarts) block.getState().getData()).getState() == NetherWartsState.RIPE;
		}

		return true;
	}

}