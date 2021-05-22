package com.ayanix.panther.impl.bungee.command;

import com.ayanix.panther.command.PantherCommand;
import com.ayanix.panther.command.PantherSubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class BungeePantherCommand extends Command implements PantherCommand
{

	/**
	 * Initiate a Panther command.
	 *
	 * @param name Name of command.
	 */
	public BungeePantherCommand(String name)
	{
		super(name);
	}

	@Override
	public void execute(CommandSender commandSender, String[] args)
	{
		if (isPlayerOnly() && !(commandSender instanceof ProxiedPlayer))
		{
			commandSender.sendMessage(getName() + " is a player-only command.");
			return;
		}

		if (getPermission() != null && !getPermission().isEmpty() && !commandSender.hasPermission(getPermission()))
		{
			getPermissionError().send(commandSender);
			return;
		}

		if (args.length < getMinArgs())
		{
			getArgsError().send(commandSender);
			return;
		}

		boolean subCommandFound = false;

		if (args.length != 0)
		{
			for (Method method : getClass().getDeclaredMethods())
			{
				if (!method.isAnnotationPresent(PantherSubCommand.class))
				{
					continue;
				}

				PantherSubCommand subCommand = method.getAnnotation(PantherSubCommand.class);

				String name = "";

				for (String alias : subCommand.aliases())
				{
					if (alias.equalsIgnoreCase(args[0]))
					{
						name = alias;
					}
				}

				if ("".equals(name))
				{
					continue;
				}

				if (subCommand.onlyPlayer() && !(commandSender instanceof ProxiedPlayer))
				{
					commandSender.sendMessage(name + " is a player-only command.");
					return;
				}

				if (!subCommand.permission().isEmpty() && !commandSender.hasPermission(subCommand.permission()))
				{
					getPermissionError().send(commandSender);
					return;
				}

				String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

				if (newArgs.length < subCommand.minArgs())
				{
					getArgsError().send(commandSender);
					return;
				}

				subCommandFound = true;

				try
				{
					method.invoke(this, commandSender, newArgs);
				} catch (IllegalAccessException | InvocationTargetException e)
				{
					ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Exception thrown executing command", e);
				}
			}
		}

		if (!subCommandFound)
		{
			run(commandSender, getName(), args);
		}
	}

	@Override
	public abstract String getPermission();

	@Override
	public abstract String[] getAliases();

}