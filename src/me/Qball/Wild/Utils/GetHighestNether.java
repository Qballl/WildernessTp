package me.Qball.Wild.Utils;
import me.Qball.Wild.*;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GetHighestNether {
	public  Wild wildTp = Wild.getInstance();
	public  GetRandomLocation random = new GetRandomLocation();
	public  int getSolidBlock(int tempx, int tempz, Player target)
	  {
		 int Y = 0;
		  for (int y = 124; y> 0; y --)
		  {
			 Y = y;
			 if(target.getWorld().getBlockAt(tempx, Y, tempz).isEmpty())
			 {
				 Location loc = new Location(target.getWorld(),tempx,Y,tempz,0.0F,0.0F);
				 if(target.getWorld().getBlockAt(tempx, loc.getBlockY()-2, tempz).isEmpty()
						 && !target.getWorld().getBlockAt(tempx,loc.getBlockY()-3, tempz).isEmpty())
				 {
					 Y-=2;
					 break;
				 }
				
			 }
		  }
		  
		  
		   return Y;
		  
	  }
 
	
}
