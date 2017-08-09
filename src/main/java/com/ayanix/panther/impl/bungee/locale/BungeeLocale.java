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
package com.ayanix.panther.impl.bungee.locale;

import com.ayanix.panther.impl.bukkit.locale.BukkitMessage;
import com.ayanix.panther.impl.common.storage.BungeeYAMLStorage;
import com.ayanix.panther.impl.common.storage.Configuration;
import com.ayanix.panther.locale.Locale;
import com.ayanix.panther.storage.DefaultStorage;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BungeeLocale extends BungeeYAMLStorage implements Locale
{

	private List<BukkitMessage> messages;

	/**
	 * Initiate a Locale instance.
	 *
	 * @param plugin Plugin storing configuration.
	 * @param name   Name of configuration.
	 */
	public BungeeLocale(Plugin plugin, String name)
	{
		super(plugin, name);

		this.messages = new ArrayList<>();
	}

	@Override
	public void load()
	{
		for (String key : getConfig().getKeys())
		{
			Configuration section = getConfig().getSection(key);

			if (section != null)
			{
				for (String innerKey : section.getKeys())
				{
					loadMessage(key + "." + innerKey);
				}

				continue;
			}

			loadMessage(key);
		}
	}

	private void loadMessage(String key)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		BukkitMessage preExisting = get(key);

		if (preExisting != null)
		{
			messages.remove(preExisting);
		}

		BukkitMessage message = null;

		if (getConfig().isList(key))
		{
			message = new BukkitMessage(key, getConfig().getStringList(key));
		} else if (getConfig().isString(key))
		{
			message = new BukkitMessage(key, Collections.singletonList(getConfig().getString(key)));
		}

		if (message == null)
		{
			return;
		}

		messages.add(message);
	}

	@Override
	public BukkitMessage get(String key)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		for (BukkitMessage message : messages)
		{
			if (message.getKey().equalsIgnoreCase(key))
			{
				return message;
			}
		}

		return new BukkitMessage(key, Collections.singletonList(key));
	}

	@Override
	public void insertDefault(DefaultStorage defaultStorage)
	{
		super.insertDefault(defaultStorage);

		for (String key : defaultStorage.getDefaultValues().keySet())
		{
			loadMessage(key);
		}
	}

}