import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {
    public void start(String hostName,int port){
        try {
            final ServerBootstrap bootstrap=new ServerBootstrap();
            NioEventLoopGroup eventLoopGroup=new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    /*.childHandler((ChannelInitializer)(socketChannel) -> {
                        ChannelPipeline pipeline=socketChannel.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());
                        pipeline.addLast("encoder",new StringEncoder());
                        pipeline.addLast("handler",new NettyServerHandler());
                    });*/
                    .childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            bootstrap.bind(hostName,port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){}
}
