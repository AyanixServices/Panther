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
package com.ayanix.panther.impl.common.storage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 * <p>
 * This file was taken from SpigotMC#BungeeCord
 */
public abstract class ConfigurationProvider
{

	private static final Map<Class<? extends ConfigurationProvider>, ConfigurationProvider> providers = new HashMap<>();

	static
	{
		providers.put(YamlConfiguration.class, new YamlConfiguration());
	}

	public static ConfigurationProvider getProvider(Class<? extends ConfigurationProvider> provider)
	{
		return providers.get(provider);
	}

	public abstract void save(Configuration config, File file) throws IOException;

	public abstract void save(Configuration config, Writer writer);

	public abstract Configuration load(File file) throws IOException;

	public abstract Configuration load(File file, Configuration defaults) throws IOException;

	public abstract Configuration load(Reader reader);

	public abstract Configuration load(Reader reader, Configuration defaults);

	public abstract Configuration load(InputStream is);

	public abstract Configuration load(InputStream is, Configuration defaults);

	public abstract Configuration load(String string);

	public abstract Configuration load(String string, Configuration defaults);

}