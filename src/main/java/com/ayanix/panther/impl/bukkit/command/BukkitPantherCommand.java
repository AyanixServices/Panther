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
package com.ayanix.panther.impl.bukkit.command;

import com.ayanix.panther.command.PantherCommand;
import com.ayanix.panther.command.PantherSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class BukkitPantherCommand extends Command implements PantherCommand, CommandExecutor
{

	/**
	 * Initiate a Panther command.
	 *
	 * @param name Name of command as specified in plugin.yml
	 */
	public BukkitPantherCommand(final String name)
	{
		super(name);
	}

	@Override
	public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings)
	{
		return command.getName().equalsIgnoreCase(getName()) && execute(commandSender, s, strings);
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String label, final String[] args)
	{
		if (isPlayerOnly() && !(commandSender instanceof Player))
		{
			commandSender.sendMessage(getName() + " is a player-only command.");
			return true;
		}

		if (getPermission() != null && !getPermission().isEmpty() && !commandSender.hasPermission(getPermission()))
		{
			getPermissionError().send(commandSender);

			return true;
		}

		if (args.length < getMinArgs())
		{
			getArgsError().send(commandSender);
			return true;
		}

		boolean subCommandFound = false;

		if (args.length != 0)
		{
			for (Method method : getClazz().getDeclaredMethods())
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

				if (subCommand.onlyPlayer() && !(commandSender instanceof Player))
				{
					commandSender.sendMessage(name + " is a player-only command.");
					return true;
				}

				if (!subCommand.permission().isEmpty() && !commandSender.hasPermission(subCommand.permission()))
				{
					getPermissionError().send(commandSender);

					return true;
				}

				String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

				if (newArgs.length < subCommand.minArgs())
				{
					getArgsError().send(commandSender);

					return true;
				}

				subCommandFound = true;

				try
				{
					method.invoke(this, commandSender, newArgs);
				} catch (IllegalAccessException | InvocationTargetException e)
				{
					Bukkit.getLogger().log(Level.SEVERE, "Exception thrown executing command", e);
				}
			}
		}

		if (!subCommandFound)
		{
			run(commandSender, label, args);
		}

		return true;
	}

	@Override
	public String getPermissionMessage()
	{
		return getPermissionError().get();
	}

	@Override
	public String getUsage()
	{
		return getArgsError().get();
	}

}