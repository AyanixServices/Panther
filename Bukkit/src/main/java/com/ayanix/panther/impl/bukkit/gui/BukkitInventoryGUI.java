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

import com.ayanix.panther.gui.bukkit.GUIDragEvent;
import com.ayanix.panther.gui.bukkit.GUIItem;
import com.ayanix.panther.gui.bukkit.GUIShiftClickMoveEvent;
import com.ayanix.panther.gui.bukkit.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class BukkitInventoryGUI implements InventoryGUI, Listener
{

	protected HashMap<Integer, GUIItem>  items;
	protected Plugin                     plugin;
	protected Player                     player;
	private   Inventory                  inventory;
	private   String                     name;
	private   int                        slots;
	private   boolean                    closed;
	private   int                        taskId;
	private   Map<Integer, GUIDragEvent> draggable;
	protected GUIShiftClickMoveEvent     guiShiftClickMoveEvent;

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
		this.draggable = new HashMap<>();

		this.closed = true;

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

		insert(slot, new FillerGUIItem(item));
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
	public void remove(int slot)
	{
		items.remove(slot);
		inventory.setItem(slot, null);
	}

	@Override
	public void clear()
	{
		this.items.clear();

		for (int x = 0; x < slots; x++)
		{
			inventory.setItem(x, new ItemStack(Material.AIR));
		}
	}

	@Override
	public void refresh()
	{
		for (int slot : items.keySet())
		{
			refresh(slot);
		}
	}

	@Override
	public void refresh(int slot)
	{
		getItem(slot).ifPresent(item -> {
			if (draggable.containsKey(slot))
			{
				// don't re-set draggable items incase of dupes
				return;
			}

			inventory.setItem(slot, item.getItemStack());
		});
	}

	@Override
	public Optional<GUIItem> getItem(int slot)
	{
		return Optional.ofNullable(items.get(slot));
	}

	@Override
	public Optional<ItemStack> getItemStack(int slot)
	{
		return Optional.ofNullable(inventory.getItem(slot) != null && inventory.getItem(slot).getType() != Material.AIR ? inventory.getItem(slot) : null);
	}

	@Override
	public void open()
	{
		init();

		player.openInventory(inventory);
		Bukkit.getPluginManager().registerEvents(this, plugin);

		this.closed = false;

		runSecondTask();
	}

	/**
	 * Used for item initiation.
	 */
	protected abstract void init();

	private void runSecondTask()
	{
		taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			if (!player.isOnline())
			{
				Bukkit.getScheduler().cancelTask(taskId);
				return;
			}

			if (player.getOpenInventory() == null)
			{
				Bukkit.getScheduler().cancelTask(taskId);
				return;
			}

			if (!player.getOpenInventory().getTitle().equalsIgnoreCase(name) ||
					!player.getOpenInventory().getTopInventory().equals(inventory))
			{
				Bukkit.getScheduler().cancelTask(taskId);
				return;
			}

			for (int slot : items.keySet())
			{
				GUIItem guiItem = items.get(slot);

				if (guiItem.shouldUpdateItem())
				{
					refresh(slot);

					player.updateInventory();
				}
			}
		}, 1L, 20L).getTaskId();
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event)
	{
		if (event.getWhoClicked().getName().equals(player.getName()) && !draggable.isEmpty())
		{
			String unformattedName     = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
			String unformattedViewName = ChatColor.stripColor(event.getView().getTitle());

			if (unformattedViewName.equalsIgnoreCase(unformattedName) &&
					event.getView().getTopInventory().equals(event.getInventory()))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getClickedInventory() == null)
		{
			return;
		}

		String unformattedName     = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
		String unformattedViewName = ChatColor.stripColor(event.getView().getTitle());

		if (event.getWhoClicked().getName().equals(player.getName()))
		{
			boolean cancel = draggable.isEmpty();

			if (event.getAction() == InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.COLLECT_TO_CURSOR)
			{
				cancel = true;
			} else if (unformattedViewName.equalsIgnoreCase(unformattedName) &&
					event.getView().getTopInventory().equals(event.getClickedInventory()))
			{
				int slot = event.getSlot();

				if (items.containsKey(slot) && !(items.get(slot) instanceof FillerGUIItem))
				{
					items.get(slot).run(player, event.getClick());

					cancel = true;
				} else if (draggable.containsKey(slot))
				{
					if (event.getClick().isShiftClick())
					{
						cancel = true;
					} else
					{
						GUIDragEvent guiDragEvent = draggable.get(slot);

						boolean cursorExists      = event.getCursor() != null && event.getCursor().getType() != Material.AIR;
						boolean currentItemExists = event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR;

						if (cursorExists && currentItemExists && event.getCurrentItem().isSimilar(event.getCursor()))
						{
							ItemStack result = event.getCursor().clone();

							if(event.getCurrentItem().getAmount() == event.getCurrentItem().getMaxStackSize()) {
								cancel = true;
							} else
							{
								if (event.getClick().isLeftClick())
								{
									result.setAmount(Math.min(result.getMaxStackSize(), event.getCursor().getAmount() + event.getCurrentItem().getAmount()));
								} else if (event.getClick().isRightClick())
								{
									result.setAmount(Math.min(result.getMaxStackSize(), event.getCurrentItem().getAmount() + 1));
								}

								cancel = !guiDragEvent.onUpdate(player, result);
							}
						} else if (event.getClick().isLeftClick())
						{
							if (cursorExists)
							{
								cancel = !guiDragEvent.onInsert(player, event.getCursor());
							}

							if (currentItemExists && !cancel)
							{
								cancel = !guiDragEvent.onRemove(player, event.getCurrentItem());
							}
						} else if(event.getClick().isRightClick()) {
							if (cursorExists)
							{
								ItemStack result = event.getCursor().clone();
								result.setAmount(1);

								cancel = !guiDragEvent.onInsert(player, result);
							}

							if (currentItemExists && !cancel)
							{
								if (event.getCurrentItem().getAmount() > 1)
								{
									ItemStack result = event.getCurrentItem().clone();
									result.setAmount(result.getAmount() / 2);

									cancel = !guiDragEvent.onUpdate(player, result);
								} else
								{
									cancel = !guiDragEvent.onRemove(player, event.getCurrentItem());
								}
							}
						} else {
							cancel = true;
						}
					}
				} else
				{
					cancel = true;
				}
			} else
			{
				if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
				{
					cancel = guiShiftClickMoveEvent == null || !guiShiftClickMoveEvent.onClick(player, event.getCurrentItem(), event.getSlot());
				}
			}

			if (cancel)
			{
				event.setCancelled(true);
			}

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

			this.closed = true;
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

	@Override
	public boolean isClosed()
	{
		return closed;
	}

	@Override
	public void setDraggable(int slot)
	{
		setDraggable(slot, new GUIDragEvent()
		{
			@Override
			public boolean onInsert(Player player, ItemStack itemStack)
			{
				/* do nothing */
				return true;
			}

			@Override
			public boolean onRemove(Player player, ItemStack itemStack)
			{
				/* do nothing */
				return true;
			}

			@Override
			public boolean onUpdate(Player player, ItemStack itemStack)
			{
				/* do nothing */
				return true;
			}
		});
	}

	@Override
	public void setDraggable(int slot, GUIDragEvent onDrag)
	{
		draggable.put(slot, onDrag);
	}

	@Override
	public boolean isDraggable(int slot)
	{
		return draggable.containsKey(slot);
	}

	@Override
	public void setOnShiftClick(GUIShiftClickMoveEvent event)
	{
		this.guiShiftClickMoveEvent = event;
	}

}