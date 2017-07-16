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

import com.ayanix.panther.impl.utils.DependencyChecks;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class, JavaPlugin.class, PluginBase.class})
public class DependencyChecksTest
{

	/**
	 * Setup Bukkit and plugin manager before hand.
	 */
	@Before
	public void setupTest()
	{
		PowerMockito.mockStatic(Bukkit.class);
		PowerMockito.when(Bukkit.getServer()).thenReturn(PowerMockito.mock(Server.class));

		PluginManager pluginManager = PowerMockito.mock(PluginManager.class);
		PowerMockito.when(Bukkit.getPluginManager()).thenReturn(pluginManager);
	}

	@Test
	public void testDependencyCheck()
	{
		JavaPlugin   plugin = PowerMockito.mock(JavaPlugin.class);
		PluginLogger logger = PowerMockito.mock(PluginLogger.class);
		PowerMockito.when(plugin.getLogger()).thenReturn(logger);

		DependencyChecks checks = PowerMockito.spy(new DependencyChecks(plugin));

		HashMap<String, String> dependencies = new HashMap<>();

		dependencies.put("Factions", "6.0.0");

		boolean allEnabled = checks.runChecks(dependencies);

		Assert.assertFalse("Dependencies must come back false if one is missing", allEnabled);
	}

}