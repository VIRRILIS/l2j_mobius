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
package handlers.telnethandlers.player;

import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.item.instance.Item;
import org.l2jmobius.gameserver.network.telnet.ITelnetCommand;
import org.l2jmobius.gameserver.util.Util;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author UnAfraid
 */
public class Give implements ITelnetCommand
{
	@Override
	public String getCommand()
	{
		return "give";
	}
	
	@Override
	public String getUsage()
	{
		return "Give <player name> <item id> [item amount] [item enchant] [donators]";
	}
	
	@Override
	public String handle(ChannelHandlerContext ctx, String[] args)
	{
		if ((args.length < 2) || args[0].isEmpty() || !Util.isDigit(args[1]))
		{
			return null;
		}
		final Player player = World.getInstance().getPlayer(args[0]);
		if (player != null)
		{
			final int itemId = Integer.parseInt(args[1]);
			int amount = 1;
			int enchanted = 0;
			if (args.length > 2)
			{
				String token = args[2];
				if (Util.isDigit(token))
				{
					amount = Integer.parseInt(token);
				}
				if (args.length > 3)
				{
					token = args[3];
					if (Util.isDigit(token))
					{
						enchanted = Integer.parseInt(token);
					}
				}
			}
			
			final Item item = ItemTable.getInstance().createItem("Telnet-Admin", itemId, amount, player, null);
			if (enchanted > 0)
			{
				item.setEnchantLevel(enchanted);
			}
			player.addItem("Telnet-Admin", item, null, true);
			return "Item has been successfully given to the player.";
		}
		return "Couldn't find player with such name.";
	}
}
