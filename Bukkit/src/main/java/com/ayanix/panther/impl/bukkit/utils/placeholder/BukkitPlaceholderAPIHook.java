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

import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholder;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
class BukkitPlaceholderAPIHook
{

	private JavaPlugin             plugin;
	private BukkitPlaceholderUtils utils;

	BukkitPlaceholderAPIHook(JavaPlugin plugin, BukkitPlaceholderUtils utils)
	{
		this.plugin = plugin;
		this.utils = utils;
	}

	protected void handlePlaceholderAPI()
	{
		PlaceholderAPI.registerPlaceholderHook(plugin, new PlaceholderHook()
		{
			@Override
			public String onPlaceholderRequest(Player player, String s)
			{
				BukkitPlaceholder placeholder = utils.getPlaceholders().get(s.toLowerCase());

				if (placeholder == null)
				{
					return null;
				}

				if (!placeholder.isRegistered(IBukkitPlaceholder.PlaceholderType.PLACEHOLDERAPI))
				{
					return null;
				}

				if (placeholder.getName().equalsIgnoreCase(s) && placeholder.isRegistered(IBukkitPlaceholder.PlaceholderType.PLACEHOLDERAPI))
				{
					if (placeholder.isPlayerOnly() && player == null)
					{
						return null;
					}

					return placeholder.getRunnable().run(player);
				}

				return null;
			}
		});
	}

	protected void unregisterPlaceholderAPI()
	{
		PlaceholderAPI.unregisterPlaceholderHook(plugin);
	}

}