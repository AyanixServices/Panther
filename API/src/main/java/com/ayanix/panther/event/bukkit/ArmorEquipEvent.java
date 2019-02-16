package com.ayanix.panther.event.bukkit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 * <p>
 * This class was based off of ArmorEquipEvent by Arnuh.
 */
public class ArmorEquipEvent extends PlayerEvent implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();

	private boolean   cancelled = false;
	private ItemStack oldArmor;
	private ItemStack newArmor;

	public ArmorEquipEvent(Player who, ItemStack oldArmor, ItemStack newArmor)
	{
		super(who);

		this.oldArmor = oldArmor;
		this.newArmor = newArmor;

		if (oldArmor != null && oldArmor.getType() == Material.AIR)
		{
			this.oldArmor = null;
		}

		if (newArmor != null && newArmor.getType() == Material.AIR)
		{
			this.newArmor = null;
		}
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	/**
	 * @return If the event has been cancelled.
	 */
	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	/**
	 * Sets whether or not the event should be cancelled.
	 *
	 * @param cancel If the event should be cancelled.
	 */
	@Override
	public void setCancelled(boolean cancel)
	{
		this.cancelled = true;
	}

	/**
	 * Gets the old armor piece. This is either NULL or an ItemStack.
	 * If the internal value is AIR, it will also return NULL.
	 *
	 * @return The old armor piece.
	 */
	public ItemStack getOldArmor()
	{
		return oldArmor;
	}

	/**
	 * Gets the new armor piece. This is either NULL or an ItemStack.
	 * If the internal value is AIR, it will also return NULL.
	 *
	 * @return The new armor piece.
	 */
	public ItemStack getNewArmor()
	{
		return newArmor;
	}

}
