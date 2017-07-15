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
 *                       .                `              `-..__.....----...., `.
 *                      '                 `  '''---..-''`'              : :  : :
 *                    .` -                '`.  `-.                       `'   `'
 *                 .-` .` '             .`'.__ ;
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

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class DependencyChecks implements IDependencyChecks
{

	public boolean runChecks(JavaPlugin plugin, HashMap<String, String> dependencies)
	{
		if (plugin == null)
		{
			throw new InvalidParameterException("Plugin cannot be null");
		}

		if (dependencies == null)
		{
			throw new InvalidParameterException("Dependencies cannot be null");
		}

		boolean allEnabled = true;

		for (String pluginName : dependencies.keySet())
		{
			String version = dependencies.get(pluginName);

			if (!isEnabled(pluginName, version))
			{
				allEnabled = false;

				String error = "✘ Missing " + pluginName;

				if (!version.isEmpty())
				{
					error += " (v" + version + ")";
				}

				plugin.getLogger().severe(error);

				continue;
			}

			plugin.getLogger().info("✔ " + pluginName + " detected");
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

		if (!dependency.getDescription().getVersion().startsWith(version))
		{
			return false;
		}

		return true;
	}

	public boolean isEnabled(String plugin)
	{
		return Bukkit.getPluginManager().isPluginEnabled(plugin);
	}

}