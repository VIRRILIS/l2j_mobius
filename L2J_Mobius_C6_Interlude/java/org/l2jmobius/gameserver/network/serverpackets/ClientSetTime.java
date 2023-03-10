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
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.taskmanager.GameTimeTaskManager;

public class ClientSetTime implements IClientOutgoingPacket
{
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CLIENT_SET_TIME.writeId(packet);
		packet.writeD(GameTimeTaskManager.getInstance().getGameTime()); // Time in client minutes.
		packet.writeD(GameTimeTaskManager.IG_DAYS_PER_DAY); // Constant to match the server time. This determines the speed of the client clock.
		return true;
	}
}
