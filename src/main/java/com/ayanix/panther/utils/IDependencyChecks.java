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
 *                       .                `              `-..__.....----...., `.
 *                      '                 `  '''---..-''`'              : :  : :
 *                    .` -                '`.  `-.                       `'   `'
 *                 .-` .` '             .`'.__ ;
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

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IDependencyChecks
{

	/**
	 * Checks if all the dependencies are enabled, printing to console the status
	 * and returning if all dependencies are enabled.
	 * <p>
	 * The version checks the starting version, e.g. a parameter of 6 would work with 6.x.x versions.
	 *
	 * @param plugin       Plugin to use logger of.
	 * @param dependencies HashMap of dependencies - key is plugin, value is version.
	 * @return If all dependencies are enabled.
	 */
	boolean runChecks(JavaPlugin plugin, HashMap<String, String> dependencies);

	/**
	 * Checks if plugin is enabled and whether the correct version is supplied.
	 *
	 * @param plugin  Name of plugin.
	 * @param version The version checks the starting version, e.g. a parameter of 6 would work with 6.x.x versions.
	 * @return If plugin is enabled and correct version is supplied.
	 */
	boolean isEnabled(String plugin, String version);

	/**
	 * Checks whether plugin is enabled.
	 *
	 * @param plugin Name of plugin.
	 * @return If plugin is enabled.
	 */
	boolean isEnabled(String plugin);

}