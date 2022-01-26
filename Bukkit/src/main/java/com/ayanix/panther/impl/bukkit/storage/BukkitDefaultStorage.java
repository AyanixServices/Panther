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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitDefaultStorage implements DefaultStorage
{

	private HashMap<String, Object> defaultValues = new HashMap<>();

	/**
	 * Initiate a configuration with default values.
	 *
	 * @param plugin Plugin where configuration is stored.
	 * @param name   Name of default resource.
	 */
	public BukkitDefaultStorage(JavaPlugin plugin, String name)
	{
		if (plugin == null || name == null)
		{
			return;
		}

		InputStream resource = plugin.getResource(name + ".yml");

		if(resource == null) {
			plugin.getLogger().log(Level.WARNING, "Unable to load default storage " + name + ".yml due to possible reload.");
			return;
		}

		try (InputStreamReader reader = new InputStreamReader(resource, Charset.forName("UTF-8")))
		{
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(reader);

			for (String key : configuration.getKeys(false))
			{
				ConfigurationSection section = configuration.getConfigurationSection(key);

				if (section != null)
				{
					loadDefaultValues(key, section);
					continue;
				}

				defaultValues.put(key, configuration.get(key));
			}
		} catch (IOException e)
		{
			plugin.getLogger().log(Level.SEVERE, "Unable to load default storage " + name + ".yml", e);
		} catch (NullPointerException e) {
			plugin.getLogger().log(Level.WARNING, "Unable to load default storage " + name + ".yml due to possible reload.");
		}
	}

	private void loadDefaultValues(String totalKey, ConfigurationSection section)
	{
		for (String innerKey : section.getKeys(false))
		{
			if (section.isConfigurationSection(innerKey))
			{
				loadDefaultValues(totalKey + "." + innerKey, section.getConfigurationSection(innerKey));
			} else
			{
				defaultValues.put(totalKey + "." + innerKey, section.get(innerKey));
			}
		}
	}

	@Override
	public void insert(String key, Object object)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Default key cannot be null");
		}

		if (object == null)
		{
			throw new IllegalArgumentException("Default value cannot be null");
		}

		defaultValues.put(key, object);
	}

	@Override
	public Map<String, Object> getDefaultValues()
	{
		return this.defaultValues;
	}

}