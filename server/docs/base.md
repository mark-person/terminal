
# TCP粘包半包
* 分两次读取两个独立的数据包，D1,D2,没有粘包和拆包
* 一次接收两个数据包，D1和D2沾在一起，称TCP沾包
* 第一次读D1和D2部分,第二次读D2剩余,称TCP拆包
* 第一次读D1部分，第二次读D1剩余和D1包。

# 发生原因
* 写入数据的字节大小大于套接字发送缓冲区的大小
* 进行MSS大小的TCP分段
* 以太网的payload大于MTU进行IP分片
如果发送的网络数据包太小，那么好他本身会启用Nagle算法(可配置是不是启用)对较小的数据包进行合并.产生粘包


# 支持哪些心跳类型设置
* readerIdleTime:为读超时时间(即测试端一定时间内末接受到被测试端消息)
* writerIdleTime:为写超时时间(即测试端一定时间内向被测试端发送消息)
* allIdleTime:所有类型的超时时间

# 默认情况起多少线程?何时启动?
默认是CPU处理器数的两倍，bind完之后启动。 

# 发送消息有几种方式？
1. 直接写入Channel中，消息从ChannelPipeline当中尾部开始移动
2. 写入和ChannelHandler绑定的ChnanelHandlerContext中，消息从ChannelPipeline中的下一下ChannelHandler中移动

# 有哪种重要组件?
Channel:网络操作抽象为类，它除了包括基本的I/O操作，如bind、connect、read、write等。
EventLoop:主要是配合Channel处理I/O操作。
ChannelFuture:框架中所有的I/O操作都为异步的，因此需要ChannelFuture的addListener()注册一个ChannelFutureListener监听事件，
当操作执行成功或者失败时，监听就会自动触发返回结果。
ChannelHandler:充当了所有处理入站和出站数据的逻辑容器。ChannelHandler主要用来处理各种事件，这里的事件很广泛，
比如可以是连接、数据接收、异常、数据转换等。




















