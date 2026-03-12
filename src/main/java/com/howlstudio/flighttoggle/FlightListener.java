package com.howlstudio.flighttoggle;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
public class FlightListener {
    private final FlightManager mgr;
    public FlightListener(FlightManager m){this.mgr=m;}
    public void register(){
        HytaleServer.get().getEventBus().registerGlobal(PlayerReadyEvent.class,e->{
            Player p=e.getPlayer();if(p==null)return;
            PlayerRef ref=p.getPlayerRef();if(ref==null)return;
            if(mgr.hasFlightEnabled(ref.getUuid()))ref.sendMessage(com.hypixel.hytale.server.core.Message.raw("[Fly] §aFlight active§r. /fly to toggle."));
        });
    }
}
