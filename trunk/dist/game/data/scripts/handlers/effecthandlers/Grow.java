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
package handlers.effecthandlers;

import com.l2jmobius.gameserver.model.StatsSet;
import com.l2jmobius.gameserver.model.actor.L2Character;
import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.effects.AbstractEffect;
import com.l2jmobius.gameserver.model.skills.BuffInfo;
import com.l2jmobius.gameserver.model.skills.Skill;

/**
 * Grow effect implementation.
 */
public final class Grow extends AbstractEffect
{
	public Grow(StatsSet params)
	{
	}
	
	@Override
	public void onStart(L2Character effector, L2Character effected, Skill skill)
	{
		if (effected.isNpc())
		{
			final L2Npc npc = (L2Npc) effected;
			npc.setCollisionHeight(npc.getTemplate().getCollisionHeightGrown());
			npc.setCollisionRadius(npc.getTemplate().getCollisionRadiusGrown());
		}
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		if (info.getEffected().isNpc())
		{
			final L2Npc npc = (L2Npc) info.getEffected();
			npc.setCollisionHeight(npc.getTemplate().getCollisionHeight());
			npc.setCollisionRadius(npc.getTemplate().getfCollisionRadius());
		}
	}
}
