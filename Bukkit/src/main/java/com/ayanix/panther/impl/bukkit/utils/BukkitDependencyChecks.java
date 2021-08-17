/*
 *                                                            _...---.._
 *                                                        _.'`       -_ ``.
 *                                                    .-'`                 `.
 *                                                 .-`                     q ;
 *                                              _-`                       __  \
 *                                          .-'`                  . ' .   \ `;/
 *                                      _.-`                    /.      `._`/
 *                              _...--'`                        \_`..._
 *                           .'`                         -         `'--:._
 *                        .-`                           \                  `-.
 *                       .                               `-..__.....----...., `.
 *                      '                   `'''---..-''`'              : :  : :
 *                    .` -                '``                           `'   `'
 *                 .-` .` '             .``
 *             _.-` .-`   '            .
 *         _.-` _.-`    .' '         .`
 * (`''--'' _.-`      .'  '        .'
 *  `'----''        .'  .`       .`
 *                .'  .'     .-'`    _____               _    _
 *              .'   :    .-`       |  __ \             | |  | |
 *              `. .`   ,`          | |__) |__ _  _ __  | |_ | |__    ___  _ __
 *               .'   .'            |  ___// _` || '_ \ | __|| '_ \  / _ \| '__|
 *              '   .`              | |   | (_| || | | || |_ | | | ||  __/| |
 *             '  .`                |_|    \__,_||_| |_| \__||_| |_| \___||_|
 *             `  '.
 *             `.___;
 */
package com.ayanix.panther.impl.bukkit.utils;

import com.ayanix.panther.utils.DependencyChecks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitDependencyChecks implements DependencyChecks
{

	private static BukkitDependencyChecks instance;
	private final Logger logger;

	/**
	 * @param plugin Plugin requesting dependencies. If null, there will be no output messages.
	 */
	public BukkitDependencyChecks(final JavaPlugin plugin)
	{
		if (plugin == null)
		{
			// Referencing plugin will throw a NPE
			this.logger = null;
			return;
		}

		this.logger = plugin.getLogger();
	}

	/**
	 * Declare the static version of BukkitDependencyChecks.
	 *
	 * @param plugin Plugin using BukkitDependencyChecks.
	 */
	public static void init(JavaPlugin plugin)
	{
		if (instance != null)
		{
			return;
		}

		instance = new BukkitDependencyChecks(plugin);
	}

	/**
	 * Grab the static version of BukkitDependencyChecks after being declared in BukkitDependencyChecks#init.
	 *
	 * @return BukkitDependencyChecks.
	 * @throws RuntimeException If BukkitDependencyChecks#init has not been called.
	 */
	public static BukkitDependencyChecks get()
	{
		if (instance == null)
		{
			throw new RuntimeException("BukkitDependencyChecks has not been initialised for static usage");
		}

		return instance;
	}

	public boolean runChecks(final Map<String, String> dependencies)
	{
		return runChecks(logger, dependencies);
	}

	public boolean isEnabled(final String plugin)
	{
		return isEnabled(plugin, "");
	}

	public boolean isEnabled(final String plugin, final String version)
	{
		final Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		return dependency != null && dependency.getDescription().getVersion().startsWith(version);
	}

	private List<Integer> getVersionValues(String version)
	{
		List<Integer> versionValues = new ArrayList<>();

		while (!version.isEmpty())
		{
			try
			{
				int index = version.indexOf(".");

				versionValues.add(Integer.parseInt(version.substring(0, index == -1 ? version.length() : index)));

				if (index != -1)
					version = version.substring(index + 1);
				else
					return versionValues;

			} catch (IllegalArgumentException e)
			{
				return versionValues;
			}
		}

		return versionValues;
	}

	public boolean isVersionAtLeast(final String plugin, final String version)
	{
		final Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		if (dependency == null)
			return false;

		List<Integer> desiredVersionValues = getVersionValues(version);

		String        pluginVersion = dependency.getDescription().getVersion();
		List<Integer> versionValues = getVersionValues(pluginVersion);

		for (int i = 0; i < desiredVersionValues.size(); i++)
		{
			int desiredNum = desiredVersionValues.get(i);
			int realNum    = versionValues.size() > i ? versionValues.get(i) : 0;

			if (desiredNum > realNum)
				return false;
			else if (desiredNum < realNum)
				return true;
		}

		return true;
	}

	public boolean isVersionNoHigherThan(final String plugin, final String version)
	{
		final Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		if (dependency == null)
			return false;

		List<Integer> desiredVersionValues = getVersionValues(version);

		String        pluginVersion = dependency.getDescription().getVersion();
		List<Integer> versionValues = getVersionValues(pluginVersion);

		for (int i = 0; i < desiredVersionValues.size(); i++)
		{
			int desiredNum = desiredVersionValues.get(i);
			int realNum    = versionValues.size() > i ? versionValues.get(i) : 0;

			if (desiredNum < realNum)
				return false;
			else if (desiredNum > realNum)
				return true;
		}

		return true;
	}

	public boolean isVersionHigherThan(final String plugin, final String version)
	{
		final Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		if (dependency == null)
			return false;

		String pluginVersion = dependency.getDescription().getVersion();

		// Faster to do this check first before parsing and comparing each value individually
		if (version.equals(pluginVersion)) return false;

		List<Integer> desiredVersionValues = getVersionValues(version);

		List<Integer> versionValues = getVersionValues(pluginVersion);

		for (int i = 0; i < Math.max(desiredVersionValues.size(), versionValues.size()); i++)
		{
			int desiredNum = desiredVersionValues.size() > i ? desiredVersionValues.get(i) : 0;
			int realNum    = versionValues.size() > i ? versionValues.get(i) : 0;

			if (desiredNum > realNum)
				return false;
			else if (desiredNum < realNum)
				return true;
		}

		// If it reaches this point it means that all values were equal (version is the same)
		return false;
	}

	public boolean isVersionLowerThan(final String plugin, final String version)
	{
		final Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		if (dependency == null)
			return false;

		String pluginVersion = dependency.getDescription().getVersion();

		// Faster to do this check first before parsing and comparing each value individually
		if (version.equals(pluginVersion)) return false;

		List<Integer> desiredVersionValues = getVersionValues(version);

		List<Integer> versionValues = getVersionValues(pluginVersion);

		for (int i = 0; i < Math.max(desiredVersionValues.size(), versionValues.size()); i++)
		{
			int desiredNum = desiredVersionValues.size() > i ? desiredVersionValues.get(i) : 0;
			int realNum    = versionValues.size() > i ? versionValues.get(i) : 0;

			if (desiredNum < realNum)
				return false;
			else if (desiredNum > realNum)
				return true;
		}

		// If it reaches this point it means that all values were equal (version is the same)
		return false;
	}

}