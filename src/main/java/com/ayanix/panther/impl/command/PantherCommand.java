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
package com.ayanix.panther.impl.command;

import com.ayanix.panther.command.IPantherCommand;
import com.ayanix.panther.command.PantherSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class PantherCommand extends Command implements IPantherCommand
{

	/**
	 * Initiate a Panther command.
	 *
	 * @param name Name of command as specified in plugin.yml
	 */
	PantherCommand(String name)
	{
		super(name);
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args)
	{
		if (isPlayerOnly() && !(commandSender instanceof Player))
		{
			commandSender.sendMessage(getName() + " is a player-only command.");
			return true;
		}

		if (getPermission() != null && !getPermission().isEmpty())
		{
			if (!commandSender.hasPermission(getPermission()))
			{
				getPermissionError().send(commandSender);

				return true;
			}
		}

		if (args.length < getMinArgs())
		{
			getArgsError().send(commandSender);
			return true;
		}

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

				if (name.equals(""))
				{
					continue;
				}

				if (subCommand.onlyPlayer() && !(commandSender instanceof Player))
				{
					commandSender.sendMessage(name + " is a player-only command.");
					return true;
				}

				if (!subCommand.permission().isEmpty())
				{
					if (!commandSender.hasPermission(subCommand.permission()))
					{
						getPermissionError().send(commandSender);

						return true;
					}
				}

				args = Arrays.copyOfRange(args, 1, args.length);

				if (args.length < subCommand.minArgs())
				{
					getArgsError().send(commandSender);

					return true;
				}

				try
				{
					method.invoke(this, commandSender, args);
				} catch (IllegalAccessException | InvocationTargetException e)
				{
					e.printStackTrace();
				}

				return true;
			}
		}

		run(commandSender, label, args);

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