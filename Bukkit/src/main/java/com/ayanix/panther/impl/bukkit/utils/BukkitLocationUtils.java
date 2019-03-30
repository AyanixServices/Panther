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
package com.ayanix.panther.impl.bukkit.utils;

import com.ayanix.panther.utils.bukkit.IBukkitLocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitLocationUtils implements IBukkitLocationUtils
{

	private static BukkitLocationUtils instance;

	/**
	 * Grab the static version of BukkitLocationUtils.
	 *
	 * @return BukkitLocationUtils.
	 */
	public static BukkitLocationUtils get()
	{
		if (instance == null)
		{
			instance = new BukkitLocationUtils();
		}

		return instance;
	}

	@Override
	public String toString(Location location)
	{
		return toString(location, true);
	}

	@Override
	public String toString(Location location, boolean yawAndPitch)
	{
		if (location == null)
		{
			throw new IllegalArgumentException("Location cannot be null");
		}

		StringBuilder builder = new StringBuilder().append(location.getWorld().getName())
				.append(':')
				.append(location.getBlockX())
				.append(':')
				.append(location.getBlockY())
				.append(':')
				.append(location.getBlockZ());

		if (yawAndPitch)
		{
			builder = builder
					.append(':')
					.append(location.getYaw())
					.append(':')
					.append(location.getPitch());
		}

		return builder.toString();
	}

	@Override
	@Nullable
	public Location fromString(@Nullable String string)
	{
		if (string == null)
		{
			return null;
		}

		if (!string.contains(":"))
		{
			// String is not location parsed.
			return null;
		}

		String[] parts = string.split(":");

		if (parts.length < 4)
		{
			// String parts is not equal to or above 4.
			return null;
		}

		World world = Bukkit.getWorld(parts[0]);

		if (world == null)
		{
			// World does not exist.
			return null;
		}

		int x = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		int z = Integer.parseInt(parts[3]);

		if (parts.length != 6)
		{
			return new Location(world, x, y, z);
		}

		return new Location(world, x, y, z, Float.valueOf(parts[4]), Float.valueOf(parts[5]));
	}

	@Override
	public Location getCenter(Location location)
	{
		Location newLoc = location.clone();

		newLoc.setX(location.getBlockX() + 0.5);
		newLoc.setZ(location.getBlockZ() + 0.5);

		return newLoc;
	}

}