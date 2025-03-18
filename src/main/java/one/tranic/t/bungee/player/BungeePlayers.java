package one.tranic.t.bungee.player;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import one.tranic.t.base.player.Player;
import one.tranic.t.bungee.TBungee;
import one.tranic.t.utils.Collections;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Deprecated
public class BungeePlayers {
    public static @NotNull List<Player<?>> getOnlinePlayers() {
        final List<Player<?>> players = Collections.newArrayList();

        for (var p : TBungee.getServer().getPlayers())
            players.add(BungeePlayer.createPlayer(p));
        return players;
    }

    public static @NotNull List<? extends ProxiedPlayer> getPlatformOnlinePlayers() {
        return Collections.newArrayList(TBungee.getServer().getPlayers());
    }

    public static @NotNull List<String> getOnlinePlayersName() {
        final List<String> players = Collections.newArrayList();
        for (var p : TBungee.getServer().getPlayers()) players.add(p.getName());
        return players;
    }
}
