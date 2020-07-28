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
package com.ayanix.panther.utils.bukkit.placeholder;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IBukkitPlaceholderUtils
{

	/**
	 * Register a placeholder in all located compatible plugins.
	 *
	 * @param plugin      The plugin which is registering the placeholder.
	 * @param placeholder The identifier of the placeholder - do not include % or {}.
	 * @param runnable    The code executed when the placeholder is called.
	 * @return IBukkitPlaceholder object containing registered status.
	 */
	default IBukkitPlaceholder registerPlaceholder(JavaPlugin plugin, String placeholder, IBukkitPlaceholderRunnable runnable)
	{
		return registerPlaceholder(plugin, placeholder, runnable, false);
	}

	/**
	 * Register a placeholder in all located compatible plugins.
	 *
	 * @param plugin      The plugin which is registering the placeholder.
	 * @param placeholder The identifier of the placeholder - do not include % or {}.
	 * @param runnable    The code executed when the placeholder is called.
	 * @param silent      If true, Panther will not output a message verifying the placeholder has been registered.
	 * @return IBukkitPlaceholder object containing registered status.
	 */
	IBukkitPlaceholder registerPlaceholder(JavaPlugin plugin, String placeholder, IBukkitPlaceholderRunnable runnable, boolean silent);

	/**
	 * Unregisters all placeholders registered by the plugin.
	 *
	 * This will only work if the same BukkitPlaceholderUtils instance registered the placeholder.
	 * This will also remove the PlaceholderAPI hook.
	 */
	void unregisterAll();

}