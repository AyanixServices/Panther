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

import com.ayanix.panther.locale.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitMessage implements Message
{

	private final String       key;
	private final List<String> values;

	/**
	 * @param key    Key associated with message.
	 * @param values Actual messages.
	 */
	public BukkitMessage(final String key, final List<String> values)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		this.key = key;
		this.values = values == null ? new ArrayList<>() : values;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}

	@Override
	public BukkitMessage replace(final String key, final String value)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null when replacing");
		}

		final List<String> newValues = new ArrayList<>();

		for (final String message : values)
		{
			newValues.add(message.replace("{" + key + "}", value == null ? "" : value));
		}

		return new BukkitMessage(key, newValues);
	}

	@Override
	public List<String> getList(final boolean formatted)
	{
		if (!formatted)
		{
			return new ArrayList<>(this.values);
		}

		final List<String> newValues = new ArrayList<>();

		for (final String message : values)
		{
			newValues.add(ChatColor.translateAlternateColorCodes('&', message));
		}

		return newValues;
	}

	@Override
	public void broadcast()
	{
		for (final Player player : Bukkit.getOnlinePlayers())
		{
			send(player);
		}

		send(Bukkit.getConsoleSender());
	}

	@Override
	@SuppressWarnings("AvoidInstantiatingObjectsInLoops")
	public void send(final Object sender)
	{
		if (sender == null)
		{
			throw new IllegalArgumentException("Sender/player cannot be null");
		}

		if (!(sender instanceof CommandSender))
		{
			throw new UnsupportedOperationException("Sender must be an implementation of CommandSender");
		}

		final CommandSender cSender = (CommandSender) sender;

		for (String message : getList())
		{
			if (cSender instanceof ConsoleCommandSender)
			{
				message = ChatColor.stripColor(message);
			}

			cSender.sendMessage(message);
		}
	}

}