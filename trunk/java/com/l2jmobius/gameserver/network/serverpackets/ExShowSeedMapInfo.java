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
package com.l2jmobius.gameserver.network.serverpackets;

import com.l2jmobius.commons.network.PacketWriter;
import com.l2jmobius.gameserver.instancemanager.GraciaSeedsManager;
import com.l2jmobius.gameserver.network.client.OutgoingPackets;

public class ExShowSeedMapInfo implements IClientOutgoingPacket
{
	public static final ExShowSeedMapInfo STATIC_PACKET = new ExShowSeedMapInfo();
	
	private ExShowSeedMapInfo()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_SEED_MAP_INFO.writeId(packet);
		
		packet.writeD(2); // seed count
		
		// Seed of Destruction
		packet.writeD(-246857); // x coord
		packet.writeD(251960); // y coord
		packet.writeD(4331); // z coord
		packet.writeD(2770 + GraciaSeedsManager.getInstance().getSoDState()); // sys msg id
		
		// Seed of Infinity
		packet.writeD(-213770); // x coord
		packet.writeD(210760); // y coord
		packet.writeD(4400); // z coord
		// Manager not implemented yet
		packet.writeD(2766); // sys msg id
		return true;
	}
}
