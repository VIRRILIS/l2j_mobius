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

import static org.l2jmobius.gameserver.data.xml.MultisellData.PAGE_SIZE;

import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.model.multisell.Entry;
import org.l2jmobius.gameserver.model.multisell.Ingredient;
import org.l2jmobius.gameserver.model.multisell.ListContainer;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class MultiSellList implements IClientOutgoingPacket
{
	private int _size;
	private int _index;
	private final ListContainer _list;
	private final boolean _finished;
	
	public MultiSellList(ListContainer list, int index)
	{
		_list = list;
		_index = index;
		_size = list.getEntries().size() - index;
		if (_size > PAGE_SIZE)
		{
			_finished = false;
			_size = PAGE_SIZE;
		}
		else
		{
			_finished = true;
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MULTI_SELL_LIST.writeId(packet);
		packet.writeD(_list.getListId()); // list id
		packet.writeD(1 + (_index / PAGE_SIZE)); // page started from 1
		packet.writeD(_finished ? 1 : 0); // finished
		packet.writeD(PAGE_SIZE); // size of pages
		packet.writeD(_size); // list length
		Entry ent;
		while (_size-- > 0)
		{
			ent = _list.getEntries().get(_index++);
			packet.writeD(ent.getEntryId());
			packet.writeD(0); // C6
			packet.writeD(0); // C6
			packet.writeC(1);
			packet.writeH(ent.getProducts().size());
			packet.writeH(ent.getIngredients().size());
			for (Ingredient ing : ent.getProducts())
			{
				if (ing.getTemplate() != null)
				{
					packet.writeH(ing.getTemplate().getDisplayId());
					packet.writeD(ing.getTemplate().getBodyPart());
					packet.writeH(ing.getTemplate().getType2());
				}
				else
				{
					packet.writeH(ing.getItemId());
					packet.writeD(0);
					packet.writeH(65535);
				}
				packet.writeD(ing.getItemCount());
				if (ing.getItemInfo() != null)
				{
					packet.writeH(ing.getItemInfo().getEnchantLevel()); // enchant level
					packet.writeD(ing.getItemInfo().getAugmentId()); // augment id
					packet.writeD(0); // mana
				}
				else
				{
					packet.writeH(ing.getEnchantLevel()); // enchant level
					packet.writeD(0); // augment id
					packet.writeD(0); // mana
				}
			}
			for (Ingredient ing : ent.getIngredients())
			{
				packet.writeH(ing.getTemplate() != null ? ing.getTemplate().getDisplayId() : ing.getItemId());
				packet.writeH(ing.getTemplate() != null ? ing.getTemplate().getType2() : 65535);
				packet.writeD(ing.getItemCount());
				if (ing.getItemInfo() != null)
				{
					packet.writeH(ing.getItemInfo().getEnchantLevel()); // enchant level
					packet.writeD(ing.getItemInfo().getAugmentId()); // augment id
					packet.writeD(0); // mana
				}
				else
				{
					packet.writeH(ing.getEnchantLevel()); // enchant level
					packet.writeD(0); // augment id
					packet.writeD(0); // mana
				}
			}
		}
		return true;
	}
}
