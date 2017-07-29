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
package com.ayanix.panther.impl.enchantment;

import com.ayanix.panther.enchantment.IPantherEnchantment;
import com.ayanix.panther.impl.utils.RomanNumerals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class PantherEnchantment extends Enchantment implements IPantherEnchantment
{

	private final RomanNumerals ROMAN_NUMERALS;
	private       String        name;
	private       List<Player>  toggled;
	private       JavaPlugin    plugin;
	@Nullable
	private       BukkitTask    task;

	/**
	 * @param id   Unique ID of the enchantment.
	 * @param name Unique name of the enchantment.
	 */
	public PantherEnchantment(JavaPlugin plugin, int id, String name)
	{
		super(id);

		this.plugin = plugin;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.ROMAN_NUMERALS = new RomanNumerals();
		this.toggled = new ArrayList<>();
		this.task = null;
	}

	@Override
	public int getStartLevel()
	{
		return 1;
	}

	@Override
	public int getMaxLevel()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public List<Material> getEnchantable()
	{
		return new ArrayList<>();
	}

	@Override
	public boolean canEnchantItem(@Nullable ItemStack item)
	{
		return item != null;
	}

	@Override
	public void apply(@NonNull ItemStack item, int level)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Item cannot be null");
		}

		ItemMeta     itemMeta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
		List<String> lore     = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();

		if (isEnchanted(item))
		{
			int currentLevel = getLevel(item);

			lore.remove(this.getDisplayName() + " " + ROMAN_NUMERALS.toRoman(currentLevel));
		}

		lore.add(this.getDisplayName() + " " + ROMAN_NUMERALS.toRoman(level));
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		item.addUnsafeEnchantment(this, level);
	}

	@EventHandler(ignoreCancelled = false)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (isEnchanted(event.getPlayer().getItemInHand()))
		{
			this.place(event.getPlayer(), event.getBlock());
		}
	}

	@Override
	public boolean isEnchanted(@Nullable ItemStack item)
	{
		return getLevel(item) != 0;
	}

	@Override
	public int getLevel(@Nullable ItemStack item)
	{
		if (item == null)
		{
			return 0;
		}

		if (!item.hasItemMeta())
		{
			return 0;
		}

		ItemMeta meta = item.getItemMeta();

		if (!meta.hasLore())
		{
			return 0;
		}

		// Use Bukkit first
		if (item.getEnchantmentLevel(this) != 0)
		{
			return item.getEnchantmentLevel(this);
		}

		// Check lore
		for (String message : meta.getLore())
		{
			if (message.startsWith(this.getDisplayName()))
			{
				message = message.replace(this.getDisplayName() + " ", "");
				message = ChatColor.stripColor(message);

				int arabic = ROMAN_NUMERALS.toInt(message);

				if (arabic != 0)
				{
					return arabic;
				}
			}
		}

		return 0;
	}

	@Override
	public String getDisplayName()
	{
		return this.name;
	}

	protected void place(Player player, Block block)
	{
		// Do nothing
	}

	@EventHandler(ignoreCancelled = false)
	public void onBlockDestroy(BlockBreakEvent event)
	{
		if (isEnchanted(event.getPlayer().getItemInHand()))
		{
			this.mine(event.getPlayer(), event.getBlock());
		}
	}

	protected void mine(Player player, Block block)
	{
		// Do nothing
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		Player damager = null;

		if (event.getDamager() instanceof Player)
		{
			damager = (Player) event.getDamager();
		} else if (event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player)
		{
			damager = (Player) ((Arrow) event.getDamager()).getShooter();
		}

		if (damager == null)
		{
			return;
		}

		if (!isEnchanted(damager.getItemInHand()))
		{
			return;
		}

		if (event.getEntity() instanceof Player)
		{
			this.pvp(damager, (Player) event.getEntity());
		} else
		{
			this.pve(damager, (LivingEntity) event.getEntity());
		}
	}

	protected void pve(Player player, LivingEntity enemy)
	{
		// Do nothing
	}

	protected void pvp(Player player, Player enemy)
	{
		// Do nothing.
	}

	@Override
	public boolean equals(@Nullable Object enchantment)
	{
		if (enchantment == null)
		{
			return false;
		}

		if (!(enchantment instanceof PantherEnchantment))
		{
			return false;
		}

		PantherEnchantment pEnchantment = (PantherEnchantment) enchantment;

		return pEnchantment.getName().equals(this.getName()) &&
				pEnchantment.getId() == this.getId();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerEquip(PlayerItemHeldEvent e)
	{
		Player    player = e.getPlayer();
		ItemStack item   = player.getInventory().getItem(e.getNewSlot());

		int level = getLevel(item);

		if (level == 0)
		{
			return;
		}

		this.toggled.add(player);

		runEquipTimer();
	}

	public void runEquipTimer()
	{
		if (this.task != null)
		{
			return;
		}

		this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				Iterator<Player> iterator = toggled.iterator();

				while (iterator.hasNext())
				{
					Player player = iterator.next();

					int level = 0;

					for (ItemStack item : player.getInventory().getArmorContents())
					{
						int cLevel = getLevel(item);

						if (cLevel > level)
						{
							level = cLevel;
						}
					}

					if (level == 0)
					{
						iterator.remove();
						continue;
					}

					equipTask(player, level);
				}
			}
		}, 0, 5 * 20);
	}

	@Override
	public EnchantmentTarget getItemTarget()
	{
		return EnchantmentTarget.ALL;
	}

	@Override
	public boolean conflictsWith(Enchantment enchantment)
	{
		return false;
	}

	/* This runs every 5 seconds. */
	protected void equipTask(Player player, int level)
	{
		// Do nothing.
	}

}
