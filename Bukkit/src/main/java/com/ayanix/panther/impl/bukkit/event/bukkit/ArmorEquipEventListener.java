package com.ayanix.panther.impl.bukkit.event.bukkit;

import com.ayanix.panther.event.bukkit.ArmorEquipEvent;
import com.ayanix.panther.event.bukkit.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2019.
 * <p>
 * This class was based off of ArmorEquipEvent by Arnuh.
 */
public class ArmorEquipEventListener implements Listener
{

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public final void onInventoryClick(InventoryClickEvent event)
	{
		boolean shift     = false;
		boolean numberKey = false;

		if (event.getAction() == InventoryAction.NOTHING)
		{
			return;
		}

		if (!(event.getWhoClicked() instanceof Player))
		{
			return;
		}

		if (event.getSlotType() != InventoryType.SlotType.ARMOR &&
				event.getSlotType() != InventoryType.SlotType.QUICKBAR &&
				event.getSlotType() != InventoryType.SlotType.CONTAINER)
		{
			return;
		}

		if (event.getClick().equals(ClickType.SHIFT_LEFT) ||
				event.getClick().equals(ClickType.SHIFT_RIGHT))
		{
			shift = true;
		}

		if (event.getClick().equals(ClickType.NUMBER_KEY))
		{
			numberKey = true;
		}

		if (event.getClickedInventory() != null &&
				!event.getClickedInventory().getType().equals(InventoryType.PLAYER))
		{
			return;
		}

		if (!event.getInventory().getType().equals(InventoryType.CRAFTING) &&
				!event.getInventory().getType().equals(InventoryType.PLAYER))
		{
			return;
		}

		ArmorType newArmorType = ArmorType.matchType(shift ? event.getCurrentItem() : event.getCursor());

		if (!shift && newArmorType != null && event.getRawSlot() != newArmorType.getSlot())
		{
			return;
		}

		Player player = (Player) event.getWhoClicked();

		if (shift)
		{
			newArmorType = ArmorType.matchType(event.getCurrentItem());

			if (newArmorType != null)
			{
				boolean equipping = true;

				if (event.getRawSlot() == newArmorType.getSlot())
				{
					equipping = false;
				}

				if (newArmorType.equals(ArmorType.HELMET) &&
						(equipping == isAirOrNull(player.getInventory().getHelmet())) ||
						newArmorType.equals(ArmorType.CHESTPLATE) && (equipping == isAirOrNull(player.getInventory().getChestplate())) ||
						newArmorType.equals(ArmorType.LEGGINGS) && (equipping == isAirOrNull(player.getInventory().getLeggings())) ||
						newArmorType.equals(ArmorType.BOOTS) && (equipping == isAirOrNull(player.getInventory().getBoots())))
				{
					ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, equipping ? null : event.getCurrentItem(), equipping ? event.getCurrentItem() : null);
					Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

					if (armorEquipEvent.isCancelled())
					{
						event.setCancelled(true);
					}
				}
			}
		} else
		{
			ItemStack newArmorPiece = event.getCursor();
			ItemStack oldArmorPiece = event.getCurrentItem();

			if (numberKey)
			{
				if (event.getClickedInventory().getType().equals(InventoryType.PLAYER))
				{
					ItemStack hotbarItem = event.getClickedInventory().getItem(event.getHotbarButton());

					if (!isAirOrNull(hotbarItem))
					{
						newArmorType = ArmorType.matchType(hotbarItem);
						newArmorPiece = hotbarItem;
						oldArmorPiece = event.getClickedInventory().getItem(event.getSlot());
					} else
					{
						newArmorType = ArmorType.matchType(!isAirOrNull(event.getCurrentItem()) ? event.getCurrentItem() : event.getCursor());
					}
				}
			} else
			{
				if (isAirOrNull(event.getCursor()) && !isAirOrNull(event.getCurrentItem()))
				{
					newArmorType = ArmorType.matchType(event.getCurrentItem());
				}
			}

			if (newArmorType != null && event.getRawSlot() == newArmorType.getSlot())
			{
				ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), oldArmorPiece, newArmorPiece);
				Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

				if (armorEquipEvent.isCancelled())
				{
					event.setCancelled(true);
				}
			}
		}
	}

	private boolean isAirOrNull(ItemStack item)
	{
		return item == null || item.getType().equals(Material.AIR);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			final Player player = event.getPlayer();

			ArmorType newArmorType = ArmorType.matchType(event.getItem());

			if (newArmorType != null)
			{
				if (newArmorType.equals(ArmorType.HELMET) && isAirOrNull(event.getPlayer().getInventory().getHelmet()) ||
						newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(event.getPlayer().getInventory().getChestplate()) ||
						newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(event.getPlayer().getInventory().getLeggings()) ||
						newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(event.getPlayer().getInventory().getBoots()))
				{
					ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(event.getPlayer(), null, event.getItem());

					Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

					if (armorEquipEvent.isCancelled())
					{
						event.setCancelled(true);
						player.updateInventory();
					}
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onInventoryDrag(InventoryDragEvent event)
	{
		ArmorType type = ArmorType.matchType(event.getOldCursor());

		if (event.getRawSlots().isEmpty())
		{
			return;
		}

		if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0))
		{
			ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), null, event.getOldCursor());
			Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

			if (armorEquipEvent.isCancelled())
			{
				event.setResult(Event.Result.DENY);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onItemBreak(PlayerItemBreakEvent event)
	{
		ArmorType type = ArmorType.matchType(event.getBrokenItem());

		if (type == null)
		{
			return;
		}

		Player          player          = event.getPlayer();
		ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, event.getBrokenItem(), null);

		Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);

		if (armorEquipEvent.isCancelled())
		{
			ItemStack item = event.getBrokenItem().clone();
			item.setAmount(1);
			item.setDurability((short) 0);

			if (type.equals(ArmorType.HELMET))
			{
				player.getInventory().setHelmet(item);
			} else if (type.equals(ArmorType.CHESTPLATE))
			{
				player.getInventory().setChestplate(item);
			} else if (type.equals(ArmorType.LEGGINGS))
			{
				player.getInventory().setLeggings(item);
			} else if (type.equals(ArmorType.BOOTS))
			{
				player.getInventory().setBoots(item);
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();

		for (ItemStack item : player.getInventory().getArmorContents())
		{
			if (!isAirOrNull(item))
			{
				Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(player, item, null));
			}
		}
	}

}
