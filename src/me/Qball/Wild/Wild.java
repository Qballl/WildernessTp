package me.Qball.Wild;
 
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
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
	public final Logger logger = Bukkit.getServer().getLogger();
	public HashMap<UUID,Long> cooldownTime;
	public static  boolean Water = false;
	public static 	boolean loaded = false;
	public static boolean inNether = false;
	public static boolean inEnd = false;
	 public static Wild plugin;
	 
	 public  int cool = this.getConfig().getInt("Cooldown");

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
		 cooldownTime = new HashMap<UUID,Long>();
		
  }
  public void Reload(Player e)
  {
	  	Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
		  e.sendMessage(ChatColor.BLACK + "["+ChatColor.GREEN+ "WildnernessTP"+ChatColor.BLACK+"]"+ChatColor.GREEN	 +"Pluging config has successfuly been reload");

  }
  public  boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
  {
	  if (cmd.getName().equalsIgnoreCase("Wildtp"))
	  { 
		  if (sender instanceof Player)
		  {
		 final Player player = (Player)sender;
		  if (args.length == 0)
		  {
			  player.sendMessage(ChatColor.BLUE+"*****************Help***************************");
			  player.sendMessage(ChatColor.BLUE+"* Command:       Description:                  *");
			  player.sendMessage(ChatColor.BLUE+"* /Wild Teleports player to random location    *");
			  player.sendMessage(ChatColor.BLUE+"* /Wild [player] Teleports the specfied player *");
			  player.sendMessage(ChatColor.BLUE+"* to a radom location                          *");
			  player.sendMessage(ChatColor.BLUE+"* /WildTp reload Reloads the plugin's config   *");
			  player.sendMessage(ChatColor.BLUE+"* /WIldTp Shows This help message              *");
			  player.sendMessage(ChatColor.BLUE+"************************************************");
		  }
		 
		  else if(args.length==1)
		  {
			  
		 final String reload = args[0];
		  
		  if (reload.equalsIgnoreCase("reload"))
		  {
		  if(!player.hasPermission("wild.wildtp.reload"))
		  {
			  player.sendMessage(ChatColor.RED+"Sorry you do not have permission to reload the plugin");
		  }
		  else
		  {
			  Reload(player);
		  }
		 
		  }
		  }
		  }
		  else
		  {
			  
			  if (args.length == 0)
			  {
				  sender.sendMessage("*****************Help***************************");
				  sender.sendMessage("* Command:       Description:                  *");
				  sender.sendMessage("* /Wild Teleports player to random location    *");
				  sender.sendMessage("* /Wild [player] Teleports the specfied player *");
				  sender.sendMessage("*  to a radom location                         *");
				  sender.sendMessage("* /WildTp reload Reloads the plugin's config   *");
				  sender.sendMessage("* /WildTp Shows This help message              *");
				  sender.sendMessage("************************************************");
			  }
			 
			  else if(args.length==1)
			  {
				  
			  String reload = args[0];
			  
			  if (reload.equalsIgnoreCase("reload"))
			  {
				 
				  Bukkit.getServer().getPluginManager().getPlugin("Wild").reloadConfig();
				  sender.sendMessage("[ WildnernessTP] Plugin config has successfuly been reload");
				
			  }
			 
		  }
			  }
		 
		  
	  }
	
	 
      if(cmd.getName().equalsIgnoreCase("Wild"))
      {

    		 if (sender instanceof Player) 
    		
    		 {
              final  Player player1 = (Player) sender;
              final  Player player  = (Player) sender;
    
              if (player1.hasPermission("Wild.wildtp")) 
    	  {
            	  
              
                 	  if (args.length == 0)
    	  {
    		  final Player target = (Player)sender;
    		  if (check(target))
    		  {
    		 Random(target);
    		 
    		  }
    		  else
    		  {
    			  target.sendMessage(ChatColor.RED + "You must wait " + cool + " second between each use of the command");

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
    	  if(!check(player1))
    	  {
			  player1.sendMessage(ChatColor.RED + "You must wait " + cool + " second between each use of the command");

    	  }
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
    			 Random(target);
   
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
    			 if (args.length == 0)
    			 {	
    	  			 sender.sendMessage("You must be a player!");
    	              return false;
    			 }
    			 else if (args.length == 1)
    			 {
    				 if( args[0] != null)
    		    	  {
    		    	    final Player target = Bukkit.getServer().getPlayer(args[0]);
    		    	    Random(target);
    		      if(target == null)
    		      {
    		    	  sender.sendMessage(args[0]+ " " + (new StringBuilder()).append(ChatColor.RED).append("is not online!!!!").toString());
    		    	  return true;
    		      }
    			 }

    		}
      
      
    	  {
    		  
    	  }


      
    	   	  
  }
     
      } 
      return false;
  }
  public boolean check(Player p){
      if(cooldownTime.containsKey(p.getUniqueId())){
          long old = cooldownTime.get(p.getUniqueId());
          long now = System.currentTimeMillis();
         
          long diff = now - old;
         
          long convert = TimeUnit.MILLISECONDS.toSeconds(diff);
         
          if(convert >= cool){
        	  cooldownTime.put(p.getUniqueId(),  now);
              return true;
          }
         
          return false;
      }else{
    	  cooldownTime.put(p.getUniqueId(), System.currentTimeMillis());
          return true;
      }
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
    final  int Y1 = Checks.getSoildBlock(tempx,tempz,target);
      String Message = this.getConfig().getString("No Suitable Location");
      if (Checks.inNether(tempx,tempz,target)==true)
      {
    	  target.sendMessage(ChatColor.RED+"Command cannot be used in the nether");
      }
      else
      {
    	  if(Checks.inEnd(tempx,tempz,target)==true)
    	  {
    		  target.sendMessage(ChatColor.RED+"Command cannot be used in end");
    	  }
    	  else
    	  {
    		if(Checks.getLiquid(tempx,tempz,target)==true)
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
	      	
	      			  Location done = new Location(target.getWorld(), x, Y1, z, 0.0F, 0.0F);
	      			  target.teleport(done);
	      		
	      
	      	
	     

	     target.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append(ChatColor.translateAlternateColorCodes((char) '&', Teleport)).toString());
    		}
    	  }
    	  }
      }
  @EventHandler
  public void onSignChange(SignChangeEvent player)
  {
	  String Message = this.getConfig().getString("No-Perm");
	  if(player.getPlayer().hasPermission("wild.wildtp.createSign"))
	  {
		  
		  if(player.getLine(1).equalsIgnoreCase("[Wild]")&&player.getLine(0).equalsIgnoreCase("WildTp"))
		  {
			Location loc=  player.getPlayer().getLocation();
			int x = loc.getBlockX();
			int z = loc.getBlockZ();
			if(player.getPlayer().getWorld().getBiome(x, z) == Biome.HELL)
			{
				player.getPlayer().sendMessage(ChatColor.RED + "Signs cannot be put in the nether");
				player.getBlock().breakNaturally();
				player.setCancelled(true);
			}
			else{
				if(player.getPlayer().getWorld().getBiome(x, z) == Biome.SKY)
				{
					player.getPlayer().sendMessage(ChatColor.RED + "Signs cannot be put in the end");
					player.getBlock().breakNaturally();
					player.setCancelled(true);
				}
				else{
			  player.setLine(0,"§4====================");
			  player.setLine(1,"[§1Wild§0]");
			  player.setLine(2,"§4====================");
			  player.getPlayer().sendMessage(ChatColor.GREEN + "Successfully made a new WildTP sign" );
		  }
			}
		  }
	  }
	  else
		  {
			 player.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes((char) '&', Message));
			 player.getBlock().breakNaturally();
			 player.setCancelled(true);
		  }
	  
  }
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent target)
  {
	  Player Target = target.getPlayer();
	  Sign sign;
	  if(target.getAction()!= Action.RIGHT_CLICK_BLOCK)
	  {
		return;
	  }
	  if(target.getClickedBlock().getState() instanceof Sign)
		  {
		  sign = (Sign)target.getClickedBlock().getState();
		  if(sign.getLine(1).equalsIgnoreCase("[§1Wild§0]")&& sign.getLine(0).equalsIgnoreCase("§4===================="))
		  {
			  
			if (!check(Target))
			{
			 Random(Target);
			}
			else
			{
  			  Target.sendMessage(ChatColor.RED + "You must wait " + cool + " second between each use of the command or sign");

			}
		  }
		  }
  }
 


 
}
