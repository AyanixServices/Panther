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
package com.ayanix.panther.impl.utils;

import com.ayanix.panther.utils.ILocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public class LocationUtils implements ILocationUtils
{

	public LocationUtils()
	{

	}

	@Override
	public String toString(Location location)
	{
		if (location == null)
		{
			throw new IllegalArgumentException("Location cannot be null");
		}

		StringBuilder builder = new StringBuilder();

		builder.append(location.getWorld().getName());
		builder.append(":");
		builder.append(location.getBlockX());
		builder.append(":");
		builder.append(location.getBlockY());
		builder.append(":");
		builder.append(location.getBlockZ());

		return builder.toString();
	}

	@Override
	public Location fromString(String string) throws Exception
	{
		if (!string.contains(":"))
		{
			throw new IllegalArgumentException("String is not location parsed");
		}

		String[] parts = string.split(":");

		if (parts.length != 4)
		{
			throw new IllegalArgumentException("String parts is not equal to 4");
		}

		World world = Bukkit.getWorld(parts[0]);

		if (world == null)
		{
			throw new Exception(parts[0] + " world does not exist");
		}

		int x = Integer.valueOf(parts[1]);
		int y = Integer.valueOf(parts[2]);
		int z = Integer.valueOf(parts[3]);

		return new Location(world, x, y, z);
	}

	@Override
	public Location getCenter(Location location)
	{
		location = location.clone();

		location.setX(location.getBlockX() + 0.5);
		location.setZ(location.getBlockZ() + 0.5);

		return location;
	}

}