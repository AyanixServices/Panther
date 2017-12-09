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
package com.ayanix.panther.utils.bukkit;

import org.bukkit.Location;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IBukkitLocationUtils
{

	/**
	 * Gets a string version of a location.
	 *
	 * @param location The location to turn into a string.
	 * @return The string version.
	 */
	String toString(Location location);

	/**
	 * Gets a string version of a location.
	 *
	 * @param location    The location to turn into a string.
	 * @param yawAndPitch Whether or not to return yaw and pitch.
	 * @return The string version.
	 */
	String toString(Location location, boolean yawAndPitch);

	/**
	 * Gets a Location from a #toString(Location) string.
	 *
	 * @param string String to get location from
	 * @return The location.
	 * @throws IllegalArgumentException If the location was invalid
	 */
	Location fromString(String string) throws IllegalArgumentException;

	/**
	 * Gets the center position of a block.
	 *
	 * @param location The location to get the center position of.
	 * @return The center position (0.5, 0.5) of the given location.
	 */
	Location getCenter(Location location);

}
