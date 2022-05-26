package com.example.dokkaebiTCP.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg){
//
//        ByteBuf in = (ByteBuf) msg;
//        ctx.write(msg);
//        log.info(in.toString());
////        <출력 메세지를 서버에 띄우는 예제임>
////        try {
////            //in.toString(netty의 CharsetUtil.US_ASCII) 로 써도 댐.
////            while(in.isReadable()){
////                log.info(String.valueOf(in.readByte()));
////            }
////        }finally {
////            //대신 in.release() 가능
////            ReferenceCountUtil.release(msg);
////        }
//        ctx.flush();
//
//
////        ctx 객체는 다양한 i/o 이벤트를 발동 및 실행할 수 있게 한다.
////        write 함수는 전송지에 적어주는 함수.. release 하지 않는다는 것을 명심.
////        왜냐면, 네티가 연결이 끊길 때 release 하면서 출력해주기 때문임
//        ctx.write(msg); // (1)
////        write 는 메세지를 써주지 않음. 내부에 버퍼되고 flush 가 실행되야 출력됨.
////        뭐 대신 짧게 ctx.writeAndFlush(msg)를 사용할 수도 있음.
//        ctx.flush(); // (2)
//    }

//    아래는 TIME 프로토콜을 실행하는 예제. 32-bit 정수를 포함하는 메세지를 다룸.
//    연결이 성립된 바로 직후의 메세지를 제외한 모든 받은 데이터를 무시하기 위해
//    channelRead 가 아닌 channelActive 함수를 사용.
//    아래 함수는 연결이 성립되고 트래픽을 생성할 준비가 되면 불러지게 됨.
//    @Override
//    public void channelActive(final ChannelHandlerContext ctx){ // (1)
//        //32-bit 정수를 쓰기위해 버퍼 생성, ByteBuf 는 적어도 4바이트가 필요.
//        //Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer
//        final ByteBuf time = ctx.alloc().buffer(4);// (2)
//        time.writeInt((int)(System.currentTimeMillis()/1000L + 2208988800L));
//
////        대체로, 구조적인 메세지를 써야 한다.
////        NIO 버퍼는 일반 버퍼와 다르게... 입력과 출력의 위치가 다를 일이 없어서
////        flip 을 쓰지 않아도 된다는 이야기를 한다.
////
////        또, ChannelHandlerContext 를 쓰거나 flush 할 경우 ChannelFuture 를 반환한다.
////        이는 아직 일어 나지 않은 i/o 동작을 나타내며 netty 가 비동기이기에 끝나지 않은 요청이
////        있을 수 있다는 것을 나타낸다. 예를 들어,
//
////        Channel ch = ...;
////        ch.writeAndFlush(message);
////        ch.close();
////
////        와 같은 코드는 메세지를 보내기도 전에 연결이 닫힐 수 있는 것이다.
////        그러므로, channelFuture 가 완료된 후 write 에 의해 생성된 close 를 호출하는 것으로
////        모든 리스너들에게 동작이 끝났다고 알릴 수 있다. 그치만 close 도 ChannelFuture 를 리턴하지롱.
//        final ChannelFuture f = ctx.writeAndFlush(time);// (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                assert f == future;
//                ctx.close();
//            }
//        });// (4)
////        쓰기 요청이 끝났다는 것은 ChannelFutureListener 를 반환된 ChannelFuture 에 추가하는 것으로
////        알릴 수 있다. 다음 예제는 동작이 끝났을 때 Channel을 닫아 주는
////        익명의 ChannelFutureListener 를 생성하는 예제이다. 혹은 사전 정의된 리스너를 등록해도 된다.
////
////        f.addListener(ChannelFutureListener.CLOSE);
//
////        To test if our time server works as expected, you can use the UNIX rdate command:
////        $ rdate -o <port> -p <host>
//    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ((ByteBuf) msg).release();
    }
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        log.info("active func");
//        ctx.fireChannelActive();
//        if (log.isDebugEnabled()) {
//            log.debug(ctx.channel().remoteAddress() + "");
//        }
//        String remoteAddress = ctx.channel().remoteAddress().toString();
//
//        ctx.writeAndFlush("Your remote address is " + remoteAddress + ".\r\n");
//    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.writeAndFlush("error occurred");
        ctx.close();
    }
}
