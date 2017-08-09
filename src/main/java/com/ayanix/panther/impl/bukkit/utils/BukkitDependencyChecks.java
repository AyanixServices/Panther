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

import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitDependencyChecks implements DependencyChecks
{

	private final JavaPlugin plugin;

	/**
	 * @param plugin Plugin requesting dependencies.
	 */
	public BukkitDependencyChecks(final JavaPlugin plugin)
	{
		if (plugin == null)
		{
			throw new IllegalArgumentException("Plugin cannot be null");
		}

		this.plugin = plugin;
	}

	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public boolean runChecks(final Map<String, String> dependencies)
	{
		if (dependencies == null)
		{
			throw new IllegalArgumentException("Dependencies cannot be null");
		}

		boolean allEnabled = true;

		final StringBuilder templateError = new StringBuilder("\u2718 Missing ");

		for (final Map.Entry<String, String> entry : dependencies.entrySet())
		{
			final String pluginName = entry.getKey();
			final String version    = entry.getValue();

			if (!isEnabled(pluginName, version))
			{
				allEnabled = false;

				final StringBuilder error = new StringBuilder(templateError)
						.append(pluginName);

				if (!version.isEmpty())
				{
					error.append(" (v").append(version).append(')');
				}

				plugin.getLogger().severe(error::toString);

				continue;
			}

			plugin.getLogger().info(() -> "\u2714 " + pluginName + " detected");
		}

		return allEnabled;
	}

	public boolean isEnabled(final String plugin, final String version)
	{
		final Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		return dependency != null && dependency.getDescription().getVersion().startsWith(version);
	}

	public boolean isEnabled(final String plugin)
	{
		return Bukkit.getPluginManager().isPluginEnabled(plugin);
	}

}