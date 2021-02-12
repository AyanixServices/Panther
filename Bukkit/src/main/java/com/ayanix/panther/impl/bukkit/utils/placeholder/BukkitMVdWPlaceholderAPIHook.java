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
package com.ayanix.panther.impl.bukkit.utils.placeholder;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
class BukkitMVdWPlaceholderAPIHook
{

	private JavaPlugin             plugin;
	private BukkitPlaceholderUtils utils;
	private Boolean                supportsSilent;
	private Method                 silentMethod;

	BukkitMVdWPlaceholderAPIHook(JavaPlugin plugin, BukkitPlaceholderUtils utils)
	{
		this.plugin = plugin;
		this.utils = utils;
	}

	void registerPlaceholder(JavaPlugin plugin, String name, BukkitPlaceholder bukkitPlaceholder, boolean silent)
	{
		if (supportsSilent == null)
		{
			try
			{
				silentMethod = PlaceholderAPI.class.getMethod("registerPlaceholder", Plugin.class, String.class, PlaceholderReplacer.class, boolean.class);

				supportsSilent = true;
			} catch (Exception e)
			{
				// Method to call silent does not exist
				// This is not running Ayanix Service's custom MVdWPlaceholderAPI
				supportsSilent = false;
			}
		}

		if (supportsSilent && silent)
		{
			try
			{
				silentMethod.invoke(null, plugin, plugin.getName().toLowerCase() + "_" + name, (PlaceholderReplacer) placeholderReplaceEvent -> bukkitPlaceholder.getRunnable().run(placeholderReplaceEvent.getPlayer()), true);
			} catch (IllegalAccessException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		} else
		{
			PlaceholderAPI.registerPlaceholder(plugin, plugin.getName().toLowerCase() + "_" + bukkitPlaceholder.getName(), event -> {
				if (bukkitPlaceholder.isPlayerOnly() && event.getPlayer() == null)
				{
					return null;
				}

				return bukkitPlaceholder.getRunnable().run(event.getPlayer());
			});
		}
	}

}