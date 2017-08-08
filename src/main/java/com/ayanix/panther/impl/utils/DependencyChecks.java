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
package com.ayanix.panther.impl.utils;

import com.ayanix.panther.utils.IDependencyChecks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class DependencyChecks implements IDependencyChecks
{

	private JavaPlugin plugin;

	/**
	 * @param plugin Plugin requesting dependencies.
	 */
	public DependencyChecks(JavaPlugin plugin)
	{
		if (plugin == null)
		{
			throw new IllegalArgumentException("Plugin cannot be null");
		}

		this.plugin = plugin;
	}

	public boolean runChecks(Map<String, String> dependencies)
	{
		if (dependencies == null)
		{
			throw new IllegalArgumentException("Dependencies cannot be null");
		}

		boolean allEnabled = true;

		for (Map.Entry<String, String> entry : dependencies.entrySet())
		{
			String pluginName = entry.getKey();
			String version    = entry.getValue();

			if (!isEnabled(pluginName, version))
			{
				allEnabled = false;

				StringBuilder error = new StringBuilder("\u2718").append(" Missing ").append(pluginName);

				if (!version.isEmpty())
				{
					error.append(" (v").append(version).append(")");
				}

				plugin.getLogger().severe(error::toString);

				continue;
			}

			plugin.getLogger().info(() -> "\u2714 " + pluginName + " detected");
		}

		return allEnabled;
	}

	public boolean isEnabled(String plugin, String version)
	{
		Plugin dependency = Bukkit.getPluginManager().getPlugin(plugin);

		if (dependency == null)
		{
			return false;
		}

		return dependency.getDescription().getVersion().startsWith(version);
	}

	public boolean isEnabled(String plugin)
	{
		return Bukkit.getPluginManager().isPluginEnabled(plugin);
	}

}