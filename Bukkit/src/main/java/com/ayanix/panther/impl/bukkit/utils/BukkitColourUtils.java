package com.ayanix.panther.impl.bukkit.utils;

import com.ayanix.panther.impl.bukkit.compat.BukkitVersion;
import com.ayanix.panther.impl.common.utils.ReflectionUtils;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Panther - Developed by Lewes D. B. (Boomclaw).
 * All rights reserved 2021.
 */
public class BukkitColourUtils
{

	private static final boolean supportsHex;

	static
	{
		supportsHex = ReflectionUtils.doesClassExist("net.minecraft.server.v1_8_R3.ChatHexColor") || BukkitVersion.isRunningMinimumVersion(BukkitVersion.v1_13);
	}

	public static String colourise(String message)
	{
		if (!supportsHex)
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