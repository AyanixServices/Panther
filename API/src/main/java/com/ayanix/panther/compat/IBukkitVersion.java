package com.ayanix.panther.compat;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public interface IBukkitVersion
{

	/**
	 * @return The version prefix related to Bukkit versioning.
	 */
	String getPrefix();

	/**
	 * @return The relative weight of the version. A larger number is a newer version.
	 */
	int getWeight();

}
