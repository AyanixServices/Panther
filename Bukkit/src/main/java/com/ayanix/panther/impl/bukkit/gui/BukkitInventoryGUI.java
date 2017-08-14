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
package com.ayanix.panther.impl.bukkit.gui;

import com.ayanix.panther.gui.GUIItem;
import com.ayanix.panther.gui.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class BukkitInventoryGUI implements InventoryGUI, Listener
{

	private Inventory                 inventory;
	private String                    name;
	private int                       slots;
	private HashMap<Integer, GUIItem> items;
	private Plugin                    plugin;
	private Player                    player;

	/**
	 * Initiate a ChestGUI instance.
	 *
	 * @param plugin Plugin owning GUI.
	 * @param player Player viewing GUI.
	 * @param name   Title of GUI
	 * @param type   Type of GUI.
	 * @param slots  Size of GUI in slots (only applicable for CHEST type).
	 */
	public BukkitInventoryGUI(Plugin plugin,
	                          Player player,
	                          String name,
	                          InventoryType type,
	                          int slots)
	{
		this.name = name;
		this.slots = slots;
		this.plugin = plugin;
		this.player = player;

		this.items = new HashMap<>();

		if (type != InventoryType.CHEST)
		{
			this.inventory = Bukkit.createInventory(player, type, ChatColor.translateAlternateColorCodes('&', name));
		} else
		{
			this.inventory = Bukkit.createInventory(player, slots, ChatColor.translateAlternateColorCodes('&', name));
		}
	}

	@Override
	public InventoryType getInventoryType()
	{
		return inventory.getType();
	}

	@Override
	public int getRows()
	{
		return this.slots / 9;
	}

	@Override
	public int getSlots()
	{
		return this.slots;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void insert(int slot, ItemStack item)
	{
		if (item == null)
		{
			insert(slot, new ItemStack(Material.AIR));
		}

		insert(slot, new BukkitGUIItem(item)
		{
			@Override
			public void run(Player player, ClickType type)
			{
				// Do nothing
			}
		});
	}

	@Override
	public void insert(int slot, GUIItem item)
	{
		if (item == null)
		{
			items.remove(slot);
			inventory.setItem(slot, new ItemStack(Material.AIR));

			return;
		}

		items.put(slot, item);
		inventory.setItem(slot, item.getItemStack());
	}

	@Override
	public void clear()
	{
		this.items.clear();

		for (int x = 0; x < slots - 1; x++)
		{
			inventory.setItem(0, new ItemStack(Material.AIR));
		}
	}

	@Override
	public void refresh()
	{
		for (Map.Entry<Integer, GUIItem> entry : items.entrySet())
		{
			inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
		}
	}

	@Override
	public void open()
	{
		init();

		player.openInventory(inventory);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Used for item initiation.
	 */
	protected abstract void init();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getClickedInventory() == null)
		{
			return;
		}

		if (event.getWhoClicked().getName().equals(player.getName()))
		{
			if (event.getClickedInventory().getName().equalsIgnoreCase(inventory.getName()))
			{
				int slot = event.getSlot();

				if (items.containsKey(slot))
				{
					items.get(slot).run(player, event.getClick());
				}
			}

			event.setCancelled(true);

			Bukkit.getScheduler().runTaskLater(plugin, () -> player.updateInventory(), 1L);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		if (event.getPlayer().getName().equals(player.getName()))
		{
			if (!canClose())
			{
				Bukkit.getScheduler().runTaskLater(plugin, () -> event.getPlayer().openInventory(inventory), 1L);

				return;
			}

			HandlerList.unregisterAll(this);

			close();
		}
	}

	/**
	 * Run when GUI is closed.
	 */
	protected abstract void close();

	/**
	 * If false, plugin will re-open GUI 1 tick later.
	 *
	 * @return Whether or not GUI can be closed.
	 */
	@Override
	public boolean canClose()
	{
		return true;
	}

}