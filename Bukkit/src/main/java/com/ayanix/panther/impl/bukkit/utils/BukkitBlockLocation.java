package com.ayanix.panther.impl.bukkit.utils;

import com.ayanix.panther.utils.bukkit.IBukkitBlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 */
public class BukkitBlockLocation implements IBukkitBlockLocation
{

	private World world;
	private int   x;
	private int   y;
	private int   z;

	/**
	 * Initiate a BukkitBlockLocation.
	 *
	 * @param world World block is in.
	 * @param x     X location of block.
	 * @param y     Y location of block.
	 * @param z     Z location of block.
	 */
	public BukkitBlockLocation(World world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Get a BukkitBlockLocation version of a Bukkit Location.
	 *
	 * @param location Location to convert to BlockLocation.
	 * @return A BukkitBlockLocation instance representing the same location as the location parameter, or null if given location is null.
	 */
	@Nullable
	public static BukkitBlockLocation valueOf(Location location)
	{
		if (location == null)
		{
			return null;
		}

		return new BukkitBlockLocation(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	/**
	 * Gets a Location from a #toString() string.
	 *
	 * @param string String to get location from
	 * @return The location, or null the location could not be retrieved.
	 */
	@Nullable
	public static BukkitBlockLocation valueOf(@Nullable String string)
	{
		if (string == null || !string.contains(":"))
		{
			return null;
		}

		String[] parts = string.split(":");

		if (parts.length < 4)
		{
			return null;
		}

		if (Bukkit.getWorld(parts[0]) == null)
		{
			return null;
		}

		return new BukkitBlockLocation(Bukkit.getWorld(parts[0]),
				Integer.parseInt(parts[1]),
				Integer.parseInt(parts[2]),
				Integer.parseInt(parts[3]));
	}

	@Override
	public String toString()
	{
		return world.getName() + ":" + getX() + ":" + getY() + ":" + getZ();
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public int getZ()
	{
		return z;
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public IBukkitBlockLocation setX(int x)
	{
		this.x = x;

		return this;
	}

	@Override
	public IBukkitBlockLocation setY(int y)
	{
		this.y = y;

		return this;
	}

	@Override
	public IBukkitBlockLocation setZ(int z)
	{
		this.y = z;

		return this;
	}

	@Override
	public IBukkitBlockLocation setWorld(@NotNull World world)
	{
		if (world == null)
		{
			throw new IllegalArgumentException("World cannot be null");
		}

		this.world = world;

		return this;
	}

	@Override
	public Location toBukkit()
	{
		return new Location(world, x, y, z);
	}

	@Override
	public int hashCode()
	{
		return world.getName().hashCode() + x + y + z;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Location)
		{
			return equals((Location) obj);
		}

		if (obj instanceof IBukkitBlockLocation)
		{
			IBukkitBlockLocation blockLocation = (IBukkitBlockLocation) obj;

			return blockLocation.getX() == getX() &&
					blockLocation.getY() == getY() &&
					blockLocation.getZ() == getZ() &&
					blockLocation.getWorld().equals(world);
		}

		return false;
	}

	private boolean equals(Location location)
	{
		return BukkitBlockLocation.valueOf(location).equals(this);
	}

}