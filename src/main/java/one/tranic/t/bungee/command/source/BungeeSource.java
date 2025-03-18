package one.tranic.t.bungee.command.source;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import one.tranic.t.base.TBase;
import one.tranic.t.base.command.Operator;
import one.tranic.t.base.command.source.CommandSource;
import one.tranic.t.base.message.Message;
import one.tranic.t.bungee.TBungee;
import one.tranic.t.bungee.player.BungeePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * This class represents a source implementation for BungeeCord command senders.
 *
 * @deprecated BungeeCord is considered outdated. It is recommended to use more modern proxies such as Velocity.
 * <p>
 * Developing plugins on modern platforms like Paper and Velocity is easier and provides better support and features.
 */
@Deprecated
public class BungeeSource implements CommandSource<CommandSender, ProxiedPlayer> {
    private final CommandSender commandSender;
    private final String[] args;
    private final BungeePlayer player;

    /**
     * Constructs a new BungeeSource instance.
     *
     * @param commandSender the CommandSender instance
     * @param args          the arguments passed with the command
     * @deprecated BungeeCord support is deprecated; consider migrating to modern platforms like Velocity.
     */
    @Deprecated
    public BungeeSource(CommandSender commandSender, String[] args) {
        this.commandSender = commandSender;
        this.args = args;
        this.player = commandSender instanceof ProxiedPlayer ? new BungeePlayer(commandSender) : null;
    }

    @Override
    public Operator getOperator() {
        if (player != null)
            return new Operator(player.getUsername(), player.getUniqueId());
        return TBase.console();
    }

    @Override
    public CommandSender getSource() {
        return commandSender;
    }

    @Override
    public boolean isPlayer() {
        return player != null;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public int argSize() {
        return args.length;
    }

    @Override
    public @Nullable Locale getLocale() {
        return player != null ? player.getLocale() : Locale.getDefault();
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        commandSender.sendMessage(Message.toBaseComponent(message));
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        commandSender.sendMessage(Message.toBaseComponent(message));
    }

    @Override
    public void showBossBar(@NotNull BossBar bossBar) {
        if (isPlayer()) TBungee.adventure().player(player.getSourcePlayer()).showBossBar(bossBar);
    }

    @Override
    public void hideBossBar(@NotNull BossBar bossBar) {
        if (isPlayer()) TBungee.adventure().player(player.getSourcePlayer()).hideBossBar(bossBar);
    }

    @Override
    public void clearBossBars() {
        // Unsupported
    }

    @Override
    public void showTitle(@NotNull Title title) {
        if (isPlayer()) {
            var t = ProxyServer.getInstance().createTitle().title(Message.toBaseComponent(title.title()))
                    .subTitle(Message.toBaseComponent(title.subtitle()));
            player.getSourcePlayer().sendTitle(t);
        }
    }

    @Override
    public void clearTitle() {
        // Unsupported
    }

    @Override
    public BungeePlayer asPlayer() {
        return player;
    }
}
