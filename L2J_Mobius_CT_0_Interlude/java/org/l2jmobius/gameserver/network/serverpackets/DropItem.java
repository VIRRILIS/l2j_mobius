/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.model.item.instance.Item;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class DropItem implements IClientOutgoingPacket
{
	private final Item _item;
	private final int _objectId;
	
	/**
	 * Constructor of the DropItem server packet
	 * @param item : Item designating the item
	 * @param playerObjId : int designating the player ID who dropped the item
	 */
	public DropItem(Item item, int playerObjId)
	{
		_item = item;
		_objectId = playerObjId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.DROP_ITEM.writeId(packet);
		packet.writeD(_objectId);
		packet.writeD(_item.getObjectId());
		packet.writeD(_item.getDisplayId());
		packet.writeD(_item.getX());
		packet.writeD(_item.getY());
		packet.writeD(_item.getZ());
		// only show item count if it is a stackable item
		packet.writeD(_item.isStackable() ? 1 : 0);
		packet.writeD(_item.getCount());
		packet.writeD(1); // unknown
		return true;
	}
}
