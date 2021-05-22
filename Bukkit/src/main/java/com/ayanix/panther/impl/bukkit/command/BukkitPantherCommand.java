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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class BukkitPantherCommand extends Command implements PantherCommand, CommandExecutor
{

	private Map<String, Method> subCommands = new LinkedHashMap<>();

	/**
	 * Initiate a Panther command.
	 *
	 * @param name Name of command as specified in plugin.yml
	 */
	public BukkitPantherCommand(final String name)
	{
		super(name);

		orderSubcommands();
	}

	private void orderSubcommands()
	{
		Map<String, Method> subCommands = new LinkedHashMap<>();

		for (Method method : getClass().getDeclaredMethods())
		{
			if (!method.isAnnotationPresent(PantherSubCommand.class))
			{
				continue;
			}

			PantherSubCommand subCommand = method.getAnnotation(PantherSubCommand.class);

			for (String alias : subCommand.aliases())
			{
				subCommands.put(alias, method);
			}
		}

		subCommands.entrySet().stream().sorted((o1, o2) -> {
			if (!o1.getKey().contains(" ") && o2.getKey().contains(" "))
			{
				return 1;
			} else if (o1.getKey().contains(" ") && !o2.getKey().contains(" "))
			{
				return -1;
			}

			return Integer.compare(o2.getKey().split(" ").length, o1.getKey().split(" ").length);
		}).forEach(entry -> {
			this.subCommands.put(entry.getKey(), entry.getValue());
		});
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
			outer:
			for (Map.Entry<String, Method> entry : subCommands.entrySet())
			{
				String alias  = entry.getKey();
				Method method = entry.getValue();

				PantherSubCommand subCommand = method.getAnnotation(PantherSubCommand.class);

				int argsFrom = 1;

				String name = "";

				if (alias.contains(" "))
				{
					String[] parts = alias.split(" ");

					boolean completeMatch = false;

					for (int x = 0; x < parts.length; x++)
					{
						if (args.length < parts.length || !parts[x].equalsIgnoreCase(args[x]))
						{
							continue outer;
						}

						completeMatch = true;
					}

					if (completeMatch)
					{
						name = alias;

						argsFrom = parts.length;
					}
				} else
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

				String[] newArgs = Arrays.copyOfRange(args, argsFrom, args.length);

				if (newArgs.length < subCommand.minArgs())
				{
					getArgsError().send(commandSender);

					return true;
				}

				subCommandFound = true;

				try
				{
					method.invoke(this, commandSender, newArgs);
					break;
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