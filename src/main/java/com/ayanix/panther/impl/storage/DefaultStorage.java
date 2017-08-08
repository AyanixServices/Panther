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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class DefaultStorage implements IDefaultStorage
{

	private HashMap<String, Object> defaultValues = new HashMap<>();

	/**
	 * Initiate a configuration with default values.
	 *
	 * @param plugin Plugin where configuration is stored.
	 * @param name   Name of default resource.
	 */
	public DefaultStorage(@Nullable JavaPlugin plugin, @Nullable String name)
	{
		if (plugin == null || name == null)
		{
			return;
		}

		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(name + ".yml"), Charset.forName("UTF-8")));

		for (String key : configuration.getKeys(false))
		{
			ConfigurationSection section = configuration.getConfigurationSection(key);

			if (section != null)
			{
				for (String innerKey : section.getKeys(false))
				{
					String totalKey = key + "." + innerKey;

					defaultValues.put(totalKey, configuration.get(totalKey));
				}

				continue;
			}

			defaultValues.put(key, configuration.get(key));
		}
	}

	@Override
	public void insert(@NonNull String key, @NonNull Object object)
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