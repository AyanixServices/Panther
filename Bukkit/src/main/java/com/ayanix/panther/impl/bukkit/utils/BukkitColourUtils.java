package com.ayanix.panther.impl.bukkit.utils;

import com.ayanix.panther.impl.bukkit.compat.BukkitVersion;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Panther - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2021.
 */
public class BukkitColourUtils
{

	public static String colourise(String message)
	{
		if (!BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_13))
		{
			return ChatColor.translateAlternateColorCodes('&', message);
		}

		Pattern hexPattern = Pattern.compile("#[A-Fa-f0-9]{6}");
		Matcher matcher    = hexPattern.matcher(message);

		while (matcher.find())
		{
			String match = matcher.group();

			message = message.replace(match, net.md_5.bungee.api.ChatColor.of(match).toString());
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}

}