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
package com.ayanix.panther.impl.bungee.storage;

import com.ayanix.panther.storage.DefaultStorage;
import com.ayanix.panther.storage.configuration.Configuration;
import com.ayanix.panther.storage.configuration.ConfigurationProvider;
import com.ayanix.panther.storage.configuration.YamlConfiguration;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BungeeDefaultStorage implements DefaultStorage
{

	private HashMap<String, Object> defaultValues = new HashMap<>();

	/**
	 * Initiate a configuration with default values.
	 *
	 * @param plugin Plugin where configuration is stored.
	 * @param name   Name of default resource.
	 */
	public BungeeDefaultStorage(Plugin plugin, String name)
	{
		if (plugin == null || name == null)
		{
			return;
		}

		Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new InputStreamReader(plugin.getResourceAsStream(name + ".yml"), Charset.forName("UTF-8")));

		for (String key : configuration.getKeys())
		{
			if (configuration.isSection(key))
			{
				Configuration section = configuration.getSection(key);

				for (String innerKey : section.getKeys())
				{
					String totalKey = key + "." + innerKey;

					defaultValues.put(totalKey, configuration.get(totalKey));
				}
			} else
			{
				defaultValues.put(key, configuration.get(key));
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