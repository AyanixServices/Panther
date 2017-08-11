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
package com.ayanix.panther.impl.bungee.utils;

import com.ayanix.panther.utils.DependencyChecks;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BungeeDependencyChecks implements DependencyChecks
{

	private final Logger logger;

	/**
	 * @param plugin Plugin requesting dependencies.
	 */
	public BungeeDependencyChecks(final Plugin plugin)
	{
		if (plugin == null)
		{
			throw new IllegalArgumentException("Plugin cannot be null");
		}

		this.logger = plugin.getLogger();
	}

	public boolean runChecks(final Map<String, String> dependencies)
	{
		return runChecks(logger, dependencies);
	}

	public boolean isEnabled(final String plugin, final String version)
	{
		final Plugin dependency = ProxyServer.getInstance().getPluginManager().getPlugin(plugin);

		return dependency != null && dependency.getDescription().getVersion().startsWith(version);
	}

	public boolean isEnabled(final String plugin)
	{
		return ProxyServer.getInstance().getPluginManager().getPlugin(plugin) != null;
	}

}