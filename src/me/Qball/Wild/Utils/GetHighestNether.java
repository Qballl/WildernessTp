package me.Qball.Wild.Utils;
import me.Qball.Wild.*;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GetHighestNether {
	private final Wild plugin;
	public GetHighestNether(Wild plugin)
	{
		this.plugin = plugin;
	}
 
	public  int getSolidBlock(int tempx, int tempz, Player target)
	  { 
		GetRandomLocation random = new GetRandomLocation(plugin);
		 int Y = 0;
		  for (int y = 124; y> 0; y --)
		  {
			 Y = y;
			 if(target.getWorld().getBlockAt(tempx, Y, tempz).isEmpty())
			 {
				 Location loc = new Location(target.getWorld(),tempx,Y,tempz,0.0F,0.0F);
				 if(!target.getWorld().getBlockAt(tempx, Y, tempz).isLiquid()
						 && target.getWorld().getBlockAt(tempx, loc.getBlockY()-2, tempz).isEmpty()
						 && !target.getWorld().getBlockAt(tempx,loc.getBlockY()-3, tempz).isEmpty()
						 && !target.getWorld().getBlockAt(tempx,loc.getBlockY()-2,tempz).isLiquid())
				 {
					 Y-=2;
					 break;
				 }
				
			 }
		  }
		  
		  
		   return Y;
		  
	  }
 
	
}