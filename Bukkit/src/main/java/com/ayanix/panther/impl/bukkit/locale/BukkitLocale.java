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
package com.ayanix.panther.impl.bukkit.locale;

import com.ayanix.panther.impl.bukkit.storage.BukkitYAMLStorage;
import com.ayanix.panther.locale.Locale;
import com.ayanix.panther.storage.DefaultStorage;
import com.ayanix.panther.storage.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitLocale extends BukkitYAMLStorage implements Locale
{

	private List<BukkitMessage> messages;

	/**
	 * Initiate a Locale instance.
	 *
	 * @param plugin Plugin storing configuration.
	 * @param name   Name of configuration.
	 */
	public BukkitLocale(final Plugin plugin, final String name)
	{
		super(plugin, name);
	}

	@Override
	public void load()
	{
		for (final String key : getConfig().getKeys())
		{
			if (getConfig().isList(key) || getConfig().isString(key))
			{
				loadMessage(key);
			} else
			{
				final Configuration section = getConfig().getSection(key);

				if (section != null)
				{
					loadMessages(key, section);
				}
			}
		}
	}

	private void loadMessages(String path, Configuration section)
	{
		for (final String innerKey : section.getKeys())
		{
			String totalPath = path + "." + innerKey;

			if (section.isSection(innerKey))
			{
				loadMessages(totalPath, section.getSection(innerKey));
			} else if (getConfig().isList(totalPath) || getConfig().isString(totalPath))
			{
				loadMessage(totalPath);
			}
		}
	}

	private void loadMessage(final String key)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		final BukkitMessage preExisting = get(key);

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
	public void reload()
	{
		super.reload();

		if (this.messages != null)
		{
			this.messages.clear();
		} else
		{
			this.messages = new ArrayList<>();
		}

		load();
	}

	@Override
	public BukkitMessage get(final String key)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		for (final BukkitMessage message : messages)
		{
			if (message.getKey().equalsIgnoreCase(key))
			{
				return message;
			}
		}

		return new BukkitMessage(key, Collections.singletonList(key));
	}

	@Override
	public void insertDefault(final DefaultStorage defaultStorage)
	{
		super.insertDefault(defaultStorage);

		for (final String key : defaultStorage.getDefaultValues().keySet())
		{
			loadMessage(key);
		}
	}

}