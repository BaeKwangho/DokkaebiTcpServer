package com.example.dokkaebiTCP.server;

import com.example.dokkaebiTCP.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DokkaebiChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel){
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(serverHandler);
    }
}
