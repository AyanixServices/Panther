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
package com.ayanix.panther.impl.storage;

import com.ayanix.panther.storage.IDefaultStorage;
import com.ayanix.panther.storage.IYAMLStorage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class YAMLStorage implements IYAMLStorage
{

	private String            name;
	private File              file;
	private YamlConfiguration configuration;
	private Plugin            plugin;

	/**
	 * Initiate a Configuration instance.
	 *
	 * @param plugin Plugin storing configuration.
	 * @param name   Name of configuration.
	 */
	public YAMLStorage(Plugin plugin, String name)
	{
		if (plugin == null)
		{
			throw new IllegalArgumentException("Plugin cannot be null");
		}

		if (name == null)
		{
			throw new IllegalArgumentException("File name cannot be null");
		}

		this.plugin = plugin;
		this.name = name;
		String fullName = name + ".yml";

		this.file = new File(plugin.getDataFolder(), fullName);

		if (!file.exists())
		{
			if (!file.getParentFile().exists() && !file.getParentFile().mkdirs())
			{
				plugin.getLogger().severe("Unable to create parent file: " + file.getParentFile().getName());
			}

			try
			{
				if (!file.createNewFile())
				{
					throw new IOException();
				}
			} catch (IOException e)
			{
				plugin.getLogger().log(Level.SEVERE, "Unable to create file: " + fullName, e);
			}
		}

		this.configuration = YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public File getFile()
	{
		return this.file;
	}

	@Override
	public YamlConfiguration getConfig()
	{
		return this.configuration;
	}

	@Override
	public void insertDefault(IDefaultStorage defaultStorage)
	{
		if (defaultStorage == null)
		{
			throw new IllegalArgumentException("Default storage cannot be null");
		}

		boolean changes = false;

		for (String key : defaultStorage.getDefaultValues().keySet())
		{
			if (configuration.isSet(key))
			{
				continue;
			}

			configuration.set(key, defaultStorage.getDefaultValues().get(key));
			plugin.getLogger().info(() -> "Loaded default value of " + key + " into " + name + ".yml");

			changes = true;
		}

		if (changes)
		{
			save();
		}
	}

	@Override
	public void save()
	{
		try
		{
			configuration.save(file);
		} catch (IOException e)
		{
			plugin.getLogger().severe("Unable to save to file: " + name + ".yml");
		}
	}

}