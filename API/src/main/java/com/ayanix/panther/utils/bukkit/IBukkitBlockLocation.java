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
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public interface IBukkitBlockLocation
{

	/**
	 * Gets a string version of the block location.
	 *
	 * @return The string version.
	 */
	String toString();

	/**
	 * Gets the X location of the block.
	 *
	 * @return The x location.
	 */
	int getX();

	/**
	 * Set the X location to a new value.
	 *
	 * @param x The new X location of the block.
	 * @return The current, and modified, block location.
	 */
	IBukkitBlockLocation setX(int x);

	/**
	 * Gets the Y location of the block.
	 *
	 * @return The y location.
	 */
	int getY();

	/**
	 * Set the Y location to a new value.
	 *
	 * @param y The new Y location of the block.
	 * @return The current, and modified, block location.
	 */
	IBukkitBlockLocation setY(int y);

	/**
	 * Gets the Y location of the block.
	 *
	 * @return The y location.
	 */
	int getZ();

	/**
	 * Set the Z location to a new value.
	 *
	 * @param z The new Z location of the block.
	 * @return The current, and modified, block location.
	 */
	IBukkitBlockLocation setZ(int z);

	/**
	 * Gets the world the block is in.
	 *
	 * @return The block's world.
	 */
	World getWorld();

	/**
	 * Set the world of the block location.
	 *
	 * @param world The new world of the block.
	 * @return The current, and modified, block location.
	 * @throws IllegalArgumentException If the world is null.
	 */
	IBukkitBlockLocation setWorld(@NotNull World world);

	/**
	 * Converts the BlockLocation to a Bukkit Location.
	 *
	 * @return Bukkit Location representing the same co-ordinates and world.
	 */
	Location toBukkit();

	/**
	 * @return Whether or not the chunk is loaded.
	 */
	boolean isChunkLoaded();

}
