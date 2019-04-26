/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel;

/**
 * 入站事件处理器，处理入站数据以及各种状态变化。由netty底层发起，往应用层传递的事件，比如接收到一个请求的数据，由Netty接收后，传递给上层应用的处理器。
 * <br/>
 * {@link ChannelHandler} which adds callbacks for state changes. This allows the user
 * to hook in to state changes easily.
 */
public interface ChannelInboundHandler extends ChannelHandler {

    /**
     * 当Channel已经注册到它的EventLoop并且能够处理I/O时会被调用
     * <br/>
     * The {@link Channel} of the {@link ChannelHandlerContext} was registered with its {@link EventLoop}
     */
    void channelRegistered(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当Channel从它的EventLoop注销并且无法处理任何I/O时会被调用
     * <br/>
     * The {@link Channel} of the {@link ChannelHandlerContext} was unregistered from its {@link EventLoop}
     */
    void channelUnregistered(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当Channel处于活动状态时被调用；Channel已经连接/绑定并且已经就绪
     * <br/>
     * The {@link Channel} of the {@link ChannelHandlerContext} is now active
     */
    void channelActive(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当Channel离开活动状态并且不再连接它的远程节点时被调用
     * <br/>
     * The {@link Channel} of the {@link ChannelHandlerContext} was registered is now inactive and reached its
     * end of lifetime.
     */
    void channelInactive(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当从Channel读取数据时被调用
     * <br/>
     * Invoked when the current {@link Channel} has read a message from the peer.
     */
    void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;

    /**
     * 当Channel上的一个读操作完成时被调用。所有刻度的字节都已经从Channel中读取之后，将会调用该回调方法；
     * 所以，可能在channelReadComplete()被调用之前看到多次调用channelRead方法
     * <br/>
     * Invoked when the last message read by the current read operation has been consumed by
     * {@link #channelRead(ChannelHandlerContext, Object)}.  If {@link ChannelOption#AUTO_READ} is off, no further
     * attempt to read an inbound data from the current {@link Channel} will be made until
     * {@link ChannelHandlerContext#read()} is called.
     */
    void channelReadComplete(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当{@link ChannelHandlerContext#fireUserEventTriggered(Object)}方法被调用时被调用
     * <br/>
     * Gets called if an user event was triggered.
     */
    void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;

    /**
     * 当Channel的可写状态发生改变时被调用。用户可以确保写操作不会完成得太快（以避免发生{@link OutOfMemoryError}）或者可以在Channel变为再次可写时恢复写入。
     * 可以通过调用{@link Channel#isWritable()}方法来检测Channel的可写性。
     * 与可写性相关的阈值可以通过Channel.config().setWriteBufferHighWaterMark()和Channel.config().setWriteBufferLowWaterMark()方法来设置
     * <br/>
     * Gets called once the writable state of a {@link Channel} changed. You can check the state with
     * {@link Channel#isWritable()}.
     */
    void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception;

    /**
     * Gets called if a {@link Throwable} was thrown.
     */
    @Override
    @SuppressWarnings("deprecation")
    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}
