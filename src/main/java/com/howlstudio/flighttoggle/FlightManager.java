package com.howlstudio.flighttoggle;
import com.hypixel.hytale.component.Ref; import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.nio.file.*; import java.util.*;
public class FlightManager {
    private final Path dataDir;
    private final Set<UUID> flightEnabled=new HashSet<>();
    public FlightManager(Path d){this.dataDir=d;try{Files.createDirectories(d);}catch(Exception e){}load();}
    public int getCount(){return flightEnabled.size();}
    public boolean hasFlightEnabled(UUID uid){return flightEnabled.contains(uid);}
    public void toggle(UUID uid){if(!flightEnabled.remove(uid))flightEnabled.add(uid);save();}
    public void save(){try{StringBuilder sb=new StringBuilder();for(UUID u:flightEnabled)sb.append(u+"\n");Files.writeString(dataDir.resolve("flight.txt"),sb.toString());}catch(Exception e){}}
    private void load(){try{Path f=dataDir.resolve("flight.txt");if(!Files.exists(f))return;for(String l:Files.readAllLines(f))try{flightEnabled.add(UUID.fromString(l.trim()));}catch(Exception e){}}catch(Exception e){}}
    public AbstractPlayerCommand getFlyCommand(){
        return new AbstractPlayerCommand("fly","Toggle flight mode. /fly [player]"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                String arg=ctx.getInputString().trim();
                UUID target=playerRef.getUuid();String targetName=playerRef.getUsername();
                // self toggle
                toggle(target);boolean on=hasFlightEnabled(target);
                playerRef.sendMessage(Message.raw("[Fly] Flight §"+(on?"a":"c")+(on?"enabled":"disabled")+"§r."+(on?" Enjoy the skies!":"")));
                if(!arg.isEmpty()&&!arg.equalsIgnoreCase(playerRef.getUsername()))
                    playerRef.sendMessage(Message.raw("[Fly] Note: to toggle another player's flight, they must run /fly themselves."));
            }
        };
    }
}
