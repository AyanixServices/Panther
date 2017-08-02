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

import org.bukkit.Location;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface ILocationUtils
{

	/**
	 * Gets a string version of a location.
	 *
	 * @param location The location to turn into a string.
	 * @return The string version.
	 */
	public String toString(Location location);

	/**
	 * Gets a Location from a #toString(Location) string.
	 *
	 * @param string String to get location from
	 * @return The location.
	 * @throws Exception If the location was invalid
	 */
	public Location fromString(String string) throws Exception;

	/**
	 * Gets the center position of a block.
	 *
	 * @param location The location to get the center position of.
	 * @return The center position (0.5, 0.5) of the given location.
	 */
	public Location getCenter(Location location);

}
