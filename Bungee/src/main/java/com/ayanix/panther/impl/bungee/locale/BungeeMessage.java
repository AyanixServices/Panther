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

import com.ayanix.panther.locale.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BungeeMessage implements Message
{

	private final String            key;
	private final List<String>      values;
	private final BungeeMessage     original;
	private final Map<String, Long> lastSent;

	/**
	 * @param key    Key associated with message.
	 * @param values Actual messages.
	 */
	public BungeeMessage(final String key, final List<String> values)
	{
		this(key, values, null);
	}

	/**
	 * @param key      Key associated with message.
	 * @param values   Actual messages.
	 * @param original The original unaltered message.
	 */
	public BungeeMessage(final String key, final List<String> values, BungeeMessage original)
	{
		if (key == null)
		{
			throw new IllegalArgumentException("Key cannot be null");
		}

		this.key = key;
		this.values = values == null ? new ArrayList<>() : values;
		this.original = original;
		this.lastSent = original == null ? new HashMap<>() : original.lastSent;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}

	@Override
	public BungeeMessage replace(final String key, final String value)
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

		return new BungeeMessage(key, newValues, original == null ? this : original);
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
		for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			send(player);
		}

		send(ProxyServer.getInstance().getConsole());
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
			if (!(cSender instanceof ProxiedPlayer))
			{
				message = ChatColor.stripColor(message);
			}

			cSender.sendMessage(TextComponent.fromLegacyText(message));
		}
	}

	@Override
	public void sendOnce(Object sender, long milliseconds)
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

		long lastSent = this.lastSent.getOrDefault(cSender.getName(), 0L);

		if (lastSent > System.currentTimeMillis() - milliseconds)
		{
			return;
		}

		this.lastSent.put(cSender.getName(), System.currentTimeMillis());

		send(sender);
	}

	@Override
	public void sendTitle(Object sender, int fadeIn, int fadeOut, int stay)
	{
		if (!(sender instanceof ProxiedPlayer))
		{
			return;
		}

		List<String> messages = getList();

		if (messages.isEmpty())
		{
			return;
		}

		Title title = ProxyServer.getInstance().createTitle()
				.title(TextComponent.fromLegacyText(messages.get(0)))
				.fadeIn(fadeIn)
				.fadeOut(fadeIn)
				.stay(stay);

		if (messages.size() > 1)
		{
			title = title.subTitle(TextComponent.fromLegacyText(messages.get(1)));
		}

		title.send((ProxiedPlayer) sender);
	}

	@Override
	public void sendSubtitle(Object sender, int fadeIn, int fadeOut, int stay)
	{
		if (!(sender instanceof ProxiedPlayer))
		{
			return;
		}

		ProxyServer.getInstance().createTitle()
				.subTitle(TextComponent.fromLegacyText(get()))
				.fadeIn(fadeIn)
				.fadeOut(fadeIn)
				.stay(stay)
				.send((ProxiedPlayer) sender);
	}

}