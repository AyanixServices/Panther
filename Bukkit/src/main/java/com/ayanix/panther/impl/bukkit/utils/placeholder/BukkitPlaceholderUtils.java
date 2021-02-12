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
import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholderRunnable;
import com.ayanix.panther.utils.bukkit.placeholder.IBukkitPlaceholderUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitPlaceholderUtils implements IBukkitPlaceholderUtils
{

	private static BukkitPlaceholderUtils         instance;
	private final  JavaPlugin                     plugin;
	private final  Map<String, BukkitPlaceholder> placeholders;
	private        BukkitPlaceholderAPIHook       placeholderAPIHook;
	private        BukkitMVdWPlaceholderAPIHook   mvdwPlaceholderAPIHook;

	public BukkitPlaceholderUtils(JavaPlugin plugin)
	{
		this.plugin = plugin;
		this.placeholders = new ConcurrentHashMap<>();

		this.placeholderAPIHook = null;
	}

	/**
	 * Declare the static version of BukkitPlaceholderUtils.
	 *
	 * @param plugin Plugin using BukkitPlaceholderUtils.
	 */
	public static void init(JavaPlugin plugin)
	{
		if (instance != null)
		{
			return;
		}

		instance = new BukkitPlaceholderUtils(plugin);
	}

	/**
	 * Grab the static version of BukkitPlaceholderUtils after being declared in BukkitPlaceholderUtils#init.
	 *
	 * @return BukkitPlaceholderUtils.
	 * @throws RuntimeException If BukkitPlaceholderUtils#init has not been called.
	 */
	public static BukkitPlaceholderUtils get()
	{
		if (instance == null)
		{
			throw new RuntimeException("BukkitPlaceholderUtils has not been initialised for static usage");
		}

		return instance;
	}

	/**
	 * Register a placeholder in all located compatible plugins.
	 *
	 * @param placeholder The identifier of the placeholder - do not include % or {}.
	 * @param runnable    The code executed when the placeholder is called.
	 * @return BukkitPlaceholder object containing registered status.
	 */
	public BukkitPlaceholder registerPlaceholder(String placeholder, IBukkitPlaceholderRunnable runnable)
	{
		return registerPlaceholder(plugin, placeholder, runnable, false);
	}

	public BukkitPlaceholder registerPlaceholder(JavaPlugin plugin, String placeholder, IBukkitPlaceholderRunnable runnable, boolean silent)
	{
		placeholder = placeholder.toLowerCase();

		BukkitPlaceholder bukkitPlaceholder = new BukkitPlaceholder(placeholder, runnable);
		DependencyChecks  dependencyChecks  = new BukkitDependencyChecks(plugin);

		placeholders.put(placeholder, bukkitPlaceholder);

		if (dependencyChecks.isEnabled("PlaceholderAPI"))
		{
			if (placeholderAPIHook == null)
			{
				handlePlaceholderAPI();
			}

			bukkitPlaceholder.setRegistered(IBukkitPlaceholder.PlaceholderType.PLACEHOLDERAPI, true);

			if (!silent)
			{
				String finalPlaceholder = placeholder;
				plugin.getLogger().info(() -> "Registered PlaceholderAPI placeholder: %" + plugin.getName().toLowerCase() + "_" + finalPlaceholder + "%");
			}
		}

		if (dependencyChecks.isEnabled("MVdWPlaceholderAPI"))
		{
			if (mvdwPlaceholderAPIHook == null)
			{
				handleMVdWPlaceholderAPI();
			}

			mvdwPlaceholderAPIHook.registerPlaceholder(plugin, placeholder, bukkitPlaceholder, silent);

			bukkitPlaceholder.setRegistered(IBukkitPlaceholder.PlaceholderType.MVDW_PLACEHOLDER_API, true);
		}

		return bukkitPlaceholder;
	}

	private void handlePlaceholderAPI()
	{
		if (!new BukkitDependencyChecks(plugin).isEnabled("PlaceholderAPI"))
		{
			return;
		}

		// This was moved to its own class to remove NoClass errors.
		placeholderAPIHook = new BukkitPlaceholderAPIHook(plugin, this);
		placeholderAPIHook.handlePlaceholderAPI();
	}

	private void handleMVdWPlaceholderAPI()
	{
		if (!new BukkitDependencyChecks(plugin).isEnabled("MVdWPlaceholderAPI"))
		{
			return;
		}

		mvdwPlaceholderAPIHook = new BukkitMVdWPlaceholderAPIHook(plugin, this);
	}

	@Override
	public void unregisterAll()
	{
		placeholders.values().forEach(IBukkitPlaceholder::unregister);

		if (placeholderAPIHook != null)
		{
			placeholderAPIHook.unregisterPlaceholderAPI();
			placeholderAPIHook = null;
		}
	}

	/**
	 * Register a placeholder in all located compatible plugins.
	 *
	 * @param placeholder The identifier of the placeholder - do not include % or {}.
	 * @param runnable    The code executed when the placeholder is called.
	 * @param silent      If true, Panther will not output a message verifying the placeholder has been registered.
	 * @return BukkitPlaceholder object containing registered status.
	 */
	public BukkitPlaceholder registerPlaceholder(String placeholder, IBukkitPlaceholderRunnable runnable, boolean silent)
	{
		return registerPlaceholder(plugin, placeholder, runnable, silent);
	}

	protected Map<String, BukkitPlaceholder> getPlaceholders()
	{
		return placeholders;
	}

}