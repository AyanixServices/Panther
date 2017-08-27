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

import com.ayanix.panther.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class BukkitLocationUtils implements LocationUtils
{

	public BukkitLocationUtils()
	{
// LocationUtils is an API.
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
	public Location fromString(String string) throws IllegalArgumentException
	{
		if (!string.contains(":"))
		{
			throw new IllegalArgumentException("String is not location parsed");
		}

		String[] parts = string.split(":");

		if (parts.length < 4)
		{
			throw new IllegalArgumentException("String parts is not equal to or above 4");
		}

		World world = Bukkit.getWorld(parts[0]);

		if (world == null)
		{
			throw new IllegalArgumentException(parts[0] + " world does not exist");
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