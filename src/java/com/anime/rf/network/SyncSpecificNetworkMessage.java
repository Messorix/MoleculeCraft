package com.anime.rf.network;

import java.io.IOException;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SyncSpecificNetworkMessage implements IMessage {

	private int networkPosition= -1;
	private NBTTagCompound storage;
	private NBTTagCompound connections;
	
	public SyncSpecificNetworkMessage() {}
	
	public SyncSpecificNetworkMessage (int id, NBTTagCompound storage, NBTTagCompound connections) {
		this.networkPosition = id;
		this.storage = storage;
		this.connections = connections;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		System.out.println("From Bytes");
		networkPosition = ByteBufUtils.readVarInt(buf, 2);
		try {
			storage = readNBTTagCompoundFromByteBuf(buf);
			connections = readNBTTagCompoundFromByteBuf(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		System.out.println("To Bytes");
		ByteBufUtils.writeVarInt(buf, networkPosition, 2);
		ByteBufUtils.writeTag(buf, storage);
		ByteBufUtils.writeTag(buf, connections);
	}
	
	@Nullable
    private NBTTagCompound readNBTTagCompoundFromByteBuf(ByteBuf buf) throws IOException {
        int i = buf.readerIndex();
        byte b0 = buf.readByte();

        if (b0 == 0) {
            return null;
        } else {
            buf.readerIndex(i);

            try {
                return CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(52428800L));
            }
            catch (IOException ioexception) {
                throw new EncoderException(ioexception);
            }
        }
    }
	
	public int getNetworkID() {
		return networkPosition;
	}
	
	public NBTTagCompound getStorage() {
		return storage;
	}
	
	public NBTTagCompound getConnections() {
		return connections;
	}
	
}
