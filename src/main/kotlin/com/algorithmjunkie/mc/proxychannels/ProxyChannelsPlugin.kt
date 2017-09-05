package com.algorithmjunkie.mc.proxychannels

import co.aikar.commands.BungeeCommandManager
import com.algorithmjunkie.mc.proxychannels.listener.ChatListener
import com.algorithmjunkie.mc.proxychannels.listener.PlayerListener
import net.md_5.bungee.api.plugin.Plugin

class ProxyChannelsPlugin: Plugin() {
    lateinit var commandManager: BungeeCommandManager
    lateinit var channelManager: ChannelManager

    override fun onEnable() {
        commandManager = BungeeCommandManager(this)
        channelManager = ChannelManager(this)
        channelManager.channelsConfig.reload()

        val pluginManager = proxy.pluginManager
        pluginManager.registerListener(this, ChatListener(this))
        pluginManager.registerListener(this, PlayerListener(this))
    }
}