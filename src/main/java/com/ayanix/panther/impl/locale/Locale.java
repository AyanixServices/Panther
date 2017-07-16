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
package com.ayanix.panther.impl.locale;

import com.ayanix.panther.impl.storage.YAMLStorage;
import com.ayanix.panther.locale.ILocale;
import com.ayanix.panther.storage.IDefaultStorage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class Locale extends YAMLStorage implements ILocale
{

	private List<Message> messages;

	/**
	 * Initiate a Locale instance.
	 *
	 * @param plugin Plugin storing configuration.
	 * @param name   Name of configuration.
	 */
	public Locale(Plugin plugin, String name)
	{
		super(plugin, name);

		this.messages = new ArrayList<>();
	}

	@Override
	public void load()
	{
		for (String key : getConfig().getKeys(false))
		{
			ConfigurationSection section = getConfig().getConfigurationSection(key);

			if (section != null)
			{
				for (String innerKey : section.getKeys(false))
				{
					loadMessage(key + "." + innerKey);
				}

				continue;
			}

			loadMessage(key);
		}
	}

	private void loadMessage(@NonNull String key)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		Message preExisting = get(key);

		if (preExisting != null)
		{
			messages.remove(preExisting);
		}

		Message message = null;

		if (getConfig().isList(key))
		{
			message = new Message(key, getConfig().getStringList(key));
		} else if (getConfig().isString(key))
		{
			message = new Message(key, Collections.singletonList(getConfig().getString(key)));
		}

		if (message == null)
		{
			return;
		}

		messages.add(message);
	}

	@Override
	public Message get(@NonNull String key)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		for (Message message : messages)
		{
			if (message.getKey().equalsIgnoreCase(key))
			{
				return message;
			}
		}

		return new Message(key, Collections.singletonList(key));
	}

	@Override
	public void insertDefault(@NonNull IDefaultStorage defaultStorage)
	{
		super.insertDefault(defaultStorage);

		for (String key : defaultStorage.getDefaultValues().keySet())
		{
			loadMessage(key);
		}
	}

}