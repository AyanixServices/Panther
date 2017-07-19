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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public abstract class PantherEnchantment extends Enchantment implements IPantherEnchantment
{

	private String name;

	/**
	 * @param id   Unique ID of the enchantment.
	 * @param name Unique name of the enchantment.
	 */
	public PantherEnchantment(int id, String name)
	{
		super(id);

		this.name = name;
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

		if (item.hasItemMeta() &&
				item.getItemMeta().hasLore() &&
				!item.getItemMeta().getLore().contains(this.getDisplayName()))
		{
			return;
		}

		ItemMeta     itemMeta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
		List<String> lore     = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();

		lore.add(ChatColor.translateAlternateColorCodes('&', this.getDisplayName()));
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		item.addUnsafeEnchantment(this, level);
	}

	@Override
	public String getDisplayName()
	{
		return this.name;
	}

	@EventHandler(ignoreCancelled = false)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (event.getPlayer().getItemInHand().hasItemMeta() &&
				event.getPlayer().getItemInHand().getItemMeta().hasLore() &&
				event.getPlayer().getItemInHand().getItemMeta().getLore().contains(this.getDisplayName()))
		{
			this.place(event.getPlayer(), event.getBlock());
		}
	}

	abstract void place(Player player, Block block);

	@EventHandler(ignoreCancelled = false)
	public void onBlockDestroy(BlockBreakEvent event)
	{
		if (event.getPlayer().getItemInHand().hasItemMeta() &&
				event.getPlayer().getItemInHand().getItemMeta().hasLore() &&
				event.getPlayer().getItemInHand().getItemMeta().getLore().contains(this.getDisplayName()))
		{
			this.mine(event.getPlayer(), event.getBlock());
		}
	}

	abstract void mine(Player player, Block block);

	@EventHandler
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

		if (event.getEntity() instanceof Player)
		{
			if (damager != null &&
					damager.getItemInHand().hasItemMeta() &&
					damager.getItemInHand().getItemMeta().hasLore() &&
					damager.getItemInHand().getItemMeta().getLore().contains(this.getDisplayName()))
			{
				this.pvp(damager, (Player) event.getEntity());
			}
		} else if (damager != null &&
				damager.getItemInHand().hasItemMeta() &&
				damager.getItemInHand().getItemMeta().hasLore() &&
				damager.getItemInHand().getItemMeta().getLore().contains(this.getDisplayName()))
		{
			this.pve(damager, (LivingEntity) event.getEntity());
		}
	}

	abstract void pve(Player player, LivingEntity enemy);

	abstract void pvp(Player player, Player enemy);

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

}
