/**
 * PacketWrapper - ProtocolLib wrappers for Minecraft packets
 * Copyright (C) dmulloy2 <http://dmulloy2.net>
 * Copyright (C) Kristian S. Strangeland
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ayanix.panther.impl.bukkit.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class WrapperPlayServerClearTitles extends AbstractPacket {
	public static final PacketType TYPE = Server.CLEAR_TITLES;

	public WrapperPlayServerClearTitles() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerClearTitles(PacketContainer packet) {
		super(packet, TYPE);
	}

	/**
	 * Retrieve 0 (RESET).
	 * <p>
	 * Notes: boolean
	 * 
	 * @return The current 0 (RESET)
	 */
	public boolean getSubtitle() {
		return handle.getBooleans().read(0);
	}

	/**
	 * Set 0 (RESET).
	 * 
	 * @param reset - new reset.
	 */
	public void setReset(boolean reset) {
		handle.getBooleans().write(0, reset);
	}

}