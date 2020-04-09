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
package com.ayanix.panther.impl.bukkit.storage;

import com.ayanix.panther.storage.DefaultStorage;
import com.ayanix.panther.storage.YAMLStorage;
import com.ayanix.panther.storage.configuration.Configuration;
import com.ayanix.panther.storage.configuration.ConfigurationProvider;
import com.ayanix.panther.storage.configuration.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitYAMLStorage implements YAMLStorage
{

	private String        name;
	private File          file;
	private Configuration configuration;
	private Plugin        plugin;

	/**
	 * Initiate a Configuration instance.
	 *
	 * @param plugin Plugin storing configuration.
	 * @param name   Name of configuration.
	 */
	public BukkitYAMLStorage(Plugin plugin, String name)
	{
		this(plugin, plugin.getDataFolder(), name);
	}

	public BukkitYAMLStorage(Plugin plugin, File folder, String name)
	{
		this(plugin, new File(folder, name + ".yml"));
	}

	public BukkitYAMLStorage(Plugin plugin, File file)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("File cannot be null");
		}

		this.file = file;
		this.plugin = plugin;
		this.name = this.file.getName().replaceFirst("[.][^.]+$", "");

		if (!this.file.exists())
		{
			if (!this.file.getParentFile().exists() && !this.file.getParentFile().mkdirs())
			{
				plugin.getLogger().severe("Unable to create parent file: " + this.file.getParentFile().getName());
			}

			try
			{
				if (!this.file.createNewFile())
				{
					throw new IOException();
				}
			} catch (IOException e)
			{
				plugin.getLogger().log(Level.SEVERE, "Unable to create file: " + this.file.getName(), e);
			}
		}

		try
		{
			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
		} catch (IOException e)
		{
			plugin.getLogger().log(Level.SEVERE, "Unable to load file " + this.file.getName(), e);
		}
	}

	@Override
	public File getFile()
	{
		return this.file;
	}

	@Override
	public Configuration getConfig()
	{
		return this.configuration;
	}

	@Override
	public void insertDefault(DefaultStorage defaultStorage)
	{
		if (defaultStorage == null)
		{
			throw new IllegalArgumentException("Default storage cannot be null");
		}

		boolean changes = false;

		for (String key : defaultStorage.getDefaultValues().keySet())
		{
			if (configuration.contains(key))
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
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
		} catch (IOException e)
		{
			plugin.getLogger().severe("Unable to save to file: " + name + ".yml");
		}
	}

}