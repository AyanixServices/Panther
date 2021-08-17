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
package com.ayanix.panther.impl.bungee.utils;

import com.ayanix.panther.utils.DependencyChecks;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BungeeDependencyChecks implements DependencyChecks
{

	private static BungeeDependencyChecks instance;
	private final Logger logger;

	/**
	 * @param plugin Plugin requesting dependencies.
	 */
	public BungeeDependencyChecks(final Plugin plugin)
	{
		if (plugin == null)
		{
			throw new IllegalArgumentException("Plugin cannot be null");
		}

		this.logger = plugin.getLogger();
	}

	/**
	 * Declare the static version of BungeeDependencyChecks.
	 *
	 * @param plugin Plugin using BungeeDependencyChecks.
	 */
	public static void init(Plugin plugin)
	{
		if (instance != null)
		{
			return;
		}

		instance = new BungeeDependencyChecks(plugin);
	}

	/**
	 * Grab the static version of BungeeDependencyChecks after being declared in BungeeDependencyChecks#init.
	 *
	 * @return BungeeDependencyChecks.
	 * @throws RuntimeException If BungeeDependencyChecks#init has not been called.
	 */
	public static BungeeDependencyChecks get()
	{
		if (instance == null)
		{
			throw new RuntimeException("BungeeDependencyChecks has not been initialised for static usage");
		}

		return instance;
	}

	public boolean runChecks(final Map<String, String> dependencies)
	{
		return runChecks(logger, dependencies);
	}

	public boolean isEnabled(final String plugin, final String version)
	{
		final Plugin dependency = ProxyServer.getInstance().getPluginManager().getPlugin(plugin);

		return dependency != null && dependency.getDescription().getVersion().startsWith(version);
	}

	public boolean isEnabled(final String plugin)
	{
		return ProxyServer.getInstance().getPluginManager().getPlugin(plugin) != null;
	}

	public boolean isVersionAtLeast(final String plugin, final String version)
	{
		final Plugin dependency = ProxyServer.getInstance().getPluginManager().getPlugin(plugin);

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
		final Plugin dependency = ProxyServer.getInstance().getPluginManager().getPlugin(plugin);

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
		final Plugin dependency = ProxyServer.getInstance().getPluginManager().getPlugin(plugin);

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
		final Plugin dependency = ProxyServer.getInstance().getPluginManager().getPlugin(plugin);

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