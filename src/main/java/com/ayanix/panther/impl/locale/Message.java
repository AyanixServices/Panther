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

import com.ayanix.panther.locale.IMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class Message implements IMessage
{

	private String       key;
	private List<String> values;

	/**
	 * @param key    Key associated with message.
	 * @param values Actual messages.
	 */
	public Message(String key, List<String> values)
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
	public Message replace(@NonNull String key, @Nullable String value)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null when replacing");
		}

		List<String> values = new ArrayList<>();

		for (String message : this.values)
		{
			values.add(message.replace("{" + key + "}", value == null ? "" : value));
		}

		return new Message(key, values);
	}

	@Override
	public String get()
	{
		return get(true);
	}

	@Override
	public String get(boolean formatted)
	{
		StringBuilder builder = new StringBuilder();

		for (String message : getList(formatted))
		{
			builder.append(message);
			builder.append('\n');
		}

		String toString = builder.toString();

		toString = toString.substring(0, toString.length() - 1);

		return toString;
	}

	@Override
	public List<String> getList(boolean formatted)
	{
		if (!formatted)
		{
			return new ArrayList<>(this.values);
		}

		List<String> values = new ArrayList<>();

		for (String message : this.values)
		{
			values.add(ChatColor.translateAlternateColorCodes('&', message));
		}

		return values;
	}

	@Override
	public void broadcast()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			send(player);
		}

		send(Bukkit.getConsoleSender());
	}

	@Override
	public void send(@NonNull CommandSender sender)
	{
		if (sender == null)
		{
			throw new IllegalArgumentException("Sender/player cannot be null");
		}

		for (String message : getList())
		{
			if (sender instanceof ConsoleCommandSender)
			{
				message = ChatColor.stripColor(message);
			}

			sender.sendMessage(message);
		}
	}

	@Override
	public List<String> getList()
	{
		return getList(true);
	}

}