package com.howlstudio.flighttoggle;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/** FlightToggle — Toggle flight mode with /fly. Persistent per-player. VIP or staff perk. */
public final class FlightTogglePlugin extends JavaPlugin {
    private FlightManager mgr;
    public FlightTogglePlugin(JavaPluginInit init){super(init);}
    @Override protected void setup(){
        System.out.println("[FlightToggle] Loading...");
        mgr=new FlightManager(getDataDirectory());
        new FlightListener(mgr).register();
        CommandManager.get().register(mgr.getFlyCommand());
        System.out.println("[FlightToggle] Ready. "+mgr.getCount()+" players with flight.");
    }
    @Override protected void shutdown(){if(mgr!=null)mgr.save();System.out.println("[FlightToggle] Stopped.");}
}
