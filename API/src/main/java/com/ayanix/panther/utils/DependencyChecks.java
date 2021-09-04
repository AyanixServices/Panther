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
package com.ayanix.panther.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface DependencyChecks
{

	/**
	 * Checks if all the dependencies are enabled, printing to console the status
	 * and returning if all dependencies are enabled.
	 * <p>
	 * The version checks the starting version, e.g. a parameter of 6 would work with 6.x.x versions.
	 *
	 * @param logger       The logger to log info and severe messages. If null, no output messages.
	 * @param dependencies HashMap of dependencies - key is plugin, value is version.
	 * @return If all dependencies are enabled.
	 */
	default boolean runChecks(Logger logger, Map<String, String> dependencies)
	{
		if (dependencies == null)
		{
			throw new IllegalArgumentException("Dependencies cannot be null");
		}

		boolean allEnabled = true;

		final StringBuilder templateError = new StringBuilder("\u2718 Missing ");

		for (final Map.Entry<String, String> entry : dependencies.entrySet())
		{
			final String pluginName = entry.getKey();
			final String version    = entry.getValue();

			if (!isEnabled(pluginName, version))
			{
				allEnabled = false;

				final StringBuilder error = new StringBuilder(templateError)
						.append(pluginName);

				if (!version.isEmpty())
				{
					error.append(" (v").append(version).append(')');
				}

				if (logger != null)
				{
					logger.severe(error::toString);
				}

				continue;
			}

			if (logger != null)
			{
				logger.info(() -> "\u2714 " + pluginName + " detected");
			}
		}

		return allEnabled;
	}

	/**
	 * Checks if plugin is enabled and whether the correct version is supplied.
	 *
	 * @param plugin  Name of plugin.
	 * @param version The version checks the starting version, e.g. a parameter of 6 would work with 6.x.x versions.
	 * @return If plugin is enabled and correct version is supplied.
	 */
	boolean isEnabled(String plugin, String version);

	/**
	 * Checks if all the dependencies are enabled, printing to console the status
	 * and returning if all dependencies are enabled.
	 * <p>
	 * The version checks the starting version, e.g. a parameter of 6 would work with 6.x.x versions.
	 *
	 * @param dependencies HashMap of dependencies - key is plugin, value is version.
	 * @return If all dependencies are enabled.
	 */
	boolean runChecks(Map<String, String> dependencies);

	/**
	 * Checks whether plugin is enabled.
	 *
	 * @param plugin Name of plugin.
	 * @return If plugin is enabled.
	 */
	boolean isEnabled(String plugin);

	default List<Integer> getVersionValues(String version)
	{
		List<Integer> versionValues = new ArrayList<>();

		while (!version.isEmpty())
		{
			try
			{
				int index = version.indexOf(".");

				versionValues.add(Integer.parseInt(version.substring(0, index == -1 ? version.length() : index)));

				if (index != -1)
					version = version.substring(index + 1);
				else
					return versionValues;

			} catch (IllegalArgumentException e)
			{
				return versionValues;
			}
		}

		return versionValues;
	}

	/**
	 * Checks whether plugin is enabled and if it's version is less than or equal to a specific version.
	 *
	 * @param plugin Name of plugin.
	 * @param version Version to compare.
	 * @return If plugin is enabled and it's version is less than or equal to a specific version.
	 */
	boolean isVersionNoHigherThan(final String plugin, final String version);

	/**
	 * Checks whether plugin is enabled and if it's version is greater than or equal to a specific version.
	 *
	 * @param plugin Name of plugin.
	 * @param version Version to compare.
	 * @return If plugin is enabled and it's version is greater than or equal to a specific version.
	 */
	boolean isVersionAtLeast(final String plugin, final String version);

	/**
	 * Checks whether plugin is enabled and if it's version is greater than a specific version.
	 *
	 * @param plugin Name of plugin.
	 * @param version Version to compare.
	 * @return If plugin is enabled and it's version is greater than a specific version.
	 */
	boolean isVersionHigherThan(final String plugin, final String version);

	/**
	 * Checks whether plugin is enabled and if it's version is less than a specific version.
	 *
	 * @param plugin Name of plugin.
	 * @param version Version to compare.
	 * @return If plugin is enabled and it's version is less than a specific version.
	 */
	boolean isVersionLowerThan(final String plugin, final String version);

}