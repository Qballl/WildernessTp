package me.Qball.Wild;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;



public class Wild extends JavaPlugin implements Listener 
{
	public static  boolean Water = false;
	public static 	boolean loaded = false;
	public static boolean inNether = false;
	public static boolean inEnd = false;
	 public static Wild plugin;
  public void onDisable()
  {
      logger.info("Wild was successfully disabled. Goodbye!");
      plugin = null;
      
  }
  
  public void onEnable() 
  

  {
	  
	  Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
	 
	  plugin = this;
      logger.info("Wilderness by Qball was successfully enabled on server!");

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
  }
  public  boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
  {
      if(cmd.getName().equalsIgnoreCase("Wild"))
      {
    	  
    	  
    	final  Player player1 = (Player) sender;
    	
    	  
    	 double MaxX = this.getConfig().getDouble("MaxX");
		 double MaxZ = this.getConfig().getDouble("MaxZ");
		 double MinX = this.getConfig().getDouble("MinX");
		 double MinZ = this.getConfig().getDouble("MinZ");
    	 boolean Retry = this.getConfig().getBoolean("Retry");
    	 int Retries = this.getConfig().getInt("Retries");
    	 String Message = this.getConfig().getString("No Suitable Location");
    	 String Teleport = this.getConfig().getString("Teleport");
    	
    		 if (sender instanceof Player) 
    		
    		 {
              final Player player = (Player) sender;
              
              if (player1.hasPermission("Wild.wildtp")) 
    	  {
            	  
              
                 	  if (args.length == 0)
    	  {
    		  final Player target = (Player)sender;
    		  
    		 
    	
    	      double x = ThreadLocalRandom.current().nextDouble(MinX, MaxX + 1); 
    	 double z = ThreadLocalRandom.current().nextDouble(MinZ,MaxZ+1);
    	 int y =120;
    	 int tempx = (int) (x);
         int tempz = (int)(z); 
    	 World world = target.getWorld();
    	 int Y = world.getHighestBlockYAt(tempx,tempz) + 1;
    	 Y = (int) (Y);
    	 if (inNether(tempx,tempz,target)==true)
    	 {
    		 target.sendMessage(ChatColor.RED+"This command cannot be used in the nether!");
    	 }
    	 else
    	 {
    		 if(inEnd(tempx,tempz,target))
    		 {
    			 target.sendMessage(ChatColor.RED+"This command cannot be used in the end");
    		 }
    		 else
    		 {
    	 
    	    	  if ( tempx <= MaxX && tempz <= MaxZ)
    	    	  {
    	    		
    	     	    		   if (  getLiquid(tempx,tempz,Y,target) == true)
    	     	     	      {
    	     	     	    	 target.sendMessage(ChatColor.RED + "There are no suitable locations :( For the best it means no safe loction for teleportation");
    	     	     	      }
    	     	     	    	 else
    	     	     	    	 {
    	     	     	    		 
    	     	    	     	      target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,400,50));
    	     	    	     	      new BukkitRunnable() {
    	     	    	     	    	  @Override
    	     	    	     	    	  public void run()
    	     	    	     	    	  {
    	     	    	     	            target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,200,50));  
    	     	    	     	    	  }
    	     	    	     	      }.runTaskLater(plugin,100);
    	     	    	  
    	     	    	     	      Location done = new Location(target.getWorld(), x, Y, z, 0.0F, 0.0F);
    	     	    	     	      target.teleport(done);
    	     	    	     	     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
    	     	     	    	 }
    	  }  else
    	    	  {
    		  if (Retry == false)
    		  {
    			  target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Message));
    		  }
    		  else
    			  
    		  {
    	    		 for ( int i = 0; i <= Retries; i ++)
    	    		 {
    	    			 /*  xr = Math.random() * Math.random() * 7D * Math.random() * 4D;
    	        	       zr = Math.random() * Math.random() * 6D * Math.random() * 8D;
    	        	       xp = xr * Math.random() * Math.random() * 43D * Math.random();
    	        	       zp = zr * Math.random() * Math.random() * 12D * Math.random() ;
    	        	     
    	        	       xu = xp * 300D;
    	        	       zu = zp * 300D;
    	        	       */
    	        	       tempx = (int) (x);
    	        	       tempz = (int)(z); 
    	        	       world = target.getWorld();
    	        	      Y = world.getHighestBlockYAt(tempx,tempz) + 1;
    	        	      if (tempx <= MaxX && tempz <= MaxZ )
    	        	      {
    	        	    	  target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,400,50));
     	    	     	      new BukkitRunnable() {
     	    	     	    	  public void run()
     	    	     	    	  {
     	    	     	            target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,200,50));  
     	    	     	    	  }
     	    	     	      }.runTaskLater(plugin,100);
     	    	  
     	    	     	       Location done = new Location(target.getWorld(), x, Y, z, 0.0F, 0.0F);
     	    	     	      target.teleport(done);
     	    	     	     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
     	     	    	 
     	     	      
    	        	    	  break;
    	        	      }
    	        	      else if ( i == Retries)
    	                  { 
    	               	   target.sendMessage(ChatColor.RED+ "No suitable locations found ");
    	                  }
    	    		 }
    	    			 
    		  }
    	    	  }
    	      }
    	      } 
    	  }
    	  
    	  else if (args.length ==1)
    	  {
    	  
    	  if( args[0] != null)
    	  {
    	    final Player target = Bukkit.getServer().getPlayer(args[0]);
    	  
      if(target == null)
      {
    	  sender.sendMessage(args[0]+ " " + (new StringBuilder()).append(ChatColor.RED).append("is not online!!!!").toString());
    	  return true;
      }
      if (player1.hasPermission("Wild.wildtp.others")) 
	  {
    	 if (inNether == true)
    	 {
    		 player1.sendMessage(ChatColor.RED + "Target is in the nether and thus cannot be teleported");
    	 }
    	 else
    	 {
    		 if(inEnd == true)
    		 {
    			 player1.sendMessage(ChatColor.RED + "Target is in the end thus cannot be teleported");
    		 }
    		 else
    		 {
   dom.current().nextDouble(MinX, MaxX + 1); 
      double z = ThreadLocalRandom.current().nextDouble(MinZ,MaxZ+1);
      int y = 120;
 
      int tempx = (int) (x);
      int tempz = (int)(z);
      World world = target.getWorld();
      int Y = world.getHighestBlockYAt(tempx,tempz)+1;
      if ( tempx <= MaxX && tempz <= MaxZ)
      {
    	  target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,400,50));
  	      new BukkitRunnable() {
  	    	  @Override
  	    	  public void run()
  	    	  {
  	            target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,200,50));  
  	    	  }
  	      }.runTaskLater(plugin,100);

  	      Location done = new Location(target.getWorld(), x, Y, z, 0.0F, 0.0F);
  	      target.teleport(done);
  	     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("Thrown to a random location...").toString());
  	 
      }
      else if ( tempx > MaxX || tempz > MaxZ)
      {
    	  for (int i = 0; i <= Retries; i++)
    	  {
    	
           y = 120;
        
            x = ThreadLocalRandom.current().nextDouble(MinX, MaxX + 1); 
 		   z = ThreadLocalRandom.current().nextDouble(MinZ,MaxZ+1);
           if ( tempx <= MaxX && tempz <= MaxZ)
           {
         	  target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,400,50));
       	      new BukkitRunnable() {
       	    	  @Override
       	    	  public void run()
       	    	  {
       	            target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,200,50));  
       	    	  }
       	      }.runTaskLater(plugin,100);

       	      Location done = new Location(target.getWorld(), x, Y, z, 0.0F, 0.0F);
       	      target.teleport(done);
       	     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("Thrown to a random location... by " + player1.getDisplayName()).toString());
       	 break;
           }
           else if ( i == Retries)
           {
        	   player1.sendMessage(ChatColor.RED+ "No suitable locations found for " + target.getDisplayName());
           }
      }
      }
      }
    	 }
    	 }
   
      else
      {
    	  player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You lack the permission to teleport other players").toString());
      }
    	  } 	 
    	  
      
  }
    	  
    	  }  
    	  else {
 			 Player player_ = (Player) sender;
	  			player_.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Sorry but you dont have permsioson to do /wild :( please ask an admin why").toString());
           }
    		 } else 
    		{

    	  			 sender.sendMessage("You must be a player!");
    	              return false;

    		}
      
      
    	  {
    		  
    	  }


      
    	   	  
  }
      return false;
  }
  public static boolean getLiquid(int tempx,int tempz,int y, Player target)
  {
	  int Y = target.getWorld().getHighestBlockYAt(tempx,tempz)+1;
	 
	  if (target.getWorld().getBlockAt(tempx,Y,tempz).isLiquid())
      {
    	  Water = true;
      }
	  else
	  {
		  Water = false;
	  }
	  return Water;
  }
  public static boolean inNether(int tempx,int tempz, Player target)
  {
	 
	  if (target.getWorld().getBiome(tempx, tempz) == Biome.HELL)
      {
    	  inNether = true;
      }
	  else
	  {
		  inNether = false;
	  }
	  return inNether;
  }
  public static boolean inEnd(int tempx,int tempz, Player target)
  {
	 
	  if (target.getWorld().getBiome(tempx, tempz) == Biome.SKY)
      {
    	  inEnd = true;
      }
	  else
	  {
		  inEnd = false;
	  }
	  return inNether;
  }
  public static boolean isChunkLoaded(int tempx, int tempz, Player target)
  {
	 
	  if (target.getWorld().isChunkLoaded(tempx,tempz) == true)
	  {
		  loaded = true;
	  }
	  else
	  {
		  loaded = false;
	  }
		  return loaded;  
	 
  }
  public static int getSoildBlock(int tempx, int tempz, Player target)
  {
	 int Y = 0;
	  for (int y = 256; y>= 0; y --)
	  {
		 Y = y;
		 if(!target.getWorld().getBlockAt(tempx, Y, tempz).isEmpty())
		 {
			Y+=1;
			break;
		 }
	  }
	 return Y;
  }
  public void Random(Player e)
  {
	  final Player target = (Player) e;
	  double MinX = this.getConfig().getDouble("MinX");
	  double MaxX = this.getConfig().getDouble("MaxX");
	  double MinZ = this.getConfig().getDouble("MinZ");
	  double MaxZ = this.getConfig().getDouble("MaxZ");
	
	  String Teleport = this.getConfig().getString("Teleport");
      World world = target.getWorld();
    
    final double x = ThreadLocalRandom.current().nextDouble(MinX, MaxX + 1); 
	 final double  z = ThreadLocalRandom.current().nextDouble(MinZ,MaxZ+1);
	   int tempx = (int)(x);
	  int tempz = (int)(z);
	  int Y = world.getHighestBlockYAt(tempx,tempz);
    final  int Y1 = getSoildBlock(tempx,tempz,target);
      String Message = this.getConfig().getString("No Suitable Location");
      if (inNether(tempx,tempz,target)==true)
      {
    	  target.sendMessage(ChatColor.RED+"Signs cannot be used in the nether");
      }
      else
      {
    	  if(inEnd(tempx,tempz,target)==true)
    	  {
    		  target.sendMessage(ChatColor.RED+"Signs cannot be used in the end");
    	  }
    	  else
    	  {
    		if(getLiquid(tempx,Y1,tempz,target)==true)
    		{
    			target.sendMessage(ChatColor.translateAlternateColorCodes((char) '&',Message));
    			
    		}
    		else{
      target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,400,50));
	      new BukkitRunnable() {
	    	  public void run()
	    	  {
	            target.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,200,50));  
	    	  }
	      }.runTaskLater(plugin,100);
	      	new BukkitRunnable(){
	      		public void run()
	      		{
	      			  Location done = new Location(target.getWorld(), x, Y1, z, 0.0F, 0.0F);
	      			  target.teleport(done);
	      		}
	      	}.runTaskLater(plugin, 100);
	      	
	     
			 target.sendMessage(target.getWorld().getBlockAt(tempx,Y,tempz)+"");

	     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
    		}
    	  }
    	  }
      }
  @EventHandler
  public void onSignChange(SignChangeEvent player)
  {
	  String Message = this.getConfig().getString("N0-Prem");
	  if(player.getPlayer().hasPermission("wild.wildtp.createSign"))
	  {
		  
		  if(player.getLine(1).equalsIgnoreCase("[Wild]")&&player.getLine(0).equalsIgnoreCase("WildTp"))
		  {
			  player.setLine(0,"§4===================");
			  player.setLine(1,"[§1Wild§0]");
			  player.setLine(2,"§4===================");
		  }
		  else
		  {
			 player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Message));
			 player.setCancelled(true);
		  }
	  }
  }
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent target)
  {
	  Player Target = target.getPlayer();
	  Sign sign;
	  if(target.getAction() != Action.RIGHT_CLICK_BLOCK)
	  {
		return;  
	  }
	  if(target.getClickedBlock().getState() instanceof Sign)
		  {
		  sign = (Sign)target.getClickedBlock().getState();
		  if(sign.getLine(1).equalsIgnoreCase("[§1Wild§0]"))
		  {
			 
			 Random(Target);
		  }
		  }
  }
 


public final Logger logger = Bukkit.getServer().getLogger();
 
}
