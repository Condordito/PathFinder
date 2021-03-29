package pathfinder.handler;

import de.bossascrew.core.player.GlobalPlayer;
import lombok.Getter;
import pathfinder.PathPlayer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler {

    @Getter
    private static PlayerHandler instance;

    private Map<Integer, PathPlayer> pathPlayer;

    public PlayerHandler() {
        instance = this;
    }

    public @Nullable
    PathPlayer getPlayer(UUID uuid) {
        PathPlayer pathPlayer = getLoadedPlayer(uuid);

        if(pathPlayer == null) {
            GlobalPlayer player = de.bossascrew.core.player.PlayerHandler.getInstance().getGlobalPlayer(uuid);

            //return null, wenn spieler noch nie auf bossascrew gespielt hat und nicht über UUID findbar
            if(player == null) return null;
            pathPlayer = createPlayer(player.getDatabaseId());
        }
        return pathPlayer;
    }

    public PathPlayer getPlayer(int globalPlayerId) {
        PathPlayer pathPlayer = getLoadedPlayer(globalPlayerId);
        if(pathPlayer == null) {
            //player wurde nicht aus der Datenbank geladen, also neuen anlegen
            pathPlayer = createPlayer(globalPlayerId);
        }
        return pathPlayer;
    }

    private @Nullable
    PathPlayer getLoadedPlayer(UUID uuid) {
        for(PathPlayer pathPlayer : pathPlayer.values()) {
            if(pathPlayer.getUuid().equals(uuid)) {
                return pathPlayer;
            }
        }
        return null;
    }

    private @Nullable
    PathPlayer getLoadedPlayer(int globalPlayerId) {
        for(PathPlayer pathPlayer : pathPlayer.values()) {
            if(pathPlayer.getGlobalPlayerId() == globalPlayerId)
                return pathPlayer;
        }
        return null;
    }

    private PathPlayer createPlayer(int globalPlayerId) {
        return new PathPlayer(globalPlayerId);
    }
}
