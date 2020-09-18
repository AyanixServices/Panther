package com.ayanix.panther.impl.bukkit.utils.item.compat;

import com.ayanix.panther.utils.bukkit.item.BukkitItemUtilsCompat;
import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PantherParent - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2020.
 */
public class v1_12_BukkitItemUtilsCompat extends BukkitItemUtilsCompat
{

	private Map<String, PlayerProfile> playerProfiles = new ConcurrentHashMap<>();

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

	@Override
	public void setSkullOwner(SkullMeta meta, String playerName)
	{
		if (playerProfiles.containsKey(playerName.toLowerCase()))
		{
			meta.setPlayerProfile(playerProfiles.get(playerName.toLowerCase()));
			return;
		}

		meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));

		PlayerProfile playerProfile = meta.getPlayerProfile();
		playerProfile.complete(true);

		playerProfiles.put(playerName.toLowerCase(), playerProfile);
	}

	@Override
	public boolean isSkullCached(String playerName)
	{
		if (!playerProfiles.containsKey(playerName.toLowerCase()))
		{
			return false;
		}

		PlayerProfile playerProfile = playerProfiles.get(playerName.toLowerCase());

		if (!playerProfile.completeFromCache() || !playerProfile.hasTextures())
		{
			playerProfiles.remove(playerName.toLowerCase());
			return false;
		}

		return true;
	}

}