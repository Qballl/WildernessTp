package me.Qball.Wild.Utils;
import me.Qball.Wild.*;
import org.bukkit.entity.Player;

public class GetHighestNether {
	public static int getSoildBlock(int tempx, int tempz, Player target)
	  {
		 int Y = 0;
		  for (int y = 128; y>= 0; y --)
		  {
			 Y = y;
			 if(!target.getWorld().getBlockAt(tempx, Y, tempz).isEmpty())
			 {
				 if(!target.getWorld().getBlockAt(tempx, Y, tempz).isLiquid())
				 {
				Y+=2;
				break;
				 }
				 else
				 {
					 Wild.Random(target);
				 }
			 }
		  }
		 
		  
		   return Y;
		  
	  }

	
}
