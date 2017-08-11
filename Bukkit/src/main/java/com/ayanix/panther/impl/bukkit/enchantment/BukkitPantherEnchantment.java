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
package com.ayanix.panther.impl.bukkit.enchantment;

import com.ayanix.panther.enchantment.PantherEnchantment;
import com.ayanix.panther.impl.common.utils.RomanNumerals;
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
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class BukkitPantherEnchantment extends Enchantment implements PantherEnchantment, Listener
{

	private static final RomanNumerals ROMAN_NUMERALS = new RomanNumerals();
	private String     name;
	private JavaPlugin plugin;
	private BukkitTask task;

	/**
	 * @param id   Unique ID of the enchantment.
	 * @param name Unique name of the enchantment.
	 */
	@SuppressWarnings({"argument.type.incompatible", "method.invocation.invalid"})
	public BukkitPantherEnchantment(JavaPlugin plugin, int id, String name)
	{
		super(id);

		this.plugin = plugin;
		this.name = ChatColor.translateAlternateColorCodes('&', name);

		Bukkit.getPluginManager().registerEvents(this, plugin);

		runEquipTimer();
	}

	/* Listeners & enchantment methods */
	private void runEquipTimer()
	{
		if (this.task != null)
		{
			return;
		}

		this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			for (Player player : Bukkit.getOnlinePlayers())
			{
				for (ItemStack item : player.getInventory().getArmorContents())
				{
					int level = getLevel(item);

					if (level == 0)
					{
						continue;
					}

					equipTask(player, level);
				}
			}
		}, 1, 100);
	}

	/*
	 * This method runs every 3 seconds.
	 */
	protected void equipTask(Player player, int level)
	{
		// Do nothing.
	}

	@Override
	public int getLevel(ItemStack item)
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
		return ChatColor.RESET + this.name;
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
	public void apply(ItemStack item, int level)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Item cannot be null");
		}

		ItemMeta     itemMeta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
		List<String> lore     = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();

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

	@Override
	public boolean conflictsWith(Enchantment enchantment)
	{
		return false;
	}

	@Override
	public boolean equals(Object enchantment)
	{
		if (enchantment == null)
		{
			return false;
		}

		if (!(enchantment instanceof BukkitPantherEnchantment))
		{
			return false;
		}

		BukkitPantherEnchantment pEnchantment = (BukkitPantherEnchantment) enchantment;

		return pEnchantment.getName().equals(this.getName());
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}

	@Override
	public boolean canEnchantItem(ItemStack item)
	{
		if (item == null)
		{
			return false;
		}

		if (getEnchantable().isEmpty())
		{
			return getItemTarget().includes(item);
		}

		return getEnchantable().contains(item.getType());
	}

	@Override
	public List<Material> getEnchantable()
	{
		return new ArrayList<>();
	}

	@Override
	public EnchantmentTarget getItemTarget()
	{
		return EnchantmentTarget.ALL;
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

		if (isEnchanted(damager.getItemInHand()))
		{
			if (event.getEntity() instanceof Player)
			{
				Player player = (Player) event.getEntity();

				if (player.getHealth() - event.getDamage() <= 0)
				{
					this.killed(damager, player);
				} else
				{
					this.pvp(damager, player);
				}
			} else
			{
				this.pve(damager, (LivingEntity) event.getEntity());
			}
		}

		if (event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();

			for (ItemStack item : player.getInventory().getArmorContents())
			{
				if (isEnchanted(item))
				{
					this.hurt(item, player, damager, event);
				}
			}
		}
	}

	@Override
	public boolean isEnchanted(ItemStack item)
	{
		return getLevel(item) != 0;
	}

	protected void pve(Player player, LivingEntity enemy)
	{
		// Do nothing
	}

	protected void pvp(Player player, Player enemy)
	{
		// Do nothing.
	}

	protected void hurt(ItemStack item, Player player, Player attacker, EntityDamageByEntityEvent event)
	{
		// Do nothing.
	}

	protected void killed(Player player, Player dead)
	{
		// Do nothing.
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (isEnchanted(event.getPlayer().getItemInHand()))
		{
			this.place(event.getPlayer(), event.getBlock());
		}
	}

	protected void place(Player player, Block block)
	{
		// Do nothing
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockDestroy(BlockBreakEvent event)
	{
		if (isEnchanted(event.getPlayer().getItemInHand()))
		{
			this.mine(event.getPlayer(), event.getBlock(), event);
		}
	}

	protected void mine(Player player, Block block, BlockBreakEvent event)
	{
		// Do nothing
	}

}
