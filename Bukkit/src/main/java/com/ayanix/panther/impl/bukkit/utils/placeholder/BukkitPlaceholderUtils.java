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

import com.ayanix.panther.impl.bukkit.utils.BukkitDependencyChecks;
import com.ayanix.panther.utils.DependencyChecks;
import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholder;
import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholderUtils;
import com.ayanix.panther.utils.bukkit.placeholder.PlaceholderRunnable;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitPlaceholderUtils implements IBukkitPlaceholderUtils
{

	private final JavaPlugin plugin;

	public BukkitPlaceholderUtils(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * Register a placeholder in all located compatible plugins.
	 *
	 * @param placeholder The identifier of the placeholder - do not include % or {}.
	 * @param runnable    The code executed when the placeholder is called.
	 * @return BukkitPlaceholder object containing registered status.
	 */
	public BukkitPlaceholder registerPlaceholder(String placeholder, PlaceholderRunnable runnable)
	{
		return registerPlaceholder(plugin, placeholder, runnable);
	}

	public BukkitPlaceholder registerPlaceholder(JavaPlugin plugin, String placeholder, PlaceholderRunnable runnable)
	{
		BukkitPlaceholder bukkitPlaceholder = new BukkitPlaceholder(placeholder, runnable);
		DependencyChecks  dependencyChecks  = new BukkitDependencyChecks(plugin);

		if (dependencyChecks.isEnabled("PlaceholderAPI"))
		{
			PlaceholderAPI.registerPlaceholderHook(plugin, new PlaceholderHook()
			{
				@Override
				public String onPlaceholderRequest(Player player, String s)
				{
					if (!s.equalsIgnoreCase(placeholder))
					{
						return null;
					}

					return runnable.run(player);
				}
			});

			bukkitPlaceholder.setRegistered(IBukkitPlaceholder.PlaceholderType.PLACEHOLDERAPI, true);

			plugin.getLogger().info(() -> "Registered PlaceholderAPI placeholder: %" + plugin.getName().toLowerCase() + "_" + placeholder + "%");
		}

		if (dependencyChecks.isEnabled("MVdWPlaceholderAPI"))
		{
			be.maximvdw.placeholderapi.PlaceholderAPI.registerPlaceholder(plugin, plugin.getName().toLowerCase() + "_" + placeholder, event -> runnable.run(event.getPlayer()));

			bukkitPlaceholder.setRegistered(IBukkitPlaceholder.PlaceholderType.MVDW_PLACEHOLDER_API, true);
		}

		return bukkitPlaceholder;
	}

}