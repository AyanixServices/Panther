package com.ayanix.panther.impl.bukkit.compat;

import com.ayanix.panther.compat.IBukkitVersion;
import org.bukkit.Bukkit;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public enum BukkitVersion implements IBukkitVersion
{

	v1_8("1.8", 1),
	v1_9("1.9", 2),
	v1_10("1.10", 3),
	v1_11("1.11", 4),
	v1_12("1.12", 5),
	v1_13("1.13", 6),
	v1_14("1.14", 7),
	v1_15("1.15", 8);

	private final String prefix;
	private final int    weight;

	BukkitVersion(String prefix, int weight)
	{
		this.prefix = prefix;
		this.weight = weight;
	}

	/**
	 * Returns whether or not the server is running the specified version or higher.
	 *
	 * @param version The minimum version.
	 * @return True if the server is running this version or above.
	 */
	public static boolean isRunningMinimumVersion(BukkitVersion version)
	{
		BukkitVersion currentVersion = BukkitVersion.getVersion();

		if (currentVersion == null)
		{
			// Unable to parse version.
			// This usually means Panther is outdated.
			return false;
		}

		return version.getWeight() <= currentVersion.getWeight();
	}

	@Override
	public int getWeight()
	{
		return weight;
	}

	/**
	 * @return The current server version.
	 */
	public static BukkitVersion getVersion()
	{
		String serverVersion = Bukkit.getBukkitVersion();

		for (BukkitVersion bukkitVersion : BukkitVersion.values())
		{
			if (serverVersion.startsWith(bukkitVersion.getPrefix()))
			{
				return bukkitVersion;
			}
		}

		return null;
	}

	@Override
	public String getPrefix()
	{
		return prefix;
	}

}
